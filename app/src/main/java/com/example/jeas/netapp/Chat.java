package com.example.jeas.netapp;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jeas.netapp.adapter.MsgAdapter;
import com.example.jeas.netapp.media.Msg;

import java.util.ArrayList;
import java.util.List;

public class Chat extends AppCompatActivity {

    private List<Msg> msgList = new ArrayList<>();
    private EditText inputText;
    private Button send;
    private RecyclerView msgRecycleView;
    private MsgAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        initMsgs();

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        inputText = (EditText) findViewById(R.id.input_text);
        send = (Button) findViewById(R.id.send);
        //实例化布局对象
        msgRecycleView = (RecyclerView) findViewById(R.id.msg_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        //想关函数调入相关实例化对象
        msgRecycleView.setLayoutManager(layoutManager);
        adapter = new MsgAdapter(msgList);
        msgRecycleView.setAdapter(adapter);

        //设置发送按钮点击事件
        send.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String content = inputText.getText().toString();
                //如果输入内容不为空，则可发送该信息
                if (!"".equals(content)) {
                    Msg msg = new Msg(content, Msg.TYPE_SENT);
                    msgList.add(msg);
                    //刷新新消息的显示
                    adapter.notifyItemInserted(msgList.size() - 1);
                    //将布局定位到最后一行最新的消息上
                    msgRecycleView.scrollToPosition(msgList.size() - 1);
                    //发送完毕清空输入框中的内容
                    inputText.setText("");
                } else {
                    Toast.makeText(Chat.this, "输入内容不可为空！", Toast.LENGTH_SHORT).show();
                    inputText.setText("");
                }
            }
        });

    }

    private void initMsgs() {
        Msg msg1 = new Msg("Hello guy!", Msg.TYPE_RECEIVED);
        msgList.add(msg1);
        Msg msg2 = new Msg("Hello, Who is that?", Msg.TYPE_SENT);
        msgList.add(msg2);
        Msg msg3 = new Msg("This is Tom, Nice to meet you.", Msg.TYPE_RECEIVED);
        msgList.add(msg3);
        Msg msg4 = new Msg("Nice to meet you to!", Msg.TYPE_SENT);
        msgList.add(msg4);
        Msg msg5 = new Msg("How are you bro?", Msg.TYPE_RECEIVED);
        msgList.add(msg5);
    }
}