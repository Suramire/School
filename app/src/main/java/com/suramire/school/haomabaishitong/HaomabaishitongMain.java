package com.suramire.school.haomabaishitong;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.support.v7.widget.SearchView;
import android.widget.TextView;

import com.suramire.school.R;
import com.suramire.school.Util.MyDataBase;

import java.util.ArrayList;

/**
 * Created by Suramire on 2017/5/1.
 */

public class HaomabaishitongMain extends AppCompatActivity {
    ArrayList<Parent> parents = new ArrayList<Parent>();
    ArrayList<Child> children = new ArrayList<Child>();
    ExpandableListView expandableListView;
    MyDataBase myDataBase;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.haomabaishitong_main);
        expandableListView = (ExpandableListView) findViewById(R.id.expandedList);
        // TODO: 2017/5/1 从数据库中读取号码信息并显示
        getData();
        setView();

    }

    @Override
    protected void onStart() {
        myDataBase = new MyDataBase(getApplicationContext());
        super.onStart();

    }

    @Override
    protected void onStop() {
        myDataBase.close();
        super.onStop();

    }

    /**
     * 从数据库获取数据
     */

    private void getData() {

        myDataBase = new MyDataBase(this);
        Cursor cursor = myDataBase.selectAll();
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    String name = cursor.getString(cursor.getColumnIndex("name"));
                    String number = cursor.getString(cursor.getColumnIndex("number"));
                    Child child = new Child(name, number);
                    children.add(child);
                }
                parents.add(new Parent(children, "默认分组"));
                cursor.close();
            }
    }

    /**
     * 显示结果集内的数据
     * @param cursor 给定的结果集
     */
    private void getData(Cursor cursor) {
        if (cursor != null) {
            children.clear();
            parents.clear();
            while (cursor.moveToNext()) {
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String number = cursor.getString(cursor.getColumnIndex("number"));
                Child child = new Child(name, number);
                children.add(child);
            }
            parents.add(new Parent(children, "默认分组"));
            cursor.close();
        }
    }

    /**
     * 将数据库中的数据装入适配器，并显示数据
     */
    private void setView() {

        BaseExpandableListAdapter adapter = new BaseExpandableListAdapter() {
            @Override
            public int getGroupCount() {
                return parents.size();
            }

            @Override
            public int getChildrenCount(int i) {
                return parents.get(i).getChildArrayList().size();
            }

            @Override
            public Object getGroup(int i) {
                return parents.get(i);
            }

            @Override
            public Object getChild(int i, int i1) {
                return parents.get(i).getChildArrayList().get(i1);
            }

            @Override
            public long getGroupId(int i) {
                return i;
            }

            @Override
            public long getChildId(int i, int i1) {
                return i1;
            }

            @Override
            public boolean hasStableIds() {
                return false;
            }

            @Override
            public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
                //// TODO: 2017/5/1 用viewholder进行优化 
                view  = LayoutInflater.from(HaomabaishitongMain.this).inflate(R.layout.haomaparent,viewGroup,false);
                TextView parentName = (TextView) view.findViewById(R.id.textView);
                parentName.setText(parents.get(i).getParentName());
                return view;
            }

            @Override
            public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
                //// TODO: 2017/5/1 用viewholder进行优化
                view  = LayoutInflater.from(HaomabaishitongMain.this).inflate(R.layout.haomachild,viewGroup,false);
                TextView childName = (TextView) view.findViewById(R.id.textView5);
                TextView childNumber = (TextView) view.findViewById(R.id.textView6);

                childName.setText(parents.get(i).getChildArrayList().get(i1).getName());
                childNumber.setText(parents.get(i).getChildArrayList().get(i1).getNumber());
                return view;
            }

            @Override
            public boolean isChildSelectable(int i, int i1) {
                return true;
            }
        };
        expandableListView.setAdapter(adapter);
        //展开列表
        for(int i=0;i<parents.size();i++) {
            expandableListView.expandGroup(i);
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.haoma_menu, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search2).getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                //// TODO: 2017/5/1 查询所要的号码并显示结果
                getData(myDataBase.selectByAnything(s));
                setView();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                getData(myDataBase.selectByAnything(s));
                setView();
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_add:
                Intent intent = new Intent(this, NewNumber.class);
                startActivity(intent);
                break;
            default:break;
        }
        return super.onOptionsItemSelected(item);
    }
}
