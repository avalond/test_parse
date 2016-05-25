package com.nuan_nuan.parse_test.exception;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;


import com.nuan_nuan.parse_test.utils.Logger;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Field;

/**
 * Created by kevin. 处理crash
 */
public class CrashHandler implements Thread.UncaughtExceptionHandler {
	private static final String TAG = CrashHandler.class.getSimpleName();
	private Context mContext;
	private static CrashHandler mCrashHandler;

	private CrashHandler() {

	}

	public static CrashHandler getCrashHandlerInstance() {
		if (mCrashHandler == null) {
			mCrashHandler = new CrashHandler();
		}
		return mCrashHandler;
	}

	public void init(Context context) {
		this.mContext = context;
	}

	/**
	 * 程序出错的时候调用
	 * @param thread
	 * @param ex
	 */
	@Override
	public void uncaughtException(Thread thread, Throwable ex) {
		StringBuilder builder = new StringBuilder();
		PackageManager manager = mContext.getPackageManager();
		PackageInfo info = null;
		try {
			info = manager.getPackageInfo(mContext.getPackageName(),
					PackageManager.GET_UNINSTALLED_PACKAGES
							| PackageManager.GET_ACTIVITIES);
			builder.append("Version number:").append(info.versionName);
			builder.append("\n");
			builder.append("Hardware:  \n");
			Field[] field = Build.class.getDeclaredFields();
			for (Field aField : field) {
				aField.setAccessible(true);
				String name = aField.getName();
				builder.append(name).append("=");
				String value = aField.get(null).toString();
				builder.append(value);
				builder.append("\n");
			}
			builder.append("Error message: \n");
			StringWriter mWriter = new StringWriter();
			PrintWriter mPrintWriter = new PrintWriter(mWriter);
			ex.printStackTrace(mPrintWriter);
			String mMessageString = mWriter.toString();
			builder.append(mMessageString);
			String msg = builder.toString();
			Logger.d(TAG,msg);
		} catch (PackageManager.NameNotFoundException | IllegalAccessException e) {
			e.printStackTrace();
		}
		android.os.Process.killProcess(android.os.Process.myPid());
		System.exit(2);
	}

}
