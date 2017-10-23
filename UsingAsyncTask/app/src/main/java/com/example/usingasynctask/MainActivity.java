package com.example.usingasynctask;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class MainActivity extends Activity {
    TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text = (TextView) findViewById(R.id.textView);
        findViewById(R.id.btnRead).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readURL("http://www.jikexueyuan.com");
            }
        });
    }
    public void readURL(String url){
        //第一个是启动后台任务时输入的参数类型 第2个是后台任务执行中返回的进度值的类型 第3个是后台执行任务完成后返回的结果类型
        new AsyncTask<String, Float, String>() {
            @Override
            //这里面进行的是后台耗时操作的处理程序
            protected String doInBackground(String... params) {//这里只能执行不涉及UI的操作，对于UI控件的操作，或者是UI主线程的互动，只能在下面的回调方法中执行。
                try {
                    URL url = new URL(params[0]);
                    URLConnection connection = url.openConnection();//获取当前的网络连接
                    long total = connection.getContentLength();//获取内容长度(在xml界面设计中，为了是界面完全展示出来，使用了ScrollView把Textview包含在里面)
                    InputStream iStream = connection.getInputStream();//层层包裹获取读取网络权限
                    InputStreamReader isr = new InputStreamReader(iStream);
                    BufferedReader br = new BufferedReader(isr);
                    String line;
                    StringBuilder builder = new StringBuilder();
                    while ((line = br.readLine())!=null){
                        builder.append(line);
                        publishProgress((float)builder.toString().length()/total);//进度的百分比(这里是手动调用publishProgress方法更新任务进度，所以下面的onProgressUpdate方法重写)
                    }
                    br.close();//获取读取网络了，然后把流都关闭
                    iStream.close();
                    return builder.toString();//返回给下面的回调方法：onPostExecute方法
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
            //调用回调方法

            @Override
            protected void onPreExecute() {
                Toast.makeText(MainActivity.this,"开始读取",Toast.LENGTH_SHORT).show();//点击按钮的时候的弹出来的提醒语句，开始读取。
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String result) {//接收到了上面返回的结果字符串(result是执行完异步任务后得到的返回值，onPostExecute方法在doInbackground方法完成后去调用)
                 text.setText(result);
                super.onPostExecute(result);//这个地方的result返回值就是上面的doInbackground中return返回的返回值
            }

            @Override
            protected void onProgressUpdate(Float... values) {
                System.err.println(values[0]);
                super.onProgressUpdate(values);
            }

            @Override
            protected void onCancelled(String s) {
                super.onCancelled(s);
            }

            @Override
            protected void onCancelled() {
                super.onCancelled();
            }
        }.execute(url);//这里的execute方法就是开始的意思
    }
}
