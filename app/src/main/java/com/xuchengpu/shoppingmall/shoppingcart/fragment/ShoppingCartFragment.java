package com.xuchengpu.shoppingmall.shoppingcart.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xuchengpu.shoppingmall.R;
import com.xuchengpu.shoppingmall.base.BaseFragment;
import com.xuchengpu.shoppingmall.home.bean.GoodsBean;
import com.xuchengpu.shoppingmall.shoppingcart.adapter.ShoppingCartAdapter;
import com.xuchengpu.shoppingmall.shoppingcart.utils.CartStorage;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 许成谱 on 2017/2/22 15:10.
 * qq:1550540124
 * for:
 */

public class ShoppingCartFragment extends BaseFragment {

    @BindView(R.id.tv_shopcart_edit)
    TextView tvShopcartEdit;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.checkbox_all)
    CheckBox checkboxAll;
    @BindView(R.id.tv_shopcart_total)
    TextView tvShopcartTotal;
    @BindView(R.id.btn_check_out)
    Button btnCheckOut;
    @BindView(R.id.ll_check_all)
    LinearLayout llCheckAll;
    @BindView(R.id.checkbox_delete_all)
    CheckBox checkboxDeleteAll;
    @BindView(R.id.btn_delete)
    Button btnDelete;
    @BindView(R.id.btn_collection)
    Button btnCollection;
    @BindView(R.id.ll_delete)
    LinearLayout llDelete;
    @BindView(R.id.iv_empty)
    ImageView ivEmpty;
    @BindView(R.id.tv_empty_cart_tobuy)
    TextView tvEmptyCartTobuy;
    @BindView(R.id.ll_empty_shopcart)
    LinearLayout llEmptyShopcart;
    private ShoppingCartAdapter adapter;

    /*
        初始化布局
        * */
    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.fragment_shopping_cart, null);
        ButterKnife.bind(this, view);


        /*
        * 处理右上角编辑与完成的textview按钮代码
        * */

        //设置编辑状态
        tvShopcartEdit.setTag(ACTION_EDIT);
        tvShopcartEdit.setText("编辑");

        //显示去结算布局
        llCheckAll.setVisibility(View.VISIBLE);
        tvShopcartEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //1.得到状态
                int action = (int) v.getTag();
                //2.根据不同状态做不同的处理
                if (action == ACTION_EDIT) {
                    //切换完成状态
                    showDelete();
                } else {
                    //切换成编辑状态
                    hideDelete();
                }
            }
        });
        return view;
    }
    //隐藏底部删除购物车商品栏
    private void hideDelete() {
        //1.设置编辑
        tvShopcartEdit.setTag(ACTION_EDIT);
        //2.隐藏删除控件
        llDelete.setVisibility(View.GONE);
        //3.显示结算控件
        llCheckAll.setVisibility(View.VISIBLE);
        //4.设置文本为-编辑
        tvShopcartEdit.setText("编辑");
        //5.把所有的数据设置勾选择状态
        if(adapter != null){
            adapter.checkAll_none(true);
//            adapter.checkAll();
            adapter.showTotalPrice();
        }
    }
    //显示底部删除购物车商品栏
    private void showDelete() {
        //1.设置完成
        tvShopcartEdit.setTag(ACTION_COMPLETE);
        //2.显示删除控件
        llDelete.setVisibility(View.VISIBLE);
        //3.隐藏结算控件
        llCheckAll.setVisibility(View.GONE);
        //4.设置文本为-完成
        tvShopcartEdit.setText("完成");
        //5.把所有的数据设置非选择状态
        if(adapter != null){
            adapter.checkAll_none(false);
//            adapter.checkAll();
            adapter.showTotalPrice();
        }

    }
    /*
    * 布局加载完后加载数据
    * */

    @Override
    public void initData() {
        super.initData();
        showData();

    }

    //保证每次切换回来时购物车栏都是显示最新的状态

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            showData();
        }
    }

    //得到数据，并设置适配器，将数据加载到recycleview中显示
    private void showData() {
        List<GoodsBean> goodsBeens = CartStorage.getInstance(mContext).getAllData();
        //有数据，影藏空界面
        if(goodsBeens!=null&goodsBeens.size()>0) {
            llEmptyShopcart.setVisibility(View.GONE);
            /*
            * 设置适配器
            * */
            adapter = new ShoppingCartAdapter(mContext,goodsBeens,tvShopcartTotal,checkboxAll,checkboxDeleteAll);
            recyclerview.setAdapter(adapter);
            //设置布局管理器
            recyclerview.setLayoutManager(new LinearLayoutManager(mContext,LinearLayoutManager.VERTICAL,false));
            //没有数据，显示空界面
        }else{
            llEmptyShopcart.setVisibility(View.VISIBLE);

        }
    }
    //编辑状态
    private static final int ACTION_EDIT = 1;
    //完成状态
    private static final int ACTION_COMPLETE = 2;



    @OnClick({R.id.tv_shopcart_edit, R.id.recyclerview, R.id.checkbox_all, R.id.tv_shopcart_total, R.id.btn_check_out, R.id.ll_check_all, R.id.checkbox_delete_all, R.id.btn_delete, R.id.btn_collection, R.id.ll_delete, R.id.iv_empty, R.id.tv_empty_cart_tobuy, R.id.ll_empty_shopcart})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_shopcart_edit:
                break;
            case R.id.recyclerview:
                break;
            //全选按钮
            case R.id.checkbox_all:
                //得到全选的CheckBox按钮状态
                boolean ischecked=checkboxAll.isChecked();
                //调用adapter中的方法具体操作每个item数据
                adapter.checkAll_none(ischecked);
                //刷新价格
                adapter.showTotalPrice();
                break;
            case R.id.tv_shopcart_total:
                break;
            case R.id.btn_check_out:
                break;
            case R.id.ll_check_all:
                break;
            //删除状态下的全选按钮
            case R.id.checkbox_delete_all:
               ischecked=checkboxDeleteAll.isChecked();
                adapter.checkAll_none(ischecked);
                adapter.showTotalPrice();
                break;
            case R.id.btn_delete:
                //调用adapter中的方法具体操作
                adapter.deleteData();
                //检查状态 设置全选按钮的状态
                adapter.checkAll();
                //检查是否为空数据，来决定显示的界面
                showEempty();
                break;
            case R.id.btn_collection:
                break;
            case R.id.ll_delete:

                break;
            case R.id.iv_empty:
                break;
            case R.id.tv_empty_cart_tobuy:
                break;
            case R.id.ll_empty_shopcart:
                break;
        }
    }
    //检查是否为空数据，来决定显示的界面
    private void showEempty() {
        if(adapter.getItemCount() == 0){
            llEmptyShopcart.setVisibility(View.VISIBLE);
        }
    }
}
