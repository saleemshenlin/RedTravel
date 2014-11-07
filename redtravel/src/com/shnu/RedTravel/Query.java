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
	 * ����Event���ݲ�ѯ�������ò�ѯ����<br>
	 * ���� 0 ѧ������ 1 ��Ӱ�ݳ� 2 ��Ʒ�γ� 3 �ҹ�ע��<br>
	 * ʱ������Ϊһ��,�Ӳ�ѯ���쿪ʼ��<br>
	 * 
	 * @param intIndex
	 *            ��ѯ����
	 * @return String ��ѯ����
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
