package com.shnu.RedTravel;

import android.app.Application;
import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;


public class RedTravelApplication extends Application {

	/**
	 * ����һ����ǩ,��LogCat�ڱ�ʾLBSApplication
	 */
	private final static String TAG = "RedTravelApplication";
	/**
	 * ����һ������,���ڱ�ʾ��Ļ���
	 */
	private static int SCREENWIDTH;
	/**
	 * ����һ������,���ڱ�ʾ��Ļ�߶�
	 */
	private static int SCREENHEIGHT;
	/**
	 * ����һ������,���ڱ�ʾDPI
	 */
	private static double SCREENDPI;
	/**
	 * ����һ������,���ڱ�ʾ������
	 */
	private static Context CONTEXT;
	
	public static Context getContext() {
		return CONTEXT;
	}
	/**
	 * ����LbsApplication<br>
	 * 1)��ȡ������,��ֵ��CONTEXT<br>
	 * 2)��ȡ��Ļ�ֱ���<br>
	 * 3)��ʼ��SuperMap����<br>
	 * 4)��ʼ��mPoint2d��LOCATIONACCUCRACY<br>
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
	 * ����Dpת����
	 * 
	 * @param context
	 *            ������
	 * @param dp
	 *            DIP
	 * @return int PX
	 */
	public static int Dp2Px(Context context, int dp) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dp * scale + 0.5f);
	}

	/**
	 * ���ڻ�ȡ��Ļ�ֱ���
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
