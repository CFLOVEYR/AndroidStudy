package com.beyondself.jalen.studyingandroid.activity.study;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.beyondself.jalen.studyingandroid.R;
import com.beyondself.jalen.studyingandroid.activity.main.BaseActivity;
import com.beyondself.jalen.studyingandroid.dao.InterViewDao;
import com.beyondself.jalen.studyingandroid.utils.StringUtils;

public class AddInterViewActivity extends BaseActivity {
    private EditText etAddInterviewTitle;
    private EditText etAddInterviewContent;
    private Button btAddInterview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_inter_view);
        initView();
        initData();
        setListener();
    }

    @Override
    protected void initView() {
        etAddInterviewTitle = (EditText) findViewById(R.id.et_add_interview_title);
        etAddInterviewContent = (EditText) findViewById(R.id.et_add_interview_content);
        btAddInterview = (Button) findViewById(R.id.bt_add_interview);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setListener() {
        super.setListener();
        //设置单击事件
        btAddInterview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = etAddInterviewTitle.getText().toString();
                String content = etAddInterviewContent.getText().toString();
                if (!StringUtils.isEmpty(title) && !StringUtils.isEmpty(content)) {
                    //添加到数据库
                    boolean result = InterViewDao.insert(title, content);
                    showToast(result ? "添加成功" : "添加失败");
                    //添加到网络

                }
            }
        });
//        //设置长点击事件
//        btAddInterview.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                showDialog();
//                return false;
//            }
//        });

    }


}
