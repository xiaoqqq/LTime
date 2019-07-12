package com.xiaoqqq.l_time.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.xiaoqqq.l_time.R;
import com.xiaoqqq.l_time.base.BaseActivity;
import com.xiaoqqq.l_time.base.BaseFragment;
import com.xiaoqqq.l_time.constants.RouterPath;
import com.xiaoqqq.l_time.fragment.HomeFragment;
import com.xiaoqqq.l_time.fragment.SettingFragment;
import com.yy.mobile.rollingtextview.RollingTextView;

import java.util.ArrayList;

/**
 * @author xiaoqqq
 */
@Route(path = RouterPath.mainActivity)
public class MainActivity extends BaseActivity {

    private RollingTextView mRollingDays;
    private TextView mTvPreDays;
    private TextView mTvDanwei;
    private String[] randomString = {"嘿嘿，恋爱", "我们在一起", "小宝贝坠落爱河的第"};

    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    private ArrayList<BaseFragment> fragments = new ArrayList<>();
    private ArrayList<String> tabNames = new ArrayList<>();
    private TextView mTitle;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
//        mTvPreDays = findViewById(R.id.tv_pre_day);
//        mTvDanwei = findViewById(R.id.tv_danwei);
//        mRollingDays = findViewById(R.id.tv_days);
        mTitle = findViewById(R.id.tv_title);
        mTabLayout = findViewById(R.id.main_tab_layout);
        mViewPager = findViewById(R.id.main_view_pager);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void initData() {
        super.initData();
        initPermission();
//        String startTime = "2019-03-23";
//        DateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        try {
//            Date startDate = simpleDateFormat.parse(startTime);
//            Date currentDate = simpleDateFormat.parse(simpleDateFormat.format(new Date()));
//            Random random = new Random();
//            int i = random.nextInt(3);
//            mTvPreDays.setText(randomString[i]);
//            mRollingDays.setText("0");
//            mTvDanwei.setText("天");
//            mRollingDays.setAnimationDuration(2000L);
//            mRollingDays.setCharStrategy(Strategy.SameDirectionAnimation(Direction.SCROLL_UP));
//            mRollingDays.addCharOrder(CharOrder.Number);
//            mRollingDays.setAnimationInterpolator(new AccelerateDecelerateInterpolator());
//            mRollingDays.addAnimatorListener(new AnimatorListenerAdapter() {
//                @Override
//                public void onAnimationEnd(Animator animation) {
//                    //finsih
//                }
//            });
//            mRollingDays.setText(DateUtils.getDaysByDate(startDate, currentDate) + "");
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }

        fragments.add(new HomeFragment());
        fragments.add(new SettingFragment());
        tabNames.add("纪念日");
        tabNames.add("我的");
        mViewPager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                if (null != fragments && fragments.size() > 0) {
                    return fragments.get(position);
                }
                return null;
            }

            @Override
            public int getCount() {
                return fragments.size();
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                super.destroyItem(container, position, object);
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {

                return tabNames.get(position);
            }
        });
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    protected void initListener() {
        super.initListener();
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mTitle.setText(tabNames.get(position));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
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
