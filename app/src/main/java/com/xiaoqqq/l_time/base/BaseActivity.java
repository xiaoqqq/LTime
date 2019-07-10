package com.xiaoqqq.l_time.base;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;

import com.xiaoqqq.l_time.utils.StatusBarUtil;

/**
 * @author xiaoqqq
 * @package com.xiaoqqq.l_time.base
 * @date 2019-07-10
 * @describe todo
 */
public abstract class BaseActivity extends AppCompatActivity {

    //是否为沉浸式状态栏  默认true
    public boolean mImmersive = true;
    //状态栏的字体是否为黑色  默认true
    public boolean mDarkMode = true;
    public int defStatusColor = Color.WHITE;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        if (mImmersive) {
            StatusBarUtil.immersive(this, defStatusColor, 1);//1 完全透明
        }
        StatusBarUtil.darkMode(this, mDarkMode);

        initView();
        initData();
        initListener();
    }

    protected abstract int getLayoutId();

    protected abstract void initView();

    protected void initData() {

    }

    protected void initListener(){

    }

}
