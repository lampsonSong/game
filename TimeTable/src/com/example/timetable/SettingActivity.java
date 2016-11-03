package com.example.timetable;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.example.timetable.database.DbConnect;
import com.example.timetable.database.otherDb;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.hardware.Camera.OnZoomChangeListener;
import android.text.Layout;
import android.view.Menu;
import android.view.View.OnClickListener;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ZoomControls;

public class SettingActivity extends Activity {

	
	TextView syn_tv,set_text_size_tv,set_bg_tv,logoff_tv,changeBG_tv;
	
	ZoomControls zoom;
	ProgressBar go_pb;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        //full screen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,  
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        setContentView(R.layout.activity_setting);
        
        //find the textviews
        syn_tv = (TextView)findViewById(R.id.synchronize_button);
        set_text_size_tv = (TextView)findViewById(R.id.set_text_size_button);
        set_bg_tv = (TextView)findViewById(R.id.set_bg_button);
        logoff_tv = (TextView)findViewById(R.id.logoff_button);
        zoom = (ZoomControls)findViewById(R.id.zoomControls1);
        go_pb = (ProgressBar)findViewById(R.id.goto_pb);
        //changeBG_tv = (TextView)findViewById(R.id.changeBG_button);
        
        
        zoom.setIsZoomInEnabled(true);
        zoom.setIsZoomOutEnabled(true);
        zoom.hide();
        zoom.setOnZoomInClickListener(InListener);
        zoom.setOnZoomOutClickListener(OutListener);
        
        //set onclicklistener
        syn_tv.setOnClickListener(syn_tv_listen);
        set_text_size_tv.setOnClickListener(text_listen);
        set_bg_tv.setOnClickListener(bg_listen);
        logoff_tv.setOnClickListener(logoff_listen);
        //changeBG_tv.setOnClickListener(changeBG_listen);
        
        //set textsize
        this.setSize();
    }
    
    public void setSize()
    {
    	syn_tv.setTextSize(MainActivity.TEXTSIZE);
    	set_text_size_tv.setTextSize(MainActivity.TEXTSIZE);
    	set_bg_tv.setTextSize(MainActivity.TEXTSIZE);
    	logoff_tv.setTextSize(MainActivity.TEXTSIZE);
    	//changeBG_tv.setTextSize(MainActivity.TEXTSIZE);
    }

    /*
    //背景更改监听
    OnClickListener changeBG_listen = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent();    
            intent.setType("image/*");    
            intent.setAction(Intent.ACTION_GET_CONTENT);   
            startActivityForResult(intent, 1);  

		}
    	
    };
    */
    
    //同步按钮监听
    OnClickListener syn_tv_listen = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent it = new Intent(SettingActivity.this,LoginActivity.class);
			startActivity(it);
			int version = Integer.valueOf(android.os.Build.VERSION.SDK);      
			if(version  >= 5) {      
			     overridePendingTransition(R.anim.zoomin, R.anim.zoomout);  //此为自定义的动画效果，下面两个为系统的动画效果   
			   //overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);     
			     //overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);   
			}
			SettingActivity.this.finish();
		}
	};
	
	//字体更改监听
	OnClickListener text_listen = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
			if(zoom.isShown())
			{
				zoom.setVisibility(View.GONE);
				//save to otherDB
				otherDb od = new otherDb(SettingActivity.this);
				SQLiteDatabase db = od.getWritableDatabase();
				
				//MainActivity ma = new MainActivity();
				//System.out.println(ma.readSize());
				if(MainActivity.HAS_VALUE)
				{
					ContentValues values = new ContentValues();
					values.put("size", MainActivity.TEXTSIZE);
					db.update(otherDb.SIZE_DB, values, "id=?", new String[]{"0"});
				}
				else
				{
					ContentValues values = new ContentValues();
					values.put("id", 0);
					values.put("size", MainActivity.TEXTSIZE);
					db.insert(otherDb.SIZE_DB, null, values);
				}
				
			}
			else
			{
				Toast.makeText(SettingActivity.this, R.string.press_again, Toast.LENGTH_SHORT).show();
				zoom.setVisibility(View.VISIBLE);
			}
			
		}
	};
    
	//it is go to view content page
	OnClickListener bg_listen = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			new syn_goto_click().execute("a","b");
		}
	};
    
	//the zoom in event
    OnClickListener InListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(MainActivity.TEXTSIZE < 60)
			{
				MainActivity.TEXTSIZE += 5;
				setSize();
			}
			else
				Toast.makeText(SettingActivity.this, R.string.noMoreOut, Toast.LENGTH_SHORT).show();
		}
	};
    //the zoom out event
	OnClickListener OutListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(MainActivity.TEXTSIZE > 20)
			{
				MainActivity.TEXTSIZE -= 5;
				setSize();
			}
			else
				Toast.makeText(SettingActivity.this, R.string.noMoreIn, Toast.LENGTH_SHORT).show();
		}
	};
	
	OnClickListener logoff_listen = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			SettingActivity.this.finish();
		}
	};
	
	//make goto button syn to UI
	class syn_goto_click extends AsyncTask<String, Integer, String>
	{
		boolean whetherSyn;
		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			whetherSyn = MainActivity.goto_where;
			return null;
		}
		//do befor back ground
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			go_pb.setVisibility(View.VISIBLE);
			super.onPreExecute();
		}

		
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			//检查是否需要同步课表数据
			if(whetherSyn)
			{
				Intent it = new Intent(SettingActivity.this,ScrollCourseActivity.class);
				startActivity(it);
				int version = Integer.valueOf(android.os.Build.VERSION.SDK);      
				if(version  >= 5) {      
				     overridePendingTransition(R.anim.zoomin, R.anim.zoomout);  //此为自定义的动画效果，下面两个为系统的动画效果   
				   //overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);     
				     //overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);   
				}
				SettingActivity.this.finish();
			}
			else
			{
				Toast.makeText(SettingActivity.this, R.string.syn_warning, Toast.LENGTH_SHORT).show();
				go_pb.setVisibility(View.GONE);
			}
			
			super.onPostExecute(result);
		}
		
		
		
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		//根据返回的值更改背景图片
		if(resultCode == RESULT_OK)
		{
			ContentResolver resolver = getContentResolver();
			Uri uri = data.getData();
			
			Bitmap bg=null;
			try {
				bg = MediaStore.Images.Media.getBitmap(resolver, uri);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//寻找setting的tablelayout
			TableLayout lo = (TableLayout)findViewById(R.id.setting_layout);
			
			//lo.setBackgroundDrawable(new BitmapDrawable(bg));
			//lo.setBackgroundResource();
			if(bg != null)
				lo.setBackgroundDrawable(new BitmapDrawable(bg));
		}
	}
    
	
	
}
