package com.example.jeas.netapp;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBar;

public class Start_Page extends TransparentBar {

    //开启是创建好友数据库列表
    private MyDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start__page);

        //隐藏默认的标题栏
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.hide();
        }
        Thread myThread = new Thread() {//创建子线程
            @Override
            public void run() {
                try {
                    sleep(1500);//使程序休眠1.5秒
                    Intent it = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(it);
                    onDestroy();
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        myThread.start();//启动线程

        //创建数据库初始化数据表并赋予初值
        dbHelper = new MyDatabaseHelper(this, "Friend_Info.db", null, 1);
        dbHelper.getWritableDatabase();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        String name1="领居家的小槑槑~O~";
        String name2="远方表哥的亲侄子^0^";
        String name3 = "我不是小古呀>i<";
        String name4="深圳堂哥的小舅子<O>";
        String name5 = "弦断有谁听~#)><(#";
        String[] name = new String[]{name1, name2, name3, name4, name5};
        //values.put("id", 001);
        for (String nickname:name){
            values.put("nickname", nickname);
            db.insert("friend_info", null, values);
            values.clear();
        }
        values.put("nickname", name1);
        db.insert("friend_info", null, values);
        values.clear();
        values.put("nickname", name2);
        db.insert("friend_info", null, values);
        values.clear();
        values.put("nickname", name3);
        db.insert("friend_info", null, values);
        values.clear();
        values.put("nickname", name4);
        db.insert("friend_info", null, values);
        values.clear();
        values.put("nickname", name5);
        db.insert("friend_info", null, values);
        values.clear();


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
