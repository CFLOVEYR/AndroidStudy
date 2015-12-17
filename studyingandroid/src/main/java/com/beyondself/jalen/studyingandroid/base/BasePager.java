package com.beyondself.jalen.studyingandroid.base;

import android.app.Activity;
import android.view.View;
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
    public TextView tv_top_question;
    public TextView tv_code_question;
    public TextView tv_bottom_question;
    public TextView tv_java_selectA;
    public TextView tv_java_selectB;
    public TextView tv_java_selectC;
    public TextView tv_java_selectD;

    public BasePager(Activity mActivity) {
        this.mActivity = mActivity;
        mRootView = initViews();
    }

    public View initViews() {
        View view = View.inflate(mActivity, R.layout.study_item, null);
        tv_toppager_number = (TextView) view.findViewById(R.id.tv_toppager_number);//记录是哪一页
        tv_toppager_time = (TextView) view.findViewById(R.id.tv_toppager_time);//记录用时时间
        tv_top_question = (TextView) view.findViewById(R.id.tv_top_question);//问题描述-上
        tv_code_question = (TextView) view.findViewById(R.id.tv_code_question);//问题描述-代码
        tv_bottom_question = (TextView) view.findViewById(R.id.tv_bottom_question);//问题描述-下
        tv_java_selectA = (TextView) view.findViewById(R.id.tv_java_SelectA);//选项A
        tv_java_selectB = (TextView) view.findViewById(R.id.tv_java_SelectB);//选线B
        tv_java_selectC = (TextView) view.findViewById(R.id.tv_java_SelectC);//选项C
        tv_java_selectD = (TextView) view.findViewById(R.id.tv_java_SelectD);//选项D
        ll_answer_study = (LinearLayout) view.findViewById(R.id.ll_answer_study);//回答模块的形式
        rl_study_show = (RelativeLayout) view.findViewById(R.id.rl_study_show);//展示答题状态
        return view;
    }

}
