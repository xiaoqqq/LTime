package com.xiaoqqq.l_time.fragment;

import android.view.View;
import android.widget.TextView;

import com.xiaoqqq.l_time.R;
import com.xiaoqqq.l_time.base.BaseFragment;

public class SettingFragment extends BaseFragment {

    private TextView mTextView;

    @Override
    protected int getFragmentLayoutId() {
        return R.layout.fragment_setting;
    }

    @Override
    protected void initView(View rootView) {
        mTextView = rootView.findViewById(R.id.setting_tv_message);
    }

    @Override
    public void initData() {
        super.initData();
        mTextView.setText("设置界面");
    }
}
