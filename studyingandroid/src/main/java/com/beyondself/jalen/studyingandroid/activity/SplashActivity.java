package com.beyondself.jalen.studyingandroid.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Toast;

import com.beyondself.jalen.studyingandroid.R;
import com.beyondself.jalen.studyingandroid.dao.BookDao;
import com.beyondself.jalen.studyingandroid.domain.Studyer;
import com.beyondself.jalen.studyingandroid.domain.TestJava;
import com.beyondself.jalen.studyingandroid.utils.DbUtils;
import com.beyondself.jalen.studyingandroid.utils.LogUtils;

import net.youmi.android.AdManager;
import net.youmi.android.offers.OffersManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import cn.bmob.push.BmobPush;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.listener.SaveListener;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        // 初始化 Bmob SDK
        Bmob.initialize(this, "9c5d53fc6f5d5e2e636ac65327bd029e");
        BmobPush.startWork(this, "9c5d53fc6f5d5e2e636ac65327bd029e");
        /**
         * 初始化有米广告
         *  isTestModel(第三个参数) : 是否开启测试模式，
         *  true 为是，false 为否。（上传有米审核及发布到市场版本，请设置为 false）
         *
         */
        AdManager.getInstance(this).init("2cd6624e59f28e2b", "10b04dddae76e760", true);
        OffersManager.getInstance(this).onAppLaunch();       //积分墙
        //赋值数据库到data中去
        DbUtils.copyDb(this, "book.db");
        //启动渐变动画
        AlphaAnimation alpha = new AlphaAnimation(0, 1);
        alpha.setDuration(2000);
        alpha.setFillAfter(true);
        findViewById(R.id.rl_splash).startAnimation(alpha);
        //动画结束后跳转
        alpha.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }


}

