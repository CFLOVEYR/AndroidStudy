package com.beyondself.jalen.studyingandroid.activity.study;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.beyondself.jalen.studyingandroid.R;
import com.beyondself.jalen.studyingandroid.activity.main.BaseActivity;
import com.beyondself.jalen.studyingandroid.dao.CommandDao;
import com.beyondself.jalen.studyingandroid.domain.Command;
import com.beyondself.jalen.studyingandroid.utils.HttpUtils;

import java.util.List;

import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.bingoogolapple.refreshlayout.BGARefreshViewHolder;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

public class CommandShowActivity extends BaseActivity implements BGARefreshLayout.BGARefreshLayoutDelegate {

    private ListView lv_command;
    private List<Command> mData;
    private BGARefreshLayout mRefreshLayout;
    private int id;
    private CommandAdapter adapter;
    //服务器获得评论
    BmobQuery<Command> query = new BmobQuery<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_command_show);
        initView();
        initData();
    }

    @Override
    protected void initView() {
        lv_command = (ListView) findViewById(R.id.lv_command);
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
        Intent intent = getIntent();
        id = intent.getIntExtra("_id", 6);
        //异步加载数据
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {

                if (HttpUtils.isNetworkConnected(CommandShowActivity.this)) {
                    //查询playerName叫“比目”的数据
                    query.addWhereEqualTo("id", id);
                    //设置一次查询多少数据
                    query.setLimit(count);
                    query.order("Zan");
                    //执行查询方法
                    query.findObjects(CommandShowActivity.this, new FindListener<Command>() {
                        @Override
                        public void onSuccess(List<Command> object) {
                            // TODO Auto-generated method stub
                            //赋值给当前的内存
                            if (mData == null) {
                                mData = object;
                            } else {
                                mData.addAll(object);
                            }

                            //保存到数据库
                            for (Command comm : object) {
                                CommandDao.insertCommandToCommand(comm.getId(),
                                        comm.getCommand(), comm.getPic(),
                                        comm.getUserName(), comm.getZan(), comm.getNoZan());
                            }
                            if (curPage == 0) {
                                // 获取最后时间
                                lastTime = mData.get(mData.size() - 1).getCreatedAt();
                            }
                            refreshView();
                        }

                        @Override
                        public void onError(int code, String msg) {
                            // TODO Auto-generated method stub
                            showToast("查询失败：" + msg);
                        }
                    });
                } else {
                    //本地获得评论
                    mData = CommandDao.queryCommands(id);
                }


                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                //刷新界面
                refreshView();
                mRefreshLayout.endLoadingMore();
                mRefreshLayout.endRefreshing();
            }


        }.execute();

    }

    /**
     * 刷新界面的方法
     */
    private void refreshView() {
        if (mData != null) {
            if (adapter == null) {
                adapter = new CommandAdapter();
                lv_command.setAdapter(adapter);
            } else {
                adapter.notifyDataSetChanged();
            }
        } else {
            showToast("数据为空");
        }
    }

    private int curPage = 0;//当前页码
    private String lastTime;//最后刷新的时间
    private final int count = 15;//一次取多少数据

    /**
     * 从网络获得最新数据
     */
    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        curPage = 0;
        query.setSkip(curPage);
        // 当是下拉刷新操作时，将当前页的编号重置为0，并把bankCards清空，重新添加
        mData.clear();
        initData();
    }

    /**
     * 下拉加载更多的逻辑
     *
     * @param refreshLayout
     * @return
     */
    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
//        // 处理时间查询
//        Date date = null;
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        try {
//            date = sdf.parse(lastTime);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        // 只查询小于等于最后一个item发表时间的数据
//        query.addWhereLessThanOrEqualTo("createdAt", new BmobDate(date));
        // 跳过之前页数并去掉重复数据
        query.setSkip((curPage + 1) * count + 1);
        initData();
        curPage++;
        return false;
    }

    /**
     * 评论条目的适配
     */
    class CommandAdapter extends BaseAdapter {
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
                convertView = View.inflate(CommandShowActivity.this, R.layout.command_item, null);
                holder.ivCommanndPic = (ImageView) convertView.findViewById(R.id.iv_commannd_pic);
                holder.tvCommanName = (TextView) convertView.findViewById(R.id.tv_comman_name);
                holder.tvCommandContent = (TextView) convertView.findViewById(R.id.tv_command_content);
                holder.tvNozan = (TextView) convertView.findViewById(R.id.tv_nozan);
                holder.tvZan = (TextView) convertView.findViewById(R.id.tv_zan);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            final Command command = mData.get(position);
            holder.ivCommanndPic.setImageResource(R.mipmap.icon);
            holder.tvCommanName.setText(command.getUserName());
            holder.tvCommandContent.setText(command.getCommand());
            holder.tvNozan.setText(command.getNoZan() + "");
            holder.tvZan.setText(command.getZan() + "");
            holder.tvZan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //赞数+1
                    command.setZan(command.getZan() + 1);
                    command.update(CommandShowActivity.this, command.getObjectId(), new UpdateListener() {

                        @Override
                        public void onSuccess() {
                            // TODO Auto-generated method stub
                            showToast("增加赞更新成功" +(command.getZan()) );
                            //本地数据库的保存也需要更新
                            CommandDao.updateZan(command.getId(), command.getZan());
                            adapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onFailure(int code, String msg) {
                            // TODO Auto-generated method stub
                            Log.i("bmob", "更新失败：" + msg);
                        }
                    });
                }
            });
            holder.tvNozan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //赞数+1
                    command.setNoZan(command.getNoZan()+1);
                    command.update(CommandShowActivity.this, command.getObjectId(), new UpdateListener() {

                        @Override
                        public void onSuccess() {
                            // TODO Auto-generated method stub
                            showToast("减少赞更新成功"+(command.getNoZan() ));
                            //本地数据库的保存也需要更新
                            CommandDao.updateZan(command.getId(),command.getNoZan());
                            adapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onFailure(int code, String msg) {
                            // TODO Auto-generated method stub
                            Log.i("bmob","更新失败："+msg);
                        }
                    });
                }
            });
            return convertView;
        }
    }

    class ViewHolder {
        ImageView ivCommanndPic;
        TextView tvCommanName;
        TextView tvCommandContent;
        TextView tvNozan;
        TextView tvZan;
    }

}
