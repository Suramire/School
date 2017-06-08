package com.suramire.school.Util;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.CommonDataKinds.StructuredName;
import android.provider.ContactsContract.Contacts.Data;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.suramire.school.haomabaishitong.Child;

import java.util.ArrayList;
import java.util.HashMap;


public  class MyContentProvider extends Activity {

    public MyContentProvider(ContentResolver contentResolver) {
        this.contentResolver = contentResolver;
    }

    private ContentResolver contentResolver;
    private String name;
    private String number;
    private long id;

    /**
     * 将财大通联系人写入系统通许路
     */


    public  void write(String username,String phonenumber) {
        try {
            Uri uri = ContactsContract.RawContacts.CONTENT_URI;
            ContentValues values =new ContentValues();
            //加入一行空数据
            Uri insert = contentResolver.insert(uri, values);
            //获取aw_contacts中的id
            long id =ContentUris.parseId(insert);
            //往data数据库插入数据
            values.put(Data.RAW_CONTACT_ID,id);//RAW_CONTACT_ID列
            values.put(Data.MIMETYPE, StructuredName.CONTENT_ITEM_TYPE);//类型 7(名字)
            values.put(StructuredName.DISPLAY_NAME,username);//显示的名字
//		插入联系人名字
            contentResolver.insert(ContactsContract.Data.CONTENT_URI, values);
            values.clear();
            values.put(Data.RAW_CONTACT_ID,id);//RAW_CONTACT_ID列
            values.put(Data.MIMETYPE, Phone.CONTENT_ITEM_TYPE);//类型 5(电话)
            values.put(Phone.NUMBER,phonenumber);//电话号码
            values.put(Phone.TYPE, Phone.TYPE_MOBILE);//号码类型 移动电话
            contentResolver.insert(ContactsContract.Data.CONTENT_URI,values);
        } catch (Exception e) {
            Toast.makeText(this, "下载联系人捕获异常"+e, Toast.LENGTH_SHORT).show();
        }
    }
    /**
     * 读取系统通讯录联系人
     */

    public ArrayList<Child> read() {
        ArrayList<Child> data = new ArrayList<>();
        try {
            //读取 id和名字
            Cursor query = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);

            while (query.moveToNext()){
                id = query.getLong(query.getColumnIndex(ContactsContract.Contacts._ID));
                //显示的名字
                name = query.getString(query.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                Cursor query1 = contentResolver.query(Phone.CONTENT_URI, null, Phone.CONTACT_ID + "=" + id, null, null);
                //读取电话号码
                if(query1.moveToNext()){
                    number = query1.getString(query1.getColumnIndex(Phone.NUMBER));
                }
                query1.close();
                Child child = new Child(name,number);
                data.add(child);
            }
            query.close();
        } catch (Exception e) {
            Toast.makeText(this, "读取联系人捕获异常"+e, Toast.LENGTH_SHORT).show();
        }
        return data;

    }

}
