package com.xiaoqqq.l_time.activity;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.TextView;

import com.tbruyelle.rxpermissions2.RxPermissions;
import com.xiaoqqq.l_time.R;
import com.xiaoqqq.l_time.base.BaseActivity;
import com.xiaoqqq.l_time.utils.DateUtils;
import com.xiaoqqq.l_time.utils.StatusBarUtil;
import com.yy.mobile.rollingtextview.CharOrder;
import com.yy.mobile.rollingtextview.RollingTextView;
import com.yy.mobile.rollingtextview.strategy.Direction;
import com.yy.mobile.rollingtextview.strategy.Strategy;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * @author xiaoqqq
 */
public class MainActivity extends BaseActivity {

    private RollingTextView mRollingDays;
    private TextView mTvPreDays;
    private TextView mTvDanwei;
    private String[] randomString = {"嘿嘿，恋爱", "我们在一起", "小宝贝坠落爱河的第"};

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        mTvPreDays = findViewById(R.id.tv_pre_day);
        mTvDanwei = findViewById(R.id.tv_danwei);
        mRollingDays = findViewById(R.id.tv_days);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void initData() {
        super.initData();
        initPermission();
        String startTime = "2019-03-23";
        DateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date startDate = simpleDateFormat.parse(startTime);
            Date currentDate = simpleDateFormat.parse(simpleDateFormat.format(new Date()));
            Random random = new Random();
            int i = random.nextInt(3);
            mTvPreDays.setText(randomString[i]);
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
        }
    }

    @SuppressLint("CheckResult")
    private void initPermission() {
        final RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions
                .request(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_PHONE_STATE)
                .subscribe(granted -> {
                    if (granted) {

                    } else {
                        finish();
                    }
                });
    }

}
