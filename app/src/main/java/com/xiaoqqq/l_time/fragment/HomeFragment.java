package com.xiaoqqq.l_time.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.xiaoqqq.l_time.R;
import com.xiaoqqq.l_time.adapter.DaysAdapter;
import com.xiaoqqq.l_time.base.BaseFragment;
import com.xiaoqqq.l_time.bean.DateBean;
import com.xiaoqqq.l_time.bean.DestopBean;
import com.xiaoqqq.l_time.bean.LocalImageBean;
import com.xiaoqqq.l_time.constants.RouterPath;
import com.xiaoqqq.l_time.db.AppDatabase;
import com.xiaoqqq.l_time.utils.DateUtils;
import com.xiaoqqq.l_time.utils.PopupWindowUtils;
import com.xiaoqqq.l_time.utils.ToastUtils;
import com.yy.mobile.rollingtextview.CharOrder;
import com.yy.mobile.rollingtextview.RollingTextView;
import com.yy.mobile.rollingtextview.strategy.Direction;
import com.yy.mobile.rollingtextview.strategy.Strategy;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class HomeFragment extends BaseFragment implements View.OnClickListener, View.OnLongClickListener {

    private static final int IMAGE_CODE = 10001;

    private RecyclerView mRecyclerView;
    private DaysAdapter mDaysAdapter;
    private ArrayList<DateBean.DataContentBean> datas = new ArrayList<>();
    private FloatingActionButton mAdd;
    private FloatingActionButton mDelete;
    private FloatingActionButton mBackgroudSetting;
    private RelativeLayout mBackground;
    private RelativeLayout mNoJinianri;
    private ImageView mHomeBackgroundImage;
    private TextView mTips;
    private TextView mTvSetJinianri;

    private RollingTextView mRollingDays;
    private TextView mTvPreDays;
    private TextView mTvDanwei;

    private boolean mIsRecycleViewDisplay = true;
    private List<DateBean.DataContentBean> mdatas = new ArrayList<>();
    private LinearLayout mTvBackgroundText;

    @Override
    protected int getFragmentLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView(View rootView) {
        mRecyclerView = rootView.findViewById(R.id.home_days_recycle_view);
        mBackground = rootView.findViewById(R.id.rl_background);
        mNoJinianri = rootView.findViewById(R.id.rl_no_jinianri);
        mAdd = rootView.findViewById(R.id.fab_add);
        mDelete = rootView.findViewById(R.id.fab_delete);
        mBackgroudSetting = rootView.findViewById(R.id.fab_background_setting);
        mHomeBackgroundImage = rootView.findViewById(R.id.home_iv_custom_image_bg);
        mTips = rootView.findViewById(R.id.home_tv_tips);
        mTvSetJinianri = rootView.findViewById(R.id.home_tv_set_jinianri);
        mTvBackgroundText = rootView.findViewById(R.id.tv_background_text);
        mTvPreDays = rootView.findViewById(R.id.tv_pre_day);
        mTvDanwei = rootView.findViewById(R.id.tv_danwei);
        mRollingDays = rootView.findViewById(R.id.tv_days);

    }

    @Override
    public void initData() {
        super.initData();
        switchDesktopDisplay(mIsRecycleViewDisplay);
        mdatas = AppDatabase.getInstance().dateDao().queryAllDate();
        mDaysAdapter = new DaysAdapter(getActivity(), (ArrayList<DateBean.DataContentBean>) mdatas);
        mRecyclerView.setAdapter(mDaysAdapter);
        //设置布局管理器 , 将布局设置成纵向
        LinearLayoutManager linerLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(linerLayoutManager);

        DateBean.DataContentBean dataContentBean = AppDatabase.getInstance().dateDao().queryDesktopWord();
        if (null != dataContentBean) {
            String startTime = DateUtils.stampToDate(dataContentBean.getDate_timestamp());
            DateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date startDate = simpleDateFormat.parse(startTime);
                Date currentDate = simpleDateFormat.parse(simpleDateFormat.format(new Date()));
                String desktopWord = dataContentBean.getDesktop_word();
                mTvPreDays.setText(desktopWord);
                mRollingDays.setText("0");
                mTvDanwei.setText("天");
                mRollingDays.setAnimationDuration(2000L);
                mRollingDays.setCharStrategy(Strategy.SameDirectionAnimation(Direction.SCROLL_UP));
                mRollingDays.addCharOrder(CharOrder.Number);
                mRollingDays.setAnimationInterpolator(new AccelerateDecelerateInterpolator());
                mRollingDays.addAnimatorListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        //finsih
                    }
                });
                mRollingDays.setText(DateUtils.getDaysByDate(startDate, currentDate) + "");
            } catch (ParseException e) {
                e.printStackTrace();
                Log.e("xiaoqqq", e.getMessage());
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        DestopBean destopBean = AppDatabase.getInstance().desktopShowDao().queryDesktop();
        if (destopBean == null || destopBean.getShow_desktop() == 0) {
            checkHaveJinianri();
        }
    }

    private void switchDesktopDisplay(boolean isRecycleViewDisplay) {
        DestopBean destopBean = AppDatabase.getInstance().desktopShowDao().queryDesktop();
        if (destopBean == null) {
            checkHaveJinianri();
        } else {
            int show_desktop = destopBean.getShow_desktop();
            switch (show_desktop) {
                case 0:
                    checkHaveJinianri();
                    break;
                case 1:
                    mRecyclerView.setVisibility(View.GONE);
                    mNoJinianri.setVisibility(View.GONE);
                    mBackground.setVisibility(View.VISIBLE);
                    checkLocalImage();
                    break;
            }
        }
    }

    /**
     * 检查本地是否保存过纪念日
     */
    private void checkHaveJinianri() {
        List<DateBean.DataContentBean> dataContentBeans = AppDatabase.getInstance().dateDao().queryAllDate();
        if (null == dataContentBeans || dataContentBeans.size() == 0) { // 没有设置过纪念日
            mRecyclerView.setVisibility(View.GONE);
            mBackground.setVisibility(View.GONE);
            mTvBackgroundText.setVisibility(View.GONE);
            mNoJinianri.setVisibility(View.VISIBLE);
        } else { // 本地已经设置过纪念日
            mRecyclerView.setVisibility(View.VISIBLE);
            mBackground.setVisibility(View.GONE);
            mTvBackgroundText.setVisibility(View.GONE);
            mNoJinianri.setVisibility(View.GONE);
            mdatas.clear();
            mdatas.addAll(dataContentBeans);
            if (null != mDaysAdapter)
                mDaysAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 检查本地是否保存过背景图
     */
    private void checkLocalImage() {
        LocalImageBean localImageBean = AppDatabase.getInstance().localImageDao().queryLocalImage();
        if (null == localImageBean) { // 没有设置过背景图
            mTips.setVisibility(View.VISIBLE);
            mTvBackgroundText.setVisibility(View.GONE);
            mHomeBackgroundImage.setVisibility(View.GONE);
        } else {
            mTips.setVisibility(View.GONE);
            mTvBackgroundText.setVisibility(View.VISIBLE);
            mHomeBackgroundImage.setVisibility(View.VISIBLE);
            showImage(localImageBean.getImagePath());
        }
    }

    @Override
    public void initListener() {
        super.initListener();
        mAdd.setOnClickListener(this);
        mDelete.setOnClickListener(this);
        mBackgroudSetting.setOnClickListener(this);
        mTips.setOnClickListener(this);
        mTvSetJinianri.setOnClickListener(this);

        mHomeBackgroundImage.setOnLongClickListener(this);

        mDaysAdapter.setOnCardViewLongClickListener(new DaysAdapter.onCardViewLongClickListener() {
            @Override
            public void onCardViewLongClicked(DaysAdapter.ViewHolder holder, int position) {
                PopupWindowUtils.getInstance().showPopupWindow(Objects.requireNonNull(getActivity()),
                        R.layout.recycleview_item, R.id.item_card_view);
                PopupWindowUtils.getInstance().setOnPopwindowItemClickListener(view -> {
                    switch (view.getId()) {
                        case R.id.tv_delete_card_view:
                            AppDatabase.getInstance().dateDao().deleteDateByDateName(mdatas.get(position).getDate_name());
                            checkHaveJinianri();
                            break;
                        case R.id.tv_edit:
                            ARouter.getInstance()
                                    .build(RouterPath.addDateActivity)
                                    .withString("DATA_POSITION", mdatas.get(position).getDate_name())
                                    .navigation();
                            break;
                        case R.id.tv_transfer_other:
                            DateBean.DataContentBean dataContentBean = mdatas.get(position);
                            String date_name = dataContentBean.getDate_name();
                            String date_timestamp = dataContentBean.getDate_timestamp();
                            String desktop_word = dataContentBean.getDesktop_word();
                            String update_timestamp = System.currentTimeMillis() + "";
                            AppDatabase.getInstance().dateDao().updateDateByDatename(date_name, date_name, date_timestamp, update_timestamp, desktop_word);
                            break;
                    }
                });
            }
        });
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(final View v) {
        switch (v.getId()) {
            case R.id.fab_add:
                ARouter.getInstance().build(RouterPath.addDateActivity).navigation();
                break;
            case R.id.fab_delete:
                ToastUtils.getInstance().customToast(getActivity(), "删除");
                break;
            case R.id.fab_background_setting:
                mIsRecycleViewDisplay = !mIsRecycleViewDisplay;
                DestopBean bean = new DestopBean();
                if (mIsRecycleViewDisplay) {
                    bean.setShow_desktop(0);
                } else {
                    bean.setShow_desktop(1);
                }
                AppDatabase.getInstance().desktopShowDao().saveDesktop(bean);
                switchDesktopDisplay(mIsRecycleViewDisplay);
                break;
            case R.id.home_tv_tips:
                selectLocalImage2backgroundImage();
                break;
            case R.id.home_tv_set_jinianri:
                ARouter.getInstance().build(RouterPath.addDateActivity).navigation();
                break;
        }
    }

    @Override
    public boolean onLongClick(View view) {
        switch (view.getId()) {
            case R.id.home_iv_custom_image_bg:
                selectLocalImage2backgroundImage();
                break;
        }
        return true;
    }

    private void selectLocalImage2backgroundImage() {
        //调用相册
        Intent intent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, IMAGE_CODE);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_CODE && resultCode == Activity.RESULT_OK
                && data != null) {
            Uri selectedImage = data.getData();
            String[] filePathColumns = {MediaStore.Images.Media.DATA};
            Cursor c = getActivity().getContentResolver().query(selectedImage, filePathColumns, null, null, null);
            c.moveToFirst();
            int columnIndex = c.getColumnIndex(filePathColumns[0]);
            String imagePath = c.getString(columnIndex);
            // 持久化图片path
            // 先删除所有，再添加  后期改造一下，支持查看历史所有设置的背景图
            AppDatabase.getInstance().localImageDao().deleteAll();
            LocalImageBean localImageBean = new LocalImageBean();
            localImageBean.setImagePath(imagePath);
            localImageBean.setTimeStamp(System.currentTimeMillis() + "");
            AppDatabase.getInstance().localImageDao().saveLocalImage(localImageBean);
            showImage(imagePath);
            c.close();
        }
    }

    //加载图片
    private void showImage(String imaePath) {
        try {
            Bitmap bm = BitmapFactory.decodeFile(imaePath);
            mHomeBackgroundImage.setImageBitmap(bm);
        } catch (Exception exception) {
            mHomeBackgroundImage.setVisibility(View.GONE);
            mTips.setVisibility(View.VISIBLE);
        }

    }


}
