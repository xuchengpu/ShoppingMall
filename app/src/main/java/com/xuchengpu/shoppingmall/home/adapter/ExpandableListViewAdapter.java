package com.xuchengpu.shoppingmall.home.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xuchengpu.shoppingmall.R;
import com.xuchengpu.shoppingmall.home.activity.GoodsListActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 许成谱 on 2017/3/6 16:13.
 * qq:1550540124
 * for:
 */

public class ExpandableListViewAdapter extends BaseExpandableListAdapter {
    private final GoodsListActivity mContext;
    private final List<String> father;
    private final List<List<String>> child;
    private int childP;
    private int groupP;


    public ExpandableListViewAdapter(GoodsListActivity goodsListActivity, List father, List child) {
        this.mContext = goodsListActivity;
        this.father = father;
        this.child = child;
    }

    @Override
    public int getGroupCount() {
        return father.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return child.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return father.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return child.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    /*局部数据刷新*/
    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.group_list_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        //设置组名
        holder.textView.setText(father.get(groupPosition));
        holder.textView.setPadding(0, 10, 0, 10);

//设置展开和非展开的状态

        if (isExpanded) {
            holder.imageView.setImageResource(R.drawable.filter_list_selected);
        } else {
            holder.imageView.setImageResource(R.drawable.filter_list_unselected);
        }


        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.child_list_item, null);
            holder = new ChildViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ChildViewHolder) convertView.getTag();
        }
        //设置文本
//        if (groupPosition != 0) {
//            holder.textView.setText(child.get(groupPosition).get(childPosition));
//        }

        List<String> s = child.get(groupPosition);
        if (s!=null&&s.size()>0) {
            holder.textView.setText(s.get(childPosition));
        }
        //被点击的孩子
        if (childP == childPosition && groupP == groupPosition) {
            //把被点击的孩子的图片-显示
            holder.childImageView.setVisibility(View.VISIBLE);
//            notifyDataSetChanged();
        } else {
            //把被点击的孩子的图片-隐藏
            holder.childImageView.setVisibility(View.GONE);
//            notifyDataSetChanged();
        }

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        childP = childPosition;
        groupP = groupPosition;
        return true;
    }


    class ViewHolder {
        @BindView(R.id.imageView)
        ImageView imageView;
        @BindView(R.id.textView)
        TextView textView;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    class ChildViewHolder {
        @BindView(R.id.textView)
        TextView textView;
        @BindView(R.id.childImageView)
        ImageView childImageView;
        @BindView(R.id.ll_child_root)
        LinearLayout llChildRoot;

        ChildViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
