package com.example.jeas.netapp;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

public class into_game_activity extends TransparentBar implements View.OnClickListener{

    private Button submit_answer_btn;
    private Button read_answer_btn;
    private ImageView game_picture;
    private EditText answer_text;
    private TextView serial_label;
    private TextView score_label;
    private String answer;
    public int score = 0;
    //图片显示
    private int sign = 0;
    ImageMaterials imageMaterials = new ImageMaterials();
    private Map<String, Integer> imagemap;
    private List<String> img_name_list;
    private List<Integer> img_resource_list;
    private ImageAnimation imageAnimation = new ImageAnimation();
    private TransitionDrawable transitionDrawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_into_game_activity);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.hide();
        }
        imagemap = imageMaterials.getImagemap();
        img_name_list = imageMaterials.getImg_name_list(imagemap);
        img_resource_list = imageMaterials.getImg_resource_list(imagemap);

        //设置图片序号和得分
        serial_label = (TextView)findViewById(R.id.serial_label);
        serial_label.setText(sign+"/"+img_name_list.size());
        score_label = (TextView)findViewById(R.id.score_label);
        score_label.setText("得分: "+score+"分");

        answer_text = (EditText)findViewById(R.id.input_answer);
        submit_answer_btn = (Button)findViewById(R.id.submit_answer_btn);
        read_answer_btn = (Button)findViewById(R.id.read_answer_btn);
        game_picture = (ImageView)findViewById(R.id.game_picture);

        //设置图片效果
//        imageAnimation.fadeIn(game_picture, img_resource_list.get(sign));
//        imageAnimation.fadeOut(game_picture, img_resource_list.get(sign));
        fadein_and_out(game_picture, R.drawable.maoxiaotong, img_resource_list.get(sign));
        submit_answer_btn.setOnClickListener(this);
        read_answer_btn.setOnClickListener(this);

    }


    public void fadein_and_out(ImageView imageView, Integer begin, Integer end){
        Drawable[] drawableArray = {
                getResources().getDrawable(begin),
                getResources().getDrawable(end)
        };
        transitionDrawable = new TransitionDrawable(drawableArray);
        imageView.setImageDrawable(transitionDrawable);
        transitionDrawable.startTransition(1500);
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.read_answer_btn:
                AlertDialog answerDialog = new AlertDialog.Builder(this)
                        .setTitle("客官,您要的答案:")//标题
                        .setMessage(img_name_list.get(sign))//内容
                        .setIcon(R.drawable.dialog_love)//图标
                        .setCancelable(true)
                        .create();
                answerDialog.show();
                break;

            case R.id.submit_answer_btn:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                final AlertDialog.Builder scoreDialog = new AlertDialog.Builder(this);
                answer = answer_text.getText().toString();
                Log.i("输入的文本为", answer);
                if(answer.equals(img_name_list.get(sign))){
                    //Toast.makeText(this, "恭喜你！答对了！！", Toast.LENGTH_LONG);
                    builder.setTitle("恭喜您答对了!!");
                    builder.setMessage("客官，继续下一题么? ^0^\n")
                            .setIcon(R.drawable.right_face);
                    score = score + 10;
                    score_label.setText("得分: "+score+"分");
                }else{
                    //Toast.makeText(this, "答错了喔！请再试一次吧！！", Toast.LENGTH_LONG);
                    builder.setTitle("很遗憾，您打错了哦!");
                    builder.setMessage("客官，继续下一题么? ^0^\n"
                            +"(点击取消可重试哦^T^)")
                    .setIcon(R.drawable.wrong_face);
                }

                builder.setCancelable(false)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if((sign+1) % img_name_list.size() != 0){
                            sign++;
                            serial_label.setText(sign+"/"+img_name_list.size());
//                            imageAnimation.fadeIn(game_picture, img_resource_list.get(sign));
//                            imageAnimation.fadeOut(game_picture, img_resource_list.get(sign));
                            fadein_and_out(game_picture, img_resource_list.get(sign-1), img_resource_list.get(sign));
                        }
                        else{
                            scoreDialog.setTitle("您的最终得分为: \n")//标题
                                    .setMessage(score + "分")//内容
                                    .setIcon(R.drawable.game_logo1)//图标
                                    .setCancelable(true)
                                    .create().show();
                            score = 0;
                            sign = 0;
                        }
                        dialog.dismiss();
                    }}

                )
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        //finish();
                    }});

                Dialog dialog = builder.create();
                dialog.show();
                break;

            default:
                break;

        }
    }

}
