package com.xiaoqqq.l_time.fragment;

import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.tencent.bugly.beta.Beta;
import com.xiaoqqq.l_time.R;
import com.xiaoqqq.l_time.base.BaseFragment;
import com.xiaoqqq.l_time.utils.AppUtil;
import com.xiaoqqq.l_time.utils.ToastUtils;

public class SettingFragment extends BaseFragment implements View.OnClickListener {

    private ConstraintLayout mCheckUpdate;
    private ConstraintLayout mAboutMe;
    private TextView mVersion;

    @Override
    protected int getFragmentLayoutId() {
        return R.layout.fragment_setting;
    }

    @Override
    protected void initView(View rootView) {
        mCheckUpdate = rootView.findViewById(R.id.setting_check_update);
        mAboutMe = rootView.findViewById(R.id.setting_about_me);
        mVersion = rootView.findViewById(R.id.setting_version);
    }

    @Override
    public void initData() {
        super.initData();
        mVersion.setText("版本号：" + AppUtil.getAppVersionName() + "_" + AppUtil.getAppVersionCode());
    }

    @Override
    public void initListener() {
        super.initListener();
        mCheckUpdate.setOnClickListener(this);
        mAboutMe.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.setting_check_update:
                Beta.checkUpgrade(true, false);
                break;
            case R.id.setting_about_me:
                ToastUtils.getInstance().customToast(getActivity(), "关于我们");
                break;
            default:
                break;
        }
    }
}
