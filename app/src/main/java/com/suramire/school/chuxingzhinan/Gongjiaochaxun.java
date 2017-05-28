package com.suramire.school.chuxingzhinan;

import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.suramire.school.MyActivity;
import com.suramire.school.MyWebActivity;
import com.suramire.school.R;

import java.io.IOException;
import java.net.URLEncoder;

/**
 * Created by Suramire on 2017/5/28.
 */

public class Gongjiaochaxun extends MyActivity {
    WebView webview;
    private WebView webview1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wodeweizhi);
        webview1 = (WebView) findViewById(R.id.webView1);
        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
        webview1.getSettings().setJavaScriptEnabled(true);
        webview1.setWebViewClient(new WebViewClient());
        webview1.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                progressBar.setProgress(newProgress);
                if(newProgress==100){
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
        webview1.loadUrl("http://uri.amap.com/line?name=地铁1号线&city=010&src=mypage&callnative=0");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        final SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setQueryHint("输入城市名-公交线路 格式进行搜索");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(final String query) {
                new Thread(new Runnable() {

                    @Override
                    public void run() {
                        final String[] split = query.split("-");
                        if(split.length ==2 && !"".equals(split[0]) && !"".equals(split[1])){
                            webview1.post(new Runnable() {

                                @Override
                                public void run() {
                                    webview1.loadUrl("http://uri.amap.com/line?name="+split[1]+"&city="+split[0]+"&src=mypage&callnative=0");
                                    Looper.prepare();
                                    Toast.makeText(Gongjiaochaxun.this, "点击列表显示详情", Toast.LENGTH_SHORT).show();
                                    Looper.loop();
                                }
                            });
                        }else{
                            Looper.prepare();
                            Toast.makeText(Gongjiaochaxun.this, "输入有误,请重新输入", Toast.LENGTH_SHORT).show();
                            Looper.loop();

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