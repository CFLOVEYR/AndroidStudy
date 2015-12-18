package com.beyondself.jalen.studyingandroid.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.beyondself.jalen.studyingandroid.R;
import com.beyondself.jalen.studyingandroid.activity.feedback.FeedActivity;
import com.beyondself.jalen.studyingandroid.activity.login.LoginActivity;
import com.beyondself.jalen.studyingandroid.activity.login.UpdateUserInfoActivity;
import com.beyondself.jalen.studyingandroid.activity.login.UserInfoActivity;
import com.beyondself.jalen.studyingandroid.domain.UserInfo;
import com.beyondself.jalen.studyingandroid.utils.ToastUtils;

import cn.bmob.v3.BmobUser;
import cn.sharesdk.onekeyshare.OnekeyShare;

public class SettingActivity extends AppCompatActivity {
    private LinearLayout ll_personal_info;
    private LinearLayout ll_personal_collect;
    private LinearLayout ll_personal_remark;
    private Button bt_setting_sign_out1;
    private LinearLayout ll_about_author;
    private LinearLayout ll_share_friend;
    private LinearLayout ll_check_version;
    private LinearLayout ll_feedback;
    private LinearLayout ll_app_recommond;
    private LinearLayout ll_message_setting;
    private LinearLayout ll_photo_setting;
    private LinearLayout ll_auto_setting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initView();
        initListener();
    }

    private void initView() {
        ll_personal_info = (LinearLayout) findViewById(R.id.ll_personal_info);//个人信息
        ll_personal_collect = (LinearLayout) findViewById(R.id.ll_personal_collect);//个人收藏
        ll_personal_remark = (LinearLayout) findViewById(R.id.ll_personal_remark);//个人评论
        ll_auto_setting = (LinearLayout) findViewById(R.id.ll_auto_setting);//自动跟新设置
        ll_photo_setting = (LinearLayout) findViewById(R.id.ll_photo_setting);//图片加载
        ll_message_setting = (LinearLayout) findViewById(R.id.ll_message_setting);//消息提醒设置
        ll_app_recommond = (LinearLayout) findViewById(R.id.ll_app_recommond);//应用推荐
        ll_feedback = (LinearLayout) findViewById(R.id.ll_feedback);//意见反馈
        ll_check_version = (LinearLayout) findViewById(R.id.ll_check_version);//检查版本更新
        ll_share_friend = (LinearLayout) findViewById(R.id.ll_share_friend);//分享给朋友
        ll_about_author = (LinearLayout) findViewById(R.id.ll_about_author);//关于作者
        bt_setting_sign_out1 = (Button) findViewById(R.id.bt_setting_sign_out);//销毁退出
    }

    /**
     * 初始化事件
     */
    private void initListener() {
        /**
         *
         * 不需要判断是否要进入登录界面的模块
         */
        //自动更新设置
        ll_auto_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showToast(SettingActivity.this, "自动更新设置", Toast.LENGTH_SHORT);
            }
        });
        //应用推荐
        ll_app_recommond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showToast(SettingActivity.this, "应用推荐", Toast.LENGTH_SHORT);
            }
        });
        //检查版本更新
        ll_check_version.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showToast(SettingActivity.this, "检查版本更新", Toast.LENGTH_SHORT);
            }
        });
        //关于作者
        ll_about_author.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ToastUtils.showToast(SettingActivity.this, "关于作者", Toast.LENGTH_SHORT);
                startActivity(new Intent(SettingActivity.this, AboutAuthorActivity.class));
            }
        });
        /**
         *  判读是否要进入登录页面的逻辑的模块
         */
        //个人信息
        ll_personal_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BmobUser bmobUser = BmobUser.getCurrentUser(SettingActivity.this);
                if (bmobUser != null) {
                    // 允许用户使用应用,然后是自己的逻辑
                    ToastUtils.showToast(SettingActivity.this, "即将进入个人信息", Toast.LENGTH_SHORT);
                    startActivity(new Intent(SettingActivity.this, UserInfoActivity.class));

                } else {
                    //缓存用户对象为空时， 可打开用户登录界面…
                    startActivity(new Intent(SettingActivity.this, LoginActivity.class));
                }
            }
        });
        //个人评论
        ll_personal_remark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BmobUser bmobUser = BmobUser.getCurrentUser(SettingActivity.this);
                if (bmobUser != null) {
                    // 允许用户使用应用,然后是自己的逻辑
                    ToastUtils.showToast(SettingActivity.this, "即将进入个人评论", Toast.LENGTH_SHORT);
                } else {
                    //缓存用户对象为空时， 可打开用户登录界面…
                    startActivity(new Intent(SettingActivity.this, LoginActivity.class));
                }
            }
        });
        //个人收藏
        ll_personal_collect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BmobUser bmobUser = BmobUser.getCurrentUser(SettingActivity.this);
                if (bmobUser != null) {
                    // 允许用户使用应用,然后是自己的逻辑
                    ToastUtils.showToast(SettingActivity.this, "即将进入个人收藏", Toast.LENGTH_SHORT);
                } else {
                    //缓存用户对象为空时， 可打开用户登录界面…
                    SettingActivity.this.startActivity(new Intent(SettingActivity.this, LoginActivity.class));
                }
            }
        });
        //图片加载设置
        ll_photo_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BmobUser bmobUser = BmobUser.getCurrentUser(SettingActivity.this);
                if (bmobUser != null) {
                    // 允许用户使用应用,然后是自己的逻辑
                    ToastUtils.showToast(SettingActivity.this, "即将进入图片加载设置", Toast.LENGTH_SHORT);
                } else {
                    //缓存用户对象为空时， 可打开用户登录界面…
                    SettingActivity.this.startActivity(new Intent(SettingActivity.this, LoginActivity.class));
                }
            }
        });
        //消息提醒设置
        ll_message_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BmobUser bmobUser = BmobUser.getCurrentUser(SettingActivity.this);
                if (bmobUser != null) {
                    // 允许用户使用应用,然后是自己的逻辑
                    ToastUtils.showToast(SettingActivity.this, "即将进入消息提醒设置", Toast.LENGTH_SHORT);
                } else {
                    //缓存用户对象为空时， 可打开用户登录界面…
                    startActivity(new Intent(SettingActivity.this, LoginActivity.class));
                }
            }
        });
        //反馈意见
        ll_feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BmobUser bmobUser = BmobUser.getCurrentUser(SettingActivity.this);
                if (bmobUser != null) {
                    // 允许用户使用应用,然后是自己的逻辑
                    ToastUtils.showToast(SettingActivity.this, "即将进入反馈意见", Toast.LENGTH_SHORT);
                    startActivity(new Intent(SettingActivity.this, FeedActivity.class));
                } else {
                    //缓存用户对象为空时， 可打开用户登录界面…
                    startActivity(new Intent(SettingActivity.this, LoginActivity.class));
                }
            }
        });
        //分享给朋友
        ll_share_friend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BmobUser bmobUser = BmobUser.getCurrentUser(SettingActivity.this);
                if (bmobUser != null) {
                    // 允许用户使用应用,然后是自己的逻辑
//                    ToastUtils.showToast(SettingActivity.this, "即将进入分享给朋友", Toast.LENGTH_SHORT);
                    /**
                     *  分享功能
                     */
                    OnekeyShare oks = new OnekeyShare();
                    oks.setTitle("轻轻松松学习Android");
                    oks.setText("Android私塾是你最好的选择!!");
                    oks.setTitleUrl("http://studyandroid.bmob.cn/");
                    oks.setImageUrl("http://f1.sharesdk.cn/imgs/2014/05/21/oESpJ78_533x800.jpg");
                    //关闭sso授权
                    oks.disableSSOWhenAuthorize();
                    // 启动分享GUI
                    oks.show(SettingActivity.this);
                } else {
                    //缓存用户对象为空时， 可打开用户登录界面…
                    startActivity(new Intent(SettingActivity.this, LoginActivity.class));

                }
            }
        });
        /**
         * 退出登录,注销的方法
         */
        bt_setting_sign_out1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BmobUser.logOut(SettingActivity.this);   //清除缓存用户对象
                BmobUser currentUser = BmobUser.getCurrentUser(SettingActivity.this);
            }
        });
    }


}
