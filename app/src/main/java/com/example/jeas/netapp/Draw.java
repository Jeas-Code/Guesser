package com.example.jeas.netapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
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

import java.io.File;
import java.io.FileOutputStream;

public class Draw extends AppCompatActivity {

    private DrawImageView div;

    private ImageView imageView;
    private Bitmap mbitmap;
    Canvas canvas = new Canvas(mbitmap);
    Paint paint = new Paint();

    private Button reset_panal_btn;
    private Button save_pic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw);
        //描绘画图框
        div = (DrawImageView) findViewById(R.id.paint_iv);
        div.draw(new Canvas());

        imageView = (ImageView)findViewById(R.id.draw_img);

        reset_panal_btn = (Button)findViewById(R.id.reset_panal_btn);
        save_pic = (Button)findViewById(R.id.save_picture_btn);

        //隐藏默认的标题栏
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.hide();
        }

        //Bitmap bitmap  = BitmapFactory.decodeResource(getResources(), R.drawable.game_notice_img2);
        mbitmap = Bitmap.createBitmap(imageView.getWidth(), imageView.getHeight(), Bitmap.Config.ARGB_8888);

        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(12);
        paint.setAntiAlias(true);

        final Matrix matrix = new Matrix();
        canvas.drawBitmap(mbitmap, matrix, paint);
        //div.setImageBitmap(mbitmap);
        imageView.setImageBitmap(mbitmap);

        imageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                float startX;
                float startY;
                try{
                    switch (event.getAction()){
                        case MotionEvent.ACTION_DOWN:
                            startX = event.getX();
                            startY = event.getY();
                            //Toast.makeText(Draw.this, "手指触下", Toast.LENGTH_SHORT).show();
                            break;
                        case MotionEvent.ACTION_MOVE:
                            int x = (int)event.getX();
                            int y = (int)event.getY();
                            //Toast.makeText(Draw.this, x+"-"+y, Toast.LENGTH_SHORT).show();
                            for(int i=-10; i<=10; i++){
                                for (int j = -10; j<=10; j++) {
                                    if (Math.sqrt((i * i) + (j * j)) <= 10) {
                                        //mbitmap.setPixel(x + i, y + j, Color.BLACK);
                                        canvas.drawPoint(x+i, y+j, paint);
                                    }
                                }
                            }
                            float stopX;
                            float stopY;
                            // 记录移动位置的点的坐标
                            stopX = event.getX();
                            stopY = event.getY();
                            //根据两点坐标，绘制连线
                            //canvas.drawLine(startX, startY, stopX, stopY, paint);
                            imageView.setImageBitmap(mbitmap);
                            break;
                        case MotionEvent.ACTION_UP:
                            //Toast.makeText(Draw.this, "手指移开", Toast.LENGTH_SHORT).show();
                            break;
                    }
                }catch (Exception e){
                e.printStackTrace();
                }
                return true;
            }
        });


    }

    protected void resetPanel(){
        if(mbitmap != null){
            mbitmap = Bitmap.createBitmap(imageView.getWidth(), imageView.getHeight(), Bitmap.Config.ARGB_8888);
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


}
