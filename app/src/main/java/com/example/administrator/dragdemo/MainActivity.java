package com.example.administrator.dragdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button movebtn;//可拖动按钮
    private boolean clickormove = true;//点击或拖动，点击为true，拖动为false
    private int downX, downY;//按下时的X，Y坐标
    private boolean hasMeasured = false;//ViewTree是否已被测量过，是为true，否为false
    private View content;//界面的ViewTree
    private int screenWidth,screenHeight;//ViewTree的宽和高

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        content = getWindow().findViewById(Window.ID_ANDROID_CONTENT);//获取界面的ViewTree根节点View

        DisplayMetrics dm = getResources().getDisplayMetrics();//获取显示屏属性
        screenWidth = dm.widthPixels;
        screenHeight = dm.heightPixels;

        ViewTreeObserver vto = content.getViewTreeObserver();
        vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {

            @Override
            public boolean onPreDraw() {

// TODO Auto-generated method stub
                if(!hasMeasured)
                {

                    screenHeight = content.getMeasuredHeight();//获取ViewTree的高度
                    hasMeasured = true;//设置为true，使其不再被测量。

                }
                return true;//如果返回false，界面将为空。

            }

        });
        movebtn = (Button) findViewById(R.id.movebtn);
        movebtn.setOnTouchListener(new View.OnTouchListener() {//设置按钮被触摸的时间

            int lastX, lastY; // 记录移动的最后的位置

            @Override
            public boolean onTouch(View v, MotionEvent event) {

// TODO Auto-generated method stub
                int ea = event.getAction();//获取事件类型
                switch (ea) {
                    case MotionEvent.ACTION_DOWN: // 按下事件

                        lastX = (int) event.getRawX();
                        lastY = (int) event.getRawY();
                        downX = lastX;
                        downY = lastY;
                        break;

                    case MotionEvent.ACTION_MOVE: // 拖动事件

// 移动中动态设置位置
                        int dx = (int) event.getRawX() - lastX;//位移量X
                        int dy = (int) event.getRawY() - lastY;//位移量Y
                        int left = v.getLeft() + dx;
                        int top = v.getTop() + dy;
                        int right = v.getRight() + dx;
                        int bottom = v.getBottom() + dy;

//++限定按钮被拖动的范围
                        if (left < 0) {

                            left = 0;
                            right = left + v.getWidth();

                        }
                        if (right > screenWidth) {

                            right = screenWidth;
                            left = right - v.getWidth();

                        }
                        if (top < 0) {

                            top = 0;
                            bottom = top + v.getHeight();

                        }
                        if (bottom > screenHeight) {

                            bottom = screenHeight;
                            top = bottom - v.getHeight();

                        }

//--限定按钮被拖动的范围

                        v.layout(left, top, right, bottom);//按钮重画


// 记录当前的位置
                        lastX = (int) event.getRawX();
                        lastY = (int) event.getRawY();
                        break;

                    case MotionEvent.ACTION_UP: // 弹起事件

//判断是单击事件或是拖动事件，位移量大于5则断定为拖动事件

                        if (Math.abs((int) (event.getRawX() - downX)) > 5
                                || Math.abs((int) (event.getRawY() - downY)) > 5)

                            clickormove = false;

                        else

                            clickormove = true;

                        break;

                }
                return false;

            }

        });
        movebtn.setOnClickListener(new View.OnClickListener() {//设置按钮被点击的监听器

            @Override
            public void onClick(View arg0) {
            // TODO Auto-generated method stub
                if (clickormove)

                    Toast.makeText(MainActivity.this, "single click",
                            Toast.LENGTH_SHORT).show();

            }

        });

    }
}
