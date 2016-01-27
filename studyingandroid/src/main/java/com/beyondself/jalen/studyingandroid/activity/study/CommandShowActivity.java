package com.beyondself.jalen.studyingandroid.activity.study;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
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

import java.util.List;

public class CommandShowActivity extends BaseActivity {

    private ListView lv_command;
    private List<Command> mData;

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
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        final int id = intent.getIntExtra("_id", 6);
        //异步加载数据
        new AsyncTask<Void, Void, Void>() {

            private CommandAdapter adapter;

            @Override
            protected Void doInBackground(Void... params) {
                mData = CommandDao.queryCommands(id);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                if (adapter == null) {
                    adapter = new CommandAdapter();
                    lv_command.setAdapter(adapter);
                } else {
                    adapter.notifyDataSetChanged();
                }
            }
        }.execute();

    }

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
                holder=new ViewHolder();
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
            Command command = mData.get(position);
            holder.ivCommanndPic.setImageResource(R.mipmap.icon);
            holder.tvCommanName.setText(command.getUserName());
            holder.tvCommandContent.setText(command.getCommand());
            holder.tvNozan.setText(command.getNoZan()+"");
            holder.tvZan.setText(command.getZan()+"");
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
