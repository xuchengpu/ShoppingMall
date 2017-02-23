package com.xuchengpu.shoppingmall.home.fragment;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xuchengpu.shoppingmall.R;
import com.xuchengpu.shoppingmall.base.BaseFragment;
import com.xuchengpu.shoppingmall.utils.Constants;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

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
                .id(100)
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
//        HomeBean homeBean

    }


    @OnClick({R.id.ll_main_scan, R.id.tv_search_home, R.id.tv_message_home, R.id.ib_top})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_main_scan:
                break;
            case R.id.tv_search_home:
                break;
            case R.id.tv_message_home:
                break;
            case R.id.ib_top:
                break;
        }
    }
}
