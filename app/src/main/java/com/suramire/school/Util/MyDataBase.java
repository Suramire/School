package com.suramire.school.Util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.suramire.school.haomabaishitong.Child;

/**
 * Created by Suramire on 2017/5/2.
 * 实现对数据库的相关操作
 */

public class MyDataBase extends SQLiteOpenHelper {
    private static final String TAG = "MyDataBase";
    SQLiteDatabase mydb;
    Context context;


    public MyDataBase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context = context;
        mydb = getWritableDatabase();
    }
    public Cursor selectByNameNumber(String name, String number){
        return  mydb.query("num",null,"name =? and number=?",new String[]{name,number},null,null,null);
    }

    /**
     * 查询所有数据
     *
     * @return
     */
    public Cursor selectAll() {
        return mydb.query("num", null, null, null, null, null, null);
    }

    /**
     * 根据条件查询数据
     *
     * @return
     */
    public Cursor selectByAnything(String text) {
        return mydb.query("num", null, "name like ? or number like ? or keyword like ? ",
                new String[]{"%" + text + "%", "%" + text + "%" , "%" + text + "%"},
                null, null, null);
    }


    /**
     * 插入数据（
     *
     * @param child
     * @return 插入位置
     */
    public long insert(Child child) {
        ContentValues values = new ContentValues();
        values.put("name", child.getName());
        values.put("number", child.getNumber());
        values.put("keyword", child.getKeyword());
        values.put("pinyin", child.getPingyin());
        values.put("p", child.getP());
        return mydb.insert("num", null, values);
    }

    public int delete(String where,String[] strings){
       return  mydb.delete("num",where,strings);
    }


    /**
     * 关闭数据库
     */
    public void close() {
        if (mydb.isOpen())
            mydb.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if not exists num(_id integer primary key autoincrement," +
                "name varchar,number varchar,pinyin varchar,p varchar,keyword varchar)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


}
