package com.example.timetable;

import com.example.timetable.database.DbConnect;

import android.os.Bundle;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnLongClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class DetailActivity extends Activity {

	TextView detail_course = null;
	TextView day_view = null,time_view = null,take_space=null;
	EditText edit_course = null;
	Button save,cancel,back;
	Intent it;
	int viewId;
	int timeId;
	String course;
	public final static int defaultValue = 0;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        //full screen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,  
        WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        setContentView(R.layout.activity_detail);
        
        //find a view and a edit text
        day_view = (TextView)findViewById(R.id.detail_days);
        time_view = (TextView)findViewById(R.id.detail_time);
        detail_course = (TextView) findViewById(R.id.detail_course);
        edit_course = (EditText) findViewById(R.id.detail_edit_course);
        take_space = (TextView)findViewById(R.id.take_space);
        
        //find buttons, save , cancel , back
        save = (Button)findViewById(R.id.saveButton);
        cancel = (Button)findViewById(R.id.cancelButton);
        back = (Button)findViewById(R.id.backButton);
        
        //get viewId and timeId
        it = getIntent();
        this.viewId = it.getIntExtra("viewId", defaultValue);
        this.timeId = it.getIntExtra("timeId", defaultValue);
        this.course = it.getStringExtra("course");
        
        //String course = "test";
        detail_course.setText(course);
        edit_course.setText(course);
        
        //set page information
        day_view.setText(DbConnect.dayAll[this.viewId]);
        time_view.setText(DbConnect.timeAll[this.timeId]);
        
        //set listener
        detail_course.setOnLongClickListener(changeToEdit);
        save.setOnClickListener(saveListener);
        cancel.setOnClickListener(cancelListener);
        back.setOnClickListener(backListener);
        
        //get width of screen
        DisplayMetrics metric = new DisplayMetrics();
    	getWindowManager().getDefaultDisplay().getMetrics(metric);
    	int width = metric.widthPixels*1/5;
        
    	detail_course.setMaxWidth(width);
    	edit_course.setMaxWidth(width);
    	
        this.setSize();
    }
    
    public void setSize()
    {
    	day_view.setTextSize(MainActivity.TEXTSIZE+10);
        time_view.setTextSize(MainActivity.TEXTSIZE);
        detail_course.setTextSize(MainActivity.TEXTSIZE);
        edit_course.setTextSize(MainActivity.TEXTSIZE);
    }

    public OnLongClickListener changeToEdit = new OnLongClickListener() {
		
		@Override
		public boolean onLongClick(View v) {
			// TODO Auto-generated method stub
			detail_course.setVisibility(View.GONE);
			edit_course.setVisibility(View.VISIBLE);
			take_space.setVisibility(View.VISIBLE);
			save.setVisibility(View.VISIBLE);
			cancel.setVisibility(View.VISIBLE);
			back.setVisibility(View.GONE);
			return false;
		}
	};
	
	public OnClickListener saveListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			String temp = null;
			temp = edit_course.getText().toString();
			detail_course.setText(temp);
			edit_course.setText(temp);
			
			edit_course.setVisibility(View.GONE);
			detail_course.setVisibility(View.VISIBLE);
			save.setVisibility(View.GONE);
			cancel.setVisibility(View.GONE);
			take_space.setVisibility(View.GONE);
			back.setVisibility(View.VISIBLE);
			
			DetailActivity.this.updateDB(DbConnect.tables[viewId], String.valueOf(timeId+1), temp);
			
		}
	};
	
	public OnClickListener cancelListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			String temp = null;
			temp = detail_course.getText().toString();
			edit_course.setText(temp);
			
			edit_course.setVisibility(View.GONE);
			detail_course.setVisibility(View.VISIBLE);
			save.setVisibility(View.GONE);
			cancel.setVisibility(View.GONE);
			take_space.setVisibility(View.GONE);
			back.setVisibility(View.VISIBLE);
		}
	};
	
	public OnClickListener backListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent back = new Intent(DetailActivity.this,ScrollCourseActivity.class);
			setResult(viewId, back);
			DetailActivity.this.finish();
			
			//set fade out animation
			int version = Integer.valueOf(android.os.Build.VERSION.SDK);      
			if(version  >= 5) {      
			     overridePendingTransition(R.anim.zoomin, R.anim.zoomout);  //此为自定义的动画效果，下面两个为系统的动画效果   
			   //overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);     
			     //overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);   
			}
		}
	};

    
    //update detail information
    public void updateDB(String tableName,String id,String course)
    {
    	DbConnect conn = new DbConnect(this);
    	SQLiteDatabase db = conn.getWritableDatabase();
    	
    	ContentValues values = new ContentValues();
    	if(course.length() == 0)
    	{
    		course = LoginActivity.Empty;
    	}
    	values.put("course", course);
    	
    	db.update(tableName, values, "id=?", new String[]{id});
    }
    //end of updateDB
    

    
}
