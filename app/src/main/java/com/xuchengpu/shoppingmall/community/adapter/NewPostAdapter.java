package com.xuchengpu.shoppingmall.community.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.opendanmaku.DanmakuItem;
import com.opendanmaku.DanmakuView;
import com.opendanmaku.IDanmakuItem;
import com.xuchengpu.shoppingmall.R;
import com.xuchengpu.shoppingmall.community.bean.NewPostBean;
import com.xuchengpu.shoppingmall.utils.Constants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 许成谱 on 2017/3/5 18:08.
 * qq:1550540124
 * for:
 */

public class NewPostAdapter extends BaseAdapter {
    private final Context mContext;
    private final List<NewPostBean.ResultBean> datas;

    public NewPostAdapter(Context mContext, List<NewPostBean.ResultBean> result) {
        this.mContext = mContext;
        this.datas = result;
    }

    @Override
    public int getCount() {
        return datas.size();
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
            convertView = View.inflate(mContext, R.layout.item_listview_newpost, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        //根据位置得到对应的数据
        NewPostBean.ResultBean entity = datas.get(position);
        //图像
        Glide.with(mContext).load(Constants.BASE_URL_IMAGE + entity.getAvatar()).into(viewHolder.ibNewPostAvatar);
        viewHolder.tvCommunityUsername.setText(entity.getUsername());
        //图像
        Glide.with(mContext).load(Constants.BASE_URL_IMAGE + entity.getFigure()).into(viewHolder.ivCommunityFigure);

        viewHolder.tvCommunitySaying.setText(entity.getSaying());
        viewHolder.tvCommunityLikes.setText(entity.getLikes());
        viewHolder.tvCommunityComments.setText(entity.getComments());

        //显示弹幕  具体操作可参考demo
        List<String> strings = entity.getComment_list();
        if (strings != null && strings.size() > 0) {
            //有弹幕数据
            List<IDanmakuItem> list = initItems(viewHolder.danmakuView,strings);
            Collections.shuffle(list);
            viewHolder.danmakuView.addItem(list, true);
            viewHolder.danmakuView.show();
            viewHolder.danmakuView.setVisibility(View.VISIBLE);
        }else{
            viewHolder.danmakuView.hide();
            viewHolder.danmakuView.setVisibility(View.GONE);
        }


        return convertView;
    }

    private List<IDanmakuItem> initItems(DanmakuView danmakuView, List<String> strings ) {
        List<IDanmakuItem> list = new ArrayList<>();
        for (int i = 0; i < strings.size(); i++) {
            IDanmakuItem item = new DanmakuItem(mContext, strings.get(i), danmakuView.getWidth());
            list.add(item);
        }
        return list;
    }

    static class ViewHolder {
        @BindView(R.id.tv_community_username)
        TextView tvCommunityUsername;
        @BindView(R.id.tv_community_addtime)
        TextView tvCommunityAddtime;
        @BindView(R.id.rl)
        RelativeLayout rl;
        @BindView(R.id.ib_new_post_avatar)
        ImageButton ibNewPostAvatar;
        @BindView(R.id.iv_community_figure)
        ImageView ivCommunityFigure;
        @BindView(R.id.danmakuView)
        com.opendanmaku.DanmakuView danmakuView;
        @BindView(R.id.tv_community_saying)
        TextView tvCommunitySaying;
        @BindView(R.id.tv_community_likes)
        TextView tvCommunityLikes;
        @BindView(R.id.tv_community_comments)
        TextView tvCommunityComments;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
