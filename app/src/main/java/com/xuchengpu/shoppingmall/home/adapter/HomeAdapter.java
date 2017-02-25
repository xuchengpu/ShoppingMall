package com.xuchengpu.shoppingmall.home.adapter;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.xuchengpu.shoppingmall.R;
import com.xuchengpu.shoppingmall.home.bean.HomeBean;
import com.xuchengpu.shoppingmall.home.view.MyGridView;
import com.xuchengpu.shoppingmall.utils.Constants;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;
import com.youth.banner.transformer.BackgroundToForegroundTransformer;
import com.zhy.magicviewpager.transformer.RotateYTransformer;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.iwgang.countdownview.CountdownView;

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
                return new BannerHolder(mContext, inflater.inflate(R.layout.item_banner, null));
            case CHANNEL:
                return new ChannelHolder(mContext, inflater.inflate(R.layout.item_channel, null));
            case ACT:
                return new ActHolder(mContext, inflater.inflate(R.layout.item_act, null));
            case SECKILL:
                return new SeckillHolder(mContext, inflater.inflate(R.layout.item_seckill, null));
            case RECOMMEND:
                return new RecommendHolder(mContext, inflater.inflate(R.layout.item_recommend, null));
            case HOT:
                return new HotHolder(mContext, inflater.inflate(R.layout.item_hot, null));
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
                BannerHolder bannerViewHolder = (BannerHolder) holder;
                bannerViewHolder.setData(result.getBanner_info());
                break;
            case CHANNEL:
                ChannelHolder channelViewHolder = (ChannelHolder) holder;
                channelViewHolder.setData(result.getChannel_info());
                break;
            case ACT:
                ActHolder actViewHolder = (ActHolder) holder;
                actViewHolder.setData(result.getAct_info());
                break;
            case SECKILL:
                SeckillHolder seckllViewHolder = (SeckillHolder) holder;
                seckllViewHolder.setData(result.getSeckill_info());
                break;
            case RECOMMEND:
                RecommendHolder recommendViewHolder = (RecommendHolder) holder;
                recommendViewHolder.setData(result.getRecommend_info());
                break;
            case HOT:
                HotHolder hotViewHolder = (HotHolder) holder;
                hotViewHolder.setData(result.getHot_info());
                break;
            default:
                break;
        }

    }

    @Override
    public int getItemCount() {
        return 6;
    }

    class HotHolder extends RecyclerView.ViewHolder {
        private final Context mContext;
        @BindView(R.id.tv_more_hot)
        TextView tvMoreHot;
        @BindView(R.id.gv_hot)
        MyGridView gvHot;
        HotGridAdapter adapter;


        public HotHolder(Context mContext, View itemView) {
            super(itemView);
            this.mContext = mContext;
            ButterKnife.bind(this, itemView);
        }

        public void setData(List<HomeBean.ResultBean.HotInfoBean> hot_info) {
            adapter=new HotGridAdapter(mContext,hot_info);
            gvHot.setAdapter(adapter);
        }
    }

    class RecommendHolder extends RecyclerView.ViewHolder {
        private final Context mContext;
        @BindView(R.id.tv_more_recommend)
        TextView tvMoreRecommend;
        @BindView(R.id.gv_recommend)
        GridView gvRecommend;
        RecommendGridAdapter adapter;

        public RecommendHolder(Context mContext, View itemView) {
            super(itemView);
            this.mContext = mContext;
            ButterKnife.bind(this, itemView);
        }

        public void setData(List<HomeBean.ResultBean.RecommendInfoBean> recommend_info) {
            adapter = new RecommendGridAdapter(mContext, recommend_info);
            gvRecommend.setAdapter(adapter);
        }
    }

    class SeckillHolder extends RecyclerView.ViewHolder {
        private final Context mContext;
        @BindView(R.id.countdownview)
        CountdownView countdownview;
        @BindView(R.id.tv_more_seckill)
        TextView tvMoreSeckill;
        @BindView(R.id.rv_seckill)
        RecyclerView rvSeckill;
        private SeckillRecycleViewAdapter adapter;

        public SeckillHolder(Context mContext, View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.mContext = mContext;
        }

        public void setData(HomeBean.ResultBean.SeckillInfoBean seckill_info) {
            countdownview.setTag("test1");
            long time1 = (long) 5 * 60 * 60 * 1000;
            countdownview.start(time1);


            adapter = new SeckillRecycleViewAdapter(mContext, seckill_info);
            rvSeckill.setAdapter(adapter);
            rvSeckill.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        }
    }


    class ActHolder extends RecyclerView.ViewHolder {

        private final Context mContext;

        ViewPager actViewpager;
        private ActViewPagerAdapter adapter;

        public ActHolder(Context mContext, View itemView) {
            super(itemView);
            this.mContext = mContext;
            actViewpager = (ViewPager) itemView.findViewById(R.id.act_viewpager);
        }

        public void setData(List<HomeBean.ResultBean.ActInfoBean> act_info) {
            adapter = new ActViewPagerAdapter(mContext, act_info);
            actViewpager.setAdapter(adapter);

            //从网上找的对viewpager设置一些特性
            //美化ViewPager库
            actViewpager.setPageMargin(20);//设置page间间距，自行根据需求设置
            actViewpager.setOffscreenPageLimit(3);//>=3
            actViewpager.setAdapter(adapter);
            //setPageTransformer 决定动画效果
            actViewpager.setPageTransformer(true, new
                    RotateYTransformer());
            adapter.setOnItemClickListener(new ActViewPagerAdapter.OnItemClickListener() {
                @Override
                public void onClick(View v, int position) {
                    Toast.makeText(mContext, "position==" + position, Toast.LENGTH_SHORT).show();
                }
            });

        }
    }

    class ChannelHolder extends RecyclerView.ViewHolder {
        private Context context;
        private GridView gv_channel;
        private ListAdapter adapter;

        public ChannelHolder(Context mContext, View itemView) {
            super(itemView);
            this.context = mContext;
            gv_channel = (GridView) itemView.findViewById(R.id.gv_channel);
        }

        public void setData(List<HomeBean.ResultBean.ChannelInfoBean> channel_info) {
            adapter = new ChannelGridAdapter(context, channel_info);
            gv_channel.setAdapter(adapter);
            gv_channel.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Toast.makeText(context, "position=" + position, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }


    class BannerHolder extends RecyclerView.ViewHolder {
        private final Context context;
        private Banner banner;

        public BannerHolder(Context mContext, View itemView) {
            super(itemView);
            banner = (Banner) itemView.findViewById(R.id.banner);
            this.context = mContext;
        }

        public void setData(List<HomeBean.ResultBean.BannerInfoBean> banner_info) {
            List images = new ArrayList();
            for (int i = 0; i < banner_info.size(); i++) {
                images.add(Constants.BASE_URL_IMAGE + banner_info.get(i).getImage());
            }
            banner.setImages(images)
                    .setImageLoader(new ImageLoader() {
                        @Override
                        public void displayImage(Context context, Object path, ImageView imageView) {
                            Glide.with(context)
                                    .load(path)
                                    .crossFade()
                                    .into(imageView);
                        }
                    })
                    .start();
            //设置样式
            banner.setBannerAnimation(BackgroundToForegroundTransformer.class);
            //设置Banner的点击事件
            banner.setOnBannerListener(new OnBannerListener() {
                @Override
                public void OnBannerClick(int position) {
                    Toast.makeText(context, "position=" + position, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}

