package com.beyondself.jalen.studyingandroid.aplication;

import android.app.Application;

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
 * 创建日期 : 2016/1/26  19:18
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
public class APP extends Application {

    private static APP sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
    }

    public static APP getInstance() {
        return sInstance;
    }
}
