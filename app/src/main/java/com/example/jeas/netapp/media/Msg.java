package com.example.jeas.netapp.media;

/**
 * Created by Jeas on 2021/1/18.
 */

public class Msg {
    public static final int TYPE_RECEIVED = 0;
    public static final int TYPE_SENT = 1;
    private String content;
    private int type;

    //改写构造方法
    public Msg(String content, int type){
        this.content = content;
        this.type = type;
    }

    public String getContent(){
        return content;
    }

    public  int getType(){
        return type;
    }

}
