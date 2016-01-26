package com.beyondself.jalen.studyingandroid.activity.study;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.beyondself.jalen.studyingandroid.R;
import com.beyondself.jalen.studyingandroid.activity.main.BaseActivity;
import com.beyondself.jalen.studyingandroid.domain.InterView;

public class InterViewShowActivity extends BaseActivity implements View.OnClickListener {
    private TextView tvInterviewShowTitle;
    private TextView tvInterviewShowContent;
    private ImageView ivRemark;
    private ImageView ivCollected;
    private ImageView ivShare;
    private InterView mData;
    private Button bt_remark_write;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inter_view_show);
        initView();
        initData();
        setListener();
    }

    @Override
    protected void initView() {
        tvInterviewShowTitle = (TextView) findViewById(R.id.tv_interview_show_title);
        tvInterviewShowContent = (TextView) findViewById(R.id.tv_interview_show_content);
        ivRemark = (ImageView) findViewById(R.id.iv_remark_read);
        bt_remark_write = (Button) findViewById(R.id.bt_remark_write);
        ivCollected = (ImageView) findViewById(R.id.iv_collected);
        ivShare = (ImageView) findViewById(R.id.iv_share);
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        mData = (InterView) intent.getSerializableExtra("data");
        tvInterviewShowTitle.setText(mData.getQuestion());
        tvInterviewShowContent.setText(mData.getAnswer());
    }

    @Override
    protected void setListener() {
        ivRemark.setOnClickListener(this);
        ivCollected.setOnClickListener(this);
        ivShare.setOnClickListener(this);
        bt_remark_write.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_remark_read:
                showToast("进入评论阅读");
                break;
            case R.id.bt_remark_write:
                showToast("评论");
                break;
            case R.id.iv_collected:
                showToast("添加到收藏");
                break;
            case R.id.iv_share:
                showToast("分享");
                break;

        }
    }
}
