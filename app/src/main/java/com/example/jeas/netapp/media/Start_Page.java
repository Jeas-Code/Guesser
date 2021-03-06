package com.example.jeas.netapp.media;

import android.os.Bundle;
import android.support.v7.app.ActionBar;

import com.example.jeas.netapp.R;

public class Start_Page extends TransparentBar {

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
//                    Intent it = new Intent(getApplicationContext(), MainActivity.class);
//                    startActivity(it);
                    //onDestroy();
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        myThread.start();//启动线程

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
