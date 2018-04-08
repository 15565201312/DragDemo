package com.example.okhttp30_demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;

import okhttp3.Request;
import okhttp3.Response;

/**
 * 爱生活，爱代码
 * 创建于：2018/4/3 15:26
 * 作 者：T
 * 微信：704003376
 */

public class GetSyncActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_sync;
    private TextView tv_sydata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_sync);
        initView();
    }

    private void initView() {
        btn_sync = (Button) findViewById(R.id.btn_sync);
        btn_sync.setOnClickListener(this);
        tv_sydata = (TextView) findViewById(R.id.tv_sydata);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_sync:
                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        Request request = new Request.Builder().url(Constants.SYNC_URL).build();
                        Response response = null;
                        try {
                            response = MyApp.mClient.newCall(request).execute();
                            if (response.code() == 200) {
                                final String str = response.body().string();
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        tv_sydata.setText(str);
                                    }
                                });

                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        } finally {
                            if (response != null)
                                response.body().close();
                        }
                    }
                }.start();

                break;
        }
    }
}
