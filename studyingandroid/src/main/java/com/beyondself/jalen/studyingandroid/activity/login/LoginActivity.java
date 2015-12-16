package com.beyondself.jalen.studyingandroid.activity.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.beyondself.jalen.studyingandroid.R;
import com.beyondself.jalen.studyingandroid.domain.UserInfo;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;

public class LoginActivity extends AppCompatActivity {

    private EditText et_login_pnumber;
    private EditText et_login_psd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        et_login_pnumber = (EditText) findViewById(R.id.et_login_pnumber);
        et_login_psd = (EditText) findViewById(R.id.et_login_psd);
    }

    /**
     * 注册
     *
     * @param view
     */
    public void jump_register(View view) {
        startActivity(new Intent(this, RegisterActivity.class));
    }

    /**
     * 登录
     *
     * @param view
     */
    public void login(View view) {
        String number = et_login_pnumber.getText().toString();
        String psd = et_login_psd.getText().toString();
        UserInfo bu2 = new UserInfo();
        bu2.setUsername(number);
        bu2.setPassword(psd);
        bu2.login(this, new SaveListener() {
            @Override
            public void onSuccess() {
                Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                //结束当前页面
                finish();
            }

            @Override
            public void onFailure(int code, String msg) {
                // TODO Auto-generated method stub
                Toast.makeText(LoginActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 跳转到忘记密码的界面
     * @param view
     */
    public void forgetpsd(View view) {
        startActivity(new Intent(LoginActivity.this, PickUpPSDActivity.class));
        finish();
    }
}
