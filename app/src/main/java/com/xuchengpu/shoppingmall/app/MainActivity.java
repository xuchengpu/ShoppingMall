package com.xuchengpu.shoppingmall.app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;
import android.widget.RadioGroup;

import com.xuchengpu.shoppingmall.R;
import com.xuchengpu.shoppingmall.community.fragment.CommunityFragment;
import com.xuchengpu.shoppingmall.home.fragment.HomeFragment;
import com.xuchengpu.shoppingmall.shoppingcart.fragment.ShoppingCartFragment;
import com.xuchengpu.shoppingmall.type.fragment.TypeFragment;
import com.xuchengpu.shoppingmall.user.fragment.UserFragment;
import com.xuchengpu.shoppingmall.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.fl_main)
    FrameLayout flMain;
    @BindView(R.id.rg_main)
    RadioGroup rgMain;

    private int position;
    private List<Fragment> fragments;
    private Fragment tempFragment;
    private LocalBroadcastManager localBroadcastManager;
    private BroadcastReceiver receiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            rgMain.check(R.id.rb_main_home);

        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        //注册广播
        localBroadcastManager=LocalBroadcastManager.getInstance(this);
        localBroadcastManager.registerReceiver(receiver,new IntentFilter(Constants.GOTOHOME));
        //初始化数据，获得碎片集合
        initData();
        //设置监听
        initListener();
    }

    private void initData() {
        fragments = new ArrayList();
        fragments.add(0, new UserFragment());
        fragments.add(0, new ShoppingCartFragment());
        fragments.add(0, new CommunityFragment());
        fragments.add(0, new TypeFragment());
        fragments.add(0, new HomeFragment());


    }

    private void initListener() {
        rgMain.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_main_home:
                        position = 0;
                        break;
                    case R.id.rb_main_type:
                        position = 1;
                        break;
                    case R.id.rb_main_community:
                        position = 2;
                        break;
                    case R.id.rb_main_shoppingcart:
                        position = 3;
                        break;
                    case R.id.rb_main_user:
                        position = 4;
                        break;
                }
                Fragment currentFragment = fragments.get(position);
                switchFragment(currentFragment);
            }

        });
        //注意放在设置监听之后
        rgMain.check(R.id.rb_main_home);


    }

    private void switchFragment(Fragment currentFragment) {

        //当前页面与缓存页面不同
        if (tempFragment != currentFragment) {
            //得到事务
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            //缓存页面不为空时
            if (tempFragment != null) {
                //当前页面没有被添加
                if (!currentFragment.isAdded()) {
                    transaction.add(R.id.fl_main, currentFragment);
                    transaction.hide(tempFragment);
                    //当前页面已经被添加
                } else {
                    transaction.hide(tempFragment);
                }
                // 缓存页面为空时
            } else {
                transaction.add(R.id.fl_main, currentFragment);
            }
            transaction.show(currentFragment);
            transaction.commit();
//            transaction.commitAllowingStateLoss();
            tempFragment = currentFragment;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        localBroadcastManager.unregisterReceiver(receiver);
    }

    @Override
    protected void onNewIntent(Intent intent) {
    super.onNewIntent(intent);
        int  gotocart = intent.getIntExtra("gotocart",R.id.rb_main_home);
        rgMain.check(gotocart);
    }


}

