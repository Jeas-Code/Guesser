package com.example.jeas.netapp;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.jeas.netapp.database.MyDatabaseHelper;
import com.example.jeas.netapp.media.TransparentBar;

import java.util.ArrayList;
import java.util.List;


public class into_invite_friends extends TransparentBar implements View.OnClickListener{

    private Button add_friends_btn;
    private ListView myListView;
    private MyDatabaseHelper dbHelper;

    //好友弹窗
    private List<String> list = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_into_invite_friends);
        ActionBar actionBar = getSupportActionBar();

        list = initData();

        if(actionBar != null){
            actionBar.hide();
        }

        add_friends_btn = (Button)findViewById(R.id.add_friends_btn);
        add_friends_btn.setOnClickListener(this);

        Showfriends();//弹框操作

    }

    //初始化数据
    private ArrayList<String> initData() {
        ArrayList<String> list = new ArrayList<String>();
        //从数据库中取出好友信息
        String name;
        dbHelper = new MyDatabaseHelper(this, "Friend_Info.db", null, 2);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query("friend_info", null, null, null, null, null, null);
        if(cursor.moveToFirst()){
            do{
                name = cursor.getString(cursor.getColumnIndex("nickname"));
                list.add(name);
            }while(cursor.moveToNext());
        }
        cursor.close();
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
                Intent chat_intent = new Intent(into_invite_friends.this, Chat.class);
                startActivity(chat_intent);
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
