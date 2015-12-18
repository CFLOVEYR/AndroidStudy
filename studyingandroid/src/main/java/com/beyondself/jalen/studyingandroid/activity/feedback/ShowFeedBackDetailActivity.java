package com.beyondself.jalen.studyingandroid.activity.feedback;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.beyondself.jalen.studyingandroid.R;

public class ShowFeedBackDetailActivity extends AppCompatActivity {

    private TextView tv_feed_title;
    private TextView tv_feed_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_feed_back_detail);
        setTitle("反馈信息详情");
        initView();
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String content = intent.getStringExtra("content");
        tv_feed_title.setText(title);
        tv_feed_content.setText(content);
    }

    private void initView() {
        tv_feed_title = (TextView) findViewById(R.id.tv_feed_title);
        tv_feed_content = (TextView) findViewById(R.id.tv_feed_content);
    }
}
