package com.nuan_nuan.parse_test.utils;


import com.nuan_nuan.parse_test.BuildConfig;

/**
 * Created by kevin.
 */
public class Logger {
	static {
		com.orhanobut.logger.Logger.init();
	}

	public static void i(String tag, String msg) {
		if (BuildConfig.DEBUG) {
			com.orhanobut.logger.Logger.t(tag).i(msg);
		}
	}

	public static void d(String tag, String msg) {
		if (BuildConfig.DEBUG) {
			com.orhanobut.logger.Logger.t(tag).d(msg);
		}
	}

	public static void e(String tag, String msg, Throwable tr) {
		if (BuildConfig.DEBUG) {
			com.orhanobut.logger.Logger.t(tag).e(tr, msg);
		}
	}

	public static void w(String tag, String msg) {
		if (BuildConfig.DEBUG) {
			com.orhanobut.logger.Logger.t(tag).w(msg);
		}
	}

	public static void e(String tag, String msg) {
		if (BuildConfig.DEBUG) {
			com.orhanobut.logger.Logger.t(tag).e(msg);
		}
	}

	public static void json(String tag, String msg) {
		com.orhanobut.logger.Logger.t(tag).json(msg);
	}

	public static void xml(String tag, String msg) {
		com.orhanobut.logger.Logger.t(tag).xml(msg);
	}

}
