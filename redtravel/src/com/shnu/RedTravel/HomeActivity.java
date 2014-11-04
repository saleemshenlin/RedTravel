package com.shnu.RedTravel;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

public class HomeActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		initLinearLayout();
	}

	private void initLinearLayout() {
		// TODO Auto-generated method stub
		LinearLayout mLinearLayout1 = (LinearLayout)findViewById(R.id.linner_layout_class_1);
		LinearLayout mLinearLayout2 = (LinearLayout)findViewById(R.id.linner_layout_class_2);
		LinearLayout mLinearLayout3 = (LinearLayout)findViewById(R.id.linner_layout_class_3);
		LinearLayout mLinearLayout4 = (LinearLayout)findViewById(R.id.linner_layout_class_4);
		LinearLayout mLinearLayout5 = (LinearLayout)findViewById(R.id.linner_layout_class_5);
		mLinearLayout1.setOnClickListener(new View.OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent mIntent = new Intent(HomeActivity.this, ListActivity.class);
				mIntent.putExtra("TYPE", 1);
				HomeActivity.this.startActivity(mIntent);
				HomeActivity.this.overridePendingTransition(
						R.anim.anim_in_right2left, R.anim.anim_out_right2left);
			}
		
		});
		mLinearLayout2.setOnClickListener(new View.OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent mIntent = new Intent(HomeActivity.this, ListActivity.class);
				mIntent.putExtra("TYPE", 2);
				HomeActivity.this.startActivity(mIntent);
				HomeActivity.this.overridePendingTransition(
						R.anim.anim_in_right2left, R.anim.anim_out_right2left);
			}
		
		});
		mLinearLayout3.setOnClickListener(new View.OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent mIntent = new Intent(HomeActivity.this, ListActivity.class);
				mIntent.putExtra("TYPE", 3);
				HomeActivity.this.startActivity(mIntent);
				HomeActivity.this.overridePendingTransition(
						R.anim.anim_in_right2left, R.anim.anim_out_right2left);
			}
		
		});
		mLinearLayout4.setOnClickListener(new View.OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent mIntent = new Intent(HomeActivity.this, ListActivity.class);
				mIntent.putExtra("TYPE", 4);
				HomeActivity.this.startActivity(mIntent);
				HomeActivity.this.overridePendingTransition(
						R.anim.anim_in_right2left, R.anim.anim_out_right2left);
			}
			
		});
		mLinearLayout5.setOnClickListener(new View.OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), R.string.action_map, Toast.LENGTH_SHORT).show();
				Intent mIntent = new Intent(HomeActivity.this, MapActivity.class);
				mIntent.putExtra("FOME", 0);
				HomeActivity.this.startActivity(mIntent);
				HomeActivity.this.overridePendingTransition(
						R.anim.anim_in_right2left, R.anim.anim_out_right2left);
			}
			
		});
	}
	

}
