package com.xuchengpu.shoppingmall.shoppingcart.utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.SparseArray;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xuchengpu.shoppingmall.home.bean.GoodsBean;
import com.xuchengpu.shoppingmall.utils.CacheUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 许成谱 on 2017/2/27 16:16.
 * qq:1550540124
 * for:
 */

public class CartStorage {

    private static final String JSON_CART ="json_cart" ;
    private static CartStorage instance;
    private final Context mContext;
    private SparseArray<GoodsBean> sparseArray;//相对于hasmap效率更高 但不能直接与json转化 故需用list中转

    private CartStorage(Context context){
        this.mContext=context;
        sparseArray=new SparseArray<>();
        //从本地获取数据 初始化sparseArray
        listToSparseArray();
    }
    public static CartStorage getInstance(Context context){
        if(instance==null) {
            //防止开启分线程时重复创建了实例
            synchronized (CartStorage.class){
                if(instance==null) {
                    instance=new CartStorage(context);
                }
            }
        }
        return instance;
    }
    //list转化为sparry 提高效率
    private void listToSparseArray() {
        List<GoodsBean> list=getAllData();
        for(int i = 0; i < list.size(); i++) {
            sparseArray.put(Integer.parseInt(list.get(i).getProduct_id()),list.get(i));
        }
    }
    //此方法仅为方便拓展
    public List<GoodsBean> getAllData() {
        return getLocalData();
    }

    private List<GoodsBean> getLocalData() {
        List<GoodsBean> list=new ArrayList<>();
        String json=CacheUtils.getString(mContext,JSON_CART);
        //将json数组转化为list集合
        if(!TextUtils.isEmpty(json)) {
            list=new Gson().fromJson(json,new TypeToken<List<GoodsBean>>(){}.getType());
        }

        return list;
    }


    public void addData(GoodsBean goodsBean){
        //1.数据添加到sparseArray
        GoodsBean tempGoodsBean= sparseArray.get(Integer.parseInt(goodsBean.getProduct_id()));
        //集合中原来有该商品
        if(tempGoodsBean!=null) {
            tempGoodsBean.setNumber(tempGoodsBean.getNumber()+goodsBean.getNumber());
           //集合中原来没有改商品
        }else{
            tempGoodsBean=goodsBean;
        }
        //添加到集合中
        sparseArray.put(Integer.parseInt(tempGoodsBean.getProduct_id()),tempGoodsBean);

        //存储到本地
        saveInLocal();

    }
    //存储到本地
    private void saveInLocal() {
        List<GoodsBean> goodsBeans=sparseArrayToList();
        // 将数组转化为String类型对象方便存储
        String goodsBeansJson=new Gson().toJson(goodsBeans);
        CacheUtils.setString(mContext,JSON_CART,goodsBeansJson);
    }
    //sparseArray转化为list集合
    private List<GoodsBean> sparseArrayToList() {
        List<GoodsBean> goodsBeens=new ArrayList<>();
        for(int i = 0; i < sparseArray.size(); i++) {
          goodsBeens.add(sparseArray.valueAt(i));
        }
        return goodsBeens;
    }
    //删除
    public void deleteData(GoodsBean goodsBean){
        //从集合中移除
        sparseArray.delete(Integer.parseInt(goodsBean.getProduct_id()));
        //从本地删除 、存储到本地
        saveInLocal();
    }
    //更新
    public  void updata(GoodsBean goodsBean){
        //添加到集合
        sparseArray.put(Integer.parseInt(goodsBean.getProduct_id()),goodsBean);
        //从本地删除 、存储到本地
        saveInLocal();
    }
}
