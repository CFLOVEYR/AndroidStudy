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
import com.beyondself.jalen.studyingandroid.domain.Studyer;
import com.beyondself.jalen.studyingandroid.domain.UserInfo;
import com.beyondself.jalen.studyingandroid.utils.SharePreUtils;
import com.beyondself.jalen.studyingandroid.utils.StringUtils;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

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
                /**查询是否已经存在相同的数据*/
                boolean exist = InterViewDao.queryByTitle(title);
                if (!exist) {

                    if (!StringUtils.isEmpty(title) && !StringUtils.isEmpty(content)) {
                        //添加到数据库
                        int max = BookDao.queryMaxCount();
                        boolean result = InterViewDao.insert(max + 1, title, content, false, 0);
                        showToast(result ? "添加成功" : "添加失败");
                        //添加到网络
                        final InterView interView = InterViewDao.queryByID(max + 1);
                        interView.save(AddInterViewActivity.this, new SaveListener() {
                            @Override
                            public void onSuccess() {
                                final BmobUser mCurrentUser = BmobUser.getCurrentUser(AddInterViewActivity.this, UserInfo.class);
//                            showToast("插入成功" + interView.toString());
                                /**分享等级的添加,只能添加一次*/
                                boolean leveFlag = SharePreUtils.getsPreBoolean(AddInterViewActivity.this, "LeveFlag", true);
                                if (leveFlag) {
                                    final Studyer stu_add = new Studyer();
                                    stu_add.setUserName(mCurrentUser.getUsername());
                                    stu_add.setScore(0);
                                    stu_add.setScore(0);
                                    stu_add.setProgress(0);
                                    stu_add.save(AddInterViewActivity.this, new SaveListener() {
                                        @Override
                                        public void onSuccess() {
                                            showToast("保存成功" + stu_add.toString());
                                        }

                                        @Override
                                        public void onFailure(int i, String s) {

                                        }
                                    });
                                    SharePreUtils.putPreBoolean(AddInterViewActivity.this, "LeveFlag", false);
                                }

                                final Studyer stu = new Studyer();
                                stu.setUserName(mCurrentUser.getUsername());
                                /**查询相应的分享经验等级*/
                                BmobQuery<Studyer> query = new BmobQuery<>();
                                //查询playerName叫“比目”的数据
                                query.addWhereEqualTo("UserName", mCurrentUser.getUsername());
                                //执行查询方法
                                query.findObjects(AddInterViewActivity.this, new FindListener<Studyer>() {
                                    @Override
                                    public void onSuccess(List<Studyer> object) {
                                        // TODO Auto-generated method stub
                                        showToast("查询成功：共" + object.size() + "条数据。");
                                        stu.setObjectId(object.get(0).getObjectId());
                                        stu.setScore(object.get(0).getScore());
                                        stu.setProgress(object.get(0).getProgress());


                                        /**更新服务器分享经验的等级*/
                                        stu.setScore(stu.getScore() + 10);
                                        stu.update(AddInterViewActivity.this, stu.getObjectId(), new UpdateListener() {
                                            @Override
                                            public void onSuccess() {
                                                showToast("更新成功" + stu.getScore());

                                            }

                                            @Override
                                            public void onFailure(int i, String s) {
                                                showToast("更新失败" + s);
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

                            @Override
                            public void onFailure(int i, String msg) {
                                showToast("插入失败" + msg);
                            }
                        });
                    } else {
                        showToast("输入内容不能为空");
                    }
                } else {
                    showToast("面试题已经存在");
                }
            }
        });

    }


}
