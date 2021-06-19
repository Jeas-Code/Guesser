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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import static com.example.jeas.netapp.media.Msg.TYPE_RECEIVED;

public class Chat extends AppCompatActivity {

    private List<Msg> msgList = new ArrayList<>();
    private EditText inputText;
    private Button send;
    private RecyclerView msgRecycleView;
    private MsgAdapter adapter;

    //
    public String host = "192.168.62.64";
    public int port = 8000;
    public Socket socket;
    //
//    //初始化Socket通信所需的类型
    private EditText mEditText;
//    private static final String TAG = "TAG";
//    private static final String HOST = "192.168.137.1";
//    private static final int PORT = 8001;
//    private PrintWriter printWriter;
//    private BufferedReader in;
//    private ExecutorService mExecutorService = null;
//    private String receiveMsg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        //启动服务器
        //socketTest = new SocketTest();
        //mTextView = (TextView) findViewById(R.id.textView);
        //mExecutorService = Executors.newCachedThreadPool();

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

        //mExecutorService = Executors.newCachedThreadPool();
        //connect(mEditText);
        inputText.setText(null);
        //设置发送按钮点击事件
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String content = inputText.getText().toString();
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

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        try {
                            String msg = content;
                            //BufferedReader br = getReader(socket);
                            PrintWriter pw = getWriter(socket);
                            if (msg != null && msg != "") {
                                //Log.i("info: ", "from client: (client)"+msg);
                                //客户端传给服务器的字符串msg
                                pw.println(msg);
                                //System.out.println(br.readLine());
                                pw.flush();
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();

            }
        });

//        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
//                .detectDiskReads().detectDiskWrites().detectNetwork()
//                .penaltyLog().build());
//        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
//                .detectLeakedSqlLiteObjects().penaltyLog().penaltyDeath()
//                .build());
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    socket = new Socket(host, port);
                    talk();
                } catch (UnsupportedEncodingException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }).start();

    }

//    public void initSocket(){
//            try{
//                socket=new Socket("192.168.0.99", 9999);
//                br = getReader(socket);
//                pw = getWriter(socket);
//            }catch (IOException e){
//                e.printStackTrace();
//            }
//    }

    private void initMsgs() {
        Msg msg1 = new Msg("Hello guy!", TYPE_RECEIVED);
        msgList.add(msg1);
        Msg msg2 = new Msg("Hello, Who is that?", Msg.TYPE_SENT);
        msgList.add(msg2);
        Msg msg3 = new Msg("This is Tom, Nice to meet you.", TYPE_RECEIVED);
        msgList.add(msg3);
        Msg msg4 = new Msg("Nice to meet you to!", Msg.TYPE_SENT);
        msgList.add(msg4);
        Msg msg5 = new Msg("How are you bro?", TYPE_RECEIVED);
        msgList.add(msg5);
    }

    //内部类EchoClient
//    public class EchoClient {

//        public EchoClient()throws IOException{
//            socket=new Socket(host,port);
//        }

    private PrintWriter getWriter(Socket socket) throws IOException {
        OutputStream socketOut = socket.getOutputStream();
        return new PrintWriter(socketOut, true);
    }

    private BufferedReader getReader(Socket socket) throws IOException {
        InputStream socketIn = socket.getInputStream();
        return new BufferedReader(new InputStreamReader(socketIn));
    }

    public void talk() throws IOException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                try {
                    BufferedReader br = getReader(socket);
                    //PrintWriter pw = getWriter(socket);
                    String msg = null;
                    //System.out.println(msg);
                    //pw.flush();
                    while ((msg = br.readLine()) != null && msg != "") {
                        //pw.println(msg);
                        Msg msgbox = new Msg(br.readLine(), Msg.TYPE_RECEIVED);
                        msgList.add(msgbox);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    //刷新新消息的显示
                                    adapter.notifyItemInserted(msgList.size() - 1);
                                    //将布局定位到最后一行最新的消息上
                                    msgRecycleView.scrollToPosition(msgList.size() - 1);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void onDestroy(){
        super.onDestroy();
    }

}
//        }
//
//    }


//    public void connect(View view) {
//        mExecutorService.execute(new connectService());  //在一个新的线程中请求 Socket 连接
//    }
//
//    public void send(View view) {
//        String sendMsg = mEditText.getText().toString();
//        mExecutorService.execute(new sendService(sendMsg));
//    }
//
//    public void disconnect(View view) {
//        mExecutorService.execute(new sendService("0"));
//    }
//
//    private class sendService implements Runnable {
//        private String msg;
//
//        sendService(String msg) {
//            this.msg = msg;
//        }
//
//        @Override
//        public void run() {
//            printWriter.println(this.msg);
//        }
//    }
//
//    private class connectService implements Runnable {
//        @Override
//        public void run() {//可以考虑在此处添加一个while循环，结合下面的catch语句，实现Socket对象获取失败后的超时重连，直到成功建立Socket连接
//            try {
//                Socket socket = new Socket(HOST, PORT);      //步骤一
//                socket.setSoTimeout(60000);
//                printWriter = new PrintWriter(new BufferedWriter(new OutputStreamWriter(   //步骤二
//                        socket.getOutputStream(), "UTF-8")), true);
//                in = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
//                receiveMsg();
//            } catch (Exception e) {
//                Log.e(TAG, ("connectService:" + e.getMessage()));   //如果Socket对象获取失败，即连接建立失败，会走到这段逻辑
//            }
//        }
//    }
//
//    private void receiveMsg() {
//        try {
//            while (true) {                                      //步骤三
//                if ((receiveMsg = mEditText.getText().toString()) != null) {
//                    Log.d(TAG, "receiveMsg:" + receiveMsg);
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            //mTextView.setText(receiveMsg + "\n\n" + mTextView.getText());
//                            Msg msg = new Msg(receiveMsg, Msg.TYPE_RECEIVED);
//                            msgList.add(msg);
//                            //刷新新消息的显示
//                            adapter.notifyItemInserted(msgList.size() - 1);
//                            //将布局定位到最后一行最新的消息上
//                            msgRecycleView.scrollToPosition(msgList.size() - 1);
//                        }
//                    });
//                }
//            }
//        } catch (Exception e) {
//            Log.e(TAG, "receiveMsg: ");
//            e.printStackTrace();
//        }
//    }
//}



//    public Chat()throws IOException{
//        socket=new Socket(host,port);
//    }
//
//    private PrintWriter getWriter(Socket socket)throws IOException {
//        OutputStream socketOut = socket.getOutputStream();
//        return new PrintWriter(socketOut,true);
//    }
//    private BufferedReader getReader(Socket socket)throws IOException{
//        InputStream socketIn = socket.getInputStream();
//        return new BufferedReader(new InputStreamReader(socketIn));
//    }
//
//    public void talk()throws IOException {
//        try{
//            BufferedReader br=getReader(socket);
//            PrintWriter pw=getWriter(socket);
//            BufferedReader localReader=new BufferedReader(new InputStreamReader(System.in));
//            String recemsg = inputText.getText().toString();
//            final String content = br.readLine();
//            while(recemsg != null){
//
//                pw.println(recemsg);
//                System.out.println(br.readLine());
//                //如果服务器输入内容不为空，则可接受该信息
//                runOnUiThread(new Runnable() {
//                    @Override
//            public void run() {
//                Msg msg = new Msg(content, Msg.TYPE_RECEIVED);
//                msgList.add(msg);
//                //刷新新消息的显示
//                adapter.notifyItemInserted(msgList.size() - 1);
//                //将布局定位到最后一行最新的消息上
//                msgRecycleView.scrollToPosition(msgList.size() - 1);
//                              }
//                    });
//                if(recemsg.equals("bye")) {
//                    break;
//                }
//
//                }
//
//        }catch(IOException e){
//            e.printStackTrace();
//        }finally{
//            try{socket.close();}catch(IOException e){e.printStackTrace();}
//        }
//    }

//    //实现Socket客户端通信的函数
//    public void connect(View view) {
//        mExecutorService.execute(new connectService());  //在一个新的线程中请求 Socket 连接
//    }
//
//    public void send(View view) {
//        String sendMsg = mEditText.getText().toString();
//        mExecutorService.execute(new sendService(sendMsg));
//    }
//
//    public void disconnect(View view) {
//        mExecutorService.execute(new sendService("0"));
//    }
//
//    private class sendService implements Runnable {
//        private String msg;
//
//        sendService(String msg) {
//            this.msg = msg;
//        }
//
//        @Override
//        public void run() {
//            printWriter.println(this.msg);
//        }
//    }
//
//    private class connectService implements Runnable {
//        @Override
//        public void run() {//可以考虑在此处添加一个while循环，结合下面的catch语句，实现Socket对象获取失败后的超时重连，直到成功建立Socket连接
//            try {
//                Socket socket = new Socket(HOST, PORT);      //步骤一
//                socket.setSoTimeout(5000);
//                printWriter = new PrintWriter(new BufferedWriter(new OutputStreamWriter(   //步骤二
//                        socket.getOutputStream(), "UTF-8")), true);
//                in = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
//                receiveMsg();
//            } catch (Exception e) {
//                Log.e(TAG, ("connectService:" + e.getMessage()));   //如果Socket对象获取失败，即连接建立失败，会走到这段逻辑
//            }
//        }
//    }
//
//    private void receiveMsg() {
//        try {
//            while (true) {                                      //步骤三
//                if ((receiveMsg = in.readLine()) != null) {
//                    Log.d(TAG, "receiveMsg:" + receiveMsg);
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            //mTextView.setText(receiveMsg + "\n\n" + mTextView.getText());
//                            Msg msg = new Msg(receiveMsg, Msg.TYPE_RECEIVED);
//                            msgList.add(msg);
//
//                        }
//                    });
//                }
//            }
//        } catch (IOException e) {
//            Log.e(TAG, "receiveMsg: ");
//            e.printStackTrace();
//        }
//    }
