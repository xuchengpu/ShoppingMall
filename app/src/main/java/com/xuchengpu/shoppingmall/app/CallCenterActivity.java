package com.xuchengpu.shoppingmall.app;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.xuchengpu.shoppingmall.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CallCenterActivity extends AppCompatActivity {

    @BindView(R.id.webview)
    WebView webview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_center);
        ButterKnife.bind(this);
        loadWeb("http://mp.weixin.qq.com/s/Cf3DrW2lnlb-w4wYaxOEZg");
    }

    private void loadWeb(String s) {

        WebSettings webSettings = webview.getSettings();
        //设置JS调用Java
        webSettings.setJavaScriptEnabled(true);
        //设置缩放
        webSettings.setBuiltInZoomControls(true);
        //设置双击
        webSettings.setUseWideViewPort(true);
        //设置WebViewClient,如果没有设置，调起系统的浏览器打开新的连接
        webview.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    view.loadUrl(request.getUrl().toString());
                }
                return true;

            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

            }
        });
        webview.loadUrl("http://www6.53kf.com/webCompany.php?arg=10007377&style=2&kflist=off&kf=info@atguigu.com,video@atguigu.com,public@atguigu.com,3069368606@qq.com,215648937@qq.com,sudan@atguigu.com,sszhang@atguigu.com&zdkf_type=1&language=zh-cn&charset=gbk&referer=http%3A%2F%2Fatguigu.com%2F&keyword=&tfrom=1&tpl=crystal_blue&uid=35fd8e6a8f0f3e7a1caedcc583f2f7a7&timeStamp=1488340364404&ucust_id=");
    }
}
