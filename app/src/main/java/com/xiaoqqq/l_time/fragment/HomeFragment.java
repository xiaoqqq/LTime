package com.xiaoqqq.l_time.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.xiaoqqq.l_time.R;
import com.xiaoqqq.l_time.adapter.DaysAdapter;
import com.xiaoqqq.l_time.base.BaseFragment;
import com.xiaoqqq.l_time.bean.DaysBean;

import java.util.ArrayList;

public class HomeFragment extends BaseFragment {

    private RecyclerView mRecyclerView;
    private DaysAdapter mDaysAdapter;
    private ArrayList<DaysBean> datas = new ArrayList<>();

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
        DaysBean daysbean = new DaysBean();
        daysbean.setDate_name("恋爱纪念日");
        daysbean.setAuthor("肖某");
        daysbean.setDate_timestamp("2019-03-23");
        daysbean.setDay_number("109");
        datas.add(daysbean);
        mDaysAdapter = new DaysAdapter(getActivity(), datas);
        mRecyclerView.setAdapter(mDaysAdapter);
        //设置布局管理器 , 将布局设置成纵向
        LinearLayoutManager linerLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(linerLayoutManager);
    }
}
