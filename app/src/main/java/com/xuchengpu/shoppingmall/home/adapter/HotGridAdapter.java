package com.xuchengpu.shoppingmall.home.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xuchengpu.shoppingmall.R;
import com.xuchengpu.shoppingmall.home.bean.HomeBean;
import com.xuchengpu.shoppingmall.utils.Constants;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 许成谱 on 2017/2/25 16:40.
 * qq:1550540124
 * for:
 */

public class HotGridAdapter extends BaseAdapter {
    private final Context mContext;
    private final List<HomeBean.ResultBean.HotInfoBean> hot_info;

    public HotGridAdapter(Context mContext, List<HomeBean.ResultBean.HotInfoBean> hot_info) {
        this.mContext = mContext;
        this.hot_info = hot_info;
    }

    @Override
    public int getCount() {
        return hot_info.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.hot_grid, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tvName.setText(hot_info.get(position).getName());
        viewHolder.tvPrice.setText(hot_info.get(position).getCover_price());
        Glide.with(mContext).load(Constants.BASE_URL_IMAGE+hot_info.get(position).getFigure()).into(viewHolder.ivHot);
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.iv_hot)
        ImageView ivHot;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_price)
        TextView tvPrice;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
