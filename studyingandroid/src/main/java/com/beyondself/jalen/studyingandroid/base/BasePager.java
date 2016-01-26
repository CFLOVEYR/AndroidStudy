package com.beyondself.jalen.studyingandroid.base;

import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.beyondself.jalen.studyingandroid.R;

/**
 * 题目内容的基类
 */
public class BasePager {
    public Activity mActivity;
    public View mRootView;
    public TextView tv_toppager_number;
    public TextView tv_toppager_time;
    public LinearLayout ll_answer_study;
    public RelativeLayout rl_study_show;
    public TextView tv_top_title;
    public EditText et_study_content;
    public EditText et_study_content1;
    public Button bt_study_putanswer;
    public TextView tv_look_other;

    public BasePager(Activity mActivity) {
        this.mActivity = mActivity;
        mRootView = initViews();
    }

    public View initViews() {
        View view = View.inflate(mActivity, R.layout.study_item, null);
        tv_toppager_number = (TextView) view.findViewById(R.id.tv_toppager_number);//记录是哪一页
        tv_toppager_time = (TextView) view.findViewById(R.id.tv_toppager_time);//记录用时时间
        tv_top_title = (TextView) view.findViewById(R.id.tv_top_title);//问题描述-上
        et_study_content1 = (EditText) view.findViewById(R.id.et_study_content);//问题回答内容
        bt_study_putanswer = (Button) view.findViewById(R.id.bt_study_putanswer);//提交按钮
        tv_look_other = (TextView) view.findViewById(R.id.tv_look_other);//查看其它人回答
        ll_answer_study = (LinearLayout) view.findViewById(R.id.ll_answer_study);//回答模块的形式
        rl_study_show = (RelativeLayout) view.findViewById(R.id.rl_study_show);//展示答题状态
        return view;
    }

}
