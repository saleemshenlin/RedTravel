package com.shnu.RedTravel;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

public class ListActivity extends Activity {
	private int mType;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list);
		Intent mIntent = getIntent();
		mType = mIntent.getExtras().getInt("TYPE");
		initActionBar(mType);
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
}
