package com.xiaoqqq.l_time;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.widget.RemoteViews;

import com.xiaoqqq.l_time.utils.DateUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Implementation of App Widget functionality.
 */
public class NewAppWidget extends AppWidgetProvider {

    private AppWidgetManager mAppWidgetManager;
    private Context mContext;
    private int appWidgetId;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(final Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                // Construct the RemoteViews object
                RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.new_app_widget);
                String startTime = "2019-03-23";
                DateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    Date startDate = simpleDateFormat.parse(startTime);
                    Date currentDate = simpleDateFormat.parse(simpleDateFormat.format(new Date()));
                    String widgetString = "恋爱第 " + DateUtils.getDaysByDate(startDate, currentDate) + " 天";
                    views.setTextViewText(R.id.appwidget_text, widgetString);
                    // Instruct the widget manager to update the widget
                    mAppWidgetManager.updateAppWidget(appWidgetId, views);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    private Thread mTimerThread;

    void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                         int appWidgetId) {
        this.mAppWidgetManager = appWidgetManager;
        this.mContext = context;
        this.appWidgetId = appWidgetId;
        mTimerThread = new Thread(new TimerThread());
        mTimerThread.start();

    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    class TimerThread extends Thread {//这里也可用Runnable接口实现

        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(1000);//每隔1s执行一次
                    Message msg = new Message();
                    msg.what = 1;
                    mHandler.sendMessage(msg);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }
    }
}

