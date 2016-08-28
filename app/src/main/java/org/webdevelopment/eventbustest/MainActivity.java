package org.webdevelopment.eventbustest;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainActivity extends AppCompatActivity {

    boolean eventStarted = false;
    int progress = 0;
    ProgBarDialog progDialog;
    Context context;
    static MyTask task; //so this reference is NOT destroyed during rotation changes.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        System.out.println("oncreate");
        this.context = this;
        if (savedInstanceState!=null)
        {
            eventStarted = savedInstanceState.getBoolean("eventStarted",false);
            progress = savedInstanceState.getInt("progress",0);
            System.out.println("bundle not null:, progress = "+progress+", " +
                    "evenstarted: "+eventStarted);
        }

        if (eventStarted) //continue to show progressbar
        {
            progDialog = new ProgBarDialog(context,100,"Loading please wait...");
            progDialog.show();
            progDialog.setBar(progress);
        }

        Button cancelButton = (Button) findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (eventStarted)
                {
                    task.cancel(true);
                    if (progDialog!=null && progDialog.isShowing())
                        progDialog.dismiss();
                    progress=0;
                    eventStarted=false;

                } else
                    Toast.makeText(context,"nothing to cancel!",Toast.LENGTH_SHORT).show();
            }
        });

        Button button = (Button) findViewById(R.id.startButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!eventStarted) //if not already running
                {
                    eventStarted = true; //run it
                    progress = 0;
                    progDialog = new ProgBarDialog(context,100,"Loading please wait...");
                    progDialog.show();
                    task = new MyTask();
                    task.execute();

                }
                else Toast.makeText(context,"already running!",Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Subscribe(sticky = true,threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        eventStarted = false; //not running - we are finished
        if (progDialog!=null)
            progDialog.dismiss();; //done showing progress
        TextView result = (TextView) findViewById(R.id.resultView);
        result.setText(event.getMessage());
        System.out.println("Got messsage");
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onProgressEvent(ProgressEvent event) {
       // Toast.makeText(getActivity(), event.message, Toast.LENGTH_SHORT).show();
        if (progDialog!=null && progDialog.isShowing())
            progDialog.setBar(event.getProgress());
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        System.out.println("onsaveinstancestate");
        outState.putBoolean("eventStarted",eventStarted);
        outState.putInt("progress",progress);
        super.onSaveInstanceState(outState);
    }


    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);

    }

    @Override
    protected void onPause() {
        super.onPause();
        System.out.println("onpause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        System.out.println("onStop");
        if (progDialog!=null && progDialog.isShowing())
            progDialog.dismiss();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onDestroy() {
        System.out.println("On Destroy Called");
        super.onDestroy();
    }
}
