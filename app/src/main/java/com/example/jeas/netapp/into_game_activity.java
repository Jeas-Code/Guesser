package com.example.jeas.netapp;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class into_game_activity extends AppCompatActivity implements View.OnClickListener{

    private Button submit_answer_btn;
    private EditText answer_text;
    private String answer;

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
        submit_answer_btn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.submit_answer_btn:
                answer = answer_text.getText().toString();
                Log.i("输入的文本为", answer);
                if(answer.equals("萝莉")||answer.equals("萌妹子")||answer.equalsIgnoreCase("twins")){
                    Toast.makeText(this, "恭喜你！答对了！！", Toast.LENGTH_LONG);
                }else{
                    Toast.makeText(this, "答错了喔！请再试一次吧！！", Toast.LENGTH_LONG);
                }
                break;

            default:
                break;

        }
    }

}
