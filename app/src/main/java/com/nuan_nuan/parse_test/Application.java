package com.nuan_nuan.parse_test;

import com.parse.Parse;

/**
 * Created by kevin.
 */
public class Application extends android.app.Application {
    private static final String TAG = Application.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(new Parse.Configuration.Builder(getApplicationContext())
                .applicationId("rmLZ2E1HCKNqwvQeGX1h")
                .clientKey("qPxlvJjupD9LA4RROpX1")
                // new
                .server("http://lokobee.herokuapp.com/parse/")   // is work
                // old
                //.server("http://lokobee.herokuapp.com/parse")  // not work

                .build()
        );
        //  Automatic User    anonymous
//        ParseUser.enableAutomaticUser();
//        ParseUser.getCurrentUser().saveInBackground();
    }
}
