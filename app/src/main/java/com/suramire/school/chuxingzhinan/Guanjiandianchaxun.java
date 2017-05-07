package com.suramire.school.chuxingzhinan;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.support.v7.widget.SearchView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.suramire.school.R;

import java.io.IOException;

/**
 * Created by Suramire on 2017/5/1.
 */

public class Guanjiandianchaxun extends AppCompatActivity {
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
        setContentView(R.layout.guanjiandianchaxun);
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
                            url2 = "http://m.amap.com/around/?locations="+longtitude+","+latitude+"&keywords="+query1+"&defaultIndex=1&defaultView=map&searchRadius=5000&key="+keyString;
                            Log.e("URL", url2);
                            webView.post(new Runnable() {

                                @Override
                                public void run() {
                                    webView.loadUrl(url2);

                                }
                            });

                        }
                        catch (IOException e) {
                            Log.e("onQueryTextSubmit", "IOException");
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
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

}