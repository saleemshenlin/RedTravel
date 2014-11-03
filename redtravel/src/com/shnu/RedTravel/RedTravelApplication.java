package com.shnu.RedTravel;

import android.app.Application;
import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;


public class RedTravelApplication extends Application {

	/**
	 * 定义一个标签,在LogCat内表示LBSApplication
	 */
	private final static String TAG = "RedTravelApplication";
	/**
	 * 定义一个常数,用于表示屏幕宽度
	 */
	private static int SCREENWIDTH;
	/**
	 * 定义一个常数,用于表示屏幕高度
	 */
	private static int SCREENHEIGHT;
	/**
	 * 定义一个常数,用于表示DPI
	 */
	private static double SCREENDPI;
	/**
	 * 定义一个常量,用于表示上下文
	 */
	private static Context CONTEXT;
	
	public static Context getContext() {
		return CONTEXT;
	}
	/**
	 * 创建LbsApplication<br>
	 * 1)获取上下文,赋值个CONTEXT<br>
	 * 2)获取屏幕分辨率<br>
	 * 3)初始化SuperMap环境<br>
	 * 4)初始化mPoint2d和LOCATIONACCUCRACY<br>
	 * 
	 */
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		CONTEXT = getApplicationContext();
		getScreenDesplay();
		Log.i(TAG, "LBSApplication getScreenDisplay height:" + SCREENHEIGHT);
	}
	/**
	 * 用于Dp转像素
	 * 
	 * @param context
	 *            上下文
	 * @param dp
	 *            DIP
	 * @return int PX
	 */
	public static int Dp2Px(Context context, int dp) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dp * scale + 0.5f);
	}

	/**
	 * 用于获取屏幕分别率
	 */
	private void getScreenDesplay() {
		DisplayMetrics dm = new DisplayMetrics();
		dm = getResources().getDisplayMetrics();
		setScreenWidth(dm.widthPixels);
		setScreenHeight(dm.heightPixels);
	}
	public static int getScreenWidth() {
		return SCREENWIDTH;
	}

	public static void setScreenWidth(float xdpi) {
		RedTravelApplication.SCREENWIDTH = (int) xdpi;
	}

	public static int getScreenHeight() {
		return SCREENHEIGHT;
	}

	public static void setScreenHeight(float ydpi) {
		RedTravelApplication.SCREENHEIGHT = (int) ydpi;
	}

	public static double getScreenDPI() {
		return SCREENDPI;
	}

	public static void setScreenDPI(double screenDPI) {
		RedTravelApplication.SCREENDPI = screenDPI;
	}

}
