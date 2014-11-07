package com.shnu.RedTravel;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;


public class Query{
	private PoiProvider mPoiProvider = null;
	private Cursor mItemCursor = null;
	private PoiDB mPoiDB = null;
	public Query(Context context) {
		super();
		mPoiDB = new PoiDB(context);
		mPoiProvider = new PoiProvider(context);
	}

	
	/**
	 * 用于Event根据查询类型设置查询条件<br>
	 * 类型 0 学术讲座 1 电影演出 2 精品课程 3 我关注的<br>
	 * 时间设置为一周,从查询当天开始算<br>
	 * 
	 * @param intIndex
	 *            查询类型
	 * @return String 查询条件
	 */
	public String getSectionViaType(int intIndex) {
		String strSQL;
		switch (intIndex) {
		case 0:
			strSQL = "";
			return strSQL;
		case 1:
			strSQL = PoiDB.C_TYPE + " = '1'";
			return strSQL;
		case 2:
			strSQL = PoiDB.C_TYPE + " = '2'";
			return strSQL;
		case 3:
			strSQL = PoiDB.C_TYPE + " = '3'";
			return strSQL;
		case 4:
			strSQL = PoiDB.C_TYPE + " = '4'";
			return strSQL;
		default:
			return null;
		}
	}
	
	public Cursor getPoiByType(int type) {
		try {
			mItemCursor = mPoiProvider.query(PoiProvider.CONTENT_URI, null,
					getSectionViaType(type), null,null);
		} catch (Exception e) {
			Log.e("Query", e.toString());
		} finally {
			if (mItemCursor.isClosed()) {
				mItemCursor.close();
			}
			if(mPoiDB!=null){
				mPoiDB.closeDatabase();
			}

		}
		return mItemCursor;
	}
	
	 public Cursor getPoiById(String id) {
		 final Uri queryUri = Uri.parse(PoiProvider.CONTENT_URI.toString() + "/"
		 + id);
		 try {
			 mItemCursor = mPoiProvider.query(queryUri, null, null, null, null);
		 } catch (Exception e) {
			 Log.e("Query", e.toString());
		 } finally {
			 if (mItemCursor.isClosed()) {
				 mItemCursor.close();
			 }
		 }
		 return mItemCursor;
	 }
}
