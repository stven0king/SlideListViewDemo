package com.tzx.slidelistviewdemo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity {
    private static final String TAG = "MainActivity";
    private SliderListView mListView;
    private List<MessageItem> mMessageItems = new ArrayList<MessageItem>();
    private SliderLinearView mLastSlideViewWidhtStatusOn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mListView = (SliderListView) findViewById(R.id.list);
        for (int i = 0; i < 20; i++) {
            MessageItem item = new MessageItem();
            item.iconRes = R.drawable.wechat_icon;
            item.time = "2015/8/10";
            item.title = "Hello";
            item.msg = "World";
            mMessageItems.add(item);
        }
        mListView.setAdapter(new SlideAdapter());
        mListView.setOnItemClickListener(new MyOnItemClickLisener());
    }
    public class MyOnItemClickLisener implements AdapterView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Log.e(TAG, "onItemClick position=" + position);
        }
    }
    private class SlideAdapter extends BaseAdapter{
        private LayoutInflater mInflater;
        SlideAdapter(){
            super();
            mInflater = getLayoutInflater();
        }
        @Override
        public int getCount() {
            return mMessageItems.size();
        }

        @Override
        public Object getItem(int position) {
            return mMessageItems.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            SliderLinearView sliderLinearView = (SliderLinearView) convertView;
            if (sliderLinearView == null) {
                View itemView = mInflater.inflate(R.layout.list_item,null);
                sliderLinearView = new SliderLinearView(MainActivity.this);
                sliderLinearView.setContentView(itemView);
                holder = new ViewHolder(sliderLinearView);
                sliderLinearView.setTag(holder);
            } else {
                holder = (ViewHolder) sliderLinearView.getTag();
            }
            MessageItem item = mMessageItems.get(position);
            sliderLinearView.shrink();
            holder.icon.setImageResource(item.iconRes);
            holder.title.setText(item.title);
            holder.msg.setText(item.msg);
            holder.time.setText(item.time);
            holder.deleteHolder.setOnClickListener(new MyOnClickListener());
            return sliderLinearView;
        }
    }
    public class MyOnClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {

        }
    }
    public class MessageItem{
        public int iconRes;
        public String title;
        public String msg;
        public String time;
    }
    private static class ViewHolder {
        public ImageView icon;
        public TextView title;
        public TextView msg;
        public TextView time;
        public ViewGroup deleteHolder;
        ViewHolder(View view) {
            icon = (ImageView) view.findViewById(R.id.icon);
            title = (TextView) view.findViewById(R.id.title);
            msg = (TextView) view.findViewById(R.id.msg);
            time = (TextView) view.findViewById(R.id.msg);
            time = (TextView) view.findViewById(R.id.time);
            deleteHolder = (ViewGroup) view.findViewById(R.id.holder);
        }
    }
}
