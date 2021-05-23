package com.example.jeas.netapp;

import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class into_game_activity extends AppCompatActivity implements View.OnClickListener{

    private Button submit_answer_btn;
    private Button read_answer_btn;
    private ImageView game_picture;
    private EditText answer_text;
    private String answer;
    private int sign = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_into_game_activity);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.hide();
        }

        answer_text = (EditText)findViewById(R.id.input_answer);
        submit_answer_btn = (Button)findViewById(R.id.submit_answer_btn);
        read_answer_btn = (Button)findViewById(R.id.read_answer_btn);
        game_picture = (ImageView)findViewById(R.id.game_picture);
        submit_answer_btn.setOnClickListener(this);
        read_answer_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.read_answer_btn:
                AlertDialog answerDialog = new AlertDialog.Builder(this)
                        .setTitle("客官,您要的答案:")//标题
                        .setMessage("萝莉")//内容
                        .setIcon(R.drawable.dialog_love)//图标
                        .setCancelable(true)
                        .create();
                answerDialog.show();
                break;

            case R.id.submit_answer_btn:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                answer = answer_text.getText().toString();
                Log.i("输入的文本为", answer);
                if(answer.equals("萝莉")){
                    //Toast.makeText(this, "恭喜你！答对了！！", Toast.LENGTH_LONG);
                    builder.setTitle("恭喜您答对了!!");
                    builder.setMessage("客官，继续下一题么? ^0^\n")
                            .setIcon(R.drawable.right_face);
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
                        if(sign == 0){
                            game_picture.setImageResource(R.drawable.game_material3);
                            sign++;
                        } else{
                            game_picture.setImageResource(R.drawable.game_material1);
                            sign--;
                        }
                        dialog.dismiss();
                    }})
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
