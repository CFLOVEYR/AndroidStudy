package com.beyondself.jalen.studyingandroid.activity.study;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.beyondself.jalen.studyingandroid.R;
import com.beyondself.jalen.studyingandroid.base.BasePager;
import com.beyondself.jalen.studyingandroid.base.ShowPager;
import com.beyondself.jalen.studyingandroid.domain.Studyer;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.listener.SaveListener;

public class StudyActivity extends AppCompatActivity implements View.OnClickListener {


    private ViewPager vp_study_content;
    private Button bt_study_footer_previous;
    private Button bt_study_footer_next;
    private Button bt_study_footer_save;
    private List<BasePager> list;
    private MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study);
        initView();
        initData();
        initEvent();
    }

    private void initEvent() {
        bt_study_footer_previous.setOnClickListener(this);
        bt_study_footer_next.setOnClickListener(this);

    }

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
        //模拟十条数据
        for (int i = 0; i < 10; i++) {
            list.add(new BasePager(this));
        }
        adapter = new MyAdapter();
        vp_study_content.setAdapter(adapter);
        vp_study_content.setCurrentItem(0,false);//设置到第一页
        /**
         *  ViewPager的事件监听
         */
        vp_study_content.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                list.get(position).tv_toppager_number.setText(position + 1 + "/" + list.size());
//                adapter.notifyDataSetChanged();
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
     * 按钮的点击事件
     * @param v
     */
    @Override
    public void onClick(View v) {
        int currentItem = vp_study_content.getCurrentItem();
        switch (v.getId()) {
            case R.id.bt_study_footer_previous:
                if (currentItem != 0) {
                    vp_study_content.setCurrentItem(--currentItem, false);//去掉动画
                }
                break;
            case R.id.bt_study_footer_next:
                if (currentItem != list.size() - 1) {
                    vp_study_content.setCurrentItem(++currentItem, false);//去掉动画
                }
                break;
            case R.id.bt_study_footer_save:
                saveInfo("10", "30");
                break;
            default:

                break;
        }
    }

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
