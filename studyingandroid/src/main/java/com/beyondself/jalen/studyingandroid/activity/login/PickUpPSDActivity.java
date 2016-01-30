package com.beyondself.jalen.studyingandroid.activity.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.beyondself.jalen.studyingandroid.R;
import com.beyondself.jalen.studyingandroid.utils.ToastUtils;

import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.RequestSMSCodeListener;
import cn.bmob.v3.listener.ResetPasswordByCodeListener;

public class PickUpPSDActivity extends AppCompatActivity {
    private EditText et_pickup_code;//手机验证码
    private EditText et_pickup_pnumber;//手机号
    private EditText et_pickup_newpsd;//新密码
    private EditText et_pickup_newpsd_confirm;//新密码确认

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_up_psd);
        initView();
    }

    private void initView() {
        et_pickup_code = (EditText) findViewById(R.id.et_pickup_code);
        et_pickup_pnumber = (EditText) findViewById(R.id.et_pickup_pnumber);
        et_pickup_newpsd = (EditText) findViewById(R.id.et_pickup_newpsd);
        et_pickup_newpsd_confirm = (EditText) findViewById(R.id.et_pickup_newpsd_confirm);
    }

    /**
     * 获取验证码
     *
     * @param view
     */
    public void getCode(View view) {
        String number = et_pickup_pnumber.getText().toString();
        BmobSMS.requestSMSCode(PickUpPSDActivity.this, number, "修改密码", new RequestSMSCodeListener() {
            @Override
            public void done(Integer integer, BmobException e) {
                if (e == null) {//验证码发送成功
                    Log.i("smile", "短信id：" + integer);//用于查询本次短信发送详情
                }
            }
        });
    }

    /**
     * 保存信息的方法
     *
     * @param view
     */
    public void save_phone_pickup(View view) {
        String number = et_pickup_pnumber.getText().toString();
        String code = et_pickup_code.getText().toString();
        String newpsd = et_pickup_newpsd.getText().toString();
        String newpsd_confirm = et_pickup_newpsd_confirm.getText().toString();
        if (TextUtils.isEmpty(number) || TextUtils.isEmpty(newpsd) ||
                TextUtils.isEmpty(code) || TextUtils.isEmpty(newpsd_confirm)) {
            Toast.makeText(PickUpPSDActivity.this, "输入内容不能为空", Toast.LENGTH_SHORT).show();
        } else {
            if (!newpsd_confirm.equals(newpsd)) {
                Toast.makeText(PickUpPSDActivity.this, "密码输入不一致,请再次输入", Toast.LENGTH_SHORT).show();
            }
            BmobUser.resetPasswordBySMSCode(this, code, newpsd, new ResetPasswordByCodeListener() {

                @Override
                public void done(BmobException ex) {
                    if (ex == null) {
                        Log.i("smile", "密码重置成功");
                        ToastUtils.showToast(PickUpPSDActivity.this, "密码重置成功", Toast.LENGTH_SHORT);
                        //跳转到登录界面
                        startActivity(new Intent(PickUpPSDActivity.this, LoginActivity.class));
                        finish();
                    } else {
                        Log.i("smile", "重置失败：code =" + ex.getErrorCode() + ",msg = " + ex.getLocalizedMessage());
                    }
                }
            });
        }
    }
}
