package com.suramire.school.chuxingzhinan;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.support.v7.widget.SearchView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.suramire.school.MyActivity;
import com.suramire.school.MyWebActivity;
import com.suramire.school.R;
import com.suramire.school.Util.MyJsonUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.nio.charset.Charset;

/**
 * Created by Suramire on 2017/5/1.
 */

public class Xianluchaxun extends MyWebActivity {
    private double targetLatitude =0.0;//目标纬度
    private double targetLongtitude=0.0;//目标经度
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
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
                            String query1 = java.net.URLEncoder.encode(query, "UTF-8");
                            JSONObject json = MyJsonUtil.readJsonFromUrl("http://restapi.amap.com/v3/geocode/geo?key=10a889d0c8e4ef5453561e7abe046c96&s=rsv3&city=35&address="+query1);
                            //从json中获得目标地点的坐标值
                            String locationString = json.getJSONArray("geocodes").getJSONObject(0).get("location").toString();
                            String[] locations = locationString.split(",");
                            for(String xString :locations){
                                Log.e("L", xString);
                            }
                            targetLatitude =Double.parseDouble(locations[0]);
                            targetLongtitude = Double.parseDouble(locations[1]);
                            url2 = "http://m.amap.com/navi/?start="+longtitude+","+latitude+"&dest="+targetLatitude+","+targetLongtitude+"&destName=%E6%98%BE%E7%A4%BA%E8%B7%AF%E7%BA%BF&key="+keyString;
                            Log.e("URL", url2);
                            webView.post(new Runnable() {

                                @Override
                                public void run() {
                                    webView.loadUrl(url2);

                                }
                            });

                        } catch (UnsupportedEncodingException e) {
                            Log.e("onQueryTextSubmit", "UnsupportedEncodingException");
                            e.printStackTrace();
                        } catch (IOException e) {
                            Log.e("onQueryTextSubmit", "IOException");
                            e.printStackTrace();
                        } catch (JSONException e) {
                            Log.e("onQueryTextSubmit", "JSONException");
                            e.printStackTrace();
                        };
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