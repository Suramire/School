package com.suramire.school.haomabaishitong;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.suramire.school.MyActivity;
import com.suramire.school.R;
import com.suramire.school.Util.MyDataBase;
import com.suramire.school.Util.PinyinUtils;

/**
 * Created by Suramire on 2017/5/1.
 * 添加新号码界面
 */

public class NewNumber extends MyActivity {
    private EditText editText;//姓名
    private EditText editText2;//电话
    private EditText editText4;
    private MyDataBase myDataBase;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newnumber);
        editText = (EditText) findViewById(R.id.editText);
        editText2 = (EditText) findViewById(R.id.editText2);
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
     * 判断字符串是否为数字
     * @param str
     * @return
     */
    public static boolean isNumeric(String str){
        for (int i = 0; i < str.length(); i++){
            System.out.println(str.charAt(i));
            if (!Character.isDigit(str.charAt(i))){
                return false;
            }
        }
        return true;
    }
    /**
     * 提交数据,将数据存入数据库
     */
    private void sumbit() {
        try {
            Child child;
            String number = editText2.getText().toString().trim();
            String name = editText.getText().toString().trim();
            if(name==null || name.length()<=0 || number.length()<=0 || number == null){
                Toast.makeText(this, "请填写联系人姓名与号码", Toast.LENGTH_SHORT).show();
            }else if(!isNumeric(number)){
                Toast.makeText(this, "电话号码只能为纯数字,请重新输入", Toast.LENGTH_SHORT).show();
            }else if(number.length()>11){
                Toast.makeText(this, "电话号码最多不超过11位,请重新输入", Toast.LENGTH_SHORT).show();
            }else{
                String pingYin = PinyinUtils.getPingYin(name);
                String p = PinyinUtils.getFirstSpell(name);
                String keyword = editText4.getText().toString().trim();
                if(keyword==null || keyword.length()<=0){
                    //不带关键字
                    child = new Child(name,number,pingYin,p);
                }else{
                    child = new Child(name,number,keyword,pingYin,p);
                }
                if(myDataBase.insert(child)!=-1){
                    Toast.makeText(this, "添加成功", Toast.LENGTH_SHORT).show();
                    finish();
                }else{
                    Toast.makeText(this, "添加失败", Toast.LENGTH_SHORT).show();
                }
            }
        } catch (Exception e) {
            Toast.makeText(this, "添加联系人捕获异常"+e, Toast.LENGTH_SHORT).show();
        }

    }
}
