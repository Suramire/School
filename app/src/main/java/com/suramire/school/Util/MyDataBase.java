package com.suramire.school.Util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.suramire.school.R;
import com.suramire.school.haomabaishitong.Child;

/**
 * Created by Suramire on 2017/5/2.
 * 实现对数据库的相关操作
 */

public class MyDataBase extends SQLiteOpenHelper {
    private static final String TAG = "MyDataBase";
    SQLiteDatabase mydb;
    Context context;

    CharSequence[] groupsname;

    public MyDataBase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context = context;
        mydb = getWritableDatabase();
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
        return mydb.query("num", null, "name like ? or number like ? or keyword like ? ", new String[]{"%" + text + "%", "%" + text + "%" , "%" + text + "%"}, null, null, null);
    }

    /**
     * 根据分组的下标索引来获取数据
     *
     * @param index
     * @return
     */
    public Cursor selectByGroup(int index) {
        return mydb.query("num", null, "groupid = " + index, null, null, null, null);
    }

    /**
     * 插入数据（姓名电话分组）
     *
     * @param child
     * @return 插入位置
     */
    public long insert(Child child) {
        ContentValues values = new ContentValues();
        if (!"".equals(child.getKeyword())) {
            values.put("keyword",child.getKeyword());
        }else{
        }
        values.put("name", child.getName());
        values.put("number", child.getNumber());
        values.put("groupid", child.getGroupindex());
        return mydb.insert("num", null, values);
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
        db.execSQL("create table if not exists num(_id integer primary key autoincrement,name varchar,number varchar,groupid integer,keyword varchar)");
        db.execSQL("create table if not exists groups(_id integer primary key autoincrement,groupname varchar)");

        //将分组名提前写入数据库
        ContentValues values = new ContentValues();
        //获取分组名
        groupsname = context.getResources().getTextArray(R.array.groups);
        for (CharSequence name : groupsname) {
            values.put("groupname", name.toString());
            db.insert("groups", null, values);
            values.clear();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


}
