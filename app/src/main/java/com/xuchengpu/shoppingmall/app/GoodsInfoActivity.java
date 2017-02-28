package com.xuchengpu.shoppingmall.app;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.xuchengpu.shoppingmall.R;
import com.xuchengpu.shoppingmall.home.adapter.HomeAdapter;
import com.xuchengpu.shoppingmall.home.bean.GoodsBean;
import com.xuchengpu.shoppingmall.shoppingcart.utils.CartStorage;
import com.xuchengpu.shoppingmall.utils.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GoodsInfoActivity extends AppCompatActivity {

    @BindView(R.id.ib_good_info_back)
    ImageButton ibGoodInfoBack;
    @BindView(R.id.ib_good_info_more)
    ImageButton ibGoodInfoMore;
    @BindView(R.id.iv_good_info_image)
    ImageView ivGoodInfoImage;
    @BindView(R.id.tv_good_info_name)
    TextView tvGoodInfoName;
    @BindView(R.id.tv_good_info_desc)
    TextView tvGoodInfoDesc;
    @BindView(R.id.tv_good_info_price)
    TextView tvGoodInfoPrice;
    @BindView(R.id.tv_good_info_store)
    TextView tvGoodInfoStore;
    @BindView(R.id.tv_good_info_style)
    TextView tvGoodInfoStyle;
    @BindView(R.id.wb_good_info_more)
    WebView wbGoodInfoMore;
    @BindView(R.id.progressbar)
    ProgressBar progressbar;
    @BindView(R.id.tv_good_info_callcenter)
    TextView tvGoodInfoCallcenter;
    @BindView(R.id.tv_good_info_collection)
    TextView tvGoodInfoCollection;
    @BindView(R.id.tv_good_info_cart)
    TextView tvGoodInfoCart;
    @BindView(R.id.btn_good_info_addcart)
    Button btnGoodInfoAddcart;
    @BindView(R.id.ll_goods_root)
    LinearLayout llGoodsRoot;
    @BindView(R.id.tv_more_share)
    TextView tvMoreShare;
    @BindView(R.id.tv_more_search)
    TextView tvMoreSearch;
    @BindView(R.id.tv_more_home)
    TextView tvMoreHome;
    @BindView(R.id.btn_more)
    Button btnMore;
    @BindView(R.id.ll_root)
    LinearLayout llRoot;
    private GoodsBean goodsBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_info);
        ButterKnife.bind(this);
        getData();
    }

    private void getData() {
        goodsBean = (GoodsBean) getIntent().getSerializableExtra(HomeAdapter.GOODSBEAN);
        if(goodsBean!=null) {
            setData(goodsBean);
        }
    }

    private void setData(GoodsBean goodsBean) {
        //1.设置图片
        Glide.with(this).load(Constants.BASE_URL_IMAGE+goodsBean.getFigure()).into(ivGoodInfoImage);
        //2.设置名称
        tvGoodInfoName.setText(goodsBean.getName());
        //3.设置价格
        tvGoodInfoPrice.setText("￥"+goodsBean.getCover_price());
        //4、设置web
        loadWeb("http://mp.weixin.qq.com/s/Cf3DrW2lnlb-w4wYaxOEZg");


    }

    private void loadWeb(String s) {

        WebSettings webSettings = wbGoodInfoMore.getSettings();
        //设置JS调用Java
        webSettings.setJavaScriptEnabled(true);
        //设置缩放
        webSettings.setBuiltInZoomControls(true);
        //设置双击
        webSettings.setUseWideViewPort(true);
        //设置WebViewClient,如果没有设置，调起系统的浏览器打开新的连接
        wbGoodInfoMore.setWebViewClient(new WebViewClient(){
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
        wbGoodInfoMore.loadUrl(s);


    }

    @OnClick({R.id.ib_good_info_back, R.id.ib_good_info_more, R.id.iv_good_info_image, R.id.tv_good_info_name, R.id.tv_good_info_desc, R.id.tv_good_info_price, R.id.tv_good_info_store, R.id.tv_good_info_style, R.id.wb_good_info_more, R.id.progressbar, R.id.tv_good_info_callcenter, R.id.tv_good_info_collection, R.id.tv_good_info_cart, R.id.btn_good_info_addcart, R.id.ll_goods_root, R.id.tv_more_share, R.id.tv_more_search, R.id.tv_more_home, R.id.btn_more, R.id.ll_root})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ib_good_info_back:
                finish();
                break;
            case R.id.ib_good_info_more:
                //设置更多界面的可见性
                if(llRoot.isShown()) {
                    llRoot.setVisibility(View.GONE);
                }else {
                    llRoot.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.iv_good_info_image:
                break;
            case R.id.tv_good_info_name:
                break;
            case R.id.tv_good_info_desc:
                break;
            case R.id.tv_good_info_price:
                break;
            case R.id.tv_good_info_store:
                break;
            case R.id.tv_good_info_style:
                break;
            case R.id.wb_good_info_more:
                break;
            case R.id.progressbar:
                break;
            case R.id.tv_good_info_callcenter:
                Toast.makeText(this, "客服中心", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_good_info_collection:
                Toast.makeText(this, "收藏", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_good_info_cart:
                Toast.makeText(this, "跳转到购物车", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_good_info_addcart:
                Toast.makeText(this, "加入到购物车", Toast.LENGTH_SHORT).show();
                CartStorage.getInstance(GoodsInfoActivity.this).addData(goodsBean);

                break;
            case R.id.ll_goods_root:
                break;
            case R.id.tv_more_share:
                Toast.makeText(this, "分享", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_more_search:
                Toast.makeText(this, "搜索", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_more_home:
                Toast.makeText(this, "首页", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_more:
               // 更多界面消失
                llRoot.setVisibility(View.GONE);
                break;
            case R.id.ll_root:
                break;
        }
    }
}
