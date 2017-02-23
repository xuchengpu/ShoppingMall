package com.xuchengpu.shoppingmall.home.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xuchengpu.shoppingmall.R;
import com.xuchengpu.shoppingmall.home.bean.HomeBean;

import java.util.List;

/**
 * Created by 许成谱 on 2017/2/23 15:41.
 * qq:1550540124
 * for:
 */

public class HomeAdapter extends RecyclerView.Adapter {
    private final Context mContext;
    private final HomeBean.ResultBean result;

    /*
    根据json解析可知result中含有六种数据类型，对应六种viewholder
    * */
    public static final int BANNER = 0;
    public static final int CHANNEL = 1;
    public static final int ACT = 2;
    public static final int SECKILL = 3;
    public static final int RECOMMEND = 4;
    public static final int HOT = 5;

    private int currentType = BANNER;
    private final LayoutInflater inflater;

    public HomeAdapter(Context mContext, HomeBean.ResultBean result) {
        this.mContext = mContext;
        this.result = result;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case BANNER:
                currentType = BANNER;
                break;
            case CHANNEL:
                currentType = CHANNEL;
                break;
            case ACT:
                currentType = ACT;
                break;
            case SECKILL:
                currentType = SECKILL;
                break;
            case RECOMMEND:
                currentType = RECOMMEND;
                break;
            case HOT:
                currentType = HOT;
                break;
            default:
                currentType = BANNER;
                break;
        }
        return currentType;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        /*
        初始化视图
        * */
        switch (viewType) {
            case BANNER:
               return new BannerHolder(mContext,inflater.inflate(R.layout.banner_viewpager,null));
            case CHANNEL:

                break;
            case ACT:
                break;
            case SECKILL:
                break;
            case RECOMMEND:
                break;
            case HOT:
                break;
            default:
                break;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        /*
        * 绑定设置数据
        * */
        switch (getItemViewType(position)) {
            case BANNER:
                BannerHolder viewHolder= (BannerHolder) holder;
                viewHolder.setData(result.getBanner_info());
                break;
            case CHANNEL:

                break;
            case ACT:
                break;
            case SECKILL:
                break;
            case RECOMMEND:
                break;
            case HOT:
                break;
            default:
                break;
        }

    }

    @Override
    public int getItemCount() {
        return 1;
    }

    class BannerHolder extends RecyclerView.ViewHolder{
        TextView tv;

        public BannerHolder(Context mContext, View itemView) {
            super(itemView);
            tv= (TextView) itemView.findViewById(R.id.tv_banner);
        }

        public void setData(List<HomeBean.ResultBean.BannerInfoBean> banner_info) {
            tv.setText("使用banner前测试");
        }
    }
}

