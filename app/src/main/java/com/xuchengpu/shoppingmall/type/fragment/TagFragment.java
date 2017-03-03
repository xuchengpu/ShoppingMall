package com.xuchengpu.shoppingmall.type.fragment;

import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.GridView;

import com.alibaba.fastjson.JSON;
import com.xuchengpu.shoppingmall.R;
import com.xuchengpu.shoppingmall.base.BaseFragment;
import com.xuchengpu.shoppingmall.type.adapter.TypeTagAdapter;
import com.xuchengpu.shoppingmall.type.bean.TagBean;
import com.xuchengpu.shoppingmall.utils.Constants;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;

/**
 * Created by 许成谱 on 2017/2/22 15:10.
 * qq:1550540124
 * for:
 */

public class TagFragment extends BaseFragment {
    @BindView(R.id.gv_tag)
    GridView gvTag;

    private int[] colors = {
            Color.parseColor("#f0a420"), Color.parseColor("#4ba5e2"), Color.parseColor("#f0839a"),
            Color.parseColor("#4ba5e2"), Color.parseColor("#f0839a"), Color.parseColor("#f0a420"),
            Color.parseColor("#f0839a"), Color.parseColor("#f0a420"), Color.parseColor("#4ba5e2")
    };


    /*
    初始化布局
    * */
    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.fragment_tag, null);
        ButterKnife.bind(this, view);
        return view;
    }
    /*
    * 布局加载完后加载数据
    * */

    @Override
    public void initData() {
        super.initData();
        getDataFormNet(Constants.TAG_URL);

    }

    public void getDataFormNet(String url) {
        OkHttpUtils.get()
                .url(url)
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
        TagBean tagBean= JSON.parseObject(response,TagBean.class);
//        Log.e("tag","tagBean=="+tagBean.getResult().get(0).getName());
        List<TagBean.ResultBean> result = tagBean.getResult();
        if(result!=null&&result.size()>0) {
            setTagAdapter(result);
        }
    }

    private void setTagAdapter(List<TagBean.ResultBean> result) {
        TypeTagAdapter adapter = new TypeTagAdapter(mContext,result,colors );
        gvTag.setAdapter(adapter);
    }
}
