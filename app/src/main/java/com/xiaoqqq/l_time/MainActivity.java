package com.xiaoqqq.l_time;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.xiaoqqq.l_time.utils.DateUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author xiaoqqq
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        String startTime = "2019-03-23";
        DateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date startDate = simpleDateFormat.parse(startTime);
            Date currentDate = simpleDateFormat.parse(simpleDateFormat.format(new Date()));
            TextView tvDay = findViewById(R.id.tv_days);
            String widgetString = "恋爱第 " + DateUtils.getDaysByDate(startDate, currentDate) + " 天";
            tvDay.setText(widgetString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
