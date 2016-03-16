package com.example.teav.zhihtest.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by TeaV on 2016/2/2.
 */
public class DBOpenHelper extends SQLiteOpenHelper{
    /**
     * User表建表语句
     */
    public static final String CREATE_USER = "create table User (" +
            "userid integer primary key autoincrement, " +
            "username text, " +
            "password text)";
    /**
     * Problem表建表语句
     */
    public static final String CREATE_PROBLEM = "create table Problem (" +
            "problemid integer primary key autoincrement, " +
            "askuserid integer, " +
            "problemtext text)";

    public DBOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_USER);
        db.execSQL(CREATE_PROBLEM);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
