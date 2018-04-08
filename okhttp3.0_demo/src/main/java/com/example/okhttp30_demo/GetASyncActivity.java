package com.example.okhttp30_demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 爱生活，爱代码
 * 创建于：2018/4/3 15:26
 * 作 者：T
 * 微信：704003376
 */

public class GetASyncActivity extends AppCompatActivity {
    private TextView mTv_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_async);
        mTv_data = (TextView) findViewById(R.id.tv_asydata);
    }

    public void async_get(View view) {
        Request request = new Request.Builder().url(Constants.SYNC_URL).build();
        MyApp.mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("TAG", e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String str = response.body().string();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mTv_data.setText(str);
                    }
                });
            }
        });

    }
}