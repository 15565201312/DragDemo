package com.example.okhttp30_demo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 爱生活，爱代码
 * 创建于：2018/4/3 15:27
 * 作 者：T
 * 微信：704003376
 */
public class DownloadActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int START = 0;
    private static final int DOWNLOADING = 1;
    private static final int END = 2;
    private Button btn_download_start;
    private Button btn_download_cancel;
    private ProgressBar progressbar;
    private ImageView iv_download;
    private EditText metInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_download);
        initView();
    }

    private void initView() {
        btn_download_start = (Button) findViewById(R.id.btn_download_start);
        btn_download_cancel = (Button) findViewById(R.id.btn_download_cancel);
        progressbar = (ProgressBar) findViewById(R.id.progressbar);
        iv_download = (ImageView) findViewById(R.id.iv_download);
        metInput = (EditText) findViewById(R.id.et_input);
        btn_download_start.setOnClickListener(this);
        btn_download_cancel.setOnClickListener(this);
    }

    private boolean isPause;
    private int mCurrentProcess;
    private MyHandler mHandler = new MyHandler(DownloadActivity.this);

    private class MyHandler extends Handler {

        private WeakReference<DownloadActivity> mWeakRefernce;

        public MyHandler(DownloadActivity downloadActivity) {
            mWeakRefernce = new WeakReference<DownloadActivity>(downloadActivity);
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case START:
                    int max = msg.arg1;
                    mWeakRefernce.get().progressbar.setMax(max);
                    break;


                case DOWNLOADING:
                    mCurrentProcess += msg.arg1;
                    mWeakRefernce.get().progressbar.setProgress(mCurrentProcess);
                    break;


                case END:
                    Bitmap bitmap = (Bitmap) msg.obj;
                    mWeakRefernce.get().iv_download.setImageBitmap(bitmap);
                    break;

            }

            super.handleMessage(msg);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //开始下载
            case R.id.btn_download_start:
                start_download();
                break;

            //暂停下载
            case R.id.btn_download_cancel:
                isPause = true;
                break;
        }
    }

    private int mSize = 0;
    private ByteArrayOutputStream mBos;

    //开始下载
    private void start_download() {
        isPause = false;
        mBos = new ByteArrayOutputStream();

        Request.Builder builder = new Request.Builder();
        builder.url(Constants.FILE_URL);

        mSize = mBos.size();
        if (mSize > 0) {
            builder.addHeader("Range", "bytes=" + mSize + "-");
            mCurrentProcess += mSize;
        }

        MyApp.mClient.newCall(builder.build()).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("TAG", e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream is = null;
                if (response.isSuccessful()) {
                    is = response.body().byteStream();


                    if (mSize == 0) {
                        int max = (int) response.body().contentLength();
                        mHandler.obtainMessage(START, max, 0).sendToTarget();
                    }

 
                    int len = 0;
                    byte[] buffer = new byte[1024];
                    while ((len = is.read(buffer)) != -1) {
                        if (isPause)
                            break;
                        mBos.write(buffer, 0, len);
                        mHandler.obtainMessage(DOWNLOADING, len, 0).sendToTarget();
                    }


                    Bitmap bitmap = BitmapFactory.decodeByteArray(mBos.toByteArray(), 0, mBos.toByteArray().length);
                    mHandler.obtainMessage(END, bitmap).sendToTarget();

                    mBos.close();
                    is.close();

                }


            }
        });


    }
}