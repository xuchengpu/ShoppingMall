package com.xuchengpu.shoppingmall.community.fragment;

import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.xuchengpu.shoppingmall.base.BaseFragment;

/**
 * Created by 许成谱 on 2017/3/5 17:18.
 * qq:1550540124
 * for:
 */

public class NewPostFragment extends BaseFragment {
    private TextView textView;
    /*
    初始化布局
    * */
    @Override
    public View initView() {
        textView=new TextView(mContext);
        textView.setTextColor(Color.RED);
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(30);
        return textView;
    }
    /*
    * 布局加载完后加载数据
    * */

    @Override
    public void initData() {
        super.initData();
        textView.setText("新帖");
    }
}
