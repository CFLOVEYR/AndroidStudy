package com.beyondself.jalen.studyingandroid.activity.login;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.beyondself.jalen.studyingandroid.R;
import com.beyondself.jalen.studyingandroid.domain.UserInfo;

import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.RequestSMSCodeListener;
import cn.bmob.v3.listener.SaveListener;

public class RegisterActivity extends AppCompatActivity {

    private EditText et_register_code;//手机验证码
    private EditText et_register_pnumber;//手机号

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        et_register_pnumber = (EditText) findViewById(R.id.et_register_pnumber);
        et_register_code = (EditText) findViewById(R.id.et_register_code);

    }

    /**
     * 获取验证码
     *
     * @param view
     */
    public void getCode(View view) {
         String number = et_register_pnumber.getText().toString();
        BmobSMS.requestSMSCode(RegisterActivity.this, number, "手机注册", new RequestSMSCodeListener() {
            @Override
            public void done(Integer integer, BmobException e) {
                if (e == null) {//验证码发送成功
                    Log.i("smile", "短信id：" + integer);//用于查询本次短信发送详情
                }
            }
        });
    }

    /**
     * 一键手机注册
     *
     * @param view
     */
    public void phone_register(View view) {
        String number = et_register_pnumber.getText().toString();
         String code = et_register_code.getText().toString();
        final UserInfo user = new UserInfo();
        user.setMobilePhoneNumber(number);//设置手机号码（必填）
//        user.setUsername(xxx);                  //设置用户名，如果没有传用户名，则默认为手机号码
        user.setPassword("123456");                  //设置用户密码123456位默认密码,可以修改
        user.setAge(18);                        //设置额外信息：此处为年龄
        user.setDecription("我是一个大帅哥");
        user.signOrLogin(RegisterActivity.this, code, new SaveListener() {
            @Override
            public void onSuccess() {
                Toast.makeText(RegisterActivity.this, "注册或登录成功", Toast.LENGTH_SHORT).show();

                Log.i("smile", "" + user.getUsername() + "-" + user.getAge() + "-" + user.getObjectId());
            }

            @Override
            public void onFailure(int code, String msg) {
                Toast.makeText(RegisterActivity.this, "错误码：" + code + ",错误原因：" + msg, Toast.LENGTH_SHORT).show();
            }
        });
    }

}
