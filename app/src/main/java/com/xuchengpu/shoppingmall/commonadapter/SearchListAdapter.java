package com.xuchengpu.shoppingmall.commonadapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xuchengpu.shoppingmall.R;
import com.xuchengpu.shoppingmall.User;
import com.xuchengpu.shoppingmall.app.SearchActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 许成谱 on 2017/3/8 10:55.
 * qq:1550540124
 * for:
 */

public class SearchListAdapter extends BaseAdapter {
    private final Context mContext;
    private final List<User> datas;

    public SearchListAdapter(SearchActivity searchActivity, List<User> users) {
        this.mContext = searchActivity;
        this.datas = users;
    }

    @Override
    public int getCount() {
        return datas==null?0:datas.size();
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
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_history, null);
            holder=new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder= (ViewHolder) convertView.getTag();
        }
        holder.tvItemHistory.setText(datas.get(position).getName());
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.tv_item_history)
        TextView tvItemHistory;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
