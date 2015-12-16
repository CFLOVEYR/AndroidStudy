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
    public LinearLayout ll_answer_study;
    public RelativeLayout rl_study_show;

    public BasePager(Activity mActivity) {
        this.mActivity = mActivity;
        mRootView = initViews();
    }

    public View initViews() {
        View view = View.inflate(mActivity, R.layout.study_item, null);
        tv_toppager_number = (TextView) view.findViewById(R.id.tv_toppager_number);
        ll_answer_study = (LinearLayout) view.findViewById(R.id.ll_answer_study);
        rl_study_show = (RelativeLayout) view.findViewById(R.id.rl_study_show);
        return view;
    }

}
