package com.xiaoqqq.l_time;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.TextView;

import com.xiaoqqq.l_time.utils.DateUtils;
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
public class MainActivity extends AppCompatActivity {

    private RollingTextView mRollingDays;
    private TextView mTvPreDays;
    private TextView mTvDanwei;
    private String[] randomString = {"嘿嘿，恋爱", "我们在一起", "小宝贝坠落爱河的第"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        initView();
        initData();
    }

    private void initData() {
        String startTime = "2019-03-23";
        DateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date startDate = simpleDateFormat.parse(startTime);
            Date currentDate = simpleDateFormat.parse(simpleDateFormat.format(new Date()));
            RollingTextView tvDay = findViewById(R.id.tv_days);
            Random random = new Random();
            int i = random.nextInt(3);
            mTvPreDays.setText(randomString[i]);
            tvDay.setText("0");
            mTvDanwei.setText("天");
            tvDay.setAnimationDuration(3000L);
            tvDay.setCharStrategy(Strategy.SameDirectionAnimation(Direction.SCROLL_UP));
            tvDay.addCharOrder(CharOrder.Number);
            tvDay.setAnimationInterpolator(new AccelerateDecelerateInterpolator());
            tvDay.addAnimatorListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    //finsih
                }
            });
            tvDay.setText(DateUtils.getDaysByDate(startDate, currentDate) + "");
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void initView() {
        mTvPreDays = findViewById(R.id.tv_pre_day);
        mTvDanwei = findViewById(R.id.tv_danwei);
        mRollingDays = findViewById(R.id.tv_days);
    }
}
