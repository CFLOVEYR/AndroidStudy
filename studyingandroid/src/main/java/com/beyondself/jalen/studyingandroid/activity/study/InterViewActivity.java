package com.beyondself.jalen.studyingandroid.activity.study;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
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
import com.beyondself.jalen.studyingandroid.activity.login.LoginActivity;
import com.beyondself.jalen.studyingandroid.activity.main.BaseActivity;
import com.beyondself.jalen.studyingandroid.dao.BookDao;
import com.beyondself.jalen.studyingandroid.dao.InterViewDao;
import com.beyondself.jalen.studyingandroid.domain.InterView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.bingoogolapple.refreshlayout.BGARefreshViewHolder;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.FindListener;

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
        initView();
        //从服务器对数据
        queryInterViewFromServer();
        initData();
        setListener();
    }

    @Override
    protected void initView() {
        //展示加载中
        showLoadingDialog();
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
        /**
         * 总共有多少条数据
         */
        pageTotlaSize = BookDao.queryCount();
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
                //停止展示加载
                dismissLoadingDialog();
                if (adapter == null) {
                    adapter = new KnowledgeAdapter();
                    lv_knowledge_show.setAdapter(adapter);
                } else {
                    adapter.notifyDataSetChanged();
                }
                // 加载完毕后在UI线程结束加载更多
                mRefreshLayout.endLoadingMore();
                mRefreshLayout.endRefreshing();
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
                BmobUser bmobUser = BmobUser.getCurrentUser(this);
                if (bmobUser != null) {
                    // 允许用户使用应用,然后是自己的逻辑
                    startActivity(new Intent(this, AddInterViewActivity.class));
                } else {
                    //缓存用户对象为空时， 可打开用户登录界面…
                    showToast("请您新登录");
                    startActivity(new Intent(this, LoginActivity.class));
                }

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //下拉刷新
    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {

        //异步加载数据
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {
                //与服务器的数据进行对比
                queryInterViewFromServer();
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                //通知从本地在20个从服务器取数据
                initData();
            }
        }.execute();


    }

    //上拉加载更多
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
//                boolean delete = InterViewDao.delete(mData.get(position).getId());
//                showToast(delete ? "删除成功" : "删除失败");
                showToast("不能删除");
                dialog.dismiss();
                adapter.notifyDataSetChanged();
            }
        });
        dialog.setView(view);
        dialog.show();
    }
    //适配器
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

    /**
     * 从服务器获得信息,并更新数据
     *
     * @return
     */
    private List<InterView> queryInterViewFromServer() {
        final List<InterView> list = new ArrayList<>();
        BmobQuery<InterView> query = new BmobQuery<InterView>();
        //        //查询playerName叫“比目”的数据
        //        query.addWhereEqualTo("playerName", "比目");
        //返回50条数据，如果不加上这条语句，默认返回10条数据
        query.setLimit(100);
        //执行查询方法
        query.findObjects(this, new FindListener<InterView>() {
            @Override
            public void onSuccess(List<InterView> object) {
                // TODO Auto-generated method stub
                Log.e("---", "查询成功：共" + object.size() + "条数据。");
                //         showToast("查询成功：共" + object.size() + "条数据。");
                for (InterView inter : object) {
                    InterView interView = new InterView();
                    int id = inter.getId();
                    String question = inter.getQuestion();
                    String answer = inter.getAnswer();
                    Integer code = inter.getUpdateCode();
                    Boolean updated = inter.getUpdated();
                    interView.setId(id);
                    interView.setQuestion(question);
                    interView.setAnswer(answer);
                    interView.setUpdateCode(code);
                    interView.setUpdated(updated);
                    list.add(interView);
//                    Log.e("------", "---------------list的值为" + list.size());
                }
                //对List进行升序排序,让InterView实现Compareable接口即可
                Collections.sort(list);
            }

            @Override
            public void onFinish() {
                super.onFinish();
                /**服务器增加数据后的处理逻辑*/
                List<InterView> mData_local = InterViewDao.getInterViewData();
                //进行数目对比,如果本地的少于网络的,就会下载多余的放到本地
                if (list.size() > mData_local.size()) {
                    List<InterView> extras = new ArrayList<>();
                    for (int i = mData_local.size(); i < list.size(); i++) {
                        extras.add(list.get(i));
                    }
                    //数据库添加
                    for (InterView inter : extras) {
                        boolean insert = InterViewDao.insert(inter.getId(), inter.getQuestion(), inter.getAnswer(), inter.getUpdated(), inter.getUpdateCode());
                        if (insert) {
//                            Log.e("------", "添加数据成功" + inter.getId() + "---" + inter.getQuestion());
                        } else {
//                            Log.e("------", "添加数据失败");
                        }
                    }
                    showToast("添加了" + (list.size() - mData_local.size()) + "道面试题");

                }
                /**服务器删除数据后的处理逻辑*/
                else if (list.size() < mData_local.size()) {
                    showToast("服务器删除了数据");
                    for (int i = list.size(); i < mData_local.size(); i++) {
                        Integer id = mData_local.get(i).getId();
                        boolean result = InterViewDao.delete(id);
                        if (result) {
                            Log.e("---删除成功--", "------------------------------" + mData_local.get(i).toString());
                        }else {
                            Log.e("---删除失败--", "------------------------------" + mData_local.get(i).toString());
                        }
                    }
                    if (mData != null) {
                        startIndex = 0;
                        mData.clear();
                    }
                } else {
                    Log.e("----", "已经是最新数据, mData的数字" + mData_local.size());
                    showToast("已经是最新数据!!");
                }


                /**服务器更新数据后的处理逻辑*/
                List<InterView> mData_local1 = InterViewDao.getInterViewDataByID();
                List<InterView> updates = new ArrayList<>();
                for (int i = 0; i < list.size(); i++) {

                    Boolean updated = list.get(i).getUpdated();

                    Integer id_server = list.get(i).getId();
                    Integer id_local = mData_local1.get(i).getId();
//                    Log.e("----id_server-----", "-----------" + id_server + "**************" + id_local);
                    Integer code_server = list.get(i).getUpdateCode();
                    Integer code_local = mData_local1.get(i).getUpdateCode();
//                    Log.e("----code_server-----", "-----------" + code_server + "**************" + code_local);
                    boolean result = code_server > code_local;
                    //如果服务器确实更新了,而且更新的版本为大于当前版本
                    if (updated && result && id_local == id_server) {
                        updates.add(list.get(i));
                    }
                }
                //更新本地数据库
                if (updates.size() > 0) {
                    if (mData != null) {
                        startIndex = 0;
                        mData.clear();
                    }
                    for (InterView inter : updates) {
                        InterViewDao.updateData(inter);
                        Log.e("------", "------------------------------" + inter.toString());
                    }
                    showToast("更新了" + updates.size() + "道面试题");
                    if (updates != null) {
                        updates.clear();

                    }
                }

                initData();
            }

            @Override
            public void onError(int code, String msg) {
                // TODO Auto-generated method stub
//                Log.e("---", code + "查询失败：" + msg);
            }
        });
        return list;
    }

}
