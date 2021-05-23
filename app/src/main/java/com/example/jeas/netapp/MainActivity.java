package com.example.jeas.netapp;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button start_game_btn;
    private Button invite_friends_btn;
    private Button setting_btn;
    private Button exit_game_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //隐藏默认的标题栏
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.hide();
            }

        start_game_btn = (Button) findViewById(R.id.start_game_btn);
        invite_friends_btn = (Button) findViewById(R.id.invite_friends_btn);
        setting_btn = (Button) findViewById(R.id.setting_btn);
        exit_game_btn = (Button) findViewById(R.id.exit_game_btn);
        start_game_btn.setOnClickListener(this);
        invite_friends_btn.setOnClickListener(this);
        setting_btn.setOnClickListener(this);
        exit_game_btn.setOnClickListener(this);

    }
    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.start_game_btn:
                //进入游戏界面
                Intent game_intent = new Intent(MainActivity.this, into_game_activity.class);
                startActivity(game_intent);
                break;
            case R.id.invite_friends_btn:
                //进入邀请好友界面
                Intent friend_intent = new Intent(MainActivity.this, into_invite_friends.class);
                startActivity(friend_intent);
                break;
            case R.id.setting_btn:
                //进入设置界面（音量调节、帮助等设置）
                Intent setting_intent = new Intent(MainActivity.this, setting.class);
                startActivity(setting_intent);
                break;
            case R.id.exit_game_btn:
                //退出游戏主界面，返回手机桌面
                //退出前弹出提示框，以防玩家误触
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("温馨提示")
                        .setMessage("您确定退出吗?")
                        .setIcon(R.drawable.exit)
                        .setCancelable(false)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }})
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }});

                Dialog dialog = builder.create();
                dialog.show();

            default:
                break;

        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        finish();
    }
}
