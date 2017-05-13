package com.suramire.school.haomabaishitong;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.suramire.school.R;
import com.suramire.school.Util.MyDataBase;

/**
 * Created by Suramire on 2017/5/1.
 */

public class NewNumber extends AppCompatActivity {
    private static final String TAG = "SCHOOL";
    private EditText editText;//姓名
    private EditText editText2;//电话
    private Spinner spinner;//分组
    private EditText editText4;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newnumber);
        editText = (EditText) findViewById(R.id.editText);
        editText2 = (EditText) findViewById(R.id.editText2);
        spinner = (Spinner) findViewById(R.id.spinner2);
        editText4 = (EditText) findViewById(R.id.editText4);
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

    /**
     * 提交数据,将数据存入数据库
     */
    private void sumbit() {
        MyDataBase myDataBase = new MyDataBase(this);
        Child child = new Child(editText.getText().toString().trim(),editText2.getText().toString().trim());
        long index =  myDataBase.insert(child);
        Toast.makeText(this,"增加第"+index+"条数据",Toast.LENGTH_SHORT).show();
        //// TODO: 2017/5/13  添加号码后返回主界面 ，主界面进行刷新
        Log.i(TAG, "sumbit: "+spinner.getSelectedItemPosition());
    }
}
