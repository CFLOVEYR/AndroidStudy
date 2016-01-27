package com.beyondself.jalen.studyingandroid.activity.study;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.beyondself.jalen.studyingandroid.R;
import com.beyondself.jalen.studyingandroid.activity.main.BaseActivity;
import com.beyondself.jalen.studyingandroid.dao.CommandDao;
import com.beyondself.jalen.studyingandroid.domain.InterView;
import com.beyondself.jalen.studyingandroid.domain.UserInfo;
import com.beyondself.jalen.studyingandroid.utils.StringUtils;

import cn.bmob.v3.BmobUser;

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
                Intent intent = new Intent(this, CommandShowActivity.class);
                intent.putExtra("_id",mData.get_id());
                startActivity(intent);
                break;
            case R.id.bt_remark_write:
                final AlertDialog.Builder builder = new AlertDialog.Builder(InterViewShowActivity.this);
                final AlertDialog dialog = builder.create();
                View view = View.inflate(InterViewShowActivity.this, R.layout.comment_item, null);
                dialog.setView(view);
                Button bt_command = (Button) view.findViewById(R.id.bt_command);
                final EditText et_command = (EditText) view.findViewById(R.id.et_command);
                dialog.show();
                bt_command.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //判断非空
                        String command = et_command.getText().toString();
                        if (!StringUtils.isEmpty(command)) {
                            //提交到服务器,并改变本地数据库,并且重新查询
                            int id = mData.get_id();
                            //添加自己的评论
                            boolean result = CommandDao.updateCommandToInterView(id, command);
                            UserInfo user = BmobUser.getCurrentUser(InterViewShowActivity.this, UserInfo.class);
//                            int pic = user.getPic();
                            String username = user.getUsername();
                            boolean result1 = CommandDao.insertCommandToCommand(id, command, 1, username);
                            if (result1) {
                                showToast("增加评论成功");
                            }

                            dialog.dismiss();
                        } else {
                            showToast("输入内容不为空");
                        }
                    }
                });

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
