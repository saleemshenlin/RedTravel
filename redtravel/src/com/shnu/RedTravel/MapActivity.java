package com.shnu.RedTravel;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.view.MenuItem;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdate;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.BitmapDescriptor;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.MarkerOptions;

public class MapActivity extends Activity {
    private MapView mapView;
    private AMap aMap;
    private int mFromNum;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);
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
		}else if(from == 1){
			int mType = getIntent().getExtras().getInt("TYPE");
			int mTitleId = getResources().getIdentifier("class_"+mType, "string","com.shnu.RedTravel");
			mActionBar.setTitle(mTitleId);
		}
	}
	
	private void initAMap() {
		// TODO Auto-generated method stub
		if (aMap == null) {
            aMap = mapView.getMap();
            LatLng mCenter = new LatLng(31.232766,121.48819); 
            CameraUpdate mCameraUpdate = CameraUpdateFactory.newLatLngZoom(mCenter,10);
            aMap.animateCamera(mCameraUpdate);
            MarkerOptions mMarkerOptions = new MarkerOptions();
            BitmapDescriptor mBitmapDescriptor = BitmapDescriptorFactory.fromResource(R.drawable.ic_poi_1);
            mMarkerOptions.position(mCenter);
            mMarkerOptions.icon(mBitmapDescriptor);
            aMap.addMarker(mMarkerOptions);
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
