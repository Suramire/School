package com.suramire.school.haomabaishitong;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.maps.model.Text;
import com.suramire.school.MyActivity;
import com.suramire.school.R;
import com.suramire.school.Util.MyContentProvider;
import com.suramire.school.Util.MyDataBase;

import java.util.ArrayList;

/**
 * Created by Suramire on 2017/5/1.
 * 号码百事通主界面 分组显示号码 提供模糊查询功能
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
    private BaseExpandableListAdapter adapter;
    private boolean once = true;
    private MyContentProvider myContentProvider;
    private long insert;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.haomabaishitong_main);
        expandableListView = (ExpandableListView) findViewById(R.id.expandedList);
        groups = getResources().getTextArray(R.array.groups);
        getSupportActionBar().setTitle("");
        adapter = new BaseExpandableListAdapter() {
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
                view = LayoutInflater.from(HaomabaishitongMain.this).inflate(R.layout.haomaparent, viewGroup, false);
                TextView parentName = (TextView) view.findViewById(R.id.textView);
                parentName.setText(parents.get(i).getParentName());
                return view;
            }

            @Override
            public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
                view = LayoutInflater.from(HaomabaishitongMain.this).inflate(R.layout.haomachild, viewGroup, false);
                view.setTag(R.layout.haomachild);
                TextView childName = (TextView) view.findViewById(R.id.textView5);
                TextView childNumber = (TextView) view.findViewById(R.id.textView6);
                TextView childKeyword = (TextView) view.findViewById(R.id.textView9);
                childName.setText(parents.get(i).getChildArrayList().get(i1).getName());
                childNumber.setText(parents.get(i).getChildArrayList().get(i1).getNumber());
                childKeyword.setText(parents.get(i).getChildArrayList().get(i1).getKeyword());
                return view;
            }

            @Override
            public boolean isChildSelectable(int i, int i1) {
                return true;
            }
        };
        expandableListView.setAdapter(adapter);
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
        parents.clear();
        myDataBase = new MyDataBase(this, "number.db", null, 1);
        //联系人总数 >0 表示列表不为空
        count = 0;
        //从stringarray中读取分组名字
        int i = 0;
        children.clear();
        for (CharSequence groupname : groups) {
            Cursor cursor1 = myDataBase.selectByGroup(i);
            if (cursor1.getCount() != 0) {
                count++;
                //该分组下有数据，添加带数据的分组
                while (cursor1.moveToNext()) {
                    String name = cursor1.getString(cursor1.getColumnIndex("name"));
                    String number = cursor1.getString(cursor1.getColumnIndex("number"));
                    String keyword = cursor1.getString(cursor1.getColumnIndex("keyword"));
                    child = new Child(name, number,keyword);
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
    private void getData(Cursor cursor) {
        parents.clear();
        if (cursor.getCount() != 0) {
            ArrayList<Child> children2 = new ArrayList<Child>();
            children.clear();
            parents.clear();
            while (cursor.moveToNext()) {
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String number = cursor.getString(cursor.getColumnIndex("number"));
                String keyword = cursor.getString(cursor.getColumnIndex("keyword"));
                int gid = cursor.getInt(cursor.getColumnIndex("groupid"));
                Child child = new Child(name, number,gid,keyword);
                switch (gid){
                    case  0:
                        children2.add(child);
                        break;
                    case  1:
                        children.add(child);
                        break;
                }
            }
            CharSequence[] groups = getResources().getTextArray(R.array.groups);
            parents.add(new Parent(children2,groups[0].toString()));
            parents.add(new Parent(children,groups[1].toString()));
            children2.clear();
            children.clear();
            cursor.close();
            once = true;
        } else {
            if(once)
            Toast.makeText(this, "没有符合要求的结果", Toast.LENGTH_SHORT).show();
            once = false;
        }
    }

    /**
     * 将数据库中的数据装入适配器，并显示数据
     */

    private void setView() {

        adapter.notifyDataSetChanged();
        //展开列表
        for (int i = 0; i < parents.size(); i++) {
            expandableListView.expandGroup(i);
        }
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                return false;
            }
        });
        expandableListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            private TextView phone;
            private TextView name;

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                name = (TextView) view.findViewById(R.id.textView5);
                phone = (TextView) view.findViewById(R.id.textView6);
                AlertDialog.Builder builder  = new AlertDialog.Builder(HaomabaishitongMain.this);
                builder.setTitle("提示").setMessage("是否删除该联系人信息").setIcon(R.mipmap.ic_launcher);
                builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String nameString = name.getText().toString();
                        String phoneString = phone.getText().toString();
                        int delete = myDataBase.delete("name=? and number=?", new String[]{nameString, phoneString});
                        if(delete !=0){
                            Toast.makeText(HaomabaishitongMain.this, "删除成功", Toast.LENGTH_SHORT).show();
                            getData();
                            setView();
                        }else{
                            Toast.makeText(HaomabaishitongMain.this, "删除失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.setCancelable(false);
                builder.create().show();

                return false;
            }
        });

    }

    /**
     * 当从添加号码页面返回时刷新联系人信息
     */
    @Override
    protected void onRestart() {
        super.onRestart();
        getData();
        setView();
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
            case R.id.action_download:
                //将财大通的联系人数据下载到本地
                //// FIXME: 2017/6/6  下载联系人重复
                try {
                    boolean has = false;
                    myContentProvider = new MyContentProvider(getContentResolver());
                    for(Parent parent:parents){
                        if(parent.getChildArrayList().size()!=0){
                            has = true;
                            ArrayList<Child> childArrayList = parent.getChildArrayList();
                            for(Child child:childArrayList){
                                myContentProvider.write(child.getName(),child.getNumber());
                            }
                        }else{

                        }
                        if(has){
                            Toast.makeText(HaomabaishitongMain.this, "下载联系人信息成功", Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(HaomabaishitongMain.this, "下载联系人信息失败", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.action_upload:
                myContentProvider =new MyContentProvider(getContentResolver());
                ArrayList<Child> children = myContentProvider.read();
                int mcount = 0;
                if(children.size()!=0){
                    //代表有联系人 将其上传至财大通数据库
                    for(Child child : children){
                        if(myDataBase.selectByNameNumber(child.getName(),child.getNumber()).getCount()!=0){
                            //当数据库已存在该联系人时(忽略关键字)不添加

                        }
                        else{
                            insert = myDataBase.insert(child);
                            mcount++;
                        }

                    }
                    if(insert!=0&& mcount!=0){
                        Toast.makeText(this, "上传"+mcount+"位联系人信息成功", Toast.LENGTH_SHORT).show();
                        getData();
                        setView();
                    }else{
                        Toast.makeText(this, "上传联系人信息失败,是否存在重复联系人", Toast.LENGTH_SHORT).show();

                    }
                    
                }else{
                    Toast.makeText(this, "未读取到本机的联系人信息,请重试", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
