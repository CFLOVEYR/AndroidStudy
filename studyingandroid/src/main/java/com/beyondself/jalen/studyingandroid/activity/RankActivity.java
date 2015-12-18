package com.beyondself.jalen.studyingandroid.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.beyondself.jalen.studyingandroid.R;
import com.beyondself.jalen.studyingandroid.domain.Studyer;
import com.beyondself.jalen.studyingandroid.domain.UserInfo;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.FindListener;

public class RankActivity extends AppCompatActivity {

    private List<Studyer> list;
    private ListView lv_rank;
    private MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank);
        setTitle("英雄榜");
        initView();
        initData();
    }

    private void initData() {
        list = new ArrayList<>();
        BmobQuery<Studyer> query = new BmobQuery<Studyer>();
        //按照时间降序
        query.order("-createdAt");
        //执行查询，第一个参数为上下文，第二个参数为查找的回调
        query.findObjects(this, new FindListener<Studyer>() {

            @Override
            public void onSuccess(List<Studyer> losts) {
                if (losts != null) {
                    list = losts;
                }
                /**
                 *  因为异步的问题,需要拿到数据以后,在设置ListView的值
                 */
                if (list != null && list.size() > 0) {
                    adapter = new MyAdapter();
                    lv_rank.setAdapter(adapter);
                }
            }

            @Override
            public void onError(int code, String arg0) {
                Toast.makeText(RankActivity.this, "添加失败", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void initView() {
        lv_rank = (ListView) findViewById(R.id.lv_rank);
    }

    class MyAdapter extends BaseAdapter {

        private ViewHolder holder;

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = View.inflate(RankActivity.this, R.layout.rank_item, null);
                holder.rank_item_name = (TextView) convertView.findViewById(R.id.rank_item_name);
                holder.rank_item_score = (TextView) convertView.findViewById(R.id.rank_item_score);
                holder.rank_item_progress = (TextView) convertView.findViewById(R.id.rank_item_progress);
                convertView.setTag(holder);
            }
            holder = (ViewHolder) convertView.getTag();
            //赋值姓名
            UserInfo user = BmobUser.getCurrentUser(RankActivity.this, UserInfo.class);
            if (user != null) {
                holder.rank_item_name.setText(user.getUsername());
            } else {
                holder.rank_item_name.setText("么么哒");
            }
            //赋值积分
            holder.rank_item_score.setText("积分为 :" + list.get(position).getScore());
            //赋值学习进度
            holder.rank_item_progress.setText("学习进度 :" + list.get(position).getProgress() + "%");
            return convertView;
        }
    }

    class ViewHolder {
        TextView rank_item_name;
        TextView rank_item_score;
        TextView rank_item_progress;
    }

}
