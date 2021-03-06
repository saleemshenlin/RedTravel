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
	 * 定义一个常量,用来纪录时候是否是首次加载,
	 */
	boolean isFirstIn = false;
	/**
	 * 定义一个常数,用于表示进入HomeActivity
	 */
	private static final int Add_DATA = 1000;
	/**
	 * 定义一个常数,用于表示进入GuideActivity
	 */
	private static final int GO_HOME = 1001;
	/**
	 * 定义一个常数,用于表示等待时间,值为1.5s
	 */
	private static final long SPLASH_DELAY_MILLIS = 1500;
	/**
	 * 定义一个常量,用于表示SharedPreferences的名称
	 */
	private static final String SHAREDPREFERENCES_NAME = "first_pref";
	/**
	 * 实例一个进度条,用来表示正在加载数据
	 */
	private ProgressBar mProgressBar;
	/**
	 * 定义一个Handler,用来表示跳转到不同界面
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
	 * 用于跳转进入HomeActivity
	 */
	private void goHome() {
		Intent mIntent = new Intent(SplashActivity.this, HomeActivity.class);
		SplashActivity.this.startActivity(mIntent);
		SplashActivity.this.finish();
		SplashActivity.this.overridePendingTransition(
				R.anim.anim_in_right2left, R.anim.anim_out_left2right);
	}

	/**
	 * 用于跳转j进入GuideActivity
	 */
	private void addData() {
		new InitDataBaseData().execute();
	}
	
	/**
	 * 类InitDataBaseData<br>
	 * 用于在首次加载时调用FileIO.getDateFromXML()<br>
	 * 采用多线程导入数据、更新課程日期，<br>
	 * 因导入数据时间比导入地图数据时间长,在结束之后调用init();
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
			return "加载完毕";
		}

	}
	
	/**
	 * 用于更新SharedPreferences，下次启动不用再次引导
	 */
	private void setGuided() {
		SharedPreferences mSharedPreferences = getSharedPreferences(
				SHAREDPREFERENCES_NAME, Context.MODE_PRIVATE);
		Editor mEditor = mSharedPreferences.edit();
		mEditor.putBoolean("isFirstIn", false);
		mEditor.commit();
	}
	
	/**
	 * 从Data.xml获取数据存入sqlite3<br>
	 * 具体方法如下:<br>
	 * 1)判断时候需要导入数据 <br>
	 * 2)使用pull方式解析XML<br>
	 * 3)遍历每一条数据,然后调用EventData.insertOrIgnore()存入数据库
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
					} else if (strRowName.equals("traffic")) {
						mContentValues.put(PoiDB.C_TRAFFIC, tagText);
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
