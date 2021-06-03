package com.example.jeas.netapp;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

public class Draw extends AppCompatActivity {

    private DrawImageView div;

    private ImageView imageView;
    private Bitmap mbitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw);
        //描绘画图框
        div = (DrawImageView) findViewById(R.id.paint_iv);
        div.draw(new Canvas());

        imageView = (ImageView)findViewById(R.id.draw_img);

        //隐藏默认的标题栏
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.hide();
        }

        //Bitmap bitmap  = BitmapFactory.decodeResource(getResources(), R.drawable.game_notice_img2);
        mbitmap = Bitmap.createBitmap(1000, 1200, Bitmap.Config.ARGB_8888);

        final Canvas canvas = new Canvas(mbitmap);

        final Paint paint = new Paint();
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
                            for(int i=-10; i<10; i++){
                                for (int j = -10; j<10; j++) {
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

}
