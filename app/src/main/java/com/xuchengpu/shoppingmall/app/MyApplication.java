package com.xuchengpu.shoppingmall.app;

import android.app.Application;

import com.uuzuche.lib_zxing.activity.ZXingLibrary;

/**
 * Created by 许成谱 on 2017/3/7 9:28.
 * qq:1550540124
 * for:
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ZXingLibrary.initDisplayOpinion(this);
    }
}
