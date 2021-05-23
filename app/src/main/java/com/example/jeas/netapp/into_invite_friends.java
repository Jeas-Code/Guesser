package com.example.jeas.netapp;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class into_invite_friends extends AppCompatActivity implements View.OnClickListener{

    private Button add_friends_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_into_invite_friends);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.hide();
        }

        add_friends_btn = (Button)findViewById(R.id.add_friends_btn);
        add_friends_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.add_friends_btn:
                Toast.makeText(this, "功能暂未上线，敬请期待！！", Toast.LENGTH_LONG).show();
        }
    }
}
