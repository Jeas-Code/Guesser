package com.example.jeas.netapp;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class into_invite_friends extends TransparentBar implements View.OnClickListener{

    private Button add_friends_btn;
    private ListView myListView;

    //好友弹窗
    private List<String> list = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_into_invite_friends);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.hide();
        }

        add_friends_btn = (Button)findViewById(R.id.add_friends_btn);
        add_friends_btn.setOnClickListener(this);

        list = initData();
        Showfriends();//弹框操作

    }

    //初始化数据
    private ArrayList<String> initData() {
        ArrayList<String> list = new ArrayList<String>();
        String name1="领居家的小槑槑~O~";
        String name2="远方表哥的亲侄子^0^";
        String name3 = "我不是小古呀>i<";
        String name4="深圳堂哥的小舅子<O>";
        String name5 = "弦断有谁听~#)><(#";
        list.add(name1);
        list.add(name2);
        list.add(name3);
        list.add(name4);
        list.add(name5);
        return list;
    }

    public void Showfriends() {
        final Context context = into_invite_friends.this;
        //LayoutInflater inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
        //View layout = inflater.inflate(R.layout.activity_into_invite_friends, null);
        myListView = (ListView) findViewById(R.id.friends_list);
        MyAdapter adapter = new MyAdapter(context, list);
        myListView.setAdapter(adapter);
        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {
                //在这里面就是执行点击后要进行的操作,这里只是做一个显示
                    Toast.makeText(context, "对方暂时不在线!! 请稍后再联系...", Toast.LENGTH_SHORT).show();
                    //alertDialog.dismiss();
            }
        });
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.add_friends_btn:
                Toast.makeText(this, "功能暂未上线，敬请期待！！", Toast.LENGTH_LONG).show();
        }
    }
}
