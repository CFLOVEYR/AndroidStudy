package com.beyondself.jalen.studyingandroid.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.beyondself.jalen.studyingandroid.R;
import com.beyondself.jalen.studyingandroid.domain.Studyer;

import cn.bmob.v3.listener.SaveListener;

public class StudyActivity extends AppCompatActivity {

    private EditText et_input_progress;
    private EditText et_input_score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study);
        initView();
    }

    private void initView() {
        et_input_score = (EditText) findViewById(R.id.et_input_score);
        et_input_progress = (EditText) findViewById(R.id.et_input_progress);
    }

    public void saveUserToServer(View view) {
        String score = et_input_score.getText().toString();
        String progress = et_input_progress.getText().toString();
        if (TextUtils.isEmpty(score)||TextUtils.isEmpty(progress)) {
            Toast.makeText(StudyActivity.this, "输入内容不能为空哟!!么么哒", Toast.LENGTH_SHORT).show();
        }else{
            Studyer user=new Studyer();
            user.setScore(Integer.parseInt(score));
            user.setProgress(Integer.parseInt(progress));
            user.save(this, new SaveListener() {
                @Override
                public void onSuccess() {
                    Toast.makeText(StudyActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(int i, String s) {
                    Toast.makeText(StudyActivity.this, "保存失败", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }
}
