package com.shnu.RedTravel;


import java.io.IOException;

import org.xmlpull.v1.XmlPullParserException;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

public class SplashActivity extends Activity {
	/* 
	 * ����һ������,������¼ʱ���Ƿ����״μ���,
	 */
	boolean isFirstIn = false;
	/**
	 * ����һ������,���ڱ�ʾ����HomeActivity
	 */
	private static final int Add_DATA = 1000;
	/**
	 * ����һ������,���ڱ�ʾ����GuideActivity
	 */
	private static final int GO_HOME = 1001;
	/**
	 * ����һ������,���ڱ�ʾ�ȴ�ʱ��,ֵΪ1.5s
	 */
	private static final long SPLASH_DELAY_MILLIS = 1500;
	/**
	 * ����һ������,���ڱ�ʾSharedPreferences������
	 */
	private static final String SHAREDPREFERENCES_NAME = "first_pref";
	/**
	 * ʵ��һ��������,������ʾ���ڼ�������
	 */
	private ProgressBar mProgressBar;
	/**
	 * ����һ��Handler,������ʾ��ת����ͬ����
	 */
	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case Add_DATA:
				addData();
				break;
			case GO_HOME:
				goHome();
				break;
			}
			super.handleMessage(msg);
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		mProgressBar = (ProgressBar) findViewById(R.id.progess_bar_data);
		init();
	}

	private void init() {
		SharedPreferences preferences = getSharedPreferences(
				SHAREDPREFERENCES_NAME, MODE_PRIVATE);
		isFirstIn = preferences.getBoolean("isFirstIn", true);
		if (!isFirstIn) {
			mHandler.sendEmptyMessageDelayed(GO_HOME, SPLASH_DELAY_MILLIS);
		} else {
			mHandler.sendEmptyMessageDelayed(Add_DATA, SPLASH_DELAY_MILLIS);
		}
	}
	
	/**
	 * ������ת����HomeActivity
	 */
	private void goHome() {
		Intent mIntent = new Intent(SplashActivity.this, HomeActivity.class);
		SplashActivity.this.startActivity(mIntent);
		SplashActivity.this.finish();
		SplashActivity.this.overridePendingTransition(
				R.anim.anim_in_right2left, R.anim.anim_out_left2right);
	}

	/**
	 * ������תj����GuideActivity
	 */
	private void addData() {
		new InitDataBaseData().execute();
	}
	
	/**
	 * ��InitDataBaseData<br>
	 * �������״μ���ʱ����FileIO.getDateFromXML()<br>
	 * ���ö��̵߳������ݡ������n�����ڣ�<br>
	 * ��������ʱ��ȵ����ͼ����ʱ�䳤,�ڽ���֮�����init();
	 */
	class InitDataBaseData extends AsyncTask<String, Integer, String> {

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			mProgressBar.setVisibility(View.GONE);
			Toast.makeText(SplashActivity.this, result, Toast.LENGTH_SHORT)
					.show();
			setGuided();
			goHome();
		}

		@Override
		protected String doInBackground(String... params) {
			getDateFromXML();
			return "�������";
		}

	}
	
	/**
	 * ���ڸ���SharedPreferences���´����������ٴ�����
	 */
	private void setGuided() {
		SharedPreferences mSharedPreferences = getSharedPreferences(
				SHAREDPREFERENCES_NAME, Context.MODE_PRIVATE);
		Editor mEditor = mSharedPreferences.edit();
		mEditor.putBoolean("isFirstIn", false);
		mEditor.commit();
	}
	
	/**
	 * ��Data.xml��ȡ���ݴ���sqlite3<br>
	 * ���巽������:<br>
	 * 1)�ж�ʱ����Ҫ�������� <br>
	 * 2)ʹ��pull��ʽ����XML<br>
	 * 3)����ÿһ������,Ȼ�����EventData.insertOrIgnore()�������ݿ�
	 */
	public void getDateFromXML() {
		Context mContext = getApplicationContext();
		Resources mResources = mContext.getResources();
		PoiDB mPoiDB = new PoiDB(mContext);

		XmlResourceParser mXmlResourceParser = mResources.getXml(R.xml.data);
		int intEventType;
		StringBuffer mStringBuffer = new StringBuffer();
		ContentValues mContentValues = new ContentValues();
		String strRowName = "";
		try {
			intEventType = mXmlResourceParser.getEventType();
			while (intEventType != XmlResourceParser.END_DOCUMENT) {
				if (intEventType == XmlResourceParser.START_TAG) {
					String tagName = mXmlResourceParser.getName().toString()
							.trim();
					if (!tagName.equals("items")) {
						mStringBuffer.append(mXmlResourceParser.getName());
						if (tagName.equals("item")) {
							mStringBuffer.append("(");
						} else {
							mStringBuffer.append(":");
							strRowName = tagName;
						}
					}
				} else if (intEventType == XmlResourceParser.END_TAG) {
					String tagName = mXmlResourceParser.getName().toString()
							.trim();
					if (tagName.equals("item")) {
						mStringBuffer.append(")");
						Log.d("FileIO", mStringBuffer.toString());
						mPoiDB.insertOrIgnore(mContentValues, PoiDB.TABLE);
						mStringBuffer.delete(0, mStringBuffer.length() - 1);
					} else if (tagName.equals("Alldata")) {
						Log.d("FileIO", "end");
					} else {
						mStringBuffer.append(", ");
					}
				} else if (intEventType == XmlResourceParser.TEXT) {
					String tagText = mXmlResourceParser.getText().toString()
							.trim();
					mStringBuffer.append(mXmlResourceParser.getText()
							.toString().trim());
					if (strRowName.equals("id")) {
						mContentValues.put(PoiDB.C_ID, tagText);
					} else if (strRowName.equals("name")) {
						mContentValues.put(PoiDB.C_NAME, tagText);
					} else if (strRowName.equals("address")) {
						mContentValues.put(PoiDB.C_ADDRESS, tagText);
					} else if (strRowName.equals("triffic")) {
						mContentValues.put(PoiDB.C_TRIFFIC, tagText);
					} else if (strRowName.equals("time")) {
						mContentValues.put(PoiDB.C_TIME, tagText);
					} else if (strRowName.equals("ticket")) {
						mContentValues.put(PoiDB.C_TICKET, tagText);
					} else if (strRowName.equals("type")) {
						mContentValues.put(PoiDB.C_TYPE, tagText);
					} else if (strRowName.equals("tel")) {
						mContentValues.put(PoiDB.C_TEL, tagText);
					} else if (strRowName.equals("desc")) {
						mContentValues.put(PoiDB.C_DESC, tagText);
					} else if (strRowName.equals("lng")) {
						mContentValues.put(PoiDB.C_LNG, tagText);
					} else if (strRowName.equals("lat")) {
						mContentValues.put(PoiDB.C_LAT, tagText);
					}
				}
				intEventType = mXmlResourceParser.next();
			}
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
}

}
