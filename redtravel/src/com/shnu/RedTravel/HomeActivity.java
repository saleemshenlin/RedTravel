package com.shnu.RedTravel;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
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

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			buildExitDialog(HomeActivity.this);
		}
		return false;
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
	
	/**
	 * 用于处理应用退出
	 * 
	 * @param context
	 *            当前上下文
	 */
	public static void buildExitDialog(Context context) {
		AlertDialog.Builder mBuilder = new AlertDialog.Builder(context);
		mBuilder.setMessage("确认退出吗？");
		mBuilder.setTitle("提示");
		mBuilder.setPositiveButton("确认", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.dismiss();
				System.exit(0);
			}
		});
		mBuilder.setNegativeButton("取消", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});
		mBuilder.create().show();
	}

}
