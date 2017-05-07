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
import com.suramire.school.R;

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

public class Xianluchaxun extends AppCompatActivity {
    private String keyString = "10a889d0c8e4ef5453561e7abe046c96";
    private double latitude = 0.0;//当前纬度
    private double longtitude = 0.0;//当前经度
    private double targetLatitude =0.0;//目标纬度
    private double targetLongtitude=0.0;//目标经度
    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;
    //声明定位回调监听器
    public AMapLocationListener mLocationListener = new AMapLocationListener() {


        @Override
        public void onLocationChanged(AMapLocation arg0) {
            // 获取定位结果
            if(arg0!=null){
                if(arg0.getErrorCode()==0){
                    latitude = arg0.getLatitude();
                    longtitude = arg0.getLongitude();
                    webView.setWebViewClient(new WebViewClient());
                    //此处根据所得到的经纬度来显示地图
                    String url = "http://m.amap.com/navi/?dest="+longtitude+","+latitude
                            +"&destName=我的位置&hideRouteIcon=1&key="+keyString;
                    Log.e("URL",url);
                    webView.loadUrl(url);
                }else{
                    Log.e("MyError", "定位失败, ErrCode:"+arg0.getErrorCode()+",errInfo:"+arg0.getErrorInfo());
                }
            }
        }
    };
    private WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.xianluchaxun);
        webView = (WebView) findViewById(R.id.webView1);
        //获取网页的配置
        WebSettings settings = webView.getSettings();
        //设置启动js
        settings.setJavaScriptEnabled(true);
        //设置不调用其他浏览器
        webView.setWebViewClient(new WebViewClient());
        //此处调用高德地图的api来获取所在的经纬度
        //声明AMapLocationClient类对象
        //初始化定位
        mLocationClient = new AMapLocationClient(getApplicationContext());
        //设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);
        //初始化AMapLocationClientOption对象
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //获取最近3s内精度最高的一次定位结果：
        //设置setOnceLocationLatest(boolean b)接口为true，启动定位时SDK会返回最近3s内精度最高的一次定位结果。如果设置其为true，setOnceLocation(boolean b)接口也会被设置为true，反之不会，默认为false。
        mLocationOption.setOnceLocationLatest(true);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        Log.i("AA", "onCreateOptionsMenu: "+searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(final String query) {
                Log.e("onQueryTextSubmit", "onQueryTextSubmit");
                new Thread(new Runnable() {

                    private String url2;

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        //先对要搜索的字符串进行url编码
                        try {
                            String query1 = java.net.URLEncoder.encode(query, "UTF-8");
                            JSONObject json = readJsonFromUrl("http://restapi.amap.com/v3/geocode/geo?key=10a889d0c8e4ef5453561e7abe046c96&s=rsv3&city=35&address="+query1);
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
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        } catch (JSONException e) {
                            Log.e("onQueryTextSubmit", "JSONException");
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        };
                    }
                }).start();

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // TODO Auto-generated method stub
                return false;
            }
        });

        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        return true;
    }
    //以下两个方式主要是从url返回的结果中获取json
    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONObject json = new JSONObject(jsonText);
            return json;
        } finally {
            is.close();
            // System.out.println("同时 从这里也能看出 即便return了，仍然会执行finally的！");
        }
    }
}