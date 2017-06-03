package com.suramire.school.xiaoyuanshenghuo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.suramire.school.MyActivity;
import com.suramire.school.R;

/**
 * Created by Suramire on 2017/5/1.
 * 根据所选的项进入相应新生指南页面
 */

public class Xinshengzhinan extends MyActivity {
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.xinshengzhinan);

    }
    public void click1(View view) {
        intent = new Intent(this, Xinshengzhinan1.class);
        intent.putExtra("title",((Button)view).getText().toString());
        switch (view.getId()) {
            case R.id.button1:
                intent.putExtra("text",0);
                break;
            case R.id.button2:
                intent.putExtra("text",1);
                break;
            case R.id.button3:
                intent.putExtra("text",2);
                break;
            case R.id.button4:
                intent.putExtra("text",3);
                break;
            case R.id.button5:
                intent.putExtra("text",4);
                break;
            case R.id.button6:
                intent.putExtra("text",5);
                break;
            case R.id.button7:
                intent.putExtra("text",6);
                break;
            case R.id.button8:
                intent.putExtra("text",7);
                break;
            default:
                break;
        }
        startActivity(intent);
    }
}