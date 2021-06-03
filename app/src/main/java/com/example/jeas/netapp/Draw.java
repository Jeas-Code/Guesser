package com.example.jeas.netapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.widget.Toast;

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

        //隐藏默认的标题栏
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.hide();
        }

        Bitmap bitmap  = BitmapFactory.decodeResource(getResources(), R.drawable.hawaii);
        mbitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());

        Canvas canvas = new Canvas(mbitmap);

        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setAntiAlias(true);

        final Matrix matrix = new Matrix();
        canvas.drawBitmap(bitmap, matrix, paint);
        div.setImageBitmap(mbitmap);

        div.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                try{
                    switch (event.getAction()){
                        case MotionEvent.ACTION_DOWN:
                            Toast.makeText(Draw.this, "手指触下", Toast.LENGTH_SHORT).show();
                            break;
                        case MotionEvent.ACTION_MOVE:
                            int x = (int)event.getX();
                            int y = (int)event.getY();

                            for(int i=-10; i<10; i++){
                                for (int j = -10; j<10; j++) {
                                    if (Math.sqrt((i * i) + (j * j)) <= 10) {
                                        mbitmap.setPixel(x + i, y + j, Color.BLACK);
                                    }
                                }
                            }
                            div.setImageBitmap(mbitmap);
                            break;
                        case MotionEvent.ACTION_UP:
                            Toast.makeText(Draw.this, "手指移开", Toast.LENGTH_SHORT).show();
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


    }

}
