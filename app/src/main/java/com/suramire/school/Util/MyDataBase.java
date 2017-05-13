package com.suramire.school.Util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.suramire.school.haomabaishitong.Child;

/**
 * Created by Suramire on 2017/5/2.
 */

public class MyDataBase {
    SQLiteDatabase db;
    public MyDataBase(Context context) {
        db = context.openOrCreateDatabase("number.db",Context.MODE_PRIVATE,null);
        init();
    }

    /**
     * 初始化 创建表(如果不存在)
     */
    public void init(){
        db.execSQL("create table if not exists num(_id integer primary key autoincrement,name varchar,number varchar,groupid integer,keyword varchar)");
        db.execSQL("create table if not exists groups(_id integer primary key autoincrement,groupname varchar)");
    }

    /**
     * 查询所有数据
     * @return
     */
    public Cursor selectAll(){
        return db.query("num", null, null, null, null, null, null);
    }

    /**
     * 根据条件查询数据
     * @return
     */
    public Cursor selectByAnything(String text){
        return db.query("num",null,"name like ? or number like ? ",new String[]{"%"+text+"%","%"+text+"%"},null,null,null);
    }

    /**
     * 插入数据（姓名电话分组）
     * @param child
     * @return 插入位置
     */
    public long insert(Child child){
        ContentValues values = new ContentValues();
        values.put("name",child.getName());
        values.put("number",child.getNumber());
        values.put("groupid",child.getGroupindex());
        return db.insert("num",null,values);
    }

    /**
     * 关闭数据库
     */
    public void close(){
        if(db.isOpen())
            db.close();
    }

}
