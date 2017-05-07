package com.suramire.school.xiaoyuanshenghuo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.suramire.school.R;

/**
 * Created by Suramire on 2017/5/1.
 */

public class XiaoyuanshenghuoMain extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.xiaoyuanshenghuo_main);
    }

    public void click1(View view) {
        Intent intent = new Intent(this, Pingmiantu.class);
        startActivity(intent);
    }
    public void click2(View view) {
        Intent intent = new Intent(this, Xiaoyuanfengjing.class);
        startActivity(intent);
    }
    public void click3(View view) {
        Intent intent = new Intent(this, Xinshengzhinan.class);
        startActivity(intent);
    }
    /*
     * 返回
     */
    public void click4(View view) {
        this.finish();
    }
}