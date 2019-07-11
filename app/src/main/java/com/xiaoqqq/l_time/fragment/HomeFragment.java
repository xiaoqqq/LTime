package com.xiaoqqq.l_time.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.xiaoqqq.l_time.R;
import com.xiaoqqq.l_time.adapter.DaysAdapter;
import com.xiaoqqq.l_time.base.BaseFragment;
import com.xiaoqqq.l_time.bean.DateBean;
import com.xiaoqqq.l_time.db.AppDatabase;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends BaseFragment {

    private RecyclerView mRecyclerView;
    private DaysAdapter mDaysAdapter;
    private ArrayList<DateBean.DataContentBean> datas = new ArrayList<>();

    @Override
    protected int getFragmentLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView(View rootView) {
        mRecyclerView = rootView.findViewById(R.id.home_days_recycle_view);
    }

    @Override
    public void initData() {
        super.initData();
        List<DateBean.DataContentBean> dataContentBeans = AppDatabase.getInstance().dateDao().queryAllDate();
        mDaysAdapter = new DaysAdapter(getActivity(), (ArrayList<DateBean.DataContentBean>) dataContentBeans);
        mRecyclerView.setAdapter(mDaysAdapter);
        //设置布局管理器 , 将布局设置成纵向
        LinearLayoutManager linerLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(linerLayoutManager);
    }
}
