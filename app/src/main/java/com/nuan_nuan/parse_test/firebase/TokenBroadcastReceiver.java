package com.nuan_nuan.parse_test.firebase;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.nuan_nuan.parse_test.utils.Logger;

/**
 * Created by kevin .
 */
public abstract class TokenBroadcastReceiver extends BroadcastReceiver {
    private static final String TAG = TokenBroadcastReceiver.class.getSimpleName();

    public static final String ACTION_TOKEN = "com.nuan_nuan.parse_test.ACTION_TOKEN";
    public static final String EXTRA_KEY_TOKEN = "key_token";


    @Override
    public void onReceive(Context context, Intent intent) {
        Logger.d(TAG, "onReceive" + intent);
        if (ACTION_TOKEN.equals(intent.getAction())) {
            String token = intent.getExtras().getString(EXTRA_KEY_TOKEN);

            onNewToken(token);
        }
    }

    public static IntentFilter getIntentFilter() {
        IntentFilter filter = new IntentFilter(ACTION_TOKEN);
        return filter;
    }


    public abstract void onNewToken(String token);
}
