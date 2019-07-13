package com.xiaoqqq.l_time.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;

import com.xiaoqqq.l_time.R;
import com.xiaoqqq.l_time.bean.DateBean;
import com.xiaoqqq.l_time.db.AppDatabase;
import com.xiaoqqq.l_time.utils.DateUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Implementation of App Widget functionality.
 */
public class DesktopWidget extends AppWidgetProvider {

    void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.new_app_widget);
        DateBean.DataContentBean dataContentBean = AppDatabase.getInstance().dateDao().queryDesktopWord();
        if (null != dataContentBean) {
            String startTime = DateUtils.stampToDate(dataContentBean.getDate_timestamp());
            DateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date startDate = simpleDateFormat.parse(startTime);
                Date currentDate = simpleDateFormat.parse(simpleDateFormat.format(new Date()));
                String desktopWord = dataContentBean.getDesktop_word();
//                int days = DateUtils.getDaysByMillions(Long.valueOf(DateUtils.getDateMillions(startTime)), System.currentTimeMillis());
//                String widgetString = desktopWord + days + " 天";
                String widgetString = desktopWord + " " + +DateUtils.getDaysByDate(startDate, currentDate) + " 天";
                views.setTextViewText(R.id.appwidget_text, widgetString);
                // Instruct the widget manager to update the widget
                appWidgetManager.updateAppWidget(appWidgetId, views);
            } catch (ParseException e) {
                e.printStackTrace();
                Log.e("xiaoqqq", e.getMessage());
            }
        }
    }

    @Override
    public void onReceive(final Context context, final Intent intent) {
        super.onReceive(context, intent);
        String action = intent.getAction();
        if ("android.intent.action.SCREEN_ON".equals(action)) {
            Bundle extras = intent.getExtras();
            if (extras != null) {
                int[] appWidgetIds = extras.getIntArray(AppWidgetManager.EXTRA_APPWIDGET_IDS);
                if (appWidgetIds != null && appWidgetIds.length > 0) {
                    this.onUpdate(context, AppWidgetManager.getInstance(context), appWidgetIds);
                }
            }
        }
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

}

