package com.xuchengpu.shoppingmall.user.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hankkin.gradationscroll.GradationScrollView;
import com.xuchengpu.shoppingmall.R;
import com.xuchengpu.shoppingmall.base.BaseFragment;
import com.xuchengpu.shoppingmall.user.view.LoginActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 许成谱 on 2017/2/22 15:10.
 * qq:1550540124
 * for:
 */

public class UserFragment extends BaseFragment implements GradationScrollView.ScrollViewListener {
    @BindView(R.id.ib_user_icon_avator)
    ImageButton ibUserIconAvator;
    @BindView(R.id.tv_username)
    TextView tvUsername;
    @BindView(R.id.rl_header)
    RelativeLayout rlHeader;
    @BindView(R.id.tv_all_order)
    TextView tvAllOrder;
    @BindView(R.id.tv_user_pay)
    TextView tvUserPay;
    @BindView(R.id.tv_user_receive)
    TextView tvUserReceive;
    @BindView(R.id.tv_user_finish)
    TextView tvUserFinish;
    @BindView(R.id.tv_user_drawback)
    TextView tvUserDrawback;
    @BindView(R.id.tv_user_location)
    TextView tvUserLocation;
    @BindView(R.id.tv_user_collect)
    TextView tvUserCollect;
    @BindView(R.id.tv_user_coupon)
    TextView tvUserCoupon;
    @BindView(R.id.tv_user_score)
    TextView tvUserScore;
    @BindView(R.id.tv_user_prize)
    TextView tvUserPrize;
    @BindView(R.id.tv_user_ticket)
    TextView tvUserTicket;
    @BindView(R.id.tv_user_invitation)
    TextView tvUserInvitation;
    @BindView(R.id.tv_user_callcenter)
    TextView tvUserCallcenter;
    @BindView(R.id.tv_user_feedback)
    TextView tvUserFeedback;
    @BindView(R.id.ll_root)
    LinearLayout llRoot;
    @BindView(R.id.scrollview)
    com.hankkin.gradationscroll.GradationScrollView scrollview;
    @BindView(R.id.tv_usercenter)
    TextView tvUsercenter;
    @BindView(R.id.ib_user_setting)
    ImageButton ibUserSetting;
    @BindView(R.id.ib_user_message)
    ImageButton ibUserMessage;

    private int height;

    /*
        初始化布局
        * */
    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.fragment_user, null);
        ButterKnife.bind(this, view);
        return view;
    }
    /*
    * 布局加载完后加载数据
    * */

    @Override
    public void initData() {
        super.initData();
        ViewTreeObserver observer = rlHeader.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                //移除视图树的监听
                rlHeader.getViewTreeObserver().removeGlobalOnLayoutListener(this);

                height=rlHeader.getHeight();

            }
        });
        scrollview.setScrollViewListener(UserFragment.this);
        tvUsercenter.setBackgroundColor(Color.argb(0,255,0,0));
    }


    @OnClick(R.id.tv_username)
    public void onClick() {
        Intent intent=new Intent(mContext, LoginActivity.class);
        mContext.startActivity(intent);
    }

    @Override
    public void onScrollChanged(GradationScrollView scrollView, int x, int y, int oldx, int oldy) {
        if(y<=0) {
            //设置标题的背景颜色  -透明
            tvUsercenter.setBackgroundColor(Color.argb(0,255,0,0));
        }else if(y>0&&y<=height){
            //滑动的距离：height=实时透明度：透明度最大值
            //实时透明度=透明度最大值*滑动的距离/height
            int alpa=255*y/height;
            tvUsercenter.setTextColor(Color.argb(alpa, 255, 255, 255));
            tvUsercenter.setBackgroundColor(Color.argb(alpa,255,0,0));
        }else{
            tvUsercenter.setBackgroundColor(Color.argb(255,255,0,0));
        }
    }
}
