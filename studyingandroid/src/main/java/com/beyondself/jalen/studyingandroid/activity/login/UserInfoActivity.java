package com.beyondself.jalen.studyingandroid.activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.beyondself.jalen.studyingandroid.R;
import com.beyondself.jalen.studyingandroid.domain.UserInfo;
import com.beyondself.jalen.studyingandroid.view.PullToZoomListView;

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
    PullToZoomListView listView;
    private String[] adapterData;
    private View footview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        initView();
        initData();
    }


    private void initView() {

        listView = (PullToZoomListView)findViewById(R.id.listview);
        listView.setDividerHeight(0);//去掉分割线
        listView.setClickable(false);
        adapterData = new String[] { ""};
        listView.setAdapter(new ArrayAdapter<String>(UserInfoActivity.this,
                android.R.layout.simple_list_item_1, adapterData));
        //头部布局
        listView.getHeaderView().setImageResource(R.mipmap.splash01);
        listView.getHeaderView().setScaleType(ImageView.ScaleType.CENTER_CROP);
        //底部布局
        footview = View.inflate(UserInfoActivity.this, R.layout.activity_user_footview, null);
        listView.addFooterView(footview);
        tv_user_name = (TextView) footview.findViewById(R.id.tv_user_name);
        tv_user_age = (TextView) footview.findViewById(R.id.tv_user_age);
        tv_user_sex = (TextView) footview.findViewById(R.id.tv_user_sex);
        tv_user_description = (TextView) footview.findViewById(R.id.tv_user_description);
        tv_user_email = (TextView) footview.findViewById(R.id.tv_user_email);
        tv_user_pnumber = (TextView) footview.findViewById(R.id.tv_user_pnumber);
        tb_user_email_bd = (ToggleButton) footview.findViewById(R.id.tb_user_email_bd);
        tb_user_pnumber_bd = (ToggleButton) footview.findViewById(R.id.tb_user_pnumber_bd);
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
