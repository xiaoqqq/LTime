package com.xiaoqqq.l_time.utils;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.ActionBar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.xiaoqqq.l_time.R;

public class PopupWindowUtils {

    private static PopupWindowUtils instance = new PopupWindowUtils();

    private PopupWindowUtils() {

    }

    public static PopupWindowUtils getInstance() {
        return instance;
    }

    /**
     * 显示popUpwindow
     *
     * @param activity
     * @param parentLayoutId
     */
    public void showPopupWindow(Activity activity, int parentLayoutId, int viewId) {
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View vPopupWindow = inflater.inflate(R.layout.layout_popup_window, null, false);//引入弹窗布局
        PopupWindow popupWindow = new PopupWindow(vPopupWindow, ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT, true);

        addBackground(activity, popupWindow);

        //引入依附的布局
        View parentView = LayoutInflater.from(activity).inflate(parentLayoutId, null);
        View rootView = parentView.findViewById(viewId);
        //相对于父控件的位置（例如正中央Gravity.CENTER，下方Gravity.BOTTOM等），可以设置偏移或无偏移
        popupWindow.showAtLocation(rootView, Gravity.BOTTOM, 0, 0);

        vPopupWindow.findViewById(R.id.tv_delete_card_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnPopwindowItemClickListener.onPopItemClicked(view);
                popupWindow.dismiss();
            }
        });

        vPopupWindow.findViewById(R.id.tv_edit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnPopwindowItemClickListener.onPopItemClicked(view);
                popupWindow.dismiss();
            }
        });

        vPopupWindow.findViewById(R.id.tv_transfer_other).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnPopwindowItemClickListener.onPopItemClicked(view);
                popupWindow.dismiss();
            }
        });
    }

    private void addBackground(Activity activity, PopupWindow popupWindow) {
        // 设置背景颜色变暗
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = 0.7f;//调节透明度
        activity.getWindow().setAttributes(lp);
        //dismiss时恢复原样
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
                lp.alpha = 1f;
                activity.getWindow().setAttributes(lp);
            }
        });
    }

    private onPopwindowItemClickListener mOnPopwindowItemClickListener;

    public void setOnPopwindowItemClickListener(onPopwindowItemClickListener onPopwindowItemClickListener) {
        mOnPopwindowItemClickListener = onPopwindowItemClickListener;
    }

    public interface onPopwindowItemClickListener {
        void onPopItemClicked(View view);
    }
}
