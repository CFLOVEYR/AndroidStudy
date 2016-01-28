package com.beyondself.jalen.studyingandroid.activity.main;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.beyondself.jalen.studyingandroid.R;
import com.beyondself.jalen.studyingandroid.utils.ToastUtils;

import cn.pedant.SweetAlert.SweetAlertDialog;

public abstract class BaseActivity extends AppCompatActivity {

    private SweetAlertDialog mLoadingDialog;

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

    protected void setListener() {

    }

    //展示数据正在加载中
    public void showLoadingDialog() {
        if (mLoadingDialog == null) {
            mLoadingDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
            mLoadingDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.colorPrimary));
            mLoadingDialog.setCancelable(false);
            mLoadingDialog.setTitleText("数据加载中...");
        }
        mLoadingDialog.show();
    }

    //展示数据加载完毕
    public void dismissLoadingDialog() {
        if (mLoadingDialog != null) {
            mLoadingDialog.dismiss();
        }
    }
}
