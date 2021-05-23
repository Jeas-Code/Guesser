package com.example.jeas.netapp;

import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button start_game_btn;
    private Button invite_friends_btn;
    private Button setting_btn;
    private Button exit_game_btn;

    @Override
    @RequiresApi(api = 26)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //隐藏默认的标题栏
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.hide();
            }
        //弹出邀请好友游戏通知
        notice();

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

    //进入游戏通知实现
    @RequiresApi(api = 26)
    public void notice() {
                String id = "001";
                String name="Guesser";
                Notification notification = null;
                NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                Intent intent = new Intent(this, Notification.class);
                PendingIntent pi = PendingIntent.getActivity(this, 0, intent, 0);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    NotificationChannel mChannel = new NotificationChannel(id, name,
                            NotificationManager.IMPORTANCE_LOW);
                    Toast.makeText(this, mChannel.toString(), Toast.LENGTH_SHORT).show();
                    notificationManager.createNotificationChannel(mChannel);
                    notification = new Notification.Builder(this)
                            .setChannelId(id)
                            .setContentTitle("欢迎来到Guesser !!!")
                            .setContentText("一个人玩无聊？快来邀请好友同玩互动吧！！")
                            .setWhen(System.currentTimeMillis())
                            .setSmallIcon(R.mipmap.ic_love)
                            .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.game_notice_img1))
                            .setContentIntent(pi)
                            .setAutoCancel(true)
                            .setDefaults(Notification.DEFAULT_ALL)
                            .setStyle(new Notification.BigPictureStyle().bigPicture(
                                    BitmapFactory.decodeResource(getResources(), R.drawable.game_notice_img2)))
                            .setPriority(Notification.PRIORITY_MAX)
                            .build();
                } else {
                    notification = new NotificationCompat.Builder(this)
                            .setContentTitle("冬去春来，好久不见~")
                            .setContentText("LuLu baby, You are the one Who I love❤")
                            .setWhen(System.currentTimeMillis())
                            .setSmallIcon(R.mipmap.ic_love)
                            .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.game_notice_img3))
                            .setOngoing(true)
                            .setChannel(id)
                            .build();//无效
                }
                notificationManager.notify(1, notification);
        }

    @Override
    protected void onNewIntent(Intent intent) {
        finish();
    }
}
