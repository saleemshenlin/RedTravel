package com.shnu.RedTravel;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.widget.CursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ListActivity extends Activity {
	private int mType;
	private Resources mResources;
	private ListView mListView;
	private Query mQuery = null;
	/**
	 * 定义一个标签,在LogCat内表示EventListFragment
	 */
	private static final String TAG = "ListActivity";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list);
		mListView = (ListView)findViewById(R.id.list_view);
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(ListActivity.this,
						DetailActivity.class);
				intent.putExtra("ID", String.valueOf(id));
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
						| Intent.FLAG_ACTIVITY_NEW_TASK);
				ListActivity.this.startActivity(intent);
				ListActivity.this.overridePendingTransition(
						R.anim.anim_in_right2left,
						R.anim.anim_out_right2left);
			}
		});
		mResources = getResources();
		Intent mIntent = getIntent();
		mType = mIntent.getExtras().getInt("TYPE");
		initActionBar(mType);
		new PoiQuery().execute(mType);
	}

	private void initActionBar(int type) {
		// TODO Auto-generated method stub
		ActionBar mActionBar = getActionBar();
	    mActionBar.setDisplayHomeAsUpEnabled(true);
	    mActionBar.setDisplayShowHomeEnabled(false);
	    int mTitleId = getResources().getIdentifier("class_"+type, "string","com.shnu.RedTravel");
	    mActionBar.setTitle(mTitleId);
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		MenuInflater mInflater = getMenuInflater();
	    mInflater.inflate(R.menu.menu, menu);
	    return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle presses on the action bar items
	    switch (item.getItemId()) {
	        case R.id.action_map:
	            Toast.makeText(getApplicationContext(), R.string.action_map, Toast.LENGTH_SHORT).show();
	            Intent mIntent = new Intent(ListActivity.this, MapActivity.class);
	            mIntent.putExtra("FROM", 1);
	            mIntent.putExtra("TYPE", mType);
				ListActivity.this.startActivity(mIntent);
				ListActivity.this.overridePendingTransition(
						R.anim.anim_in_right2left, R.anim.anim_out_right2left);
	            return true;
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
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	class PoiCursorAdapter extends CursorAdapter {
		private int resourceId;

		public PoiCursorAdapter(Context context, int textViewResourceId,
				Cursor cursor) {
			super(context, cursor, textViewResourceId);
			this.resourceId = textViewResourceId;
		}
		
		public View newView(Context context, Cursor cursor, ViewGroup parent) {  
	          
	       LinearLayout view =null;  
	       LayoutInflater vi = null;  
	       vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);  
	       view =(LinearLayout)vi.inflate(resourceId, parent, false);  
	           //v =(TextView)vi.inflate(textViewResourceId,null);  
	       Log.i(TAG,"newView"+view);  
	       return view;  
	    }  

		@Override  
	    public void bindView(View view, Context context, Cursor cursor) {  
	        Log.i("TAG","bind"+view);  
	        TextView mTextView = (TextView) view.findViewById(R.id.text_poi_title);
	        RelativeLayout mRelativeLayout = (RelativeLayout) view.findViewById(R.id.relative_layout_row);
	        int imgId = mResources.getIdentifier("img_"+cursor  
	                .getString(cursor.getColumnIndex(PoiDB.C_ID)), "drawable",
					"com.shnu.RedTravel");
			Drawable mDrawable = mResources.getDrawable(imgId);
			
	        mRelativeLayout.setBackground(mDrawable);
	        // Set the name  
	        mTextView.setText(cursor  
	                .getString(cursor.getColumnIndex(PoiDB.C_NAME)));  
	    }  
	}

	class PoiQuery extends AsyncTask<Integer, Cursor, Cursor> {
		Cursor mItemCursor;
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
				Toast.makeText(ListActivity.this, "成功获取数据",
						Toast.LENGTH_LONG).show();
				PoiCursorAdapter mPoiCursorAdapter = new PoiCursorAdapter(ListActivity.this,R.layout.row, result);
				mListView.setAdapter(mPoiCursorAdapter);
				
			} else {
				Toast.makeText(ListActivity.this, "获取数据失败",
						Toast.LENGTH_LONG).show();
			}
		}
	}
}
