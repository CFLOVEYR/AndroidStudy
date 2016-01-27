package com.beyondself.jalen.studyingandroid.activity.study;

import android.content.Intent;
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

import cn.bmob.v3.BmobUser;

public class CollectionActivity extends BaseActivity implements AdapterView.OnItemClickListener {

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
        mData = CollectionDao.queryCommands(mCurrentUser.getUsername());
        if (mData != null) {
            adapter = new CollectionAdapter();
            lv_collection.setAdapter(adapter);
        }
        lv_collection.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        int _id = mData.get(position).get_id();
        InterView interView = BookDao.queryInterViewById(_id);
        Intent intent = new Intent(CollectionActivity.this, InterViewShowActivity.class);
        intent.putExtra("data", interView);
        startActivity(intent);
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
}
