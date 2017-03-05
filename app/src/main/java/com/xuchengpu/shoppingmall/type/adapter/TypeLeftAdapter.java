package com.xuchengpu.shoppingmall.type.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xuchengpu.shoppingmall.R;
import com.xuchengpu.shoppingmall.type.fragment.ListFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 许成谱 on 2017/3/3 15:27.
 * qq:1550540124
 * for:
 */

public class TypeLeftAdapter extends BaseAdapter {
    private final String[] datas;
    private final Context mContext;
    private final ListFragment listFragment;
    private int prePosition=0;
    //注意对象也可以传进来
    public TypeLeftAdapter(String[] titles, Context mContext, ListFragment listFragment) {
        this.datas = titles;
        this.mContext = mContext;
        this.listFragment = listFragment;
    }

    @Override
    public int getCount() {
        return datas.length;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_type, null);
            viewHolder=new ViewHolder(convertView);
            convertView.setTag(viewHolder);

        }else{
            viewHolder= (ViewHolder) convertView.getTag();
        }
        viewHolder.tvTitle.setText(datas[position]);

        convertView.setBackgroundResource(R.drawable.bg2);  //其他项背景
        viewHolder.tvTitle.setTextColor(Color.parseColor("#323437"));
        //设置点击改变选中item颜色  用preposition记录位置  也可用一个接口回调的形式在listfragment中操作
        if(prePosition==position) {
            convertView.setBackgroundResource(R.drawable.type_item_background_selector);
            //将数字直接转化为颜色
            viewHolder.tvTitle.setTextColor(Color.parseColor("#fd3f3f"));
        }

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prePosition=position;
                notifyDataSetChanged();
                //点击哪条item 就对哪条item请求数据
                listFragment.getDataFormNet(listFragment.urls[position]);

            }
        });
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.tv_title)
        TextView tvTitle;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
