package com.xuchengpu.shoppingmall.home.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.xuchengpu.shoppingmall.R;
import com.xuchengpu.shoppingmall.app.GoodsInfoActivity;
import com.xuchengpu.shoppingmall.base.BaseFragment;
import com.xuchengpu.shoppingmall.home.adapter.HomeAdapter;
import com.xuchengpu.shoppingmall.home.bean.GoodsBean;
import com.xuchengpu.shoppingmall.home.bean.HomeBean;
import com.xuchengpu.shoppingmall.utils.Constants;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

import static com.xuchengpu.shoppingmall.home.adapter.HomeAdapter.GOODSBEAN;

/**
 * Created by 许成谱 on 2017/2/22 15:10.
 * qq:1550540124
 * for:
 */

public class HomeFragment extends BaseFragment {
    @BindView(R.id.iv_scan)
    ImageView ivScan;
    @BindView(R.id.ll_main_scan)
    LinearLayout llMainScan;
    @BindView(R.id.tv_search_home)
    TextView tvSearchHome;
    @BindView(R.id.tv_message_home)
    TextView tvMessageHome;
    @BindView(R.id.rv_home)
    RecyclerView rvHome;
    @BindView(R.id.ib_top)
    ImageButton ibTop;
    private HomeAdapter adapter;
    public  static final int REQUEST_CODE=1;

    /*
        初始化布局
        * */
    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.fragment_home, null);
        ButterKnife.bind(this, view);
        return view;
    }
    /*
    * 布局加载完后加载数据
    * */

    @Override
    public void initData() {
        super.initData();
        getDataFromNet();

    }

    private void getDataFromNet() {
        OkHttpUtils
                .get()
                .url(Constants.HOME_URL)
                .id(100)//100:http 、101：https
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("tag","联网请求失败=="+e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("tag","联网请求成功=="+response);
                        processData(response);
                    }
                });

    }

    private void processData(String response) {
        HomeBean homeBean = JSON.parseObject(response,HomeBean.class);
        Log.e("tag","fastjson解析成功="+homeBean.getResult().getAct_info().get(1).getName());

        //设置RecyclerView的适配器
        adapter = new HomeAdapter(mContext, homeBean.getResult());
        rvHome.setAdapter(adapter);
        rvHome.setLayoutManager(new LinearLayoutManager(mContext,LinearLayoutManager.VERTICAL,false));

    }


    @OnClick({R.id.ll_main_scan, R.id.tv_search_home, R.id.tv_message_home, R.id.ib_top})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_main_scan:
                Intent intent = new Intent(mContext, CaptureActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
                break;
            case R.id.tv_search_home:
                break;
            case R.id.tv_message_home:
                break;
            case R.id.ib_top:
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
/**
 * 处理二维码扫描结果
 */
        if (requestCode == REQUEST_CODE) {
            //处理扫描结果（在界面上显示）
            if (null != data) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    String result = bundle.getString(CodeUtils.RESULT_STRING);
                    Toast.makeText(mContext, "解析结果:" + result, Toast.LENGTH_LONG).show();
                    String[] mresult=result.split(",");


                    GoodsBean goodsBean = new GoodsBean();
                    goodsBean.setCover_price(mresult[2]);
                    goodsBean.setFigure(mresult[0]);
                    goodsBean.setName(mresult[1]);
                    goodsBean.setProduct_id(mresult[3]);

                    Intent intent=new Intent(mContext, GoodsInfoActivity.class);
                    intent.putExtra(GOODSBEAN,goodsBean);
                    mContext.startActivity(intent);

                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    Toast.makeText(mContext, "解析二维码失败", Toast.LENGTH_LONG).show();

                }
            }
        }

    }
}
