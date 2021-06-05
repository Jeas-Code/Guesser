package com.example.jeas.netapp.media;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.jeas.netapp.R;

import java.io.File;
import java.io.FileOutputStream;

public class Draw extends AppCompatActivity implements View.OnClickListener{

    private ImageView imageView;
    private Bitmap mbitmap;
    private Canvas canvas;
    private Paint paint;

    private Button reset_panal_btn;
    private Button save_pic;
    //private ImageView bingPicImg;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw);

        imageView = (ImageView)findViewById(R.id.draw_img);
        reset_panal_btn = (Button)findViewById(R.id.reset_panal_btn);
        save_pic = (Button)findViewById(R.id.save_picture_btn);

        //隐藏默认的标题栏
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.hide();
        }

//        //初始化微软每日一图
//        bingPicImg = (ImageView)findViewById(R.id.bing_pic_img);
//        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
//        String bingPic = prefs.getString("bing pic", null);
//        if(bingPic != null){
//            Glide.with(this).load(bingPic).into(bingPicImg);
//        }else{
//            loadBingPic();
//        }


        //Bitmap bitmap  = BitmapFactory.decodeResource(getResources(), R.drawable.game_notice_img2);
        paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(12);
        paint.setAntiAlias(true);
        mbitmap = Bitmap.createBitmap(1000, 1600, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(mbitmap);
        canvas.drawColor(Color.WHITE);

        imageView.setImageBitmap(mbitmap);

        imageView.setOnTouchListener(new View.OnTouchListener() {
            float startX;
            float startY;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                try{
                    switch (event.getAction()){
                        case MotionEvent.ACTION_DOWN:
                            startX = event.getX();
                            startY = event.getY();
                            // 第一次绘图初始化内存图片，指定背景为白色
                            if (mbitmap == null) {
                                    mbitmap = Bitmap.createBitmap(1000, 1600, Bitmap.Config.ARGB_8888);
                                    canvas = new Canvas(mbitmap);
                                    canvas.drawColor(Color.WHITE);
                                    //imageView.setImageBitmap(mbitmap);
                            }
                            //Toast.makeText(Draw.this, "手指触下", Toast.LENGTH_SHORT).show();
                            break;
                        case MotionEvent.ACTION_MOVE:
                            // 记录移动位置的点的坐标
                            float stopX = event.getX();
                            float stopY = event.getY();
                            //根据两点坐标，绘制连线
                            canvas.drawLine(startX, startY, stopX, stopY, paint);
                            // 更新开始点的位置
                            startX = event.getX();
                            startY = event.getY();

                            imageView.setImageBitmap(mbitmap);
                            break;
                        case MotionEvent.ACTION_UP:
                            //Toast.makeText(Draw.this, "手指移开", Toast.LENGTH_SHORT).show();
                            break;

                        default:
                            break;
                    }
                }catch (Exception e){
                e.printStackTrace();
                }
                return true;
            }
        });

        reset_panal_btn.setOnClickListener(this);
        save_pic.setOnClickListener(this);

    }


    public void onClick(View v){
        switch (v.getId()){
            case R.id.save_picture_btn:
                saveBitmap();
                break;
            case R.id.reset_panal_btn:
                resetPanel();
                break;
            default:
                break;
        }
    }

    protected void resetPanel(){
        if(mbitmap != null){
            mbitmap = Bitmap.createBitmap(1000, 1600, Bitmap.Config.ARGB_8888);
            canvas = new Canvas(mbitmap);
            canvas.drawColor(Color.WHITE);
            imageView.setImageBitmap(mbitmap);
            Toast.makeText(Draw.this, "清除画板成功!", Toast.LENGTH_SHORT).show();
        }
    }

    protected void saveBitmap(){
        try{
            File file = new File(Environment.getExternalStorageDirectory(),
                    System.currentTimeMillis()+".png");
            FileOutputStream stream = new FileOutputStream(file);
            mbitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            Toast.makeText(Draw.this, "成功保存图片!", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_MEDIA_MOUNTED);
            intent.setData(Uri.fromFile(Environment.getDownloadCacheDirectory()));
            sendBroadcast(intent);

        }catch (Exception e){
            Toast.makeText(Draw.this, "保存图片失败!!", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
//    private void loadBingPic() {
//        String requestBingPic = "http://guolin.tech/api/bing_pic";
//        HttpUtil.sendOkHttpRequest(requestBingPic, new Callback() {
//            @Override
//            public void onFailure(okhttp3.Call call, IOException e) {
//                e.printStackTrace();
//            }
//
//            @Override
//            public void onResponse(okhttp3.Call call, Response response) throws IOException {
//                final String bingPic = response.body().string();
//                SharedPreferences.Editor editor = PreferenceManager
//                        .getDefaultSharedPreferences(Draw.this).edit();
//                editor.putString("bing_pic", bingPic);
//                editor.apply();
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        Glide.with(Draw.this).load(bingPic).into(bingPicImg);
//                    }
//                });
//            }
//        });
//    }
}

