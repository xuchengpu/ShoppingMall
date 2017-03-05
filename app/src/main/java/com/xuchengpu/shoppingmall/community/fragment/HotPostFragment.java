package com.xuchengpu.shoppingmall.community.fragment;

import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.xuchengpu.shoppingmall.R;
import com.xuchengpu.shoppingmall.base.BaseFragment;
import com.xuchengpu.shoppingmall.community.adapter.HotPostAdapter;
import com.xuchengpu.shoppingmall.community.bean.HotPostBean;
import com.xuchengpu.shoppingmall.utils.Constants;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;

/**
 * Created by 许成谱 on 2017/3/5 17:18.
 * qq:1550540124
 * for:
 */

public class HotPostFragment extends BaseFragment {

    @BindView(R.id.lv_hot_post)
    ListView lvHotPost;

    /*
        初始化布局
        * */
    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.fragment_hot_post, null);
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
        OkHttpUtils.get()
                .url(Constants.HOT_POST_URL)
                .id(100)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("tag", "联网请求失败==" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("tag", "联网请求成功==" + response);
                        processData(response);
                    }
                });

    }

    private void processData(String response) {
        HotPostBean newPostBean = JSON.parseObject(response, HotPostBean.class);
        Log.e("tag", "tagBean==" + newPostBean.getResult().get(0).getSaying());
        List<HotPostBean.ResultBean> result = newPostBean.getResult();
        if (result != null && result.size() > 0) {
            setTagAdapter(result);
        }
    }

    private void setTagAdapter(List<HotPostBean.ResultBean> result) {
        HotPostAdapter adapter = new HotPostAdapter(mContext, result);
        lvHotPost.setAdapter(adapter);
    }

}
