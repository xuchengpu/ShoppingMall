package com.xuchengpu.shoppingmall.type.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.xuchengpu.shoppingmall.R;
import com.xuchengpu.shoppingmall.type.bean.TagBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 许成谱 on 2017/3/3 18:51.
 * qq:1550540124
 * for:
 */

public class TypeTagAdapter extends BaseAdapter {
    private final Context mContext;
    private final List<TagBean.ResultBean> data;
    private final int[] colors;

    public TypeTagAdapter(Context mContext, List<TagBean.ResultBean> result, int[] colors) {
        this.mContext = mContext;
        this.data = result;
        this.colors = colors;
    }

    @Override
    public int getCount() {
        return data.size();
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
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_tag, null);
            viewHolder=new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder= (ViewHolder) convertView.getTag();
        }
        viewHolder.tvTag.setText(data.get(position).getName());
        //设置颜色随机
        viewHolder.tvTag.setTextColor(colors[position%colors.length]);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "name="+data.get(position).getName(), Toast.LENGTH_SHORT).show();
            }
        });
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.tv_tag)
        TextView tvTag;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
