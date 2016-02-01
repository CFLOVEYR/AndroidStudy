package com.beyondself.jalen.studyingandroid.activity.login;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.beyondself.jalen.studyingandroid.R;
import com.beyondself.jalen.studyingandroid.domain.UserInfo;
import com.beyondself.jalen.studyingandroid.utils.ToastUtils;

import java.util.Timer;
import java.util.TimerTask;

import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.RequestSMSCodeListener;
import cn.bmob.v3.listener.SaveListener;

public class RegisterActivity extends AppCompatActivity {

    private EditText et_register_code;//手机验证码
    private EditText et_register_pnumber;//手机号
    private TextView tv_getSmsCode;
    private int Count = 60;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        et_register_pnumber = (EditText) findViewById(R.id.et_register_pnumber);
        et_register_code = (EditText) findViewById(R.id.et_register_code);
        tv_getSmsCode = (TextView) findViewById(R.id.tv_getSmsCode);

    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    tv_getSmsCode.setText(Count + "后重新发送");
                    tv_getSmsCode.setClickable(false);
                    break;
                case 1:
                    tv_getSmsCode.setText("重新获取验证码");
                    tv_getSmsCode.setClickable(true);
                    break;
                default:

                    break;

            }
        }
    };

    /**
     * 获取验证码
     *
     * @param view
     */
    public void getCode(View view) {
        String number = et_register_pnumber.getText().toString();
        BmobSMS.requestSMSCode(RegisterActivity.this, number, "手机注册", new RequestSMSCodeListener() {
            @Override
            public void done(Integer smsId, BmobException e) {
                if (e == null) {//验证码发送成功
                    Log.i("smile", "短信id：" + smsId);//用于查询本次短信发送详情
                    ToastUtils.showToast(RegisterActivity.this, "短信已发送,请注意查收", Toast.LENGTH_SHORT);
                    tv_getSmsCode.setText("短信已发送");
                    final Timer timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            handler.sendEmptyMessage(0);

                            Count--;
                            if (Count < 0) {
                                timer.cancel();
                                Count = 60;
                                handler.sendEmptyMessage(1);
                            }
                        }
                    }, 0, 1000);
                } else {
                    ToastUtils.showToast(RegisterActivity.this, "短信发送失败", Toast.LENGTH_SHORT);
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
        Toast.makeText(RegisterActivity.this, "点击", Toast.LENGTH_SHORT).show();
        String number = et_register_pnumber.getText().toString();
        String code = et_register_code.getText().toString();
        final UserInfo user = new UserInfo();
        user.setMobilePhoneNumber(number);//设置手机号码（必填）
//        user.setUsername(xxx);                  //设置用户名，如果没有传用户名，则默认为手机号码
        user.setPassword("123456");                  //设置用户密码123456位默认密码,可以修改
        user.setAge(18);                        //设置额外信息：此处为年龄
        user.setDecription("我们都是一名出色的程序员");
        user.signOrLogin(RegisterActivity.this, code, new SaveListener() {
            @Override
            public void onSuccess() {
                Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                finish();
                Log.i("smile", "" + user.getUsername() + "-" + user.getAge() + "-" + user.getObjectId());
            }

            @Override
            public void onFailure(int code, String msg) {
                Toast.makeText(RegisterActivity.this, "错误码：" + code + ",错误原因：" + msg, Toast.LENGTH_SHORT).show();
            }
        });
    }

}
