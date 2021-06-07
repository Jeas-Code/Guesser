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
import java.net.Socket;
public class EchoClient {
    private String host="localhost";
    private int port=8010;
    private Socket socket;

    public EchoClient()throws IOException{
        socket=new Socket(host,port);
    }
    public static void main(String args[])throws IOException{
        new EchoClient().talk();
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
    public void talk()throws IOException {

        new Thread(new Runnable() {
            BufferedReader br =getReader(socket);
            PrintWriter pw = getWriter(socket);
            String msg=null;
            @Override
            public void run() {
                // TODO Auto-generated method stub
                try {
                    while((msg = br.readLine())!= null){
                        //服务器端传给客户端的字符串echoe(msg)
                        pw.println(msg);
                        System.out.println(msg);
                        pw.flush();
                    }
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        }).start();

        new Thread(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                try{
                    BufferedReader br=getReader(socket);
                    PrintWriter pw=getWriter(socket);
                    BufferedReader localReader=new BufferedReader(new InputStreamReader(System.in));
                    String msg=null;
                    while((msg = localReader.readLine())!=null){
                        //客户端传给服务器的字符串msg
                        pw.println(msg);
                        System.out.println(br.readLine());
                        pw.flush();
                    }

                }catch(IOException e){
                    e.printStackTrace();
                }finally{
                    try{socket.close();}catch(IOException e){e.printStackTrace();}
                }
            }
        }).start();
    }
}
