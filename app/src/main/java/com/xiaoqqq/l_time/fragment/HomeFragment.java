package com.xiaoqqq.l_time.fragment;

import android.view.View;
import android.widget.TextView;

import com.xiaoqqq.l_time.R;
import com.xiaoqqq.l_time.base.BaseFragment;

public class HomeFragment extends BaseFragment {

    private TextView mTextView;

    @Override
    protected int getFragmentLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView(View rootView) {
        mTextView = rootView.findViewById(R.id.home_tv_message);
    }

    @Override
    public void initData() {
        super.initData();
        mTextView.setText("纪念日界面");
    }
}
