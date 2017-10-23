package com.example.thread;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //这是第一种方法:主线程中放入操作会造成卡顿。所以这里另开一个线程处理耗时操作，解决卡顿问题
                /*try {
                    Thread.sleep(1000);
                    System.out.println(">>>>>>>>>>>>>>>AsyncTask");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }*/
                //这是第2中方法：另开线程处理耗时操作。这样就不会造成主线程卡顿或者卡死的现象
                new Thread(){
                    @Override
                    public void run() {
                        try {
                            while (true){           //设置一个循环，每隔一秒输出一句话便于识别
                                sleep(1000);
                                System.out.println(">>>>>>>>>>>>>AsyncTask");
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
            }
        });
    }
}
