package com.beyondself.jalen.studyingandroid.activity.study;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.beyondself.jalen.studyingandroid.R;
import com.beyondself.jalen.studyingandroid.activity.main.BaseActivity;
import com.beyondself.jalen.studyingandroid.dao.BookDao;
import com.beyondself.jalen.studyingandroid.dao.InterViewDao;
import com.beyondself.jalen.studyingandroid.domain.InterView;
import com.beyondself.jalen.studyingandroid.utils.StringUtils;

import java.util.List;

import cn.bmob.v3.listener.SaveListener;

public class AddInterViewActivity extends BaseActivity {
    private EditText etAddInterviewTitle;
    private EditText etAddInterviewContent;
    private Button btAddInterview;
    private List<InterView> mData;

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
                    int max = BookDao.queryMaxCount();
                    boolean result = InterViewDao.insert(max + 1, title, content, false, 0);
                    showToast(result ? "添加成功" : "添加失败");
                    //添加到网络
                    final InterView interView = InterViewDao.queryByID(max+1);
                    interView.save(AddInterViewActivity.this, new SaveListener() {
                        @Override
                        public void onSuccess() {
                            showToast("插入成功" + interView.toString());
                        }

                        @Override
                        public void onFailure(int i, String msg) {
                            showToast("插入失败" + msg);
                        }
                    });
                }
            }
        });

    }


}
