package com.xuchengpu.shoppingmall.type.fragment;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.xuchengpu.shoppingmall.R;
import com.xuchengpu.shoppingmall.base.BaseFragment;
import com.xuchengpu.shoppingmall.type.adapter.TypeLeftAdapter;
import com.xuchengpu.shoppingmall.type.adapter.TypeRightAdapter;
import com.xuchengpu.shoppingmall.type.bean.TypeBean;
import com.xuchengpu.shoppingmall.utils.Constants;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;

/**
 * Created by 许成谱 on 2017/2/22 15:10.
 * qq:1550540124
 * for:
 */

public class ListFragment extends BaseFragment {
    @BindView(R.id.lv_left)
    ListView lvLeft;
    @BindView(R.id.rv_right)
    RecyclerView rvRight;

    //网络请求得到数据
    private String[] titles = new String[]{"小裙子", "上衣", "下装", "外套", "配件", "包包", "装扮", "居家宅品",
            "办公文具", "数码周边", "游戏专区"};

    //联网的url的集合
    public String[] urls = new String[]{Constants.SKIRT_URL, Constants.JACKET_URL, Constants.PANTS_URL, Constants.OVERCOAT_URL,
            Constants.ACCESSORY_URL, Constants.BAG_URL, Constants.DRESS_UP_URL, Constants.HOME_PRODUCTS_URL, Constants.STATIONERY_URL,
            Constants.DIGIT_URL, Constants.GAME_URL};


    /*
        初始化布局
        * */
    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.fragment_list, null);
        ButterKnife.bind(this, view);
        return view;
    }
    /*
    * 布局加载完后加载数据
    * */

    @Override
    public void initData() {
        super.initData();
        TypeLeftAdapter adapter=new TypeLeftAdapter(titles,mContext,ListFragment.this);
        lvLeft.setAdapter(adapter);
        getDataFormNet(urls[0]);
    }

    public void getDataFormNet(String url) {
        OkHttpUtils.get()
                .url(url)
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
        TypeBean typeBean= JSON.parseObject(response,TypeBean.class);
//        Log.e("tag","typeBean=="+typeBean.getResult().get(0).getName());
        TypeBean.ResultBean resultBean = typeBean.getResult().get(0);
        if(resultBean!=null) {
            setRightAdapter(resultBean);
        }

    }

    private void setRightAdapter(TypeBean.ResultBean resultBean) {
        TypeRightAdapter adapter =new TypeRightAdapter(mContext,resultBean);
        rvRight.setAdapter(adapter);
        // 用Grid布局管理器  设置第一行 三格为一体，其它一格为一体  实现布局的分类
        GridLayoutManager manager=new GridLayoutManager(mContext,3);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                int num=0;
                if(position==0) {
                    num=3;
                }else{
                    num=1;
                }
                return num;
            }
        });
        rvRight.setLayoutManager(manager);
    }


}
