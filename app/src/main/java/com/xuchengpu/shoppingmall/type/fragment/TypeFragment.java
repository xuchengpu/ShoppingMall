package com.xuchengpu.shoppingmall.type.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.flyco.tablayout.SegmentTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.xuchengpu.shoppingmall.R;
import com.xuchengpu.shoppingmall.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 许成谱 on 2017/2/22 15:10.
 * qq:1550540124
 * for:
 */

public class TypeFragment extends BaseFragment {
    @BindView(R.id.tl_1)
    SegmentTabLayout tl1;
    @BindView(R.id.iv_type_search)
    ImageView ivTypeSearch;
    @BindView(R.id.fl_type)
    FrameLayout flType;
    private String[] titles = {"分类", "标签"};
    private List<Fragment> fragments;
    private Fragment tempFragment;

    /*
        初始化布局
        * */
    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.fragment_type, null);
        ButterKnife.bind(this, view);
        return view;
    }
    /*
    * 布局加载完后加载数据
    * */

    @Override
    public void initData() {
        super.initData();
        initListener();
        initFragment();
        switchFragment(fragments.get(0));
    }

    private void switchFragment(Fragment currentFragment) {

        //当前页面与缓存页面不同
        if (tempFragment != currentFragment) {
            //得到事务
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            //缓存页面不为空时
            if (tempFragment != null) {
                //当前页面没有被添加
                if (!currentFragment.isAdded()) {
                    transaction.add(R.id.fl_type, currentFragment);
                    transaction.hide(tempFragment);
                    //当前页面已经被添加
                } else {
                    transaction.hide(tempFragment);
                }
                // 缓存页面为空时
            } else {
                transaction.add(R.id.fl_type, currentFragment);
            }
            transaction.show(currentFragment);
            transaction.commit();
//            transaction.commitAllowingStateLoss();
            tempFragment = currentFragment;
        }
    }

    private void initFragment() {
        fragments = new ArrayList<>();
        fragments.add(new ListFragment());
        fragments.add(new TagFragment());

    }

    private void initListener() {
        //给flycoTablayout传递标题数据
        tl1.setTabData(titles);
        //给flycoTablayout设置改变的监听 以切换页面
        tl1.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                switchFragment(fragments.get(position));
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
    }


}
