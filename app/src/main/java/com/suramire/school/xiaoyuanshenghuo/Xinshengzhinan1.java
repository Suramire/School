package com.suramire.school.xiaoyuanshenghuo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.suramire.school.R;

/**
 * Created by Suramire on 2017/5/1.
 */

public class Xinshengzhinan1 extends AppCompatActivity {
    private TextView textView;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.xinshengzhinan1);
        textView = (TextView) findViewById(R.id.textView1);
        intent = getIntent();
        int index = intent.getIntExtra("text",0);
        String text = getResources().getTextArray(R.array.guids)[index].toString();
        textView.setText(text);
        setTitle(intent.getStringExtra("title"));
    }
    public void click1(View view) {
        switch (view.getId()) {
            case R.id.button1:

                break;

            default:
                break;
        }
    }
}