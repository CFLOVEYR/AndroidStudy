package com.beyondself.jalen.studyingandroid.activity.main;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

import cn.bingoogolapple.refreshlayout.BGAMoocStyleRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.FindListener;

public class RankActivity extends AppCompatActivity implements BGARefreshLayout.BGARefreshLayoutDelegate {

    private List<Studyer> list;
    private ListView lv_rank;

    private MyAdapter adapter;
    private BGARefreshLayout mRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank);
        setTitle("分享豪杰榜");
        initView();
        initData();

    }

    private void initView() {
        lv_rank = (ListView) findViewById(R.id.lv_rank);
        View header = View.inflate(this, R.layout.rank_header, null);
        lv_rank.addHeaderView(header);
        mRefreshLayout = (BGARefreshLayout) findViewById(R.id.rl_modulename_refresh);
        // 为BGARefreshLayout设置代理
        mRefreshLayout.setDelegate(this);
        // 设置下拉刷新和上拉加载更多的风格     参数1：应用程序上下文，参数2：是否具有上拉加载更多功能
        BGAMoocStyleRefreshViewHolder refreshViewHolder = new BGAMoocStyleRefreshViewHolder(this, true);
//        请调用BGAMoocStyleRefreshViewHolder的setOriginalImage方法设置原始图片资源
//        请调用BGAMoocStyleRefreshViewHolder的setUltimateColor方法设置最终生成图片的填充颜色资源
        refreshViewHolder.setOriginalImage(R.mipmap.custom_mooc_icon);
        refreshViewHolder.setUltimateColor(R.color.red);
        // 设置下拉刷新和上拉加载更多的风格
        mRefreshLayout.setRefreshViewHolder(refreshViewHolder);
    }

    private void initData() {
        if (list == null) {
            list = new ArrayList<>();
        }
        BmobQuery<Studyer> query = new BmobQuery<Studyer>();
        //按照时间降序
        query.order("score");
        //执行查询，第一个参数为上下文，第二个参数为查找的回调
        query.findObjects(RankActivity.this, new FindListener<Studyer>() {

            @Override
            public void onSuccess(List<Studyer> losts) {
                list = losts;
                refreshView();
            }

            @Override
            public void onError(int code, String arg0) {
                Toast.makeText(RankActivity.this, "添加失败", Toast.LENGTH_SHORT).show();
            }
        });

    }

    //刷新界面
    private void refreshView() {
        if (adapter == null) {
            adapter = new MyAdapter();
            lv_rank.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
        mRefreshLayout.endLoadingMore();
        mRefreshLayout.endRefreshing();
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        refreshView();
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {

        return false;
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
            Integer progress = list.get(position).getProgress();
            if (progress < 100) {
                holder.rank_item_progress.setText("阶位: 青铜圣斗士");
            } else if (progress >= 100 && progress < 200) {
                holder.rank_item_progress.setText("阶位: 白银圣斗士");
            } else if (progress >= 200 && progress < 300) {
                holder.rank_item_progress.setText("阶位: 黄金圣斗士");
            } else if (progress >= 400 && progress < 500) {
                holder.rank_item_progress.setText("阶位: 铂金圣斗士");
            } else if (progress >= 500 && progress < 600) {
                holder.rank_item_progress.setText("阶位: 钻石圣斗士");
            }

            return convertView;
        }
    }

    class ViewHolder {
        TextView rank_item_name;
        TextView rank_item_score;
        TextView rank_item_progress;
    }

}
