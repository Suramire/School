package com.suramire.school;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.suramire.school.chuxingzhinan.ChuxingzhinanMain;
import com.suramire.school.haomabaishitong.HaomabaishitongMain;
import com.suramire.school.xiaoyuanshenghuo.XiaoyuanshenghuoMain;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void click1(View view) {
        switch (view.getId()) {
            case R.id.button1:
                Intent intent = new Intent(this, XiaoyuanshenghuoMain.class);
                startActivity(intent);
                break;
            case R.id.button2:
                Intent intent2 = new Intent(this,ChuxingzhinanMain.class);
                startActivity(intent2);
                break;
            case R.id.button4:
                Intent intent4 = new Intent(this,HaomabaishitongMain.class);
                startActivity(intent4);
                break;
            default:
                break;
        }

    }
}
