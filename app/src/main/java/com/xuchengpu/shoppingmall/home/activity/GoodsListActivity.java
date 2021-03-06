package com.xuchengpu.shoppingmall.home.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.xuchengpu.shoppingmall.R;
import com.xuchengpu.shoppingmall.app.GoodsInfoActivity;
import com.xuchengpu.shoppingmall.app.SearchActivity;
import com.xuchengpu.shoppingmall.home.adapter.ExpandableListViewAdapter;
import com.xuchengpu.shoppingmall.home.adapter.GoodListAdapter;
import com.xuchengpu.shoppingmall.home.adapter.HomeAdapter;
import com.xuchengpu.shoppingmall.home.bean.GoodListBean;
import com.xuchengpu.shoppingmall.home.bean.GoodsBean;
import com.xuchengpu.shoppingmall.home.view.SpaceItemDecoration;
import com.xuchengpu.shoppingmall.utils.Constants;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

public class GoodsListActivity extends AppCompatActivity {
    @BindView(R.id.ib_goods_list_back)
    ImageButton ibGoodsListBack;
    @BindView(R.id.tv_goods_list_search)
    TextView tvGoodsListSearch;
    @BindView(R.id.ib_goods_list_home)
    ImageButton ibGoodsListHome;
    @BindView(R.id.tv_goods_list_sort)
    TextView tvGoodsListSort;
    @BindView(R.id.tv_goods_list_price)
    TextView tvGoodsListPrice;
    @BindView(R.id.iv_goods_list_arrow)
    ImageView ivGoodsListArrow;
    @BindView(R.id.ll_goods_list_price)
    LinearLayout llGoodsListPrice;
    @BindView(R.id.tv_goods_list_select)
    TextView tvGoodsListSelect;
    @BindView(R.id.ll_goods_list_head)
    LinearLayout llGoodsListHead;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.ib_drawer_layout_back)
    ImageButton ibDrawerLayoutBack;
    @BindView(R.id.tv_ib_drawer_layout_title)
    TextView tvIbDrawerLayoutTitle;
    @BindView(R.id.ib_drawer_layout_confirm)
    TextView ibDrawerLayoutConfirm;
    @BindView(R.id.rb_select_hot)
    RadioButton rbSelectHot;
    @BindView(R.id.rb_select_new)
    RadioButton rbSelectNew;
    @BindView(R.id.rg_select)
    RadioGroup rgSelect;
    @BindView(R.id.tv_drawer_price)
    TextView tvDrawerPrice;
    @BindView(R.id.tv_drawer_recommend)
    TextView tvDrawerRecommend;
    @BindView(R.id.rl_select_recommend_theme)
    RelativeLayout rlSelectRecommendTheme;
    @BindView(R.id.tv_drawer_type)
    TextView tvDrawerType;
    @BindView(R.id.rl_select_type)
    RelativeLayout rlSelectType;
    @BindView(R.id.btn_select_all)
    Button btnSelectAll;
    @BindView(R.id.ll_select_root)
    LinearLayout llSelectRoot;
    @BindView(R.id.btn_drawer_layout_cancel)
    Button btnDrawerLayoutCancel;
    @BindView(R.id.btn_drawer_layout_confirm)
    Button btnDrawerLayoutConfirm;
    @BindView(R.id.iv_price_no_limit)
    CheckBox ivPriceNoLimit;
    @BindView(R.id.rl_price_nolimit)
    RelativeLayout rlPriceNolimit;
    @BindView(R.id.iv_price_0_15)
    CheckBox ivPrice015;
    @BindView(R.id.rl_price_0_15)
    RelativeLayout rlPrice015;
    @BindView(R.id.iv_price_15_30)
    CheckBox ivPrice1530;
    @BindView(R.id.rl_price_15_30)
    RelativeLayout rlPrice1530;
    @BindView(R.id.iv_price_30_50)
    CheckBox ivPrice3050;
    @BindView(R.id.rl_price_30_50)
    RelativeLayout rlPrice3050;
    @BindView(R.id.iv_price_50_70)
    CheckBox ivPrice5070;
    @BindView(R.id.rl_price_50_70)
    RelativeLayout rlPrice5070;
    @BindView(R.id.iv_price_70_100)
    CheckBox ivPrice70100;
    @BindView(R.id.rl_price_70_100)
    RelativeLayout rlPrice70100;
    @BindView(R.id.iv_price_100)
    CheckBox ivPrice100;
    @BindView(R.id.rl_price_100)
    RelativeLayout rlPrice100;
    @BindView(R.id.et_price_start)
    EditText etPriceStart;
    @BindView(R.id.v_price_line)
    View vPriceLine;
    @BindView(R.id.et_price_end)
    EditText etPriceEnd;
    @BindView(R.id.rl_select_price)
    RelativeLayout rlSelectPrice;
    @BindView(R.id.ll_price_root)
    LinearLayout llPriceRoot;
    @BindView(R.id.btn_drawer_theme_cancel)
    Button btnDrawerThemeCancel;
    @BindView(R.id.btn_drawer_theme_confirm)
    Button btnDrawerThemeConfirm;
    @BindView(R.id.iv_theme_all)
    ImageView ivThemeAll;
    @BindView(R.id.rl_theme_all)
    RelativeLayout rlThemeAll;
    @BindView(R.id.iv_theme_note)
    ImageView ivThemeNote;
    @BindView(R.id.rl_theme_note)
    RelativeLayout rlThemeNote;
    @BindView(R.id.iv_theme_funko)
    ImageView ivThemeFunko;
    @BindView(R.id.rl_theme_funko)
    RelativeLayout rlThemeFunko;
    @BindView(R.id.iv_theme_gsc)
    ImageView ivThemeGsc;
    @BindView(R.id.rl_theme_gsc)
    RelativeLayout rlThemeGsc;
    @BindView(R.id.iv_theme_origin)
    ImageView ivThemeOrigin;
    @BindView(R.id.rl_theme_origin)
    RelativeLayout rlThemeOrigin;
    @BindView(R.id.iv_theme_sword)
    ImageView ivThemeSword;
    @BindView(R.id.rl_theme_sword)
    RelativeLayout rlThemeSword;
    @BindView(R.id.iv_theme_food)
    ImageView ivThemeFood;
    @BindView(R.id.rl_theme_food)
    RelativeLayout rlThemeFood;
    @BindView(R.id.iv_theme_moon)
    ImageView ivThemeMoon;
    @BindView(R.id.rl_theme_moon)
    RelativeLayout rlThemeMoon;
    @BindView(R.id.iv_theme_quanzhi)
    ImageView ivThemeQuanzhi;
    @BindView(R.id.rl_theme_quanzhi)
    RelativeLayout rlThemeQuanzhi;
    @BindView(R.id.iv_theme_gress)
    ImageView ivThemeGress;
    @BindView(R.id.rl_theme_gress)
    RelativeLayout rlThemeGress;
    @BindView(R.id.ll_theme_root)
    LinearLayout llThemeRoot;
    @BindView(R.id.btn_drawer_type_cancel)
    Button btnDrawerTypeCancel;
    @BindView(R.id.btn_drawer_type_confirm)
    Button btnDrawerTypeConfirm;
    @BindView(R.id.expandableListView)
    ExpandableListView expandableListView;
    @BindView(R.id.ll_type_root)
    LinearLayout llTypeRoot;
    @BindView(R.id.dl_left)
    DrawerLayout dlLeft;
    private List<String> father;
    private List<List<String>> child;
    /**
     * 请求网络
     */
    private String[] urls = new String[]{
            Constants.CLOSE_STORE,
            Constants.GAME_STORE,
            Constants.COMIC_STORE,
            Constants.COSPLAY_STORE,
            Constants.GUFENG_STORE,
            Constants.STICK_STORE,
            Constants.WENJU_STORE,
            Constants.FOOD_STORE,
            Constants.SHOUSHI_STORE,
    };
    //记录点击的次数
    private int click_count;
    private ExpandableListViewAdapter adapter;
//    private LocalBroadcastManager manager;
    //价格的CheckBox实例集合
    private Map<CheckBox,String>  priceCheckBoxes;
    private List<CheckBox> priceList;
    private CheckBox priceChecked;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_list);
        ButterKnife.bind(this);
        //初始化广播
//        manager = LocalBroadcastManager.getInstance(this);

        //获取从频道传过来的点击位置
        int position = getIntent().getIntExtra("position", 0);
        //请求网络
        getDataFormNet(urls[position]);
        initData();
        initView();

    }

    private void initData() {
        priceCheckBoxes = new HashMap<>();
        priceCheckBoxes.put(ivPriceNoLimit,"NoLimit");
        priceCheckBoxes.put(ivPrice015,"0-15");
        priceCheckBoxes.put(ivPrice1530,"15-30");
        priceCheckBoxes.put(ivPrice3050,"30-50");
        priceCheckBoxes.put(ivPrice5070,"50-70");
        priceCheckBoxes.put(ivPrice70100,"70-100");
        priceCheckBoxes.put(ivPrice100,"100");

        priceList=new ArrayList<>();
        priceList.add(ivPriceNoLimit);
        priceList.add(ivPrice015);
        priceList.add(ivPrice1530);
        priceList.add(ivPrice3050);
        priceList.add(ivPrice5070);
        priceList.add(ivPrice70100);
        priceList.add(ivPrice100);

    }

    private void initView() {
        //设置综合排序高亮显示
        tvGoodsListSort.setTextColor(Color.parseColor("#ed4141"));

        //价格设置默认
        tvGoodsListPrice.setTextColor(Color.parseColor("#333538"));

        //筛选设置默认
        tvGoodsListSelect.setTextColor(Color.parseColor("#333538"));


        showSelectorLayout();//默认显示筛选页面
    }

    //筛选页面
    private void showSelectorLayout() {
        llPriceRoot.setVisibility(View.GONE);
        llThemeRoot.setVisibility(View.GONE);
        llTypeRoot.setVisibility(View.GONE);
    }

    //价格页面
    private void showPriceLayout() {
        llSelectRoot.setVisibility(View.GONE);
        llThemeRoot.setVisibility(View.GONE);
        llTypeRoot.setVisibility(View.GONE);


    }

    //主题页面
    private void showThemeLayout() {
        llSelectRoot.setVisibility(View.GONE);
        llPriceRoot.setVisibility(View.GONE);
        llTypeRoot.setVisibility(View.GONE);
    }

    //类别页面
    private void showTypeLayout() {
        llSelectRoot.setVisibility(View.GONE);
        llPriceRoot.setVisibility(View.GONE);
        llThemeRoot.setVisibility(View.GONE);

        //初始化ExpandableListView
        initExpandableListView();
    }

    private void initExpandableListView() {
        father = new ArrayList();
        child = new ArrayList();
        //添加数据
        addInfo("全部", new String[]{});
        addInfo("上衣", new String[]{"古风", "和风", "lolita", "日常"});
        addInfo("下装", new String[]{"日常", "泳衣", "汉风", "lolita", "创意T恤"});
        addInfo("外套", new String[]{"汉风", "古风", "lolita", "胖次", "南瓜裤", "日常"});

        adapter = new ExpandableListViewAdapter(this, father, child);

        expandableListView.setAdapter(adapter);

        //设置孩子的点击事件
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                //把位置传入适配器中
                adapter.isChildSelectable(groupPosition, childPosition);
                adapter.notifyDataSetChanged();
                return true;
            }
        });
        // 这里是控制如果列表没有孩子菜单不展开的效果
        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                if (child.get(groupPosition).isEmpty()) {// isEmpty没有
                    return true;//TRUE表示非伸展状态
                } else {
                    return false;
                }

            }
        });
    }

    private void addInfo(String group, String[] strings) {
        father.add(group);
        List<String> datas = new ArrayList();
        for (int i = 0; i < strings.length; i++) {
            datas.add(strings[i]);
        }
        child.add(datas);
    }

    public void getDataFormNet(String url) {
        OkHttpUtils.get()
                .url(url)
                .id(100)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("tag", "联网请求失败==" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("tag", "联网请求成功==");
                        processData(response);
                    }
                });

    }

    private void processData(String response) {
        GoodListBean goodListBean = JSON.parseObject(response, GoodListBean.class);
//        Log.e("tag","tagBean=="+tagBean.getResult().get(0).getName());
        List<GoodListBean.ResultBean.PageDataBean> page_data = goodListBean.getResult().getPage_data();
        if (page_data != null && page_data.size() > 0) {
            setTagAdapter(page_data);
        }
    }

    private void setTagAdapter(List<GoodListBean.ResultBean.PageDataBean> page_data) {
        GoodListAdapter adapter = new GoodListAdapter(this, page_data);
        recyclerview.setAdapter(adapter);

        recyclerview.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerview.addItemDecoration(new SpaceItemDecoration(10));

        //封装到bean对象中，实现跳转到商品详情页面
        adapter.setOnItemClickListener(new GoodListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(GoodListBean.ResultBean.PageDataBean data) {
                GoodsBean goodsBean = new GoodsBean();
                goodsBean.setProduct_id(data.getProduct_id());
                goodsBean.setName(data.getName());
                goodsBean.setCover_price(data.getCover_price());
                goodsBean.setFigure(data.getFigure());

                Intent intent = new Intent(GoodsListActivity.this, GoodsInfoActivity.class);
                intent.putExtra(HomeAdapter.GOODSBEAN, goodsBean);
                startActivity(intent);
            }
        });
    }

    @OnClick({R.id.rl_price_nolimit, R.id.rl_price_0_15, R.id.rl_price_15_30, R.id.rl_price_30_50, R.id.rl_price_50_70, R.id.rl_price_70_100, R.id.rl_price_100,R.id.ib_goods_list_back, R.id.tv_goods_list_search, R.id.ib_goods_list_home, R.id.tv_goods_list_sort, R.id.tv_goods_list_price, R.id.tv_goods_list_select, R.id.ib_drawer_layout_back, R.id.ib_drawer_layout_confirm, R.id.rl_select_price, R.id.rl_select_recommend_theme, R.id.rl_select_type, R.id.btn_drawer_layout_cancel, R.id.btn_drawer_layout_confirm, R.id.btn_drawer_theme_cancel, R.id.btn_drawer_theme_confirm, R.id.btn_drawer_type_cancel, R.id.btn_drawer_type_confirm})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ib_goods_list_back:
                finish();
                break;
            case R.id.tv_goods_list_search:
                Toast.makeText(this, "搜索", Toast.LENGTH_SHORT).show();
                Intent intentsearch = new Intent(this, SearchActivity.class);
                startActivity(intentsearch);
                break;
            case R.id.ib_goods_list_home:
                //两种做法
//                Toast.makeText(this, "主页面", Toast.LENGTH_SHORT).show();
//                Intent intent=new Intent(Constants.GOTOHOME);
//                manager.sendBroadcast(intent);
                finish();
                break;
            case R.id.tv_goods_list_sort:
                click_count = 0;
                ivGoodsListArrow.setBackgroundResource(R.drawable.new_price_sort_normal);

                //设置综合排序高亮显示
                tvGoodsListSort.setTextColor(Color.parseColor("#ed4141"));

                //价格设置默认
                tvGoodsListPrice.setTextColor(Color.parseColor("#333538"));

                //筛选设置默认
                tvGoodsListSelect.setTextColor(Color.parseColor("#333538"));

                break;
            case R.id.tv_goods_list_price:
                //设置价格高亮
                tvGoodsListPrice.setTextColor(Color.parseColor("#ed4141"));
                //综合设置默认
                tvGoodsListSort.setTextColor(Color.parseColor("#333538"));
                //筛选设置默认
                tvGoodsListSelect.setTextColor(Color.parseColor("#333538"));


                click_count++;

                if (click_count % 2 == 1) {

                    // 箭头向下红
                    ivGoodsListArrow.setBackgroundResource(R.drawable.new_price_sort_desc);
                } else {
                    // 箭头向上红
                    ivGoodsListArrow.setBackgroundResource(R.drawable.new_price_sort_asc);
                }
//
                break;
            case R.id.tv_goods_list_select:
//                Toast.makeText(this, "筛选", Toast.LENGTH_SHORT).show();
                //置零
                click_count = 0;
                ivGoodsListArrow.setBackgroundResource(R.drawable.new_price_sort_normal);

                //筛选设置高亮
                tvGoodsListSelect.setTextColor(Color.parseColor("#ed4141"));
                //综合设置默认
                tvGoodsListSort.setTextColor(Color.parseColor("#333538"));
                //价格设置默认
                tvGoodsListPrice.setTextColor(Color.parseColor("#333538"));

                //打开DrawLayout侧滑菜单
                dlLeft.openDrawer(Gravity.RIGHT);

                break;

            case R.id.ib_drawer_layout_back:
                //关闭DrawLayout
                dlLeft.closeDrawer(Gravity.RIGHT);
                break;
            case R.id.ib_drawer_layout_confirm:
                String price=tvDrawerPrice.getText().toString();
                String theme=tvDrawerRecommend.getText().toString();
                String type=tvDrawerType.getText().toString();
                Toast.makeText(this, "筛选了price="+price+"，主题="+theme+" ,类别="+type, Toast.LENGTH_SHORT).show();
                if(!price.equals("NoLimit")||!theme.equals("全部")||!type.equals("全部")) {
                    getDataFormNet(urls[2]);
                }else{
                    getDataFormNet(urls[0]);
                }
                dlLeft.closeDrawer(Gravity.RIGHT);
                break;
            case R.id.rl_select_price://显示-价格
                llPriceRoot.setVisibility(View.VISIBLE);
                showPriceLayout();
                break;
            case R.id.rl_select_recommend_theme://主题
                llThemeRoot.setVisibility(View.VISIBLE);
                showThemeLayout();
                break;
            case R.id.rl_select_type://类别
                llTypeRoot.setVisibility(View.VISIBLE);
                showTypeLayout();
                break;
            case R.id.btn_drawer_layout_cancel:
                llSelectRoot.setVisibility(View.VISIBLE);
                showSelectorLayout();
                break;
            case R.id.btn_drawer_layout_confirm:
                Toast.makeText(this, "价格-确定", Toast.LENGTH_SHORT).show();
                for(int i = 0; i < priceList.size(); i++) {
                  if(priceList.get(i).isChecked()) {
                      tvDrawerPrice.setText(priceCheckBoxes.get(priceList.get(i)));
                  }
                }
                llSelectRoot.setVisibility(View.VISIBLE);
                showSelectorLayout();
                break;
            case R.id.btn_drawer_theme_cancel:
                llSelectRoot.setVisibility(View.VISIBLE);
                showSelectorLayout();
                break;
            case R.id.btn_drawer_theme_confirm:
                Toast.makeText(this, "主题-确定", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_drawer_type_cancel:
                llSelectRoot.setVisibility(View.VISIBLE);
                showSelectorLayout();
                break;
            case R.id.btn_drawer_type_confirm:
                Toast.makeText(this, "类别-确定", Toast.LENGTH_SHORT).show();
                break;



            case R.id.rl_price_nolimit:
                setALlFalse();
                ivPriceNoLimit.setChecked(true);

                break;
            case R.id.rl_price_0_15:
                setALlFalse();
                ivPrice015.setChecked(true);
                break;
            case R.id.rl_price_15_30:
                setALlFalse();
                ivPrice1530.setChecked(true);
                break;
            case R.id.rl_price_30_50:
                setALlFalse();
                ivPrice3050.setChecked(true);
                break;
            case R.id.rl_price_50_70:
                setALlFalse();
                ivPrice5070.setChecked(true);
                break;
            case R.id.rl_price_70_100:
                setALlFalse();
                ivPrice70100.setChecked(true);
                break;
            case R.id.rl_price_100:
                setALlFalse();
                ivPrice100.setChecked(true);
                break;
        }
    }

    private void setALlFalse() {
        for(int i = 0; i < priceList.size(); i++) {
          priceList.get(i).setChecked(false);

        }
    }


}
