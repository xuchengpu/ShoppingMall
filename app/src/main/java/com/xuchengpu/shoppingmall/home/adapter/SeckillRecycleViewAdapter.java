package com.xuchengpu.shoppingmall.home.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xuchengpu.shoppingmall.R;
import com.xuchengpu.shoppingmall.home.bean.HomeBean;
import com.xuchengpu.shoppingmall.utils.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 许成谱 on 2017/2/25 15:28.
 * qq:1550540124
 * for:
 */

public class SeckillRecycleViewAdapter extends RecyclerView.Adapter<SeckillRecycleViewAdapter.ViewHolder> {
    private final Context mContext;
    private final HomeBean.ResultBean.SeckillInfoBean seckill_info;


    public SeckillRecycleViewAdapter(Context mContext, HomeBean.ResultBean.SeckillInfoBean seckill_info) {
        this.mContext = mContext;
        this.seckill_info = seckill_info;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(View.inflate(mContext, R.layout.seckill_recycleview, null));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setData(seckill_info,position);
    }

    @Override
    public int getItemCount() {
        return seckill_info.getList().size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_figure)
        ImageView ivFigure;
        @BindView(R.id.tv_cover_price)
        TextView tvCoverPrice;
        @BindView(R.id.tv_origin_price)
        TextView tvOriginPrice;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        public void setData(HomeBean.ResultBean.SeckillInfoBean seckill_info, final int position) {
            tvCoverPrice.setText(seckill_info.getList().get(position).getCover_price());
            tvOriginPrice.setText(seckill_info.getList().get(position).getOrigin_price());
            Glide.with(mContext).load(Constants.BASE_URL_IMAGE+seckill_info.getList().get(position).getFigure()).into(ivFigure);
            ivFigure.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onClick(v,position);
                }
            });
        }
    }
    public  interface OnItemClickListener{
        void onClick(View v, int position);
    }
    private OnItemClickListener onItemClickListener;
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener=onItemClickListener;
    }

}
