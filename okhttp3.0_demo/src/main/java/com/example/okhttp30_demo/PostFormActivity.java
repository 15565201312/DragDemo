package com.example.okhttp30_demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 爱生活，爱代码
 * 创建于：2018/4/4 11:40
 * 作 者：T
 * 微信：704003376
 */

public class PostFormActivity extends AppCompatActivity {
    private TextView mtv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_postform);
        mtv = (TextView) findViewById(R.id.post_tv_sydata);
    }


    public void post_form(View view) {
        FormBody body = new FormBody.Builder().add("key", "a332c6b34264527ac142764eaed9364d").build();

        Request request = new Request.Builder().url(Constants.POST_SYNC_URL).post(body).build();
        MyApp.mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String str = response.body().string();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mtv.setText(str);
                    }
                });
            }
        });


    }
}
