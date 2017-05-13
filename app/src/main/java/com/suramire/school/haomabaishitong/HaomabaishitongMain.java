package com.suramire.school.haomabaishitong;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.support.v7.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.suramire.school.MyActivity;
import com.suramire.school.R;
import com.suramire.school.Util.MyDataBase;

import java.util.ArrayList;

/**
 * Created by Suramire on 2017/5/1.
 */

public class HaomabaishitongMain extends MyActivity {
    private static final String TAG = "SCHOOL";
    ArrayList<Parent> parents = new ArrayList<Parent>();
    ArrayList<Child> children = new ArrayList<Child>();
    ExpandableListView expandableListView;
    MyDataBase myDataBase;
    private CharSequence[] groups;
    private Child child;
    private int count;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.haomabaishitong_main);
        expandableListView = (ExpandableListView) findViewById(R.id.expandedList);
        // TODO: 2017/5/1 从数据库中读取号码信息并显示
        //// TODO: 2017/5/13 无数据时显示对应信息
        groups = getResources().getTextArray(R.array.groups);
        getData();
        setView();

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
     * 从数据库获取数据
     */

    private void getData() {

//        myDataBase = new MyDataBase(this,groups);
        myDataBase = new MyDataBase(this, "number.db", null, 1);
        //联系人总数 >0 表示列表不为空
        count = 0;
        //从stringarray中读取分组名字
        int i = 0;
        children.clear();
        for (CharSequence groupname : groups) {
            // TODO: 2017/5/13 获取每个分组下的信息记录
            Cursor cursor1 = myDataBase.selectByGroup(i);
            if (cursor1.getCount() != 0) {
                count++;
                //该分组下有数据，添加带数据的分组
                while (cursor1.moveToNext()) {
                    String name = cursor1.getString(cursor1.getColumnIndex("name"));
                    Log.e(TAG, "getData: name" + name);
                    String number = cursor1.getString(cursor1.getColumnIndex("number"));
                    Log.e(TAG, "getData: number" + number);
                    child = new Child(name, number);
                    children.add(child);
                }
                cursor1.close();
                parents.add(new Parent(children, groupname.toString()));
                children.clear();
            } else {
                //添加空分组

                parents.add(new Parent(groupname.toString()));
            }
            i++;
        }
        if(count==0){
            Toast.makeText(this, "暂无联系人信息，点击右上角添加。", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 显示结果集内的数据
     *
     * @param cursor 给定的结果集
     */
    // TODO: 2017/5/13 对搜索到的结果进行分组
    private void getData(Cursor cursor) {
        if (cursor.getCount() != 0) {
            children.clear();
            parents.clear();
            while (cursor.moveToNext()) {
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String number = cursor.getString(cursor.getColumnIndex("number"));
                Child child = new Child(name, number);
                children.add(child);
            }
            parents.add(new Parent(children, "搜索结果"));
            cursor.close();
        } else {
            Toast.makeText(this, "没有符合要求的结果", Toast.LENGTH_SHORT).show();
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
                view = LayoutInflater.from(HaomabaishitongMain.this).inflate(R.layout.haomaparent, viewGroup, false);
                TextView parentName = (TextView) view.findViewById(R.id.textView);
                parentName.setText(parents.get(i).getParentName());
                return view;
            }

            @Override
            public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
                //// TODO: 2017/5/1 用viewholder进行优化
                view = LayoutInflater.from(HaomabaishitongMain.this).inflate(R.layout.haomachild, viewGroup, false);
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
        for (int i = 0; i < parents.size(); i++) {
            expandableListView.expandGroup(i);
        }
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Log.e(TAG, "onChildClick: "+groupPosition +" / "+childPosition +" / " +id);
                return false;
            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.haoma_menu, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search2).getActionView();
        //设置提示文字
        searchView.setQueryHint("输入姓名或电话号码进行搜索");
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
        switch (item.getItemId()) {
            case R.id.action_add:
                Intent intent = new Intent(this, NewNumber.class);
                startActivity(intent);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
