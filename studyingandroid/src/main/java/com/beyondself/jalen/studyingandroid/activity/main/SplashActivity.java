package com.beyondself.jalen.studyingandroid.activity.main;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Toast;

import com.beyondself.jalen.studyingandroid.R;
import com.beyondself.jalen.studyingandroid.dao.InterViewDao;
import com.beyondself.jalen.studyingandroid.domain.InterView;
import com.beyondself.jalen.studyingandroid.utils.LocalDbUtils;
import com.beyondself.jalen.studyingandroid.utils.ToastUtils;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
//        initData();
        //初始化用到的第三方组件
        initSDK();
        //赋值数据库到data中去
        LocalDbUtils.copyDb(this, "book.db");
        //启动渐变动画
        AlphaAnimation alpha = new AlphaAnimation(0, 1);
        alpha.setDuration(2000);
        alpha.setFillAfter(true);
        findViewById(R.id.rl_splash).startAnimation(alpha);
        //动画结束后跳转
        alpha.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    private void initData() {

        //异步加载数据
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {
                //从本地数据库取出所有面试题
                List<InterView> mData_local = InterViewDao.getInterViewData();
                //从服务器取出所有面试题
                List<InterView> mData_server = queryInterViewFromServer();
                //进行对比,如果本地的少于网络的,就会下载多余的放到本地
                if (mData_server.size() > mData_local.size()) {
                    List<InterView> extras = new ArrayList<>();
                    for (int i = mData_local.size(); i < mData_server.size(); i++) {
                        extras.add(mData_server.get(i));
                    }
                    //把新增的数据放到本地数据库
                    mData_local.addAll(extras);
                }

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {

            }
        }.execute();
    }

    //从服务器获得数据
    private List<InterView> queryInterViewFromServer() {
        final List<InterView> list = new ArrayList<>();
        BmobQuery<InterView> query = new BmobQuery<InterView>();
        //        //查询playerName叫“比目”的数据
        //        query.addWhereEqualTo("playerName", "比目");
        //返回50条数据，如果不加上这条语句，默认返回10条数据
        query.setLimit(50);
        //执行查询方法
        query.findObjects(this, new FindListener<InterView>() {
            @Override
            public void onSuccess(List<InterView> object) {
                // TODO Auto-generated method stub
                ToastUtils.showToast(SplashActivity.this, "查询成功：共" + object.size() + "条数据。", Toast.LENGTH_SHORT);
                for (InterView inter : object) {
                    InterView interView = new InterView();
                    int id = inter.getId();
                    String question = inter.getQuestion();
                    String answer = inter.getAnswer();
                    boolean collected = inter.isCollected();
                    String command = inter.getCommand();
                    String remark = inter.getRemark();

                    interView.setId(id);
                    interView.setQuestion(question);
                    interView.setAnswer(answer);
                    interView.setCollected(collected);
                    interView.setCommand(command);
                    interView.setRemark(remark);
                    list.add(interView);
                }

            }

            @Override
            public void onError(int code, String msg) {
                // TODO Auto-generated method stub
                ToastUtils.showToast(SplashActivity.this, "查询失败：" + msg, Toast.LENGTH_SHORT);

            }
        });
        return list;
    }

    private void initSDK() {
        // 初始化 Bmob SDK
        Bmob.initialize(this, "9c5d53fc6f5d5e2e636ac65327bd029e");
        /**
         * 语音识别的权限申请
         */
        SpeechUtility.createUtility(this, SpeechConstant.APPID + "=5673a716");
    }


}

