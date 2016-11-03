package com.example.timetable;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.example.timetable.database.DbConnect;
import com.example.timetable.handle.MyViewGroup;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.format.DateFormat;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnLongClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class ScrollCourseActivity extends Activity {

	public MyViewGroup ViewGroup;
	View[] days;
	//the times here is wrong named, it should be time_course
	public static final int[] times = new int[]{R.id.eight_course,R.id.nine_course,R.id.ten_course,R.id.eleven_course,
		R.id.twelve_course,R.id.thirteen_course,R.id.fourteen_course,R.id.fifteen_course,
		R.id.sixteen_course,R.id.seventeen_course,R.id.eighteen_course,R.id.nineteen_course};
	
	public static final int[] times_view = new int[]{R.id.eight,R.id.nine,R.id.ten,R.id.eleven,R.id.twelve,
		R.id.thirteen,R.id.fourteen,R.id.fifteen,R.id.sixteen,R.id.seventeen,R.id.eighteen,R.id.nineteen};
	
	DetailActivity detail;
	TextView[] timeTV;
	TextView[] time_view_TV;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        //full screen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,  
        WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        days = new View[6];
        ViewGroup = new MyViewGroup(this); 
        
        detail = new DetailActivity();
        
        //turn the layout into views,each for one day
        LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        for(int i = 0; i < days.length; i++)
        {
        	days[i] = inflater.inflate(R.layout.view_scroll_course, null);
        	this.initView(i);
        	ViewGroup.addView(days[i]);
        }
        setContentView(ViewGroup);
        this.setFirstView();
    }
    
    
    //set the first view of each time to visit
    public void setFirstView()
    {
    	Date date = new Date();
    	SimpleDateFormat df = new SimpleDateFormat("EEEE");
    	df.format(date);
    	
    	
    	//get the screen width
    	DisplayMetrics metric = new DisplayMetrics();
    	getWindowManager().getDefaultDisplay().getMetrics(metric);
    	int width = metric.widthPixels;
    	//get the day of a week
    	int dayWeek = date.getDay();
    	if(dayWeek > 0)
    	{
	    	//scroll to current day view
	    	ViewGroup.scrollTo(width*(dayWeek-1), 0);
    	}
    	else
    	{
    		ViewGroup.scrollTo(0, 0);
    	}
    }

    
    
    //initialize views
    public void initView(int viewId)
    {
    	TextView tv = (TextView)days[viewId].findViewById(R.id.day_view);
    	tv.setText(DbConnect.dayAll[viewId]);
    	//set the title size
    	tv.setTextSize(MainActivity.TEXTSIZE+10);
    	
    	
    	timeTV = new TextView[ScrollCourseActivity.times.length];
    	time_view_TV = new TextView[ScrollCourseActivity.times_view.length];
    	
    	String course = null;
    	
    	
    	this.get(this, DbConnect.tables[viewId],viewId);
    	
    }
    
  //get data from DB
    public void get(Context context,String tableName,int viewId)
    {
    	DbConnect conn = new DbConnect(context);
    	SQLiteDatabase db = conn.getReadableDatabase();
    	//get course of the request day and time
    	Cursor cursor = db.query(tableName, new String[]{"course"}, null, null, null, null, null);
    	//move text from cursor
    	String course = null;
    	for(int i = 0; cursor.moveToNext(); i++)
    	{
    		timeTV[i] = (TextView)days[viewId].findViewById(ScrollCourseActivity.times[i]);
    		time_view_TV[i] = (TextView)days[viewId].findViewById(ScrollCourseActivity.times_view[i]);
    		
    		course = cursor.getString(cursor.getColumnIndex("course"));
    		timeTV[i].setText(course);
    		//set the text size
    		timeTV[i].setTextSize(MainActivity.TEXTSIZE);
    		time_view_TV[i].setTextSize(MainActivity.TEXTSIZE);
    		//set onclick listener
    		timeTV[i].setOnLongClickListener(new longClick(viewId,i,course));
    	}
    }
    //end of get
    
    class longClick implements OnLongClickListener
    {
    	int viewId;
    	int timeId;
    	String course;
    	public longClick(int viewId, int timeId,String course)
    	{
    		this.viewId = viewId;
    		this.timeId = timeId;
    		this.course = course;
    	}
    	@Override
    	public boolean onLongClick(View v) {
    		// TODO Auto-generated method stub
    		Intent it = new Intent(ScrollCourseActivity.this,DetailActivity.class);
    		it.putExtra("viewId", this.viewId);
    		it.putExtra("timeId", this.timeId);
    		it.putExtra("course", this.course);
    		//startActivity(it);
    		startActivityForResult(it, 0);
    		
    		//set fade out animation
    		int version = Integer.valueOf(android.os.Build.VERSION.SDK);      
    		if(version  >= 5) {      
    		     overridePendingTransition(R.anim.zoomin, R.anim.zoomout);  //此为自定义的动画效果，下面两个为系统的动画效果   
    		   //overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);     
    		     //overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);   
    		}   
    		return false;
    	}
    	
    }

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		this.initView(resultCode);
		super.onActivityResult(requestCode, resultCode, data);
	}
    
	//rewrite back button
    @Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if(keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0)
		{
			Intent it = new Intent(ScrollCourseActivity.this,SettingActivity.class);
			startActivity(it);
			int version = Integer.valueOf(android.os.Build.VERSION.SDK);      
			if(version  >= 5) {      
			     overridePendingTransition(R.anim.zoomin, R.anim.zoomout);  //此为自定义的动画效果，下面两个为系统的动画效果   
			   //overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);     
			     //overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);   
			}
			ScrollCourseActivity.this.finish();
		}
		return super.onKeyDown(keyCode, event);
	}
    
}


