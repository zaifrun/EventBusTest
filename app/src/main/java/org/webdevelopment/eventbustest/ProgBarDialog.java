package org.webdevelopment.eventbustest;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.ProgressBar;

public class ProgBarDialog extends Dialog {
	
    Context context;
    static ProgressBar bar;
    int max;
    String title = null;

	public ProgBarDialog(Context context, int maximum) {
	
		super(context);
		this.context = context;
		this.max = maximum;

	}

	public ProgBarDialog(Context context)
	{
		super(context);
		this.context = context;
	}

	public ProgBarDialog(Context context,String title)
	{
		super(context);
		this.context = context;
		this.title = title;
	}
	
	public ProgBarDialog(Context context, int maximum,String title) {
		
		super(context);
		this.context = context;
		this.max = maximum;
		this.title = title;

	}
	
	
	
	@Override
	public void onBackPressed() {
		
		//super.onBackPressed();
	}
	
	public void addTobar(int i)
	{
		bar.incrementProgressBy(i);
	}
	
	public void setBar(int i)
	{
		bar.setProgress(i);
	}
	
	public void close()
	{
		dismiss();
	}
	


	
	public void setMax(int max)
	{
		bar.setMax(max);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.progbar);
		if (title==null)
			setTitle("Loading, please wait...");
		else
			setTitle(title);
		
		bar = (ProgressBar) findViewById(R.id.progbar);
		if (bar!=null)
			bar.setMax(max);
		else
			System.out.println("progbar is zero");
		//writeUI(tournament);
	}


}
