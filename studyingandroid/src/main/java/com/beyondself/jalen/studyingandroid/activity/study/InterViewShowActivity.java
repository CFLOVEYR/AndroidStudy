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
import com.beyondself.jalen.studyingandroid.activity.login.LoginActivity;
import com.beyondself.jalen.studyingandroid.activity.main.BaseActivity;
import com.beyondself.jalen.studyingandroid.dao.CollectionDao;
import com.beyondself.jalen.studyingandroid.dao.CommandDao;
import com.beyondself.jalen.studyingandroid.domain.Collection;
import com.beyondself.jalen.studyingandroid.domain.Command;
import com.beyondself.jalen.studyingandroid.domain.InterView;
import com.beyondself.jalen.studyingandroid.domain.UserInfo;
import com.beyondself.jalen.studyingandroid.utils.LogUtils;
import com.beyondself.jalen.studyingandroid.utils.StringUtils;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.DeleteListener;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

public class InterViewShowActivity extends BaseActivity implements View.OnClickListener {
    private TextView tvInterviewShowTitle;
    private TextView tvInterviewShowContent;
    private ImageView ivRemark;
    private ImageView ivCollected;
    private ImageView ivShare;
    private InterView mData;
    private Button bt_remark_write;

    private UserInfo mCurrentUser;

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
        setTitle("面试题详细展示");
        tvInterviewShowTitle = (TextView) findViewById(R.id.tv_interview_show_title);
        tvInterviewShowContent = (TextView) findViewById(R.id.tv_interview_show_content);
        ivRemark = (ImageView) findViewById(R.id.iv_remark_read);
        bt_remark_write = (Button) findViewById(R.id.bt_remark_write);
        ivCollected = (ImageView) findViewById(R.id.iv_collected);
        ivShare = (ImageView) findViewById(R.id.iv_share);

    }

    @Override
    protected void initData() {
        mCurrentUser = BmobUser.getCurrentUser(InterViewShowActivity.this, UserInfo.class);
        Intent intent = getIntent();
        mData = (InterView) intent.getSerializableExtra("data");
        tvInterviewShowTitle.setText(mData.getQuestion());
        tvInterviewShowContent.setText(mData.getAnswer());
        boolean exsit = CollectionDao.queryExsit(mData.getId());
        if (exsit) {
            ivCollected.setImageResource(R.mipmap.star_on);
        } else {
            ivCollected.setImageResource(R.mipmap.star_off);
        }
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
                mCurrentUser = BmobUser.getCurrentUser(InterViewShowActivity.this, UserInfo.class);
                showToast("进入评论阅读");
                Intent intent = new Intent(this, CommandShowActivity.class);
                intent.putExtra("_id", mData.getId());
                startActivity(intent);
                break;
            case R.id.bt_remark_write:
                mCurrentUser = BmobUser.getCurrentUser(InterViewShowActivity.this, UserInfo.class);
                if (mCurrentUser != null) {
                    //评论的的对话框展示
                    showDialogWrite();
                } else {
                    showToast("请先登录");
                    startActivity(new Intent(InterViewShowActivity.this, LoginActivity.class));
                }

                break;
            case R.id.iv_collected:
                mCurrentUser = BmobUser.getCurrentUser(InterViewShowActivity.this, UserInfo.class);
                if (mCurrentUser != null) {
                    //添加到收藏的判断
                    judgeCollected();
                } else {
                    showToast("请先登录");
                    startActivity(new Intent(InterViewShowActivity.this, LoginActivity.class));
                }

                break;
            case R.id.iv_share:
                showToast("分享");
                break;

        }
    }

    /**
     * 判断是否收藏过的逻辑
     */
    private void judgeCollected() {

        int id = mData.getId();
        boolean exsit = CollectionDao.queryExsit(id);
        if (!exsit) {
            final Collection coll = new Collection();
            coll.setId(id);
            coll.setQuestion(mData.getQuestion());
            coll.setAnswer(mData.getAnswer());
            coll.setUserName(mCurrentUser.getUsername());
            coll.setRemark(mData.getRemark());
            /**首先要先添加到网络上*/
            coll.save(InterViewShowActivity.this, new SaveListener() {
                @Override
                public void onSuccess() {
                    LogUtils.e("--添加收藏成功---", coll.toString());
                }

                @Override
                public void onFailure(int i, String s) {
//                    showToast("添加失败" + s);
                }
            });
            /**从网络获取数据,并存入本地数据库*/
            BmobQuery<Collection> query = new BmobQuery<Collection>();
            //查询id的相对应的值
            query.addWhereEqualTo("id", id);
            //执行查询方法
            query.findObjects(InterViewShowActivity.this, new FindListener<Collection>() {
                @Override
                public void onSuccess(List<Collection> object) {
                    // TODO Auto-generated method stub
                    LogUtils.e("--查到数据---", "" + object.get(0).getObjectId());
                    //添加到本地数据库
                    boolean result = CollectionDao.insertCollection(object.get(0).getObjectId(), coll.getId(), coll.getQuestion()
                            , coll.getAnswer(), coll.getRemark(),
                            mCurrentUser.getUsername());
                    if (result) {
                        showToast("收藏成功");
                        ivCollected.setImageResource(R.mipmap.star_on);
                    } else {
                        showToast("收藏失败");
                    }
                }

                @Override
                public void onError(int code, String msg) {
                    // TODO Auto-generated method stub
                    showToast("查询失败：" + msg);
                }
            });

        } else {
            //数据库删除
            boolean delete = CollectionDao.deleteItem(mData.getId());
            if (delete) {
//                showToast("本地数据库删除成功");
                ivCollected.setImageResource(R.mipmap.star_off);
            } else {
//                showToast("本地数据库删除失败");
            }
            /**从网络获取数据,删除服务器的数据*/
            BmobQuery<Collection> query = new BmobQuery<Collection>();
            //查询id的相对应的值
            query.addWhereEqualTo("id", id);
            //执行查询方法
            query.findObjects(InterViewShowActivity.this, new FindListener<Collection>() {
                @Override
                public void onSuccess(List<Collection> object) {
                    // TODO Auto-generated method stub
                    LogUtils.e("--查到数据---", "" + object.get(0).getObjectId());
                    final Collection coll = new Collection();
                    coll.setObjectId(object.get(0).getObjectId());
                    coll.delete(InterViewShowActivity.this, new DeleteListener() {
                        @Override
                        public void onSuccess() {
//                            showToast("服务器删除成功" + coll.toString());
                        }

                        @Override
                        public void onFailure(int i, String s) {
//                            showToast("服务器删除失败" + s);
                        }
                    });
                }

                @Override
                public void onError(int code, String msg) {
                    // TODO Auto-generated method stub
                    showToast("查询失败：" + msg);
                }
            });


        }
    }

    private void showDialogWrite() {
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
                    int id = mData.getId();
//                    //添加自己的评论
//                    boolean result = CommandDao.updateCommandToInterView(id, command);
//                            int pic = user.getPic();

                    //模拟添加数据
                    for (int i = 0; i <50; i++) {

                        //添加到本地服务器
                        String username = mCurrentUser.getUsername();
                        boolean result1 = CommandDao.insertCommandToCommand(id, command, 0, username, 0, 0);
                        if (result1) {
//                            showToast("本地数据库增加评论成功");
                        }

                        //添加到服务器
                        Command server = new Command();
                        server.setId(id);
                        server.setCommand(command);
                        server.setUserName(username);
                        server.setPic(0);
                        server.setZan(i);
                        server.setNoZan(0);
                        server.save(InterViewShowActivity.this, new SaveListener() {
                            @Override
                            public void onSuccess() {
//                                showToast("数据库保存成功");
                            }

                            @Override
                            public void onFailure(int i, String s) {
                                showToast("保存失败" + s);
                            }
                        });
                    }
                    //取消对话框
                    dialog.dismiss();
                } else {
                    showToast("输入内容不为空");
                }
            }
        });

    }
}
