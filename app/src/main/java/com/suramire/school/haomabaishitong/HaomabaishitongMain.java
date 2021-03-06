package com.suramire.school.haomabaishitong;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.SearchView;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.suramire.school.MyActivity;
import com.suramire.school.R;
import com.suramire.school.Util.MyContentProvider;
import com.suramire.school.Util.MyDataBase;
import com.suramire.school.Util.MyHandler;
import com.suramire.school.Util.PinyinComparator;
import com.suramire.school.Util.SideBar;
import com.suramire.school.Util.SortAdapter;

import java.util.ArrayList;
import java.util.Collections;

import static com.suramire.school.Util.MyHandler.MYHANDLER_DOWNLOAD;
import static com.suramire.school.Util.MyHandler.MYHANDLER_SINGLEDELETE;
import static com.suramire.school.Util.MyHandler.MYHANDLER_SINGLEDOWNLOAD;
import static com.suramire.school.Util.MyHandler.MYHANDLER_UPLOAD;

/**
 * Created by Suramire on 2017/5/1.
 * 号码百事通主界面 分组显示号码 提供模糊查询功能
 */

public class HaomabaishitongMain extends MyActivity {
    private MyDataBase myDataBase;
    private ListView listView;
    private TextView dialog;
    private SideBar sideBar;
    private SortAdapter sortAdapter;
    private ArrayList<Child> children;
    private MyHandler myHandler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.haomabaishitong_main);
        myDataBase = new MyDataBase(this, "number.db", null, 1);
        listView = (ListView) findViewById(R.id.listview2);
        sideBar = (SideBar) findViewById(R.id.sidebar);
        dialog = (TextView) findViewById(R.id.dialog);
        registerForContextMenu(listView);
        BroadcastReceiver receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                init();
            }
        };
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.suramire.school.refresh");
        registerReceiver(receiver,filter);
        init();

    }

    /**
     * 初始化上下文菜单
     * @param menu
     * @param v
     * @param menuInfo
     */
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.contextmenu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int position = menuInfo.position;//view中item下标
        Child mchild = children.get(position);
        switch (item.getItemId()) {
            case  R.id.menu_phone:
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("tel:"+mchild.getNumber()));
                startActivity(intent);
                break;
            case R.id.menu_download:
                myHandler = new MyHandler(myDataBase,new MyContentProvider(getContentResolver()),children,HaomabaishitongMain.this);
                Message msg = Message.obtain();
                msg.what = MYHANDLER_SINGLEDOWNLOAD;
                msg.obj = mchild;
                myHandler.sendMessage(msg);//发送单条删除的消息
                break;
            case R.id.menu_delete:
                myHandler = new MyHandler(
                        myDataBase,//数据库操作对象
                        new MyContentProvider(getContentResolver()),//系统通讯录操作对象
                        children,//当前列表
                        HaomabaishitongMain.this);//上下文对象
                Message message = Message.obtain();
                message.what = MYHANDLER_SINGLEDELETE;//将要执行单条删除操作
                message.obj = mchild;//将要删除的联系人对象
                myHandler.sendMessage(message);//发送删除消息,交由Handler处理
                break;
            default:
                break;
        }
        return false;
    }

    /**
     * 初始化listview 包括获取数据与设置listview
     *  2017年6月7日 20:31:04
     */
    private void init() {
        //获取数据
        try{
            ArrayList<Child> data = getData();
            if (data.size() <= 0) {
                Toast.makeText(HaomabaishitongMain.this, "暂无联系人数据,请先添加", Toast.LENGTH_SHORT).show();
                listView.setAdapter(new SortAdapter(this, data));
            } else {
                //进行排序
                Collections.sort(data, new PinyinComparator());
                sideBar.setTextView(dialog);
                //设置适配器
                sortAdapter = new SortAdapter(this, data);
                sideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {

                    @Override
                    public void onTouchingLetterChanged(String s) {
                        // 该字母首次出现的位置
                        int position = sortAdapter.getPositionForSelection(s.charAt(0));
                        if (position != -1) {
                            listView.setSelection(position);
                        }
                    }
                });
                listView.setAdapter(sortAdapter);
            }
        }catch (Exception e){
            Toast.makeText(this, "初始化页面失败:"+e, Toast.LENGTH_SHORT).show();
        }



    }

    /**
     * 根据条件初始化listview
     * 2017年6月7日 20:31:09
     * @param something 查询条件
     */
    public void initBy(String something) {
        //获取数据
        ArrayList<Child> data = getDataBy(something);

        try {
            if (data.size() <= 0) {
                Toast.makeText(HaomabaishitongMain.this, "暂无符合条件的联系人数据", Toast.LENGTH_SHORT).show();
                listView.setAdapter(new SortAdapter(this, data));
            } else {
                //进行排序
                Collections.sort(data, new PinyinComparator());
                sideBar.setTextView(dialog);
                //设置适配器
                sortAdapter = new SortAdapter(this, data);
                sideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {

                    @Override
                    public void onTouchingLetterChanged(String s) {
                        // 该字母首次出现的位置
                        int position = sortAdapter.getPositionForSelection(s.charAt(0));
                        if (position != -1) {
                            listView.setSelection(position);
                        }
                    }
                });
                listView.setAdapter(sortAdapter);
            }
        } catch (Exception e) {
            Toast.makeText(this, "搜索失败 异常"+e, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onStart() {
        myDataBase = new MyDataBase(this, "number.db", null, 1);
        super.onStart();
    }

    @Override
    protected void onStop() {
        myDataBase.close();
        super.onStop();

    }

    /**
     * 获取数据库所有联系人的数据
     * 2017年6月7日 20:31:46
     * @return 从数据获取的联系人列表
     */
    private ArrayList<Child> getData() {
        children = new ArrayList<>();
        //获取数据 存入list
        try {
            Cursor cursor = myDataBase.selectAll();
            while (cursor.moveToNext()) {
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String number = cursor.getString(cursor.getColumnIndex("number"));
                String pinyin = cursor.getString(cursor.getColumnIndex("pinyin"));
                String keyword = cursor.getString(cursor.getColumnIndex("keyword"));
                String p = cursor.getString(cursor.getColumnIndex("p"));
                if (keyword == null || keyword.length() <= 0) {
                    Child child = new Child(name, number, pinyin, p);
                    children.add(child);
                } else {
                    Child child = new Child(name, number, keyword, pinyin, p);
                    children.add(child);
                }
            }
        } catch (Exception e) {
            Toast.makeText(this, "获取联系人数据异常"+e, Toast.LENGTH_SHORT).show();
        }
        return children;
    }

    /**
     * 根据条件获取数据库联系人数据
     * 2017年6月7日 20:32:06
     * @param something 查询条件
     * @return 符合条件的联系人信息
     */
    private ArrayList<Child> getDataBy(String something) {
        children = new ArrayList<>();
        try {
            //获取数据 存入list
            Cursor cursor = myDataBase.selectByAnything(something);
            while (cursor.moveToNext()) {
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String number = cursor.getString(cursor.getColumnIndex("number"));
                String pinyin = cursor.getString(cursor.getColumnIndex("pinyin"));
                String keyword = cursor.getString(cursor.getColumnIndex("keyword"));
                String p = cursor.getString(cursor.getColumnIndex("p"));
                if (keyword == null || keyword.length() <= 0) {
                    Child child = new Child(name, number, pinyin, p);
                    children.add(child);
                } else {
                    Child child = new Child(name, number, keyword, pinyin, p);
                    children.add(child);
                }
            }
        } catch (Exception e) {
            Toast.makeText(this, "获取联系人数据异常"+e, Toast.LENGTH_SHORT).show();
        }
        return children;
    }


    /**
     * 当从添加号码页面返回时刷新联系人信息
     */
    @Override
    protected void onRestart() {
        super.onRestart();
        myDataBase = new MyDataBase(this, "number.db", null, 1);
        init();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.haoma_menu, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search2).getActionView();
        //设置提示文字
        searchView.setQueryHint("输入姓名或号码或关键字搜索");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                initBy(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                initBy(s);
                return false;
            }
        });
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                Intent intent = new Intent(this, NewNumber.class);
                startActivity(intent);
                break;
            case R.id.action_download:
                myHandler = new MyHandler(myDataBase,new MyContentProvider(getContentResolver()),children,HaomabaishitongMain.this);
                Message msg = Message.obtain();
                msg.what = MYHANDLER_DOWNLOAD;
                myHandler.sendMessage(msg);
                break;

            case R.id.action_upload:
                myHandler = new MyHandler(myDataBase,new MyContentProvider(getContentResolver()),children,HaomabaishitongMain.this);
                Message message = Message.obtain();
                message.what = MYHANDLER_UPLOAD;
                myHandler.sendMessage(message);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }


}
