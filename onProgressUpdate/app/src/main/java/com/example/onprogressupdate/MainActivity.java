package com.example.onprogressupdate;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends Activity {
    private ProgressBar progressBar;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }
    public  void initView(){
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        textView = (TextView) findViewById(R.id.textView);
    }
    public void download(View view){
       // Utils utils = new Utils(textView,progressBar);这里可以另外创建一个类，也可以直接在下面的方法中直接写（两种方式都可以）
        //utils.execute();

        new AsyncTask<Void,Integer,String>(){         //这个是第2种方法，第一种方法是另外创建了一个Utils类，但我感觉第一种类太过于复杂，不如这种方法简单

            @Override
            protected String doInBackground(Void... params) {
                for (int i = 0;i<=100;i++){
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    publishProgress(i);
                    System.out.println("执行过程中被执行的方法");//为了便于看出执行步骤而设置的输出语句
                }
                return "下载完成";

            }

            @Override
            protected void onPostExecute(String s) {
                textView.setText(s);
                super.onPostExecute(s);
                System.out.println("进度完成后调用的方法");
            }

            @Override
            protected void onProgressUpdate(Integer... values) {
                progressBar.setProgress(values[0]);
                textView.setText("下载进度:"+values[0]+"%");
                super.onProgressUpdate(values);
                System.out.println("更新进度时调用的方法");
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                System.out.println("初始化数据的时候调用的方法");
            }
        }.execute();
    }
}
