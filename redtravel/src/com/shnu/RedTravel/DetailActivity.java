package com.shnu.RedTravel;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class DetailActivity extends Activity {
	private Resources mResources;
	private Query mQuery;
	private String mPoiId;
	private ActionBar mActionBar;
	private ImageView mImageView;
	/**
	 * 定义一个标签,在LogCat内表示EventListFragment
	 */
	private static final String TAG = "DetailActivity";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail);
		mResources = getResources();
		Intent mIntent = getIntent();
		mActionBar = getActionBar();
		mPoiId = mIntent.getExtras().getString("ID");
		if( mPoiId != null){
			new PoiQuery().execute(mPoiId);
		}
		mImageView = (ImageView)findViewById(R.id.image_view_right);
		mImageView.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
	            Toast.makeText(getApplicationContext(), R.string.action_map, Toast.LENGTH_SHORT).show();
	            Intent mIntent = new Intent(DetailActivity.this, MapActivity.class);
	            mIntent.putExtra("FROM", 2);
	            mIntent.putExtra("ID", mPoiId);
	            DetailActivity.this.startActivity(mIntent);
	            DetailActivity.this.overridePendingTransition(
						R.anim.anim_in_right2left, R.anim.anim_out_right2left);
			}
		});
	}
	
	class PoiQuery extends AsyncTask<String, Cursor, Cursor> {
		Cursor mItemCursor;
		
		@Override
		protected Cursor doInBackground(String... types) {
			mQuery = new Query(getApplicationContext());
			try {
				mItemCursor = mQuery.getPoiById(types[0]);
				int num = mItemCursor.getCount();
				Log.i(TAG, "ActivityProvider cursor" + num);
			} catch (Exception e) {
				Log.e(TAG, e.toString());
			} finally {
				if (mItemCursor.isClosed()) {
					mItemCursor.close();
				}
				PoiDB mPoiDB = new PoiDB(getApplicationContext());
				mPoiDB.closeDatabase();
			}
			return mItemCursor;
		}

		@Override
		protected void onPostExecute(Cursor result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (result != null) {
				Toast.makeText(DetailActivity.this, "成功获取数据",
						Toast.LENGTH_LONG).show();
				result.moveToFirst();
				ImageView mImageView = (ImageView)findViewById(R.id.image_view_poi);
				int imgId = mResources.getIdentifier("img_"+result.getString(result.getColumnIndex(PoiDB.C_ID)), "drawable",
						"com.shnu.RedTravel");
				Drawable mDrawable = mResources.getDrawable(imgId);
				mImageView.setImageDrawable(mDrawable);
				TextView mTextDesc = (TextView)findViewById(R.id.text_view_desc);
				TextView mTextAddress = (TextView)findViewById(R.id.text_view_address);
				TextView mTextTime = (TextView)findViewById(R.id.text_view_time);
				TextView mTextTraffic = (TextView)findViewById(R.id.text_view_traffic);
				TextView mTextTicket = (TextView)findViewById(R.id.text_view_ticket);
				TextView mTextTel = (TextView)findViewById(R.id.text_view_tel);
				mTextDesc.setText(result.getString(result.getColumnIndex(PoiDB.C_DESC)));
				mTextAddress.setText(result.getString(result.getColumnIndex(PoiDB.C_ADDRESS)));
				mTextTime.setText(result.getString(result.getColumnIndex(PoiDB.C_TIME)));
				mTextTraffic.setText(result.getString(result.getColumnIndex(PoiDB.C_TRAFFIC)));
				mTextTicket.setText(result.getString(result.getColumnIndex(PoiDB.C_TICKET)));
				mTextTel.setText(result.getString(result.getColumnIndex(PoiDB.C_TEL)));
				mActionBar.setTitle(result.getString(result.getColumnIndex(PoiDB.C_NAME)));
			} else {
				Toast.makeText(DetailActivity.this, "获取数据失败",
						Toast.LENGTH_LONG).show();
			}
		}
	}

}
