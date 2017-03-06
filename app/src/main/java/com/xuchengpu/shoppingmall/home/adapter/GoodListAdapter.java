package com.xuchengpu.shoppingmall.home.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xuchengpu.shoppingmall.R;
import com.xuchengpu.shoppingmall.home.activity.GoodsListActivity;
import com.xuchengpu.shoppingmall.home.bean.GoodListBean;
import com.xuchengpu.shoppingmall.utils.Constants;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 许成谱 on 2017/3/6 14:58.
 * qq:1550540124
 * for:
 */

public class GoodListAdapter extends RecyclerView.Adapter<GoodListAdapter.MyViewHolder> {
    private final Context mContext;
    private final List<GoodListBean.ResultBean.PageDataBean> datas;


    public GoodListAdapter(GoodsListActivity goodsListActivity, List<GoodListBean.ResultBean.PageDataBean> page_data) {
        this.mContext = goodsListActivity;
        this.datas = page_data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(mContext, R.layout.item_goods_list, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        GoodListBean.ResultBean.PageDataBean entity = datas.get(position);
        //2.绑定数据
        Glide.with(mContext).load(Constants.BASE_URL_IMAGE + entity.getFigure()).placeholder(R.drawable.new_img_loading_2).into(holder.ivHot);
        holder.tvName.setText(entity.getName());
        holder.tvPrice.setText("￥" + entity.getCover_price());

    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_hot)
        ImageView ivHot;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_price)
        TextView tvPrice;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(datas.get(getLayoutPosition()));
                }
            });
        }

    }

    /**
     * 点击item的接口
     */
    public interface OnItemClickListener{
        public void onItemClick(GoodListBean.ResultBean.PageDataBean  data);
    }

    private OnItemClickListener listener;

    /**
     * 设置item的点击事件
     * @param listener
     */
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
