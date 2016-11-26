package com.nuan_nuan.parse_test;

import com.parse.Parse;

/**
 * Created by kevin.
 */
public class Application extends android.app.Application {
	private static final String TAG = Application.class.getSimpleName();

	@Override public void onCreate() {
		super.onCreate();
		Parse.initialize(new Parse.Configuration.Builder(getApplicationContext())
				.applicationId("rmLZ2E1HCKNqwvQeGX1h")
				.clientKey("qPxlvJjupD9LA4RROpX1")
				// new
				.server("http://lokobee.herokuapp.com/parse/")   // is work
				// old
				//.server("http://lokobee.herokuapp.com/parse")  // not work
				.build());


		//  Automatic User    anonymous
		//        ParseUser.enableAutomaticUser();
		//        ParseUser.getCurrentUser().saveInBackground();

//
//		//push
//		ParseInstallation.getCurrentInstallation().saveEventually(new SaveCallback() {
//			@Override public void done(ParseException e) {
//				if (e == null) {
//					Logger.d(TAG, "=-------");
//
//					ParseQuery query = ParseInstallation.getQuery();
//					// Notification for iOS users
//					query.whereEqualTo("deviceType", "android");
//					ParsePush iOSPush = new ParsePush();
//					iOSPush.setMessage("Your suitcase has been filled with tiny apples!");
//					iOSPush.setQuery(query);
//					iOSPush.sendInBackground(new SendCallback() {
//						@Override public void done(ParseException e) {
//							if (e == null) {
//								Logger.d(TAG, "");
//							} else {
//								Logger.e(TAG, e.getMessage() + "--" + e.getCode());
//							}
//						}
//					});
//
//				} else {
//					Logger.d(TAG,
//							"message==" + e.getMessage() + "__________" + "code===" + e.getCode());
//				}
//			}
//		});

	}
}
