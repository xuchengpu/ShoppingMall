package com.xuchengpu.shoppingmall.app;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.xuchengpu.shoppingmall.R;
import com.xuchengpu.shoppingmall.home.adapter.HomeAdapter;
import com.xuchengpu.shoppingmall.home.bean.WebViewBean;
import com.xuchengpu.shoppingmall.utils.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WebViewActivity extends AppCompatActivity {

    @BindView(R.id.ib_back)
    ImageButton ibBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.ib_more)
    ImageButton ibMore;
    @BindView(R.id.webview)
    WebView webview;
    @BindView(R.id.progressbar)
    ProgressBar progressbar;
    @BindView(R.id.activity_web_view)
    LinearLayout activityWebView;
    private WebViewBean webViewBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        ButterKnife.bind(this);
        getData();
        initListener();

    }

    private void initListener() {
        ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ibMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(WebViewActivity.this, "更多……", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getData() {
        webViewBean = (WebViewBean) getIntent().getSerializableExtra(HomeAdapter.WEBVIEW_BEAN);
        setData(webViewBean);

    }

    private void setData(WebViewBean webViewBean) {

        tvTitle.setText(webViewBean.getName());

        WebSettings webSettings = webview.getSettings();
        //设置JS调用Java
        webSettings.setJavaScriptEnabled(true);
        //设置缩放
        webSettings.setBuiltInZoomControls(true);
        //设置双击
        webSettings.setUseWideViewPort(true);
        //设置WebViewClient,如果没有设置，调起系统的浏览器打开新的连接
        webview.setWebViewClient(new WebViewClient() {
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
                progressbar.setVisibility(View.GONE);
            }
        });
        webview.addJavascriptInterface(new MyJavascriptInterface(),"cyc");
        webview.loadUrl(Constants.BASE_URL_IMAGE+webViewBean.getUrl());

    }
    class MyJavascriptInterface{
        @JavascriptInterface
        public void jumpForAndroid(String s){
            Toast.makeText(WebViewActivity.this, "s=="+s, Toast.LENGTH_SHORT).show();
        }

    }


}
