package org.webdevelopment.eventbustest;

import android.os.AsyncTask;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by makn on 28-08-2016.
 */
public class MyTask extends AsyncTask<Void,Integer,String> {
    @Override
    protected String doInBackground(Void... params) {
        for (int i = 0; i<100; i++) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //publishProgress(i);
            EventBus.getDefault().post(new ProgressEvent(i));
            if (i%20==0)
                System.out.println("progress:"+i);
            if (isCancelled())
                return "";
        }
        return "Succes";
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if (!isCancelled())
            EventBus.getDefault().postSticky(new MessageEvent("WE have finished!"));
    }
}
