package com.suramire.school.chuxingzhinan;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.support.v7.widget.SearchView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.suramire.school.MyActivity;
import com.suramire.school.MyWebActivity;
import com.suramire.school.R;

import java.io.IOException;
import java.net.URLEncoder;

/**
 * Created by Suramire on 2017/5/1.
 */

public class Guanjiandianchaxun extends MyWebActivity {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(final String query) {
                Log.e("onQueryTextSubmit", "onQueryTextSubmit");
                new Thread(new Runnable() {

                    private String url2;

                    @Override
                    public void run() {
                        //先对要搜索的字符串进行url编码
                        try {
                            String query1 = URLEncoder.encode(query, "UTF-8");
                            //若获取不到当前坐标
                            if(longtitude==0.0 && latitude==0.0){
                                Toast.makeText(Guanjiandianchaxun.this, "查询失败,未获取当前坐标", Toast.LENGTH_LONG).show();
                            }else{
                                url2 = "http://m.amap.com/around/?locations="+longtitude+","+latitude+"&keywords="+query1+"&defaultIndex=1&defaultView=map&searchRadius=5000&key="+keyString;
                                webView.post(new Runnable() {

                                    @Override
                                    public void run() {
                                        webView.loadUrl(url2);
                                    }
                                });
                            }
                        }
                        catch (IOException e) {
                            Log.e("onQueryTextSubmit", "IOException");
                            e.printStackTrace();
                        }
                    }
                }).start();

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return true;
    }

}