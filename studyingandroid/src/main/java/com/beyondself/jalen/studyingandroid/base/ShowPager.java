package com.beyondself.jalen.studyingandroid.base;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.beyondself.jalen.studyingandroid.R;

/**
 * Created by Jalen on 2015/12/16.
 */
public class ShowPager {
    public Activity mActivity;
    public View mRootView;
    public TextView tv_toppager_number;

    public ShowPager(Activity mActivity) {
        this.mActivity = mActivity;
        mRootView = initViews();
    }

    public View initViews() {
        View view = View.inflate(mActivity, R.layout.showresult_item, null);
        tv_toppager_number = (TextView) view.findViewById(R.id.tv_toppager_number);
        return view;
    }
}
