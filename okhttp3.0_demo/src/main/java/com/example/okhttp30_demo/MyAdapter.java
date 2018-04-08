package com.example.okhttp30_demo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * 爱生活，爱代码
 * 创建于：2018/4/3 09:55
 * 作 者：T
 * 微信：704003376
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private Context mContext;
    private String[] mStrArr;



    public MyAdapter(Context context, String[] strArr) {
        this.mContext = context;
        this.mStrArr = strArr;
    }


    // 相当于ListView里面 setTag , getTag  复用ViewHolder
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    //    View itemView = View.inflate(mContext, R.layout.recycler_item, null);
      View itemView=  LayoutInflater.from(mContext).inflate(R.layout.recycler_item,parent,false);
        MyViewHolder viewHolder = new MyViewHolder(itemView);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.mTv.setText(mStrArr[position]);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListenner != null) {

                    mListenner.onItemClick(v, position);

                }
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mListenner != null) {
                    mListenner.onItemLongClick(v, position);
                }
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mStrArr.length;
    }


    static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView mTv;

        public MyViewHolder(View itemView) {
            super(itemView);
            mTv = itemView.findViewById(R.id.item_tv);
        }
    }


    private onItemClickListenner mListenner;


    public interface onItemClickListenner {
        public void onItemClick(View view, int position);

        public void onItemLongClick(View view, int position);
    }


    public void setOnItemClickListenner(onItemClickListenner listenner) {
        this.mListenner = listenner;
    }


}
