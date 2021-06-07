package com.example.jeas.netapp.service;

/**
 * Created by Jeas on 2021/6/7.
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {
    public int port=8010;
    public ServerSocket serverSocket;

    public EchoServer() throws IOException {
        serverSocket = new ServerSocket(port);
        System.out.println("服务器启动");
    }

    public void service() {
        while (true) {
            Socket socket=null;
            try {
                socket = serverSocket.accept();  //接收客户连接
                Thread workThread=new Thread(new Handler(socket));  //创建一个工作线程
                workThread.start();  //启动工作线程
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String args[])throws IOException {
        new EchoServer().service();
    }
}

class Handler implements Runnable{
    private Socket socket;

    public Handler(Socket socket){
        this.socket=socket;
    }
    private PrintWriter getWriter(Socket socket)throws IOException{
        OutputStream socketOut = socket.getOutputStream();
        return new PrintWriter(socketOut,true);
    }
    private BufferedReader getReader(Socket socket)throws IOException{
        InputStream socketIn = socket.getInputStream();
        return new BufferedReader(new InputStreamReader(socketIn));
    }
    public String echo(String msg) {
        return "echo:" + msg;
    }
    public void run(){
        System.out.println("New connection accepted " +
                socket.getInetAddress() + ":" +socket.getPort());
        try {
            new Thread(new Runnable() {
                String msg=null;
                BufferedReader br =getReader(socket);
                PrintWriter pw = getWriter(socket);
                BufferedReader localReader=new BufferedReader(new InputStreamReader(System.in));
                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    try {
                        while((msg = localReader.readLine())!=null){
                            //客户端传给服务器的字符串msg
                            pw.println(msg);
                            System.out.println(br.readLine());
                            pw.flush();
                        }
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }).start();

        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        new Thread(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                try {
                    BufferedReader br =getReader(socket);
                    PrintWriter pw = getWriter(socket);
                    BufferedReader localReader=new BufferedReader(new InputStreamReader(System.in));

                    String msg = null;

                    while((msg = br.readLine())!= null){

                        //服务器端传给客户端的字符串echoe(msg)
                        pw.println(msg);
                        System.out.println(msg);
                        pw.flush();
                    }
                }catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    try{
                        if(socket!=null)socket.close();
                    }catch (IOException e) {e.printStackTrace();}
                }
            }}).start();
    }
}
