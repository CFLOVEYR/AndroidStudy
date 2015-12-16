package com.beyondself.jalen.studyingandroid.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.beyondself.jalen.studyingandroid.R;
import com.beyondself.jalen.studyingandroid.activity.login.LoginActivity;
import com.beyondself.jalen.studyingandroid.activity.login.UpdateUserInfoActivity;
import com.beyondself.jalen.studyingandroid.activity.login.UserInfoActivity;
import com.beyondself.jalen.studyingandroid.domain.UserInfo;
import com.beyondself.jalen.studyingandroid.utils.SharePreUtils;
import com.beyondself.jalen.studyingandroid.utils.ToastUtils;

import cn.bmob.v3.BmobUser;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button bt_answer_pattern;
    private Button bt_story_pattern;
    private Button bt_rank;
    private Button bt_setting;
    private Button bt_login;

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
                    ToastUtils.showToast(this, "已经登录了!!么么哒");
                } else {
                    //缓存用户对象为空时， 可打开用户登录界面…
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                }

                break;
            default:
                break;
        }
    }
}
