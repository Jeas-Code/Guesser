package com.example.jeas.netapp.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by Jeas on 2021/6/4.
 */

public class MyDatabaseHelper extends SQLiteOpenHelper{

    public static final String CREATE_DATATABLE = "create table friend_info("
            +"id integer primary key autoincrement, "+
            "nickname text)";

    private Context mContext;
    public MyDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
                            int version){
        super(context, name, factory, version);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(CREATE_DATATABLE);
        Toast.makeText(mContext, "数据表创建成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        //db.execSQL(CREATE_DATATABLE);
    }

}
