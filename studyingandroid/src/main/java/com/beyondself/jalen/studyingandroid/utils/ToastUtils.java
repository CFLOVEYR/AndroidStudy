package com.beyondself.jalen.studyingandroid.utils;

import android.content.Context;
import android.widget.Toast;

import com.beyondself.jalen.studyingandroid.aplication.APP;

/**
 * 弹出框的工具类
 */
public class ToastUtils {
    /**
     * 弹出框的方法
     *
     * @param context
     * @param message
     */
    public static void showToast(Context context, String message, int time) {
        Toast.makeText(context, message, time).show();
        //自定义Toast的样式
//        View layout = View.inflate(context, R.layout.toast_item, null);
//        TextView tv_toast = (TextView) layout.findViewById(R.id.tv_toast);
//        tv_toast.setText(message);
//        Toast toast = new Toast(context);
//        toast.setGravity(Gravity.CENTER, 0, 0);
//        toast.setDuration(time);//设置显示时间
//        toast.setView(layout);
//        toast.show();
    }

    private ToastUtils() {
    }

    public static void show(CharSequence text) {
        if (text.length() < 10) {
            Toast.makeText(APP.getInstance(), text, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(APP.getInstance(), text, Toast.LENGTH_LONG).show();
        }
    }

}
