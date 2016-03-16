package com.example.teav.zhihtest.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.teav.zhihtest.model.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by TeaV on 2016/2/2.
 */
public class ZhihuDB {
    public static final String DB_NAME = "Zhihu";

    public static int VERSION = 1;

    private static ZhihuDB Zhihudb;

    private SQLiteDatabase db;

    private ZhihuDB(Context context) {
        DBOpenHelper dbOpenHelper = new DBOpenHelper(context, DB_NAME, null, VERSION);
        db = dbOpenHelper.getWritableDatabase();
    }

    public synchronized static ZhihuDB getInstance(Context context) {
        if (Zhihudb == null) {
            Zhihudb = new ZhihuDB(context);
        }
        return Zhihudb;
    }

    /**
     * 将用户存入数据库
     */
    public void saveUser(User user) {
        if (user != null) {
            db.execSQL("insert into User (username,password) values (?,?)",
                    new String[] {user.getUsername(), user.getPassword()});
        }
    }

    /**
     * 将问题存入数据库
     */
    public void saveProblem(Problem problem) {
        if (problem != null) {
            db.execSQL("insert into Problem (askuserid,problemtext) values (?,?)",
                    new String[] {String.valueOf(problem.getAskUserId()), problem.getProblemText()});
        }
    }

    /**
     * 取出所有问题
     */
    public List<Problem> getAllproblem() {
        List<Problem> list = new ArrayList<Problem>();
        Cursor cursor = db.rawQuery("select * from Problem", null);
        if (cursor.moveToFirst()) {
            do {
                Problem problem = new Problem();
                problem.setId(cursor.getInt(cursor.getColumnIndex("problemid")));
                problem.setProblemText(cursor.getString(cursor.getColumnIndex("problemtext")));
                problem.setAskUserId(cursor.getInt(cursor.getColumnIndex("askuserid")));
                list.add(problem);
            }while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
        //cursor.close();
        Collections.reverse(list);
        return list;
    }

    /**
     * 查询是否有该用户
     */
    public boolean checkUser(User user) {
        boolean isBe = false;
        String db_username, db_password;

        Cursor cursor = db.rawQuery("select * from User", null);
        if (cursor.moveToFirst()) {
            do {
                db_username = cursor.getString(cursor.getColumnIndex("username"));
                db_password = cursor.getString(cursor.getColumnIndex("password"));
                if (user.getUsername().equals(db_username) &&
                    user.getPassword().equals(db_password)){
                    isBe = true;
                    break;
                }
            }while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
        return isBe;
    }
}
