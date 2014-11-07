package com.shnu.RedTravel;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdate;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.BitmapDescriptor;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.MarkerOptions;

public class MapActivity extends Activity{
    private MapView mapView;
    private AMap aMap;
    private int mFromNum;
	private Resources mResources;
	private Cursor mItemCursor;
	private Query mQuery;
	private String mPoiId;
	private int mType;
	
	/**
	 * 定义一个标签,在LogCat内表示EventListFragment
	 */
	private static final String TAG = "MapActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);
		mResources = getResources();
		mFromNum = getIntent().getExtras().getInt("FROM");
		initActionBar(mFromNum);
		mapView = (MapView) findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);// 必须要写
        initAMap();
	}

	private void initActionBar(int from) {
		// TODO Auto-generated method stub
		ActionBar mActionBar = getActionBar();
		mActionBar.setTitle(R.string.class_5);
		if(from == 0){
			mActionBar.setDisplayHomeAsUpEnabled(true);
			mActionBar.setDisplayShowHomeEnabled(false);
			new PoiQueryByType().execute(0);
		}else if(from == 1){
			mType = getIntent().getExtras().getInt("TYPE");
			int mTitleId = getResources().getIdentifier("class_"+mType, "string","com.shnu.RedTravel");
			mActionBar.setTitle(mTitleId);
			new PoiQueryByType().execute(mType);
		}else if(from == 2){
			mPoiId = getIntent().getExtras().getString("ID");
			new PoiQueryById().execute(mPoiId);
		}
	}
	
	private void initAMap() {
		// TODO Auto-generated method stub
		if (aMap == null) {
            aMap = mapView.getMap();
        }
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	    // Respond to the action bar's Up/Home button
	    case android.R.id.home:
        	// This is called when the Home (Up) button is pressed in the action bar.
            // Create a simple intent that starts the hierarchical parent activity and
            // use NavUtils in the Support Package to ensure proper handling of Up.
            Intent upIntent = new Intent(this, HomeActivity.class);
            if (NavUtils.shouldUpRecreateTask(this, upIntent)) {
                // This activity is not part of the application's task, so create a new task
                // with a synthesized back stack.
                TaskStackBuilder.create(this)
                        // If there are ancestor activities, they should be added here.
                        .addNextIntent(upIntent)
                        .startActivities();
                finish();
            } else {
                // This activity is part of the application's task, so simply
                // navigate up to the hierarchical parent activity.
                NavUtils.navigateUpTo(this, upIntent);
            }
            overridePendingTransition(R.anim.anim_in_left2right, R.anim.anim_out_left2right);
            finish();
            return true;
	    }
	    return super.onOptionsItemSelected(item);
	}
		
	class PoiQueryById extends AsyncTask<String, Cursor, Cursor> {

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
				Toast.makeText(MapActivity.this, "成功获取数据",
						Toast.LENGTH_LONG).show();
				result.moveToFirst();
				String imgStr = "ic_poi_"+result.getString(result.getColumnIndex(PoiDB.C_TYPE));
				int imgId = mResources.getIdentifier(imgStr, "drawable",
						"com.shnu.RedTravel");
				double mLng = Double.parseDouble(result.getString(result.getColumnIndex(PoiDB.C_LNG)));
				double mLat = Double.parseDouble(result.getString(result.getColumnIndex(PoiDB.C_LAT)));
				LatLng mCenter = new LatLng(mLat,mLng); 
	            CameraUpdate mCameraUpdate = CameraUpdateFactory.newLatLngZoom(mCenter,20);
	            aMap.animateCamera(mCameraUpdate);
	            MarkerOptions mMarkerOptions = new MarkerOptions();
	            BitmapDescriptor mBitmapDescriptor = BitmapDescriptorFactory.fromResource(imgId);
	            mMarkerOptions.position(mCenter);
	            mMarkerOptions.icon(mBitmapDescriptor);
	            mMarkerOptions.title(result.getString(result.getColumnIndex(PoiDB.C_NAME)));
	            mMarkerOptions.snippet(result.getString(result.getColumnIndex(PoiDB.C_DESC)));
	            aMap.addMarker(mMarkerOptions);
			} else {
				Toast.makeText(MapActivity.this, "获取数据失败",
						Toast.LENGTH_LONG).show();
			}
		}
	}
	
	class PoiQueryByType extends AsyncTask<Integer, Cursor, Cursor> {

		@Override
		protected Cursor doInBackground(Integer... types) {
			mQuery = new Query(getApplicationContext());
			try {
				mItemCursor = mQuery.getPoiByType(types[0]);
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
				Toast.makeText(MapActivity.this, "成功获取数据",
						Toast.LENGTH_LONG).show();
				result.moveToFirst();
				while (result.moveToNext()) {
					String imgStr = "ic_poi_"+result.getString(result.getColumnIndex(PoiDB.C_TYPE));
					int imgId = mResources.getIdentifier(imgStr, "drawable",
							"com.shnu.RedTravel");
					double mLng = Double.parseDouble(result.getString(result.getColumnIndex(PoiDB.C_LNG)));
					double mLat = Double.parseDouble(result.getString(result.getColumnIndex(PoiDB.C_LAT)));
					LatLng mCenter = new LatLng(mLat,mLng); 
		            MarkerOptions mMarkerOptions = new MarkerOptions();
		            BitmapDescriptor mBitmapDescriptor = BitmapDescriptorFactory.fromResource(imgId);
		            mMarkerOptions.position(mCenter);
		            mMarkerOptions.icon(mBitmapDescriptor);
		            mMarkerOptions.title(result.getString(result.getColumnIndex(PoiDB.C_NAME)));
		            mMarkerOptions.snippet(result.getString(result.getColumnIndex(PoiDB.C_DESC)));
		            aMap.addMarker(mMarkerOptions);
				}
	            CameraUpdate mCameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(31.232766,121.48819),11);
	            aMap.animateCamera(mCameraUpdate);
			} else {
				Toast.makeText(MapActivity.this, "获取数据失败",
						Toast.LENGTH_LONG).show();
			}
		}
	}
	
	/**
     * 方法必须重写
     */
    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }
 
    /**
     * 方法必须重写
     */
    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }
     
    /**
     * 方法必须重写
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }
 
    /**
     * 方法必须重写
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

}
