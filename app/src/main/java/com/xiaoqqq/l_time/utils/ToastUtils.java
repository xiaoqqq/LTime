package com.xiaoqqq.l_time.utils;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.xiaoqqq.l_time.R;

/**
 * @author xiaoqqq
 * @package com.xiaoqqq.l_time.utils
 * @date gift-07-11
 * @describe 系统 & 自定义样式的toast工具类
 */
public class ToastUtils {

    private static ToastUtils instance = new ToastUtils();
    private TextView mMessage;

    private ToastUtils() {

    }

    public static ToastUtils getInstance() {
        return instance;
    }


    public void customToast(Context context, String toastMessage) {
        if (null != context) {
            Toast toast = new Toast(context);
            View layout = View.inflate(context, R.layout.layout_toast, null);
            mMessage = layout.findViewById(R.id.toast_tv_message);
            toast.setView(layout);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.setDuration(Toast.LENGTH_SHORT);
            mMessage.setText(toastMessage);
            toast.show();
        }
    }
}
