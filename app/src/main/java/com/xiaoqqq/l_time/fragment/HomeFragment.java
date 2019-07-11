package com.xiaoqqq.l_time.fragment;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.xiaoqqq.l_time.R;
import com.xiaoqqq.l_time.adapter.DaysAdapter;
import com.xiaoqqq.l_time.base.BaseFragment;
import com.xiaoqqq.l_time.bean.DateBean;
import com.xiaoqqq.l_time.bean.LocalImageBean;
import com.xiaoqqq.l_time.db.AppDatabase;
import com.xiaoqqq.l_time.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends BaseFragment implements View.OnClickListener, View.OnLongClickListener {

    private static final int IMAGE_CODE = 10001;

    private RecyclerView mRecyclerView;
    private DaysAdapter mDaysAdapter;
    private ArrayList<DateBean.DataContentBean> datas = new ArrayList<>();
    private FloatingActionButton mAdd;
    private FloatingActionButton mDelete;
    private FloatingActionButton mBackgroudSetting;
    private RelativeLayout mBackground;
    private ImageView mHomeBackgroundImage;
    private TextView mTips;

    private boolean mIsRecycleViewDisplay = true;

    @Override
    protected int getFragmentLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView(View rootView) {
        mRecyclerView = rootView.findViewById(R.id.home_days_recycle_view);
        mBackground = rootView.findViewById(R.id.rl_background);
        mAdd = rootView.findViewById(R.id.fab_add);
        mDelete = rootView.findViewById(R.id.fab_delete);
        mBackgroudSetting = rootView.findViewById(R.id.fab_background_setting);
        mHomeBackgroundImage = rootView.findViewById(R.id.home_iv_custom_image_bg);
        mTips = rootView.findViewById(R.id.home_tv_tips);
    }

    @Override
    public void initData() {
        super.initData();
        switchDesktopDisplay(mIsRecycleViewDisplay);
        List<DateBean.DataContentBean> dataContentBeans = AppDatabase.getInstance().dateDao().queryAllDate();
        mDaysAdapter = new DaysAdapter(getActivity(), (ArrayList<DateBean.DataContentBean>) dataContentBeans);
        mRecyclerView.setAdapter(mDaysAdapter);
        //设置布局管理器 , 将布局设置成纵向
        LinearLayoutManager linerLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(linerLayoutManager);
    }

    private void switchDesktopDisplay(boolean isRecycleViewDisplay) {
        if (isRecycleViewDisplay) {
            mRecyclerView.setVisibility(View.VISIBLE);
            mBackground.setVisibility(View.GONE);
        } else {
            mRecyclerView.setVisibility(View.GONE);
            mBackground.setVisibility(View.VISIBLE);
            checkLocalImage();
        }
    }

    private void checkLocalImage() {
        LocalImageBean localImageBean = AppDatabase.getInstance().localImageDao().queryLocalImage();
        if (null == localImageBean) { // 没有设置过背景图
            mTips.setVisibility(View.VISIBLE);
            mHomeBackgroundImage.setVisibility(View.GONE);
        } else {
            mTips.setVisibility(View.GONE);
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

        mHomeBackgroundImage.setOnLongClickListener(this);
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
                ToastUtils.getInstance().customToast(getActivity(), "新增");
                break;
            case R.id.fab_delete:
                ToastUtils.getInstance().customToast(getActivity(), "删除");
                break;
            case R.id.fab_background_setting:
                mIsRecycleViewDisplay = !mIsRecycleViewDisplay;
                switchDesktopDisplay(mIsRecycleViewDisplay);
                break;
            case R.id.home_tv_tips:
                selectLocalImage2backgroundImage();
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
