package com.xuchengpu.shoppingmall.type.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.xuchengpu.shoppingmall.R;
import com.xuchengpu.shoppingmall.app.GoodsInfoActivity;
import com.xuchengpu.shoppingmall.home.adapter.HomeAdapter;
import com.xuchengpu.shoppingmall.home.bean.GoodsBean;
import com.xuchengpu.shoppingmall.type.bean.TypeBean;
import com.xuchengpu.shoppingmall.utils.Constants;
import com.xuchengpu.shoppingmall.utils.DensityUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 许成谱 on 2017/3/3 16:20.
 * qq:1550540124
 * for:
 */

public class TypeRightAdapter extends RecyclerView.Adapter {
    private final Context mContext;
    private final List<TypeBean.ResultBean.ChildBean> child;
    private final List<TypeBean.ResultBean.HotProductListBean> hot_product_list;
    private final LayoutInflater inflater;


    public TypeRightAdapter(Context mContext, TypeBean.ResultBean resultBean) {
        this.mContext = mContext;
        child = resultBean.getChild();
        hot_product_list = resultBean.getHot_product_list();
        inflater = LayoutInflater.from(mContext);
    }
    //实现分类显示
    private static final int CHILD = 1;
    private static final int HOT = 0;

    @Override
    public int getItemCount() {
        return 1 + child.size();
    }

    private int currenttype;

    //人为指定类型
    @Override
    public int getItemViewType(int position) {
        if (position == HOT) {
            currenttype = HOT;
        } else {
            currenttype = CHILD;
        }
        return currenttype;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == HOT) {
            return new HotViewHolder(inflater.inflate(R.layout.item_hot_right, null));
        } else if (viewType == CHILD) {
            return new ChildViewHolder(inflater.inflate(R.layout.item_child_right, null));
        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == HOT) {
            HotViewHolder hotViewHolder = (HotViewHolder) holder;
            hotViewHolder.setData();
        } else if (getItemViewType(position) == CHILD) {
            ChildViewHolder childViewHolder = (ChildViewHolder) holder;
            int readPosition = position-1;
            childViewHolder.setData(readPosition);
        }

    }

    class ChildViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_ordinary_right)
        ImageView ivOrdinaryRight;
        @BindView(R.id.tv_ordinary_right)
        TextView tvOrdinaryRight;
        @BindView(R.id.ll_root)
        LinearLayout llRoot;

        public ChildViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setData(final int readPosition) {
            Glide.with(mContext).load(Constants.BASE_URL_IMAGE + child.get(readPosition).getPic()).placeholder(R.drawable.new_img_loading_2).into(ivOrdinaryRight);
            tvOrdinaryRight.setText(child.get(readPosition).getName());
            llRoot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext, "child.get(readPosition).getName():"+child.get(readPosition).getName(), Toast.LENGTH_SHORT).show();
                }
            });

        }
    }

    class HotViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ll_hot_right)
        LinearLayout llHotRight;
        @BindView(R.id.hsl_hot_right)
        HorizontalScrollView hslHotRight;


        public HotViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        public void setData() {
            for (int i = 0; i < hot_product_list.size(); i++) {
                Log.e("tag", "i==" + i);
                //用代码书写布局
                LinearLayout linearLayout = new LinearLayout(mContext);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(-2, -2);
                linearLayout.setOrientation(LinearLayout.VERTICAL);
                linearLayout.setGravity(Gravity.CENTER);
                params.setMargins((DensityUtil.dip2px(mContext, 5)), 0, DensityUtil.dip2px(mContext, 5), DensityUtil.dip2px(mContext, 20));
//                linearLayout.setLayoutParams(params);

                ImageView imageView = new ImageView(mContext);
                LinearLayout.LayoutParams ivParams = new LinearLayout.LayoutParams(DensityUtil.dip2px(mContext, 80), DensityUtil.dip2px(mContext, 80));
                ivParams.setMargins(0, 0, 0, DensityUtil.dip2px(mContext, 10));
                Glide.with(mContext).load(Constants.BASE_URL_IMAGE + hot_product_list.get(i).getFigure()).into(imageView);
                imageView.setLayoutParams(ivParams);

                TextView textview = new TextView(mContext);
                LinearLayout.LayoutParams tvParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                textview.setGravity(Gravity.CENTER);
                textview.setTextColor(Color.parseColor("#ed3f3f"));
                textview.setText("￥" + hot_product_list.get(i).getCover_price());
                //将位置参数添加进对应的控件
                textview.setLayoutParams(tvParams);
                //将控件添加进容器里面
                linearLayout.addView(imageView);
                linearLayout.addView(textview);

                llHotRight.addView(linearLayout, params);

                final int finalI = i;
                linearLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String cover_price = hot_product_list.get(finalI).getCover_price();
                        String name = hot_product_list.get(finalI).getName();
                        String figure = hot_product_list.get(finalI).getFigure();
                        String product_id = hot_product_list.get(finalI).getProduct_id();

                        //创建商品Bean对象
                        GoodsBean goodsBean = new GoodsBean();
                        goodsBean.setProduct_id(product_id);
                        goodsBean.setFigure(figure);
                        goodsBean.setCover_price(cover_price);
                        goodsBean.setName(name);

                        Intent intent = new Intent(mContext, GoodsInfoActivity.class);
                        intent.putExtra(HomeAdapter.GOODSBEAN, goodsBean);
                        mContext.startActivity(intent);
                    }
                });

            }

        }
    }

}
