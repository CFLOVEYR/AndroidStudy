package com.beyondself.jalen.studyingandroid.activity.login;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.TextureView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.beyondself.jalen.studyingandroid.R;
import com.beyondself.jalen.studyingandroid.domain.UserInfo;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.UpdateListener;

public class UpdateUserInfoActivity extends AppCompatActivity {

    private EditText et_user_name;
    private EditText et_user_age;
    private EditText et_user_sex;
    private EditText et_user_description;
    private EditText et_user_pnumber;
    private EditText et_user_email;
    private ToggleButton tb_user_pnumber_bd_et;
    private ToggleButton tb_user_email_bd_et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user_info);
        initView();
        initData();
    }


    private void initView() {
        et_user_name = (EditText) findViewById(R.id.et_user_name);
        et_user_age = (EditText) findViewById(R.id.et_user_age);
        et_user_sex = (EditText) findViewById(R.id.et_user_sex);
        et_user_description = (EditText) findViewById(R.id.et_user_description);
        et_user_pnumber = (EditText) findViewById(R.id.et_user_pnumber);
        et_user_email = (EditText) findViewById(R.id.et_user_email);
        tb_user_pnumber_bd_et = (ToggleButton) findViewById(R.id.tb_user_pnumber_bd_et);
        tb_user_email_bd_et = (ToggleButton) findViewById(R.id.tb_user_email_bd_et);

    }

    private void initData() {
        //初始化数据
        UserInfo user = BmobUser.getCurrentUser(this, UserInfo.class);
        if (user != null) {
            //判断是否为空
            if (user.getAge() != null) {
                et_user_age.setText(user.getAge() + "");
            }
            //判断是否为空
            if (user.getSex() != null) {
                et_user_sex.setText(user.getSex());
            }
            //判断是否为空
            if (user.getDecription() != null) {
                et_user_description.setText(user.getDecription());
            }
            //判断是否为空
            if (user.getUsername() != null) {
                et_user_name.setText(user.getUsername());
            }
            //判断是否为空
            if (user.getEmail() != null) {
                et_user_email.setText(user.getEmail());
            }
            //判断是否为空
            if (user.getMobilePhoneNumber() != null) {
                et_user_pnumber.setText(user.getMobilePhoneNumber());
            }
            //判断是否绑定手机
            if (user.getMobilePhoneNumberVerified()) {
                tb_user_pnumber_bd_et.setChecked(true);
            } else {
                tb_user_pnumber_bd_et.setChecked(false);
            }
            //判断是否绑定邮箱
            if (user.getEmailVerified() != null) {
                if (user.getEmailVerified()) {
                    tb_user_email_bd_et.setChecked(true);
                } else {
                    tb_user_email_bd_et.setChecked(false);
                }
            }

        }
    }

    /**
     * 更新数据
     *
     * @param view
     */
    public void updateUserInfo(View view) {
        String name = et_user_name.getText().toString();
        String age = et_user_age.getText().toString();
        String sex = et_user_sex.getText().toString();
        String desc = et_user_description.getText().toString();
        String pnumber = et_user_pnumber.getText().toString();
        String email = et_user_email.getText().toString();
        UserInfo newUser = new UserInfo();
        UserInfo oldUser = BmobUser.getCurrentUser(this, UserInfo.class);
        //更细的信息
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(age) || TextUtils.isEmpty(sex) ||
                TextUtils.isEmpty(desc) || TextUtils.isEmpty(pnumber) || TextUtils.isEmpty(email)) {
            Toast.makeText(UpdateUserInfoActivity.this, "输入内容不能为空,请仔细检查", Toast.LENGTH_SHORT).show();
        } else {
            newUser.setUsername(name);
            newUser.setAge(Integer.parseInt(age));
//            if (sex.contains("男")) {
//                newUser.setSex("男");
//            } else if (sex.contains("女")) {
//                newUser.setSex("女");
//            }
            newUser.setSex(sex);
            newUser.setDecription(desc);
            newUser.setMobilePhoneNumber(pnumber);
            newUser.setEmail(email);
            if (tb_user_pnumber_bd_et.isChecked()) {
                newUser.setMobilePhoneNumberVerified(true);
            } else {
                newUser.setMobilePhoneNumberVerified(false);
            }
            if (tb_user_email_bd_et.isChecked()) {
                newUser.setEmailVerified(true);
            } else {
                newUser.setEmailVerified(false);
            }
            newUser.update(this, oldUser.getObjectId(), new UpdateListener() {
                @Override
                public void onSuccess() {
                    Toast.makeText(UpdateUserInfoActivity.this, "更新数据成功", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(int code, String msg) {
                    Toast.makeText(UpdateUserInfoActivity.this, "更新数据失败" + msg, Toast.LENGTH_SHORT).show();
                }
            });
        }


    }
}
