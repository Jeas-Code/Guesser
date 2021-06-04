package com.example.jeas.netapp;

import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.NotificationCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends TransparentBar implements View.OnClickListener {

    private Button start_game_btn;
    private Button invite_friends_btn;
    private Button draw_btn;
    private Button setting_btn;
    private Button exit_game_btn;
    private Button frame_animation_btn;
    private TextView frame_animation_label;

    private MyDatabaseHelper dbHelper;

    public final static MediaPlayer mediaPlayer = new MediaPlayer();
    public static Uri mp3uri = Uri.parse("android.resource://com.example.jeas.netapp/"+R.raw.midnight);

    //好友弹窗
    private List<String> list = new ArrayList<String>();
    private AlertDialog.Builder builder;
    private AlertDialog alertDialog;


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

        start_game_btn = (Button) findViewById(R.id.start_game_btn);
        invite_friends_btn = (Button) findViewById(R.id.invite_friends_btn);
        draw_btn = (Button) findViewById(R.id.draw_btn);
        setting_btn = (Button) findViewById(R.id.setting_btn);
        exit_game_btn = (Button) findViewById(R.id.exit_game_btn);
        start_game_btn.setOnClickListener(this);
        invite_friends_btn.setOnClickListener(this);
        draw_btn.setOnClickListener(this);
        setting_btn.setOnClickListener(this);
        exit_game_btn.setOnClickListener(this);

        //弹出邀请好友游戏通知
        notice();

        //好友弹窗功能
        list = initData();

        //播放音乐
//        if(ContextCompat.checkSelfPermission(MainActivity.this,
//                Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
//                PackageManager.PERMISSION_GRANTED){
//            ActivityCompat.requestPermissions(MainActivity.this, new String[]{
//                    Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
//        }else{
//            initMediaPlayer(mediaPlayer, mp3uri);
//        }

        initMediaPlayer(mediaPlayer, mp3uri);
        if (!mediaPlayer.isPlaying()) {
            mediaPlayer.start();
            mediaPlayer.setLooping(true);
        }

        //设置帧动画效果:
        frame_animation_label = (TextView) findViewById(R.id.frame_animation_label);
        frame_animation_btn = (Button) findViewById(R.id.frame_animation_btn);
        frame_animation_btn.setBackgroundResource(R.drawable.frame_animation);//把Drawable设置为button的背景
        //拿到这个我们定义的Drawable，实际也就是AnimationDrawable
        AnimationDrawable animationDrawable = (AnimationDrawable) frame_animation_btn.getBackground();
        animationDrawable.start();//开启动画

        frame_animation_btn.setOnClickListener(this);
        frame_animation_label.setOnClickListener(this);

//        bindService(new Intent(MainActivity.this, InternetService.class),
//                internetServiceConnection, Context.BIND_AUTO_CREATE);

    }


    //网络服务
    ///在前台页面上，由于前台和后台要进行数据交互，因此需要使用bindService方法绑定服务。
//    InternetService innetService ;
//    public ServiceConnection internetServiceConnection = new ServiceConnection() {
//
//        public void onServiceConnected(ComponentName arg0, IBinder service) {
//            innetService = ((InternetService.InterBinder) service).getService();
//        }
//
//        public void onServiceDisconnected(ComponentName arg0) {
//
//            innetService = null;
//        }
//
//    };

    //初始化数据
    private ArrayList<String> initData() {
        ArrayList<String> list = new ArrayList<String>();
        //从数据库中取出好友信息
        String name;
        dbHelper = new MyDatabaseHelper(this, "Friend_Info.db", null, 1);
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

    public void ShowDialog() {
        final Context context = MainActivity.this;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.list_tx_picture, null);
        ListView myListView = (ListView) layout.findViewById(R.id.formcustomspinner_list);
        MyAdapter adapter = new MyAdapter(context, list);
        myListView.setAdapter(adapter);
        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {
                //在这里面就是执行点击后要进行的操作,这里只是做一个显示
                if (alertDialog != null) {
                    Toast.makeText(context, "对方暂时不在线!! 请稍后联系...", Toast.LENGTH_SHORT).show();
                    alertDialog.dismiss();
                }
            }
        });
        builder = new AlertDialog.Builder(context);
        builder.setView(layout);
        alertDialog = builder.create();
        alertDialog.show();
    }

    //初始化音乐组件
    public void initMediaPlayer(MediaPlayer mediaplayer, Uri uri) {
        try {
            //mediaPlayer.setDataSource("/storage/emulated/0/qqmusic/song/夜空中最亮的星.mp3");
            mediaplayer.setDataSource(this, uri);
            mediaplayer.prepare();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //获取请求
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults){
        switch (requestCode){
            case 1:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    initMediaPlayer(mediaPlayer, mp3uri);
                }else{
                    Toast.makeText(this, "播放音乐失败！", Toast.LENGTH_LONG).show();
                    finish();
                }
                break;
            default:
                break;
        }
    }

    protected void onDestroy(){
        //unbindService(internetServiceConnection);
        super.onDestroy();
        if(mediaPlayer != null){
            mediaPlayer.stop();
            mediaPlayer.release();
        }
    }

//    //顶部状态栏透明化
//    void transparentBar(){
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            Window window = getWindow();
//            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
//                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
//            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.setStatusBarColor(Color.TRANSPARENT);
//            window.setNavigationBarColor(Color.TRANSPARENT);
//        }
//        else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            Window window = getWindow();
//            window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
//                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//        }
//    }


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
            case R.id.draw_btn:
                //进入绘画界面
                Intent draw_intent = new Intent(MainActivity.this, Draw.class);
                startActivity(draw_intent);
                break;

            case R.id.setting_btn:
                //Toast.makeText(this, "进入设置!!", Toast.LENGTH_SHORT).show();
                //进入设置界面（音量调节、帮助等设置）
                Intent setting_intent = new Intent(MainActivity.this, setting.class);
                //setting_intent.putExtra("MediaPlayer", MainActivity.class);
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
                break;


            case R.id.frame_animation_btn:
                //Toast.makeText(this, "好友列表暂时为空!!", Toast.LENGTH_SHORT).show();
                ShowDialog();//弹框操作
                break;

            case R.id.frame_animation_label:
                ShowDialog();//弹框操作
                //Toast.makeText(this, "好友列表暂时为空!!", Toast.LENGTH_SHORT).show();
                break;

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
                Intent intent = new Intent(this, into_invite_friends.class);
                PendingIntent pi = PendingIntent.getActivity(this, 0, intent, 0);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    NotificationChannel mChannel = new NotificationChannel(id, name,
                            NotificationManager.IMPORTANCE_LOW);
                    //Toast.makeText(this, mChannel.toString(), Toast.LENGTH_SHORT).show();
                    notificationManager.createNotificationChannel(mChannel);
                    notification = new Notification.Builder(this)
                            .setChannelId(id)
                            .setTicker("一个人玩无聊？快来邀请好友同玩互动吧！")
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
                            .setContentTitle("欢迎来到Guesser !!!")
                            .setContentText("一个人玩无聊？快来邀请好友同玩互动吧！！")
                            .setWhen(System.currentTimeMillis())
                            .setSmallIcon(R.mipmap.ic_love)
                            .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.game_notice_img3))
                            .setOngoing(true)
                            .setChannel(id)
                            .build();//无效

                }
                notificationManager.notify(1, notification);
        }

    public MediaPlayer getMediaPlayer(){
            return mediaPlayer;
    }

    public Uri getUri(){
        return mp3uri;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        finish();
    }
}
