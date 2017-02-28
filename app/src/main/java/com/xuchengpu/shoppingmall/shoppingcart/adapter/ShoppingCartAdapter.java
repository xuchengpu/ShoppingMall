package com.xuchengpu.shoppingmall.shoppingcart.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xuchengpu.shoppingmall.R;
import com.xuchengpu.shoppingmall.home.bean.GoodsBean;
import com.xuchengpu.shoppingmall.shoppingcart.utils.CartStorage;
import com.xuchengpu.shoppingmall.shoppingcart.view.AddSubView;
import com.xuchengpu.shoppingmall.utils.Constants;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 许成谱 on 2017/2/28 15:38.
 * qq:1550540124
 * for:
 */

public class ShoppingCartAdapter extends RecyclerView.Adapter<ShoppingCartAdapter.MyViewHolder> {

    private TextView tvShopcartTotal;
    private CheckBox checkboxAll;
    private CheckBox checkboxDeleteAll;
    private Context mContext;
    private List<GoodsBean> datas;

    public ShoppingCartAdapter(Context mContext, List<GoodsBean> goodsBeens, TextView tvShopcartTotal, CheckBox checkboxAll, CheckBox checkboxDeleteAll) {
        this.mContext = mContext;
        this.datas = goodsBeens;
        this.tvShopcartTotal = tvShopcartTotal;
        this.checkboxAll = checkboxAll;
        this.checkboxDeleteAll = checkboxDeleteAll;

        showTotalPrice();


    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new MyViewHolder(View.inflate(mContext, R.layout.item_shop_cart, null));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final GoodsBean goodsBean = datas.get(position);
        holder.cbGov.setChecked(goodsBean.isChecked());
        Glide.with(mContext).load(Constants.BASE_URL_IMAGE + goodsBean.getFigure()).into(holder.ivGov);
        holder.tvDescGov.setText(goodsBean.getName());
        holder.tvPriceGov.setText("￥" + goodsBean.getCover_price());
        holder.addSubView.setValue(goodsBean.getNumber());
        holder.addSubView.setMaxValue(100);
        holder.addSubView.setMinValue(1);
        holder.addSubView.setOnNumberChangerListener(new AddSubView.OnNumberChangerListener() {
            @Override
            public void OnNumberChanger(int value) {
                //得到从AddSubView类回调的value值后  重新设置给goodsbean对象 再刷新
                goodsBean.setNumber(value);
                CartStorage.getInstance(mContext).updata(goodsBean);
                showTotalPrice();
            }
        });

    }

    public void showTotalPrice() {
        //显示总价格
        tvShopcartTotal.setText("合计:" + getTotalPrice());
        checkAll();
    }

    public double getTotalPrice() {
        double totlePrice = 0;
        if (datas != null && datas.size() > 0) {
            for (int i = 0; i < datas.size(); i++) {
                if (datas.get(i).isChecked()) {
                    totlePrice += Double.parseDouble(datas.get(i).getCover_price()) * datas.get(i).getNumber();
                }
            }
        }
        return totlePrice;
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public void checkAll_none(boolean ischecked) {
        if (datas != null && datas.size() > 0) {
            for (int i = 0; i < datas.size(); i++) {
                GoodsBean goodsBean = datas.get(i);
                goodsBean.setChecked(ischecked);
                CartStorage.getInstance(mContext).updata(goodsBean);
                checkboxAll.setChecked(ischecked);
                checkboxDeleteAll.setChecked(ischecked);
                //更新视图
                notifyItemChanged(i);
                // notifyDataSetChanged();

            }
        } else {
            checkboxAll.setChecked(false);
        }

    }

    public void deleteData() {
        if (datas != null && datas.size() > 0) {
            for (int i = 0; i < datas.size(); i++) {
                GoodsBean goodsBean = datas.get(i);
                //当勾选的才删除
                if (goodsBean.isChecked()) {
                    //1.内存中删除
                    datas.remove(goodsBean);
                    //2.本地也好保持
                    CartStorage.getInstance(mContext).deleteData(goodsBean);
                    //刷新数据
                    notifyItemRemoved(i);
                    i--;
                }
            }
        }
    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.cb_gov)
        CheckBox cbGov;
        @BindView(R.id.iv_gov)
        ImageView ivGov;
        @BindView(R.id.tv_desc_gov)
        TextView tvDescGov;
        @BindView(R.id.tv_price_gov)
        TextView tvPriceGov;
        @BindView(R.id.addSubView)
        AddSubView addSubView;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    GoodsBean goodsBean = datas.get(getLayoutPosition());
                    goodsBean.setChecked(!goodsBean.isChecked());
                    CartStorage.getInstance(mContext).updata(goodsBean);
                    showTotalPrice();
                    notifyDataSetChanged();

                }
            });
        }
    }

    public void checkAll() {
        if (datas != null && datas.size() > 0) {
            int num = 0;
            for (int i = 0; i < datas.size(); i++) {
                GoodsBean goodsBean = datas.get(i);
                if (goodsBean.isChecked()) {
                    num++;
                }
            }
            if (num == datas.size()) {
                checkboxAll.setChecked(true);
                checkboxDeleteAll.setChecked(true);
            } else {
                checkboxAll.setChecked(false);
                checkboxDeleteAll.setChecked(false);
            }
        }
        /*if (datas != null && datas.size() > 0) {
            int number = 0;
            for (int i = 0; i < datas.size(); i++) {
                GoodsBean goodsBean = datas.get(i);
                if (!goodsBean.isChecked()) {
                    checkboxAll.setChecked(false);
                    checkboxDeleteAll.setChecked(false);
                } else {
                    number++;

                }

            }

            if(datas.size() == number){
                checkboxAll.setChecked(true);
                checkboxDeleteAll.setChecked(true);
            }
        }else {
            checkboxAll.setChecked(false);
            checkboxDeleteAll.setChecked(false);
        }*/

    }
}
