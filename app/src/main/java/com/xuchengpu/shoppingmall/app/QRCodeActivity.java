package com.xuchengpu.shoppingmall.app;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.xuchengpu.shoppingmall.R;
import com.xuchengpu.shoppingmall.home.adapter.HomeAdapter;
import com.xuchengpu.shoppingmall.home.bean.GoodsBean;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import butterknife.BindView;
import butterknife.ButterKnife;

public class QRCodeActivity extends AppCompatActivity {

    @BindView(R.id.qrcode_image)
    ImageView qrcodeImage;
    private GoodsBean goodsBean;
    private String textContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);
        ButterKnife.bind(this);
        goodsBean = (GoodsBean) getIntent().getSerializableExtra(HomeAdapter.GOODSBEAN);
        creatQRCode();
    }

    private void creatQRCode() {

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                final Bitmap logo = returnBitMap(textContent);
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        Bitmap mBitmap = CodeUtils.createImage("nihao", 400, 400, null);
//
//                        qrcodeImage.setImageBitmap(mBitmap);
//                    }
//                });
//            }
//        });
        String textContent =goodsBean.getFigure()+","+goodsBean.getName()+","+goodsBean.getCover_price()+","+goodsBean.getProduct_id();

        Bitmap mBitmap = CodeUtils.createImage(textContent, 400, 400, null);
        qrcodeImage.setImageBitmap(mBitmap);


    }

    public Bitmap returnBitMap(String url) {
        URL myFileUrl = null;
        Bitmap bitmap = null;
        InputStream is = null;
        try {
            myFileUrl = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            HttpURLConnection conn = (HttpURLConnection) myFileUrl.openConnection();
            conn.setDoInput(true);
            conn.connect();
            is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null)
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
        return bitmap;
    }
}
