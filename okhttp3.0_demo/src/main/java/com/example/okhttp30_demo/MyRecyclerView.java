package com.example.okhttp30_demo;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

/**
 * 爱生活，爱代码
 * 创建于：2018/4/3 10:27
 * 作 者：T
 * 微信：704003376
 */

public class MyRecyclerView extends RecyclerView {
    public MyRecyclerView(Context context) {
        this(context, null);
    }

    public MyRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        addOnChildAttachStateChangeListener(new OnChildAttachStateChangeListener() {
            @Override
            public void onChildViewAttachedToWindow(View view) {
                view.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mListener != null) {
                            mListener.onItemClick(v);

                        }

                    }
                });


                view.setOnLongClickListener(new OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        if (mListener != null) {
                            mListener.onItemLongClick(v);
                        }
                        return false;
                    }
                });
            }


            @Override
            public void onChildViewDetachedFromWindow(View view) {
                view.setOnClickListener(null);
            }
        });

    }


    public interface onItemClickListenner {
        public void onItemClick(View view);

        public void onItemLongClick(View view);
    }

    private onItemClickListenner mListener;

    public void setOnItemClickListenner(onItemClickListenner listenner) {
        this.mListener = listenner;
    }


}
