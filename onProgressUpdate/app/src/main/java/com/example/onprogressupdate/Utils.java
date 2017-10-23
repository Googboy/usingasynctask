
package com.example.onprogressupdate;


import android.os.AsyncTask;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * Created by 潘硕 on 2017/10/23.
 */

class Utils extends AsyncTask<Void,Integer,String>{
    private TextView textView;
    private ProgressBar progressBar;
    public Utils(TextView textView, ProgressBar progressBar) {
        this.textView = textView;
        this.progressBar = progressBar;
    }

    @Override
    protected String doInBackground(Void... params) {
        for (int i = 0;i<=100;i++){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            publishProgress(i);
        }
        return "下载完成";
    }

    @Override
    protected void onPostExecute(String s) {
        textView.setText(s);
        super.onPostExecute(s);
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        progressBar.setProgress(values[0]);
        textView.setText("下载进度:"+values[0]+"%");
        super.onProgressUpdate(values);
    }
}

