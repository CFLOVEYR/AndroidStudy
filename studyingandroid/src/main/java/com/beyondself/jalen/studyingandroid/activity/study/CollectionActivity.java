package com.beyondself.jalen.studyingandroid.activity.study;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.beyondself.jalen.studyingandroid.R;
import com.beyondself.jalen.studyingandroid.activity.main.BaseActivity;
import com.beyondself.jalen.studyingandroid.dao.BookDao;
import com.beyondself.jalen.studyingandroid.dao.CollectionDao;
import com.beyondself.jalen.studyingandroid.domain.Collection;
import com.beyondself.jalen.studyingandroid.domain.InterView;
import com.beyondself.jalen.studyingandroid.domain.UserInfo;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.DeleteListener;
import cn.bmob.v3.listener.FindListener;

public class CollectionActivity extends BaseActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    private ListView lv_collection;
    private UserInfo mCurrentUser;
    private List<Collection> mData;
    private CollectionAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);
        initView();
        initData();
    }

    @Override
    protected void initView() {
        lv_collection = (ListView) findViewById(R.id.lv_collection);
    }

    @Override
    protected void initData() {
        mCurrentUser = BmobUser.getCurrentUser(CollectionActivity.this, UserInfo.class);
        //首先从本地取数据
        mData = CollectionDao.queryCommands(mCurrentUser.getUsername());
        if (mData.size() == 0) {
            //从网络取数据
            getDataFromServer();
        } else {
            showToast("开始从本地取数据了");
            refreshView();
        }

    }

    private void getDataFromServer() {
        showToast("开始从网络取数据了");
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                BmobQuery<Collection> query = new BmobQuery<>();
                //查询playerName叫“比目”的数据
                query.addWhereEqualTo("UserName", mCurrentUser.getUsername());
                //返回50条数据，如果不加上这条语句，默认返回10条数据
                query.setLimit(50);
                //执行查询方法
                query.findObjects(CollectionActivity.this, new FindListener<Collection>() {
                    @Override
                    public void onSuccess(List<Collection> object) {
                        // TODO Auto-generated method stub
                        showToast("查询成功：共" + object.size() + "条数据。");
                        //保存到数据库
                        for (Collection coll : object) {
                            CollectionDao.insertCollection(coll.getObjectId(), coll.getId(),
                                    coll.getQuestion(), coll.getAnswer(),
                                    coll.getRemark(), coll.getUserName());
                        }
                        mData = CollectionDao.queryCommands(mCurrentUser.getUsername());
                        showToast("mDATA的值为" + mData.size());
                        refreshView();
                    }

                    @Override
                    public void onError(int code, String msg) {
                        // TODO Auto-generated method stub
                        showToast("查询失败：" + msg);
                    }
                });
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                refreshView();
            }
        }.execute();
    }

    private void refreshView() {
        if (adapter == null) {
            adapter = new CollectionAdapter();
            lv_collection.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
        lv_collection.setOnItemClickListener(CollectionActivity.this);
        lv_collection.setOnItemLongClickListener(CollectionActivity.this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        int _id = mData.get(position).getId();
        InterView interView = BookDao.queryInterViewById(_id);
        Intent intent = new Intent(CollectionActivity.this, InterViewShowActivity.class);
        intent.putExtra("data", interView);
        startActivity(intent);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        //长点击出现对话框
        showLongClickDialog(position);
        return false;
    }

    class CollectionAdapter extends BaseAdapter {

        private TextView tv_collection;

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
                convertView = View.inflate(CollectionActivity.this, R.layout.collection_item, null);
            }
            tv_collection = (TextView) convertView.findViewById(R.id.tv_collection);
            tv_collection.setText(mData.get(position).getQuestion());
            return convertView;
        }
    }

    private void showLongClickDialog(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(CollectionActivity.this);
        final AlertDialog dialog = builder.create();
        View view = View.inflate(CollectionActivity.this, R.layout.addinterview_longclick_item, null);
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
                final boolean[] result = {false};
                //数据库删除
                boolean delete = CollectionDao.deleteItem(mData.get(position).getId());
                if (delete) {
                    showToast("本地数据库删除成功");
                    result[0] = true;
                } else {
                    showToast("本地数据库删除失败");
                    result[0] = false;
                }
                //删除服务器的数据
                final Collection coll = new Collection();
                coll.setObjectId(mData.get(position).getObjectId());
                coll.delete(CollectionActivity.this, new DeleteListener() {
                    @Override
                    public void onSuccess() {
                        showToast("服务器删除成功" + coll.toString());
                        result[0] = true;
                    }

                    @Override
                    public void onFailure(int i, String s) {
                        showToast("服务器删除失败" + s);
                    }
                });
                if (result[0]) {
                    mData.clear();
                    initData();
                }
                dialog.dismiss();
            }
        });
        dialog.setView(view);
        dialog.show();
    }
}
