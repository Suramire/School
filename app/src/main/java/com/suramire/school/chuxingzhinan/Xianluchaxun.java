package com.suramire.school.chuxingzhinan;

import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.Menu;
import android.support.v7.widget.SearchView;
import android.widget.Toast;
import com.suramire.school.MyWebActivity;
import com.suramire.school.R;
import com.suramire.school.Util.MyJsonReader;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;


/**
 * Created by Suramire on 2017/5/1.
 * 线路规划 显示当前位置到目的地之间的路径
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
        searchView.setQueryHint("请输入目的地名");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(final String query) {
                new Thread(new Runnable() {
                    private String url2;
                    @Override
                    public void run() {
                        //先对要搜索的字符串进行url编码
                        try {
                            String query1 = URLEncoder.encode(query, "UTF-8");
                            //以下这段url用于返回目的地的json
                            JSONObject json = MyJsonReader.readJsonFromUrl("http://restapi.amap.com/v3/geocode/geo?" +
                                    "key=10a889d0c8e4ef5453561e7abe046c96&s=rsv3&city=35&address="+query1);
                            //从json中获得目标地点的坐标值
                            String locationString = json.getJSONArray("geocodes").getJSONObject(0).get("location").toString();
                            String[] locations = locationString.split(",");
                            targetLatitude =Double.parseDouble(locations[0]);//目的地的纬度
                            targetLongtitude = Double.parseDouble(locations[1]);//目的地的经度
                            url2 = "http://m.amap.com/navi/?start="+longtitude+","+latitude+"" +
                                    "&dest="+targetLatitude+","+targetLongtitude+
                                    "&destName=%E6%98%BE%E7%A4%BA%E8%B7%AF%E7%BA%BF&key="+keyString;
                            webView.post(new Runnable() {
                                @Override
                                public void run() {
                                    webView.loadUrl(url2);//加载URL

                                }
                            });

                        } catch (UnsupportedEncodingException e) {
                            Log.e("onQueryTextSubmit", "UnsupportedEncodingException");
                            e.printStackTrace();
                        } catch (IOException e) {
                            Log.e("onQueryTextSubmit", "IOException");
                            e.printStackTrace();
                        } catch (JSONException e) {
                            //使用此方法来在线程中toast 2017-5-24 16:22:14
                            Looper.prepare();
                            Toast.makeText(Xianluchaxun.this, "请输入正确的关键字", Toast.LENGTH_SHORT).show();
                            Looper.loop();

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