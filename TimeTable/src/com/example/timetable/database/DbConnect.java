package com.example.timetable.database;

import java.util.ArrayList;

import com.example.timetable.R;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbConnect extends SQLiteOpenHelper
{
	public final static String DB_NAME = "timetable";
	public final static int VERSION = 1;
	public static String[] tables = new String[]{"MondayCourse","TuesdayCourse","WednesdayCourse","ThursdayCourse","FridayCourse","SaturdayCourse"};
	public static String[] dayAll = new String[]{"Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"};
	public static String[] timeAll = new String[]{"8:00-9:00","9:00-10:00","10:00-11:00","11:00-12:00","12:00-13:00","13:00-14:00","14:00-15:00","15:00-16:00",
						"16:00-17:00","17:00-18:00","18:00-19:00","19:00-20:00"};
	
	public DbConnect(Context context) {
		super(context, DB_NAME, null, VERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		StringBuffer executeStr = new StringBuffer();
		
		//create six tables to save courses
		for(int i = 0; i < tables.length; i++)
		{
			executeStr = this.createTable(tables[i]);
			//System.out.println(tables[i]);
			db.execSQL(executeStr.toString());
			executeStr = null;
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}
	
	public StringBuffer createTable(String tableName)
	{
		StringBuffer executeStr = new StringBuffer();
		
		executeStr.append("CREATE TABLE if not exists ");
		executeStr.append(tableName);
		executeStr.append("(id integer primary key autoincrement,course text);");
		
		return executeStr;
	}
}
