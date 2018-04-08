package com.example.okhttp30_demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private String[] strArr = new String[10];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inintView();

    }


    private void inintView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        for (int i = 0; i < strArr.length; i++) {
            strArr[i] = "条目" + i;
        }

        //设置数据纵向展示
        LinearLayoutManager manager = new LinearLayoutManager(MainActivity.this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(manager);

        //设置分割线
        mRecyclerView.addItemDecoration(new DividerItemDecoration
                (MainActivity.this, DividerItemDecoration.VERTICAL));
        MyAdapter adapter = new MyAdapter(MainActivity.this, strArr);
        //设置适配器
        mRecyclerView.setAdapter(adapter);


        adapter.setOnItemClickListenner(new MyAdapter.onItemClickListenner() {
            @Override
            public void onItemClick(View view, int position) {
                  Intent intent =new Intent();
                switch (position) {
                    case 0:
                        intent.setClass(MainActivity.this, GetSyncActivity.class);
                        break;

                    case 1:
                        intent.setClass(MainActivity.this, GetASyncActivity.class);
                        break;

                    case 2:
                        intent.setClass(MainActivity.this, DownloadActivity.class);
                        break;

                    case 3:
                        intent.setClass(MainActivity.this, PostFormActivity.class);
                        break;


                    case 4:
                        intent.setClass(MainActivity.this, UploadActivity.class);
                        break;


                }
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });


    }


}
