package com.suramire.school.haomabaishitong;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.suramire.school.MyActivity;
import com.suramire.school.R;
import com.suramire.school.Util.MyDataBase;

/**
 * Created by Suramire on 2017/5/1.
 * 添加新号码界面
 */

public class NewNumber extends MyActivity {
    private static final String TAG = "SCHOOL";
    private EditText editText;//姓名
    private EditText editText2;//电话
    private Spinner spinner;//分组
    private EditText editText4;
    private CharSequence[] groups;
    private MyDataBase myDataBase;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newnumber);
        groups = getResources().getTextArray(R.array.groups);
        editText = (EditText) findViewById(R.id.editText);
        editText2 = (EditText) findViewById(R.id.editText2);
        spinner = (Spinner) findViewById(R.id.spinner2);
        editText4 = (EditText) findViewById(R.id.editText4);
        myDataBase = new MyDataBase(this,"number.db",null,1);
    }
    public void click(View view){
        switch (view.getId()){
            //提交
            case R.id.button9:
                sumbit();
                break;
            //重置
            case R.id.button:
                reset();
                break;
        }
    }

    /**
     * 重置表单数据
     */
    private void reset() {
        editText.setText("");
        editText2.setText("");
        editText4.setText("");
    }

    @Override
    protected void onDestroy() {
        //关闭添加号码页面时同时关闭数据库连接
        myDataBase.close();
        super.onDestroy();
    }

    /**
     * 提交数据,将数据存入数据库
     */
    private void sumbit() {
        Child child = null;
        if("".equals(editText.getText().toString().trim()) ||"".equals(editText2.getText().toString().trim())){
            Toast.makeText(this, "请填写联系人姓名与号码", Toast.LENGTH_SHORT).show();
        }else{
            if("".equals(editText4.getText().toString())){
                //不带关键字的联系人信息
                child = new Child(editText.getText().toString().trim(),editText2.getText().toString().trim(),spinner.getSelectedItemPosition());
            }else{
                child = new Child(editText.getText().toString().trim(),editText2.getText().toString().trim(),spinner.getSelectedItemPosition(),editText4.getText().toString());
            }
            if(myDataBase.insert(child)!=-1){
                Toast.makeText(this, "添加成功", Toast.LENGTH_SHORT).show();
                finish();
            }else{
                Toast.makeText(this, "添加失败", Toast.LENGTH_SHORT).show();
            }
        }

    }
}
