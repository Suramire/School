package com.suramire.school.chuxingzhinan;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

import com.suramire.school.MyActivity;
import com.suramire.school.R;

/**
 * Created by Suramire on 2017/5/1.
 */

public class ChuxingzhinanMain extends MyActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chuxingzhinan_main);
    }
    public void click(View view) {
        switch (view.getId()) {
            //我的位置
            case R.id.button1:
                Intent intent = new Intent(this, Wodeweizhi.class);
                startActivity(intent);
                break;
            //线路查询
            case R.id.button2:
                Intent intent2 = new Intent(this, Xianluchaxun.class);
                startActivity(intent2);
                break;
            //关键点查询
            case R.id.button3:
                Intent intent3 = new Intent(this, Guanjiandianchaxun.class);
                startActivity(intent3);
                break;
            default:
                break;
        }
    }
}