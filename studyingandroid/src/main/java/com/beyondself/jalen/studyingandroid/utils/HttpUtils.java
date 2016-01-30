package com.beyondself.jalen.studyingandroid.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * =========================================================
 * <p>
 * 版权: 个人开发Mr.Jalen  版权所有(c) 2016
 * <p>
 * 作者:Jalen
 * <p>
 * 版本: 1.0
 * <p>
 * <p>
 * 创建日期 : 2016/1/30  14:26
 * <p>
 * <p>
 * 邮箱：Studylifetime@sina.com
 * <p>
 * 描述:
 * <p>
 * <p>
 * 修订历史:
 * <p>
 * =========================================================
 */
public class HttpUtils {
    /**
     * 判断网络是否可用
     */
    public static boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }
}
