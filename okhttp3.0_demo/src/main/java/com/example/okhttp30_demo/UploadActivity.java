package com.example.okhttp30_demo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 爱生活，爱代码
 * 创建于：2018/4/8 14:36
 * 作 者：T
 * 微信：704003376
 */

public class UploadActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btn_open_gallery;
    private Button btn_upload_file;
    private ImageView iv_pre;
    private EditText met_upload_url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upload_file);
        initView();
    }

    private void initView() {
        btn_open_gallery = (Button) findViewById(R.id.btn_open_gallery);
        btn_upload_file = (Button) findViewById(R.id.btn_upload_file);
        iv_pre = (ImageView) findViewById(R.id.iv_pre);
        met_upload_url = (EditText) findViewById(R.id.et_input_upload_url);

        btn_open_gallery.setOnClickListener(this);
        btn_upload_file.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_open_gallery:
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 0);
                break;
            case R.id.btn_upload_file:
                uploadFile();
                break;
        }
    }

    //文件上传
    private void uploadFile() {
        String etStr = met_upload_url.getText().toString().trim();
        if (uri == null) {
            Toast.makeText(UploadActivity.this, "图片路径不正确", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(etStr)) {
            Toast.makeText(UploadActivity.this, "上传的服务器路径不正确", Toast.LENGTH_SHORT).show();
            return;
        }
        InputStream is = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            is = getContentResolver().openInputStream(uri);
            int len = 0;
            byte[] buffer = new byte[1024];
            while ((len = is.read(buffer)) != -1) {
                bos.write(buffer, 0, len);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bos != null)
                    bos.close();
                if (is != null)
                    is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        MediaType mediaType = MediaType.parse("image/*");
        RequestBody requestBody = RequestBody.create(mediaType, bos.toByteArray());
        MultipartBody.Builder multipartbuilder = new MultipartBody.Builder();
        multipartbuilder.setType(MultipartBody.FORM); // multipart/form-data 多种数据格式表单
        multipartbuilder.addFormDataPart("file", "tes.jpg", requestBody);

        Request.Builder builder = new Request.Builder();
        builder.url(etStr);
        builder.post(requestBody);


        MyApp.mClient.newCall(builder.build()).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("TAG", e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String str = response.body().string();
                    Log.i("TAG", "上传成功" + str);
                } else {
                    Log.i("TAG", "上传失败");
                }
            }
        });


    }


    private Uri uri;
    private Bitmap bitmap;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK)
            return;

        if (requestCode != 0)
            return;

        uri = data.getData();
        InputStream is = null;
        try {
            is = getContentResolver().openInputStream(uri);
            bitmap = BitmapFactory.decodeStream(is);
            iv_pre.setImageBitmap(bitmap);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null)
                    is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }
}
