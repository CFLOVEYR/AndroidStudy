package com.beyondself.jalen.studyingandroid.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.beyondself.jalen.studyingandroid.R;
import com.beyondself.jalen.studyingandroid.activity.login.LoginActivity;
import com.beyondself.jalen.studyingandroid.activity.study.StudyActivity;
import com.beyondself.jalen.studyingandroid.utils.LogUtils;
import com.beyondself.jalen.studyingandroid.utils.ToastUtils;
import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;

import cn.bmob.v3.BmobUser;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button bt_answer_pattern;
    private Button bt_story_pattern;
    private Button bt_rank;
    private Button bt_setting;
    private Button bt_login;
    private RecognizerListener recognizerListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        initView();
        initLitener();
    }


    /**
     * 初始化数据
     */
    private void initData() {

    }

    /**
     * 初始化组件
     */
    private void initView() {
        bt_answer_pattern = (Button) findViewById(R.id.bt_answer_pattern);
        bt_story_pattern = (Button) findViewById(R.id.bt_story_pattern);
        bt_rank = (Button) findViewById(R.id.bt_rank);
        bt_setting = (Button) findViewById(R.id.bt_setting);
        bt_login = (Button) findViewById(R.id.bt_login);
    }

    /**
     * 初始化点击事件
     */
    private void initLitener() {
        bt_answer_pattern.setOnClickListener(this);
        bt_story_pattern.setOnClickListener(this);
        bt_rank.setOnClickListener(this);
        bt_setting.setOnClickListener(this);
        bt_login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_answer_pattern:
                startActivity(new Intent(MainActivity.this, StudyActivity.class));
                break;
            case R.id.bt_story_pattern:
                Toast.makeText(MainActivity.this, "努力开发中,敬请期待...", Toast.LENGTH_SHORT).show();
                Listener();
                break;
            case R.id.bt_rank:
                startActivity(new Intent(MainActivity.this, RankActivity.class));
                break;
            case R.id.bt_setting:
                startActivity(new Intent(MainActivity.this, SettingActivity.class));
                break;
            case R.id.bt_login:
                BmobUser bmobUser = BmobUser.getCurrentUser(this);
                if (bmobUser != null) {
                    // 允许用户使用应用,然后是自己的逻辑
                    ToastUtils.showToast(this, "已经登录了!!么么哒", Toast.LENGTH_LONG);
                } else {
                    //缓存用户对象为空时， 可打开用户登录界面…
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                }

                break;
            default:
                break;
        }
    }

    private void Listener() {
        //1.创建SpeechRecognizer对象，第二个参数：本地听写时传InitListener
        SpeechRecognizer mIat = SpeechRecognizer.createRecognizer(this, null);
        //2.设置听写参数，详见《科大讯飞MSC API手册(Android)》SpeechConstant类
        mIat.setParameter(SpeechConstant.DOMAIN, "iat");
        mIat.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
        mIat.setParameter(SpeechConstant.ACCENT, "mandarin ");
        //3.开始听写
        mIat.startListening(recognizerListener);
        recognizerListener = new RecognizerListener() {
            @Override
            public void onResult(com.iflytek.cloud.RecognizerResult recognizerResult, boolean b) {
                LogUtils.e("Test", recognizerResult.getResultString());
                Toast.makeText(MainActivity.this, "结果为:" + recognizerResult.getResultString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onVolumeChanged(int i, byte[] bytes) {

            }

            @Override
            public void onBeginOfSpeech() {

            }

            @Override
            public void onEndOfSpeech() {

            }


            @Override
            public void onError(SpeechError speechError) {

            }

            @Override
            public void onEvent(int i, int i1, int i2, Bundle bundle) {

            }
        };
    }
}






