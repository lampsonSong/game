package com.example.timetable.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/*
 * use this otherDb class to create a database that
 * saves the text size
 */
public class otherDb extends SQLiteOpenHelper
{
	public static String SIZE_DB = "textsize";
	public static final int VERSION = 1;
	
	
	public otherDb(Context context) {
		super(context,SIZE_DB,null,VERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		StringBuffer sizeBuffer = new StringBuffer();
		sizeBuffer.append("CREATE TABLE if not exists ");
		sizeBuffer.append(SIZE_DB);
		sizeBuffer.append("(id integer primary key autoincrement,size float);");
		db.execSQL(sizeBuffer.toString());
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}
	
	
}
