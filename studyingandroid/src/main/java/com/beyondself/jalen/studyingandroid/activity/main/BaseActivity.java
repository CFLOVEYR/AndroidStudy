package com.beyondself.jalen.studyingandroid.activity.main;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.beyondself.jalen.studyingandroid.R;
import com.beyondself.jalen.studyingandroid.utils.ToastUtils;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
    }

    //展示数据
    public void showToast(String text) {
        ToastUtils.show(text);
    }
    protected abstract void initView();
    protected abstract void initData();
    protected  void setListener(){

    };
}
