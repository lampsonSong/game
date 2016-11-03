package com.example.timetable;

import java.io.IOException;

import com.example.timetable.database.DbConnect;
import com.example.timetable.database.otherDb;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.view.Display;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnTouchListener;

public class MainActivity extends Activity {
	
	Bitmap nowPic = null;
	Bitmap nextPic = null;
	Bitmap bg = null;
	Display display;
	int width ;
	int height ;
	Canvas mCanvas = null;
	Canvas nextCanvas = null;
	
	public static float TEXTSIZE = 20;
	public static boolean HAS_VALUE = false;
	public static boolean goto_where = false;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        //full screen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,  
        WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
 
        setContentView(R.layout.activity_main);
        new initAll().execute("a","b");
     
    }

    class initAll extends AsyncTask<String, Integer, String>
    {
    	
		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			readSize();
			checkSyn();
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			Intent it;
			if(!goto_where)
			{
				it = new Intent(MainActivity.this,SettingActivity.class);
			}
			else
			{
				it = new Intent(MainActivity.this,ScrollCourseActivity.class);
			}
			startActivity(it);
			
			int version = Integer.valueOf(android.os.Build.VERSION.SDK);      
			if(version  >= 5) {      
			     overridePendingTransition(R.anim.zoomin, R.anim.zoomout);  //此为自定义的动画效果，下面两个为系统的动画效果   
			   //overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);     
			     //overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);   
			}
			MainActivity.this.finish();
			super.onPostExecute(result);
		}
    	
    }

    //read text size from DB
	public void readSize()
	{
		otherDb connOther = new otherDb(this);
		SQLiteDatabase db = connOther.getReadableDatabase();
		Cursor cursor = db.query(otherDb.SIZE_DB, new String[]{"size"}, null, null, null,null,null);
		
		if(cursor.moveToNext())
		{
			MainActivity.TEXTSIZE = cursor.getFloat(cursor.getColumnIndex("size"));
			MainActivity.HAS_VALUE = true;
		}
		else
		{
			MainActivity.TEXTSIZE = 20;
			
		}
	}
    
	//check whether it has syn
	public void checkSyn()
	{
		DbConnect conn = new DbConnect(this);
		SQLiteDatabase db = conn.getReadableDatabase();
		
		Cursor cursor = db.query(DbConnect.tables[0], new String[]{"course"}, null, null, null, null, null);
		
		if(cursor.moveToNext())
		{
			
			goto_where = true;
		}
		
	}
    
}
