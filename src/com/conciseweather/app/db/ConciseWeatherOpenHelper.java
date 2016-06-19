package com.conciseweather.app.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class ConciseWeatherOpenHelper extends SQLiteOpenHelper {
	/**
	 * Province表建表语句
	 */
	public static final String CREATE_PROVINCE = "create table Province("// 表名(字段设计列表)
			+ "id integer primary key autoincrement,"// 字段名 字段数据类型 字段约束
			+ "province_name text,"// 省名，各字段用,分隔
			+ "province_code text)";// 省级代号
	/**
	 * City表建表语句
	 */
	public static final String CREATE_CITY = "create table City("//
			+ "id integer primary key autoincrement,"//
			+ "city_name text,"//
			+ "city_code text,"//
			+ "province_id integer)";//
	public static final String CREATE_COUNTY = "create table County("//
			+ "id integer primary key autoincrement,"//
			+ "county_name text,"//
			+ "county_code text,"//
			+ "city_id integer)";//
	public ConciseWeatherOpenHelper(Context context, String name, CursorFactory factory, int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_PROVINCE);// 创建Province表
		db.execSQL(CREATE_CITY);// 创建City表
		db.execSQL(CREATE_COUNTY);// 创建County表
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

}
