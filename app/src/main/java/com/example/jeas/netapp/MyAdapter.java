package com.example.jeas.netapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jeas on 2021/5/27.
 */

class MyAdapter extends BaseAdapter {
    private List<String> mlist;
    private Context mContext;

    public MyAdapter(Context context, List<String> list) {
        this.mContext = context;
        mlist = new ArrayList<String>();
        this.mlist = list;
    }

    @Override
    public int getCount() {
        return mlist.size();
    }

    @Override
    public Object getItem(int position) {
        return mlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Person person = null;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(R.layout.list_tx_item,null);
            person = new Person();
            person.name = (TextView)convertView.findViewById(R.id.txtext);
            convertView.setTag(person);
        }else{
            person = (Person)convertView.getTag();
        }
        person.name.setText(mlist.get(position).toString());
        return convertView;
    }
    class Person{
        TextView name;
    }
}
