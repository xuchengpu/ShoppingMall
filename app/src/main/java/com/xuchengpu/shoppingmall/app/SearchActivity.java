package com.xuchengpu.shoppingmall.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.anye.greendao.gen.UserDao;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;
import com.xuchengpu.shoppingmall.R;
import com.xuchengpu.shoppingmall.User;
import com.xuchengpu.shoppingmall.commonadapter.SearchListAdapter;
import com.xuchengpu.shoppingmall.home.activity.GoodsListActivity;
import com.xuchengpu.shoppingmall.utils.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SearchActivity extends AppCompatActivity {

    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.tv_message_search)
    TextView tvMessageSearch;
    @BindView(R.id.tv_search)
    TextView tvSearch;
    @BindView(R.id.tv_history)
    TextView tvHistory;
    @BindView(R.id.lv_search)
    ListView lvSearch;
    @BindView(R.id.rv_clear)
    RelativeLayout rvClear;
    @BindView(R.id.iv_voice)
    ImageView ivVoice;
    private UserDao mUserDao;
    private List<User> users;
    private SearchListAdapter adapter;
    private User mUser;
    // 用HashMap存储听写结果
    private HashMap<String, String> mIatResults = new LinkedHashMap<String, String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        SpeechUtility.createUtility(this, SpeechConstant.APPID + "=5838f0d9");


        mUserDao = MyApplication.getInstances().getDaoSession().getUserDao();

        initData();

    }

    private void initData() {
        users = mUserDao.loadAll();
        if (users != null && users.size() > 0) {
            tvHistory.setVisibility(View.VISIBLE);
            rvClear.setVisibility(View.VISIBLE);
        } else {
            tvHistory.setVisibility(View.GONE);
            rvClear.setVisibility(View.GONE);
        }
        setAdapter();

    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    private void setAdapter() {
        adapter = new SearchListAdapter(this, users);
        lvSearch.setAdapter(adapter);
    }

    @OnClick({R.id.iv_voice,R.id.tv_message_search, R.id.rv_clear})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_message_search:
                int id = users.size() + 1;
                String text = etSearch.getText().toString();
                if (!TextUtils.isEmpty(text)) {
                    mUser = new User((long) id, text);
                    mUserDao.insert(mUser);//添加一个

                    etSearch.setText("");

                    Intent intent = new Intent(this, GoodsListActivity.class);
                    intent.putExtra("position", 0);
                    startActivity(intent);
                } else {
                    Toast.makeText(SearchActivity.this, "搜索内容不能为空", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.rv_clear:
                for (int i = 0; i < users.size(); i++) {
                    mUserDao.deleteByKey(users.get(i).getId());
                }
                initData();
                break;
            case R.id.iv_voice:
                showDialogVoice();

                break;
        }
    }
    private void showDialogVoice() {
        //1.创建RecognizerDialog对象
        RecognizerDialog mDialog = new RecognizerDialog(this, new MyInitListener());
//2.设置accent、 language等参数
        mDialog.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
        mDialog.setParameter(SpeechConstant.ACCENT, "mandarin");
//若要将UI控件用于语义理解，必须添加以下参数设置，设置之后onResult回调返回将是语义理解
//结果
// mDialog.setParameter("asr_sch", "1");
// mDialog.setParameter("nlp_version", "2.0");
//3.设置回调接口
        mDialog.setListener(new MyRecognizerDialogListener());
//4.显示dialog，接收语音输入
        mDialog.show();
    }

    class MyRecognizerDialogListener implements RecognizerDialogListener {

        @Override
        public void onResult(RecognizerResult recognizerResult, boolean b) {
            String result = recognizerResult.getResultString();
            System.out.println(result);
            String text = JsonParser.parseIatResult(recognizerResult.getResultString());

            String sn = null;
            // 读取json结果中的sn字段
            try {
                JSONObject resultJson = new JSONObject(recognizerResult.getResultString());
                sn = resultJson.optString("sn");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            mIatResults.put(sn, text);

            StringBuffer resultBuffer = new StringBuffer();
            for (String key : mIatResults.keySet()) {
                resultBuffer.append(mIatResults.get(key));
            }
            String reulst = resultBuffer.toString();
            reulst = reulst.replace("。","");
            etSearch.setText(reulst);
            etSearch.setSelection(etSearch.length());

        }

        @Override
        public void onError(SpeechError speechError) {

            Toast.makeText(SearchActivity.this, "出错了哦", Toast.LENGTH_SHORT).show();
        }
    }

    class MyInitListener implements InitListener {

        @Override
        public void onInit(int i) {


        }
    }

}
