package com.beyondself.jalen.studyingandroid.activity.study;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.beyondself.jalen.studyingandroid.R;
import com.beyondself.jalen.studyingandroid.activity.main.BaseActivity;
import com.beyondself.jalen.studyingandroid.dao.BookDao;
import com.beyondself.jalen.studyingandroid.dao.InterViewDao;
import com.beyondself.jalen.studyingandroid.domain.InterView;

import java.util.List;

import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.bingoogolapple.refreshlayout.BGARefreshViewHolder;

public class InterViewActivity extends BaseActivity implements BGARefreshLayout.BGARefreshLayoutDelegate, AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    private ListView lv_knowledge_show;
    private List<InterView> mData;
    /**
     * 每次从哪一条数据开始取
     */
    private int startIndex = 0;
    /**
     * 一共有多少条数据
     */
    private int pageTotlaSize;
    /**
     * 每次总共取多少条数据
     */
    private int maxCount = 20;
    private BGARefreshLayout mRefreshLayout;
    private KnowledgeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_knowledge);
        /**
         * 总共有多少条数据
         */
        pageTotlaSize = BookDao.queryCount();
        initView();
        initData();
        setListener();
    }

    @Override
    protected void initView() {
        setTitle("面试题题库");
        lv_knowledge_show = (ListView) findViewById(R.id.lv_knowledge_show);
        lv_knowledge_show.setOnItemClickListener(this);
        lv_knowledge_show.setOnItemLongClickListener(this);
        mRefreshLayout = (BGARefreshLayout) findViewById(R.id.rl_modulename_refresh);
        // 为BGARefreshLayout设置代理
        mRefreshLayout.setDelegate(this);
        // 设置下拉刷新和上拉加载更多的风格     参数1：应用程序上下文，参数2：是否具有上拉加载更多功能
        BGARefreshViewHolder refreshViewHolder = new BGANormalRefreshViewHolder(this, true);
        // 设置下拉刷新和上拉加载更多的风格
        mRefreshLayout.setRefreshViewHolder(refreshViewHolder);
    }

    @Override
    protected void initData() {
        //异步加载数据
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {
                /**
                 * 分批加载数据放到集合中
                 */
                if (mData == null) {
                    mData = BookDao.queryInterViewData(maxCount, startIndex);
                } else {
                    mData.addAll(BookDao.queryInterViewData(maxCount, startIndex));
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {

                if (adapter == null) {
                    adapter = new KnowledgeAdapter();
                    lv_knowledge_show.setAdapter(adapter);
                } else {
                    adapter.notifyDataSetChanged();
                }
                // 加载完毕后在UI线程结束加载更多
                mRefreshLayout.endLoadingMore();
            }
        }.execute();
    }

    @Override
    protected void setListener() {

    }

    /**
     * 菜单的操作
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.interview, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * 菜单栏的点击事件
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                startActivity(new Intent(this, AddInterViewActivity.class));
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //上拉刷新
    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        showToast("已经是最新数据");
        mRefreshLayout.endRefreshing();
    }

    //下拉加载更多
    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        startIndex += maxCount;
        //判断是否还有更多数据
        if (startIndex >= pageTotlaSize) {
            mRefreshLayout.endLoadingMore();
            showToast("没有更多数据..");
            return false;
        }
        initData();
        refreshLayout.endLoadingMore();
        return true;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(InterViewActivity.this, InterViewShowActivity.class);
        intent.putExtra("data", mData.get(position));
        startActivity(intent);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        showLongClickDialog(position);
        return false;
    }

    private void showLongClickDialog(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(InterViewActivity.this);
        final AlertDialog dialog = builder.create();
        View view = View.inflate(InterViewActivity.this, R.layout.addinterview_longclick_item, null);
        TextView tv_addinterview_top = (TextView) view.findViewById(R.id.tv_addinterview_top);
        TextView tv_addinterview_delete = (TextView) view.findViewById(R.id.tv_addinterview_delete);
        tv_addinterview_top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("正在开发中...");
                dialog.dismiss();
            }
        });
        tv_addinterview_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean delete = InterViewDao.delete(mData.get(position).get_id());
                showToast(delete ? "删除成功" : "删除失败");
                dialog.dismiss();
                adapter.notifyDataSetChanged();
            }
        });
        dialog.setView(view);
        dialog.show();
    }

    class KnowledgeAdapter extends BaseAdapter {
        ViewHolder holder;

        @Override
        public int getCount() {
            return mData.size();
        }

        @Override
        public Object getItem(int position) {
            return mData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = View.inflate(InterViewActivity.this, R.layout.iterview_item, null);
                holder.textView = (TextView) convertView.findViewById(R.id.tv_interview_item);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.textView.setText(mData.get(position).getQuestion());
            return convertView;
        }
    }

    class ViewHolder {
        TextView textView;
    }
}
