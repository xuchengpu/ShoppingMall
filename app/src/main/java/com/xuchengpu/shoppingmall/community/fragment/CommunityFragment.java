package com.xuchengpu.shoppingmall.community.fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageButton;

import com.xuchengpu.shoppingmall.R;
import com.xuchengpu.shoppingmall.base.BaseFragment;
import com.xuchengpu.shoppingmall.community.adapter.CommunityPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 许成谱 on 2017/2/22 15:10.
 * qq:1550540124
 * for:
 */

public class CommunityFragment extends BaseFragment {
    @BindView(R.id.ib_community_icon)
    ImageButton ibCommunityIcon;
    @BindView(R.id.ib_community_message)
    ImageButton ibCommunityMessage;
    @BindView(R.id.tablayout)
    TabLayout tablayout;
    @BindView(R.id.view_pager)
    ViewPager viewPager;

    private List<Fragment> fragments;
    private CommunityPagerAdapter adapter;


    /*
        初始化布局
        * */
    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.community_fragment, null);
        ButterKnife.bind(this, view);
        return view;
    }
    /*
    * 布局加载完后加载数据
    * */

    @Override
    public void initData() {
        super.initData();
        initFragments();
        setAdapter();
        //设置指示器参数
        //关联viewpager
        tablayout.setupWithViewPager(viewPager);
        //滚动模式
        tablayout.setTabMode(TabLayout.MODE_SCROLLABLE);

    }

    private void setAdapter() {
        //设置适配器
        if(fragments!=null&&fragments.size()>0) {
            adapter=new CommunityPagerAdapter(getFragmentManager(),fragments);
            viewPager.setAdapter(adapter);
        }
    }


    private void initFragments() {
        fragments=new ArrayList<>();
        //先添加的位置为零，在前边
        fragments.add(new NewPostFragment());
        fragments.add(new HotPostFragment());
    }


}
