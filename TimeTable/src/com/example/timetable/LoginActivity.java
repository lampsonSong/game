package com.example.timetable;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.DefaultClientConnection;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.example.timetable.database.DbConnect;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity {

	String result=null;
	TextView name_text,pass_text;
	EditText userName,password;
	
	Button login,cancel;
	

	public final static String Empty = "--";
	
	Thread syn_thread;
	ProgressBar pb;
	
	boolean loginOK = false;
	
	String uName;
	String uPass;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        //去掉标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //去掉上方显示栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,  
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        setContentView(R.layout.activity_login);
        
        //tr = (TextView)findViewById(R.id.days);
        //tr.setOnLongClickListener(longclick);
        
        userName = (EditText)findViewById(R.id.user_name);
        password = (EditText)findViewById(R.id.password);
        //view
        name_text = (TextView)findViewById(R.id.name_view);
        pass_text = (TextView)findViewById(R.id.pass_view);
        
        
        login = (Button)findViewById(R.id.loginYes);
        cancel = (Button)findViewById(R.id.loginCancel);
        pb = (ProgressBar)findViewById(R.id.progressBar);
        
        login.setOnClickListener(login_longClick);
        cancel.setOnClickListener(cancelListen);
        
        this.setSize();
    }
    
    public void setSize()
    {
    	userName.setTextSize(MainActivity.TEXTSIZE);
    	password.setTextSize(MainActivity.TEXTSIZE);
    	name_text.setTextSize(MainActivity.TEXTSIZE);
    	pass_text.setTextSize(MainActivity.TEXTSIZE);
    }
    
    OnClickListener cancelListen = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent it = new Intent(LoginActivity.this,SettingActivity.class);
			startActivity(it);
			int version = Integer.valueOf(android.os.Build.VERSION.SDK);      
			if(version  >= 5) {      
			     overridePendingTransition(R.anim.zoomin, R.anim.zoomout);  //此为自定义的动画效果，下面两个为系统的动画效果   
			   //overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);     
			     //overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);   
			}
			LoginActivity.this.finish();
		}
	};
    
    OnClickListener login_longClick = new OnClickListener() {
		
		@SuppressLint("ShowToast")
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			uName = userName.getText().toString();
			uPass = password.getText().toString();
			
			
			
			if(uName.length() != 0 && uPass.length() != 0)
			{
				new syn_class().execute(uName,uPass);
			}
			else
			{
				Toast.makeText(LoginActivity.this, R.string.empty_warning, Toast.LENGTH_LONG).show();
			}
			
		}
	};
    
	class syn_class extends AsyncTask<String, Integer, String>
	{

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			
			try {
				synchronize();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return null;
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			login.setVisibility(View.INVISIBLE);
			cancel.setVisibility(View.INVISIBLE);
			pb.setVisibility(View.VISIBLE);
			super.onPreExecute();
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			
			if(loginOK == true)
			{
				Intent it = new Intent(LoginActivity.this,ScrollCourseActivity.class);
				startActivity(it);
				int version = Integer.valueOf(android.os.Build.VERSION.SDK);      
				if(version  >= 5) {      
				     overridePendingTransition(R.anim.zoomin, R.anim.zoomout);  //此为自定义的动画效果，下面两个为系统的动画效果   
				   //overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);     
				     //overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);   
				}   
				
				LoginActivity.this.finish();
			}
			else
			{
				Toast.makeText(LoginActivity.this, R.string.wrong_warning, Toast.LENGTH_LONG).show();
        		userName.setText("");
        		password.setText("");
        		login.setVisibility(View.VISIBLE);
    			cancel.setVisibility(View.VISIBLE);
    			pb.setVisibility(View.INVISIBLE);
			}
			super.onPostExecute(result);
		}

		
		
		
	}
	
	//课表网址：http://mis.uic.edu.hk/mis/student/es/timetable.do
    //http://mis.uic.edu.hk/base/login.sec
	//synchronize timetable
    public void synchronize() throws Exception
    {
    	
    	String add = "http://mis.uic.edu.hk/mis/usr/login.sec";
		String schedule = "http://mis.uic.edu.hk/mis/student/tts/timetable.do";
    	
    	HttpClient httpClient;
		HttpPost post;
		
		httpClient = new DefaultHttpClient();
		post = new HttpPost(add);
		
		//课表get
		HttpGet getCourse = new HttpGet(schedule);
		
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("j_username", uName));
		params.add(new BasicNameValuePair("j_password", uPass));
		
		//添加请求参数到对象
		post.setEntity(new UrlEncodedFormEntity(params,HTTP.UTF_8));
		
		httpClient.execute(post);
		
		HttpResponse httpResponse = httpClient.execute(getCourse);
			
		System.out.println(httpResponse.getStatusLine().getStatusCode());
		
		if(httpResponse.getStatusLine().getStatusCode() == 200)
		{
			// 获取服务器响应字符串  
			String result = EntityUtils  
                .toString(httpResponse.getEntity());
			Document doc = Jsoup.parse(result);
			
			
			
            if(!doc.title().equals("UIC MIS Login Page"))
            {
            	loginOK = true;
            	
					//System.out.println(DbConnect.tables.get(2));
					Element timetable = doc.getElementById("mytimetable");
					//get all rows in the timetable
					Elements times = timetable.select("tr");
					
					
					//allocate classes row by row,start from 1, for 0 is Mon,Tue..
					for(int i = 1;i<times.size()-2;i++)
					{
						Elements days = times.get(i).select("td");
						//allocate each row column by column,start from 0
						for(int j = 0; j<days.size();j++)
						{
							//get course detail
							Element courseText = days.get(j);
							String nbsp = courseText.html().substring(0, 5);
							
							
							//if not empty
							if(!nbsp.contains("&nbsp"))
							{
								//insert into table
								this.insert(DbConnect.tables[j], i, courseText.text());
							}
							else
							{
								this.insert(DbConnect.tables[j], i, Empty);
							}
						}
					}
				MainActivity.goto_where = true;
            }
            //end of if the page is right
			
		}
		//end of if the status is 200
    }
    
  //insert an item into exactly tables
    public void insert(String tableName,int id,String course)
    {
    	DbConnect conn = new DbConnect(this);
    	SQLiteDatabase db = conn.getWritableDatabase();
    	
    	ContentValues values = new ContentValues();
    	values.put("id", id);
    	values.put("course", course);
    	//get writable dB
    	
    	db.insert(tableName, null, values);
    }

    
  //rewrite back function
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if(keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0)
		{
			Intent it = new Intent(LoginActivity.this,SettingActivity.class);
			startActivity(it);
			int version = Integer.valueOf(android.os.Build.VERSION.SDK);      
			if(version  >= 5) {      
			     overridePendingTransition(R.anim.zoomin, R.anim.zoomout);  //此为自定义的动画效果，下面两个为系统的动画效果   
			   //overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);     
			     //overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);   
			}
			LoginActivity.this.finish();
		}
		return super.onKeyDown(keyCode, event);
	}

    
    
  
}


