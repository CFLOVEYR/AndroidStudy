package com.beyondself.jalen.studyingandroid.activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.beyondself.jalen.studyingandroid.R;
import com.beyondself.jalen.studyingandroid.domain.UserInfo;

import cn.bmob.v3.BmobUser;

public class UserInfoActivity extends AppCompatActivity {


private TextView tv_user_name;
    private TextView tv_user_age;
    private TextView tv_user_sex;
    private TextView tv_user_description;
    private TextView tv_user_email;
    private TextView tv_user_pnumber;
    private ToggleButton tb_user_email_bd;
    private ToggleButton tb_user_pnumber_bd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        initView();
        initData();
    }


    private void initView() {
        tv_user_name = (TextView) findViewById(R.id.tv_user_name);
        tv_user_age = (TextView) findViewById(R.id.tv_user_age);
        tv_user_sex = (TextView) findViewById(R.id.tv_user_sex);
        tv_user_description = (TextView) findViewById(R.id.tv_user_description);
        tv_user_email = (TextView) findViewById(R.id.tv_user_email);
        tv_user_pnumber = (TextView) findViewById(R.id.tv_user_pnumber);
        tb_user_email_bd = (ToggleButton) findViewById(R.id.tb_user_email_bd);
        tb_user_pnumber_bd = (ToggleButton) findViewById(R.id.tb_user_pnumber_bd);
    }

    private void initData() {
        UserInfo user = BmobUser.getCurrentUser(this, UserInfo.class);
        if (user != null) {
            //判断是否为空
            if (user.getAge() != null) {
                tv_user_age.setText(user.getAge() + "");
            }
            //判断是否为空
            if (user.getSex() != null) {
                tv_user_sex.setText(user.getSex());
            }
            //判断是否为空
            if (user.getDecription() != null) {
                tv_user_description.setText(user.getDecription());
            }
            //判断是否为空
            if (user.getUsername() != null) {
                tv_user_name.setText(user.getUsername());
            }
            //判断是否为空
            if (user.getEmail() != null) {
                tv_user_email.setText(user.getEmail());
            }
            //判断是否为空
            if (user.getMobilePhoneNumber() != null) {
                tv_user_pnumber.setText(user.getMobilePhoneNumber());
            }
            //判断是否绑定手机
            if (user.getMobilePhoneNumberVerified()) {
                tb_user_pnumber_bd.setChecked(true);
            } else {
                tb_user_pnumber_bd.setChecked(false);
            }
            //判断是否绑定邮箱
            if (user.getEmailVerified() != null) {
                if (user.getEmailVerified()) {
                    tb_user_email_bd.setChecked(true);
                } else {
                    tb_user_email_bd.setChecked(false);
                }
            }

        }
    }

    public void jump_update_Userinfo(View view) {
        startActivity(new Intent(UserInfoActivity.this, UpdateUserInfoActivity.class));
        finish();
    }
}
