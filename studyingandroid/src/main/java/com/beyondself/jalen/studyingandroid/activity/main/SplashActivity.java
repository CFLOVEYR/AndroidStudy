package com.beyondself.jalen.studyingandroid.activity.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

import com.beyondself.jalen.studyingandroid.R;
import com.beyondself.jalen.studyingandroid.utils.DbUtils;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;

import cn.bmob.v3.Bmob;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        //初始化用到的第三方组件
        initSDK();
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

    private void initSDK() {
        // 初始化 Bmob SDK
        Bmob.initialize(this, "9c5d53fc6f5d5e2e636ac65327bd029e");
        /**
         * 语音识别的权限申请
         */
        SpeechUtility.createUtility(this, SpeechConstant.APPID + "=5673a716");
    }


}

