package com.xiaoqqq.l_time.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * <pre>
 *     @author : xiaoqing
 *     e-mail : qing.xiao@getech.cn
 *     time   : 2017/08/02
 *     desc   :
 *     version: 1.0
 * </pre>
 */

public class DisplayUtil {

    private DisplayUtil() {
        throw new UnsupportedOperationException("DisplayUtil cannot be instantiated");
    }

    private static Resources getResources() {
        return AppUtil.getInstance().getApplication().getResources();
    }

    private static DisplayMetrics dm;

    /**
     * 获取 显示信息
     */
    public static DisplayMetrics getDisplayMetrics() {
        if (null == dm) {
            dm = AppUtil.getInstance().getApplication().getResources().getDisplayMetrics();
        }
        return dm;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dp2px(float dpValue) {
        final float scale = getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(float pxValue) {
        final float scale = getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static float px2sp(float pxValue) {
        return (pxValue / getResources().getDisplayMetrics().scaledDensity);
    }

    public static int sp2px(int spValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                spValue, getDisplayMetrics());
    }

    /**
     * 隐藏软键盘
     */
    public static void hideSoftInput(Activity activity) {
        if (activity == null) {
            return;
        }
        InputMethodManager inputManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputManager != null && inputManager.isActive()) {
            View focusView = activity.getCurrentFocus();
            if (focusView != null) {
                inputManager.hideSoftInputFromWindow(focusView.getWindowToken(), 0);
            }
        }
    }

    public static int getStatusBarHeight(Context context) {
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        return context.getResources().getDimensionPixelSize(resourceId);
    }


    /**
     * 判断坐标是否在view上
     *
     * @param x    横坐标
     * @param y    竖坐标
     * @param view 可视对象
     */
    public static boolean pointInView(float x, float y, View view) {
        int[] location = new int[2];
        view.getLocationInWindow(location);
        return x > location[0] && x < location[0] + view.getWidth()
                && y > location[1] && y < location[1] + view.getHeight();
    }
    
}
