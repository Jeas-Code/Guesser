package com.example.jeas.netapp;

import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class music_adjust extends AppCompatActivity implements View.OnClickListener{

    private Button open_music_btn;
    private Button close_music_btn;

    private MainActivity main= new MainActivity();
    private MediaPlayer mediaPlayer = main.getMediaPlayer();
    public Uri mp3uri = main.getUri();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_adjust);
        //隐藏默认的标题栏
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.hide();
        }

        open_music_btn = (Button)findViewById(R.id.open_music_btn);
        close_music_btn = (Button)findViewById(R.id.close_music_btn);

        open_music_btn.setOnClickListener(this);
        close_music_btn.setOnClickListener(this);
        initMediaPlayer(mediaPlayer, mp3uri);

    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.open_music_btn:
                if (!mediaPlayer.isPlaying()) {
                    mediaPlayer.start();
                }
                break;

            case R.id.close_music_btn:
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.pause();
                    mediaPlayer.reset();
                    initMediaPlayer(mediaPlayer, mp3uri);
                }
                break;

            default:
                break;
        }
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

}
