package com.xiaoqqq.l_time.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.xiaoqqq.l_time.R;
import com.xiaoqqq.l_time.adapter.DaysAdapter;
import com.xiaoqqq.l_time.base.BaseFragment;
import com.xiaoqqq.l_time.bean.DateBean;
import com.xiaoqqq.l_time.db.AppDatabase;
import com.xiaoqqq.l_time.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends BaseFragment implements View.OnClickListener {

    private RecyclerView mRecyclerView;
    private DaysAdapter mDaysAdapter;
    private ArrayList<DateBean.DataContentBean> datas = new ArrayList<>();
    private FloatingActionButton mAdd;
    private FloatingActionButton mDelete;
    private FloatingActionButton mBackgroudSetting;
    private RelativeLayout mBackground;

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
        }
    }

    @Override
    public void initListener() {
        super.initListener();
        mAdd.setOnClickListener(this);
        mDelete.setOnClickListener(this);
        mBackgroudSetting.setOnClickListener(this);
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
        }
    }
}
