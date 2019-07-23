package com.xiaoqqq.l_time.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.xiaoqqq.l_time.R;
import com.xiaoqqq.l_time.base.BaseActivity;
import com.xiaoqqq.l_time.bean.DateBean;
import com.xiaoqqq.l_time.constants.RouterPath;
import com.xiaoqqq.l_time.db.AppDatabase;
import com.xiaoqqq.l_time.utils.DateUtils;
import com.xiaoqqq.l_time.utils.ToastUtils;

/**
 * @author xiaoqqq
 * @package com.xiaoqqq.l_time.activity
 * @date gift-07-12
 * @describe 添加纪念日 activity
 */
@Route(path = RouterPath.addDateActivity)
public class AddDateActivity extends BaseActivity implements View.OnClickListener {

    private TextView mSave;
    private DatePicker mDatePicker;
    private TextView mSelectDate;
    private TextView mDateName;
    private TextView mDesktopWord;
    private TextView mTitle;
    private String mDate_name;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_date;
    }

    @Override
    protected void initView() {
        mTitle = findViewById(R.id.tv_title);
        mSave = findViewById(R.id.tv_save);
        mDateName = findViewById(R.id.tv_date_name);
        mDesktopWord = findViewById(R.id.tv_desktop_words);
        mSelectDate = findViewById(R.id.tv_select_date);
        mDatePicker = findViewById(R.id.datepick);
    }

    @Override
    protected void initData() {
        super.initData();
        Intent intent = getIntent();
        mDate_name = intent.getStringExtra("DATA_POSITION");
        if (!TextUtils.isEmpty(mDate_name)) {
            mTitle.setText("编辑纪念日");
            DateBean.DataContentBean dataContentBean = AppDatabase.getInstance().dateDao().queryDateByDateName(mDate_name);
            mDateName.setText(dataContentBean.getDate_name());
            mSelectDate.setText(DateUtils.stampToDate(dataContentBean.getDate_timestamp()));
            mDesktopWord.setText(dataContentBean.getDesktop_word());

        } else {
            mTitle.setText("添加纪念日");
            mDateName.setText("");
            mSelectDate.setText("请选择日期");
            mDesktopWord.setText("");
        }
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void initListener() {
        super.initListener();
        mSave.setOnClickListener(this);
        mSelectDate.setOnClickListener(this);
        mDatePicker.setOnDateChangedListener((view, year, monthOfYear, dayOfMonth) -> {
            mSelectDate.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
            mDatePicker.setVisibility(View.GONE);
        });

    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(final View v) {
        switch (v.getId()) {
            case R.id.tv_save:
                saveDate2Db(mDateName.getText().toString().trim(), mSelectDate.getText().toString().trim(), mDesktopWord.getText().toString().trim());
                break;
            case R.id.tv_select_date:
                mDatePicker.setVisibility(View.VISIBLE);
                break;
        }
    }

    private void saveDate2Db(String dateName, String date, String desktopWord) {
        if (TextUtils.isEmpty(dateName)) {
            ToastUtils.getInstance().customToast(this, "纪念日名字不能为空");
            return;
        }
        if (TextUtils.isEmpty(date)) {
            ToastUtils.getInstance().customToast(this, "日期不能为空");
            return;
        }
        if (!TextUtils.isEmpty(mDate_name)) {
            DateBean.DataContentBean dataContentBean = AppDatabase.getInstance().dateDao().queryDateByDateName(mDate_name);
            mDateName.setText(dataContentBean.getDate_name());
            mSelectDate.setText(date);
            mDesktopWord.setText(desktopWord);
            AppDatabase.getInstance().dateDao().updateDateByDatename(dataContentBean.getDate_name(), mDateName.getText().toString().trim()
                    , DateUtils.dateToStamp(mSelectDate.getText().toString()) + "", System.currentTimeMillis() + "", mDesktopWord.getText().toString().trim());
        } else {
            DateBean.DataContentBean bean = new DateBean.DataContentBean();
            bean.setDate_name(dateName);
            bean.setDate_timestamp(DateUtils.getDateMillions(date));
            bean.setDesktop_word(desktopWord);
            AppDatabase.getInstance().dateDao().saveDate(bean);
        }

        finish();
    }
}
