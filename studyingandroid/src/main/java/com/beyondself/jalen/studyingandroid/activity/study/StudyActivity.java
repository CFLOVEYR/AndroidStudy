package com.beyondself.jalen.studyingandroid.activity.study;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.beyondself.jalen.studyingandroid.R;
import com.beyondself.jalen.studyingandroid.base.BasePager;
import com.beyondself.jalen.studyingandroid.dao.BookDao;
import com.beyondself.jalen.studyingandroid.domain.Studyer;
import com.beyondself.jalen.studyingandroid.domain.TestJava;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.listener.SaveListener;

public class StudyActivity extends AppCompatActivity implements View.OnClickListener {


    private ViewPager vp_study_content;
    private Button bt_study_footer_previous;
    private Button bt_study_footer_next;
    private Button bt_study_footer_save;
    private List<BasePager> list;
    private MyAdapter adapter;
    private List<TestJava> testJavaList;
    private boolean flag;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                Bundle b = (Bundle) msg.obj;
                String time = b.getString("result");
                int position = b.getInt("position");
                list.get(position).tv_toppager_time.setText(time);
            }
        }
    };
    private long startTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study);
        initView();
        initData();
        initEvent();
    }

    /**
     * 初始化组件
     */
    private void initView() {
        vp_study_content = (ViewPager) findViewById(R.id.vp_study_content);
        bt_study_footer_previous = (Button) findViewById(R.id.bt_study_footer_previous);
        bt_study_footer_next = (Button) findViewById(R.id.bt_study_footer_next);
        bt_study_footer_save = (Button) findViewById(R.id.bt_study_footer_save);
    }

    /**
     * 初始化数据
     */
    private void initData() {
        list = new ArrayList<>();
        testJavaList = BookDao.getAllData();
        //模拟十条数据
        for (int i = 0; i < testJavaList.size(); i++) {
            /**
             * 记录进入的事件
             */
            startTime = System.currentTimeMillis();
            BasePager pager = new BasePager(this);
            pager.tv_top_question.setText(testJavaList.get(i).getTop_question());
            pager.tv_code_question.setText(testJavaList.get(i).getCode_question());
            pager.tv_bottom_question.setText(testJavaList.get(i).getBottom_question());
            pager.tv_java_selectA.setText(testJavaList.get(i).getSelectA());
            pager.tv_java_selectB.setText(testJavaList.get(i).getSelectB());
            pager.tv_java_selectC.setText(testJavaList.get(i).getSelectC());
            pager.tv_java_selectD.setText(testJavaList.get(i).getSelectD());
            list.add(pager);
        }
        adapter = new MyAdapter();
        vp_study_content.setAdapter(adapter);
        vp_study_content.setCurrentItem(0, false);//设置到第一页
        /**
         *  ViewPager的事件监听
         */
        vp_study_content.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //设置页码
                list.get(position).tv_toppager_number.setText(position + 1 + "/" + list.size());
                //设置时间的内容
                if (position == list.size() - 1) {
                    flag = false;
                } else {
                    flag = true;
                }
                getCurrentTime(position);
                if (position == list.size() - 1) {
                    bt_study_footer_next.setVisibility(View.GONE);
                    bt_study_footer_previous.setVisibility(View.GONE);
                    bt_study_footer_save.setVisibility(View.VISIBLE);
                    bt_study_footer_save.setText("继续挑战,并存档!!");
                    list.get(position).ll_answer_study.setVisibility(View.GONE);
                    list.get(position).rl_study_show.setVisibility(View.VISIBLE);
                } else if (position == 0) {
                    bt_study_footer_next.setVisibility(View.VISIBLE);
                    bt_study_footer_previous.setVisibility(View.GONE);
                    bt_study_footer_save.setVisibility(View.GONE);
                } else {
                    bt_study_footer_next.setVisibility(View.VISIBLE);
                    bt_study_footer_previous.setVisibility(View.VISIBLE);
                    bt_study_footer_save.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /**
     * 返回计时时间
     *
     * @return
     */
    private void getCurrentTime(final int position) {
        new Thread() {
            @Override
            public void run() {
                super.run();
                while (flag) {
                    SystemClock.sleep(1000);
                    long endTime = System.currentTimeMillis();
                    long time = endTime - startTime;
                    String result = new SimpleDateFormat("mm:ss").format(new Date(time));
                    Message message = handler.obtainMessage();
                    message.what = 0;
                    Bundle b = new Bundle();
                    b.putString("result", result);
                    b.putInt("position", position);
                    message.obj = b;
                    handler.sendMessage(message);
                }
            }
        }.start();

    }


    /**
     * 初始化点击事件
     */
    private void initEvent() {
        bt_study_footer_previous.setOnClickListener(this);
        bt_study_footer_next.setOnClickListener(this);
        bt_study_footer_save.setOnClickListener(this);
    }

    /**
     * 按钮的点击事件
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        int currentItem = vp_study_content.getCurrentItem();
        switch (v.getId()) {
            case R.id.bt_study_footer_previous://上一题
                if (currentItem != 0) {
                    vp_study_content.setCurrentItem(--currentItem, false);//去掉动画
                }
                break;
            case R.id.bt_study_footer_next://下一题
                if (currentItem != list.size() - 1) {
                    vp_study_content.setCurrentItem(++currentItem, false);//去掉动画
                }
                break;
            case R.id.bt_study_footer_save://保存数据的方法
                saveInfo("10", "30");
                break;
            default:

                break;
        }
    }

    /**
     * ViewPager的适配器
     */
    class MyAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(list.get(position).mRootView);
            return list.get(position).mRootView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    /**
     * 保存用户信息
     *
     * @param score
     * @param progress
     */
    private void saveInfo(String score, String progress) {
        if (TextUtils.isEmpty(score) || TextUtils.isEmpty(progress)) {
            Toast.makeText(StudyActivity.this, "输入内容不能为空哟!!么么哒", Toast.LENGTH_SHORT).show();
        } else {
            Studyer user = new Studyer();
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
