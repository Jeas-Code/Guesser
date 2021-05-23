package com.example.jeas.netapp;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ButtonBarLayout;
import android.view.View;
import android.widget.Button;

public class setting extends AppCompatActivity implements View.OnClickListener{

    private Button help_btn;
    private Button about_btn;
    private Button bgm_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.hide();
        }

        help_btn = (Button)findViewById(R.id.setting_help_btn);
        about_btn = (Button)findViewById(R.id.setting_about_btn);
        bgm_btn = (Button)findViewById(R.id.setting_music_btn);

        help_btn.setOnClickListener(this);
        about_btn.setOnClickListener(this);
        bgm_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.setting_help_btn:
                //进入帮助界面
                Intent help_intent = new Intent(setting.this, help.class);
                startActivity(help_intent);
                break;
            case R.id.setting_about_btn:
                //进入帮助界面
                Intent about_intent = new Intent(setting.this, AboutUs.class);
                startActivity(about_intent);
                break;
            case R.id.setting_music_btn:
                //进入设置背景音乐界面
                Intent bgm_intent = new Intent(setting.this, music_adjust.class);
                startActivity(bgm_intent);
                break;
            default:
                break;
        }
    }


}
