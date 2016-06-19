package com.conciseweather.app.db;

import java.util.ArrayList;
import java.util.List;

import com.conciseweather.app.model.City;
import com.conciseweather.app.model.County;
import com.conciseweather.app.model.Province;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * 工具类，封装常用的数据库操作
 * 
 * @author Administrator
 *
 */
public class ConciseWeatherDB {
	/**
	 * 数据库名
	 */
	public static final String DB_NAME = "concise_weather";
	/**
	 * 数据库版本
	 */
	public static final int VERSION = 1;
	private static ConciseWeatherDB conciseWeatherDB;
	private SQLiteDatabase db;

	/**
	 * 将构造方法私有化
	 * 
	 * @param context
	 */
	private ConciseWeatherDB(Context context) {
		ConciseWeatherOpenHelper dbHelper = new ConciseWeatherOpenHelper(context, DB_NAME, null, VERSION);
		this.db = dbHelper.getWritableDatabase();
	}

	/**
	 * 获取ConciseWeatherDB的实例
	 * 
	 * @param context
	 * @return
	 */
	public synchronized static ConciseWeatherDB getInstance(Context context) {
		if (conciseWeatherDB == null) {
			conciseWeatherDB = new ConciseWeatherDB(context);
		}
		return conciseWeatherDB;
	}

	/**
	 * 将Province的实例保存到数据库
	 * 
	 * @param province
	 */
	public void saveProvince(Province province) {
		if (province != null) {
			ContentValues values = new ContentValues();
			values.put("province_name", province.getProvinceName());
			values.put("province_code", province.getProvinceCode());
			db.insert("Province", null, values);// 返回新插入行的id
		}
	}
	/**
	 * 从数据库读取全国所有的省份信息
	 * @return
	 */
	public List<Province> loadProvinces() {
		List<Province> list = new ArrayList<Province>();
		Cursor cursor = db.query("Province", null, null, null, null, null, null);
		while (cursor.moveToNext()) {
			Province province = new Province();
			province.setId(cursor.getInt(cursor.getColumnIndex("id")));
			province.setProvinceName(cursor.getString(cursor.getColumnIndex("province_name")));
			province.setProvinceCode(cursor.getString(cursor.getColumnIndex("province_code")));
			list.add(province);
		}
		cursor.close();
		return list;
	}
	/**
	 *  将City实例存储到数据库
	 * @param city
	 */
	public void saveCity(City city) {
		if (city != null) {
			ContentValues values = new ContentValues();
			values.put("cityName", city.getCityName());
			values.put("cityCode", city.getCityCode());
			values.put("provinceId", city.getProvinceId());
			db.insert("City", null, values);
		}
	}
	/**
	 * 从数据库读取某省下所有的城市信息
	 * @param provinceId
	 * @return
	 */
	public List<City> loadCities(int provinceId) {
		List<City> list = new ArrayList<City>();
		Cursor cursor = db.query("City", null, "province_id=?", new String[] { String.valueOf(provinceId) }, null,
				null, null);
		while (cursor.moveToNext()) {
			City city = new City();
			city.setId(cursor.getInt(cursor.getColumnIndex("id")));
			city.setCityName(cursor.getString(cursor.getColumnIndex("city_name")));
			city.setCityCode(cursor.getString(cursor.getColumnIndex("city_code")));
			city.setProvinceId(provinceId);
			list.add(city);
		}
		cursor.close();
		return list;
	}

	/**
	 * 将County实例存储到数据库
	 * 
	 * @param county
	 */
	public void saveCounty(County county) {
		if (county != null) {
			ContentValues values = new ContentValues();
			values.put("county_name", county.getCountyName());
			values.put("county_code", county.getCountyCode());
			values.put("city_id", county.getCityId());
			db.insert("City", null, values);
		}
	}
	/**
	 * 从数据库某城市下读取所有县的信息
	 * @param cityId
	 * @return
	 */
	public List<County> loadCounties(int cityId) {
		List<County> list = new ArrayList<County>();
		String selection = "city_id=?";
		String[] selectionArgs = { String.valueOf(cityId) };
		Cursor cursor = db.query("County", null, selection, selectionArgs, null, null, null);
		while (cursor.moveToNext()) {
			County county = new County();
			county.setCityId(cityId);
			county.setCountyCode(cursor.getString(cursor.getColumnIndex("county_code")));
			county.setCountyName(cursor.getString(cursor.getColumnIndex("county_name")));
			county.setId(cursor.getInt(cursor.getColumnIndex("id")));
			list.add(county);
		}
		cursor.close();
		return list;
	}
}
