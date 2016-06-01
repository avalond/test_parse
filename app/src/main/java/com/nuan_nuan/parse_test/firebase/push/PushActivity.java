package com.nuan_nuan.parse_test.firebase.push;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.nuan_nuan.parse_test.R;
import com.nuan_nuan.parse_test.utils.Logger;

/**
 * Created by kevin .
 */
public class PushActivity extends AppCompatActivity {

	private static final String TAG = "MainActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fire_base_push);

		// If a notification message is tapped, any data accompanying the notification
		// message is available in the intent extras. In this sample the launcher
		// intent is fired when the notification is tapped, so any accompanying data would
		// be handled here. If you want a different intent fired, set the click_action
		// field of the notification message to the desired intent. The launcher intent
		// is used when no click_action is specified.
		//
		// Handle possible data accompanying notification message.
		// [START handle_data_extras]
		if (getIntent().getExtras() != null) {
			for (String key : getIntent().getExtras().keySet()) {
				String value = getIntent().getExtras().getString(key);
				Logger.d(TAG, "Key: " + key + " Value: " + value);
			}
		}
		// [END handle_data_extras]

		Button subscribeButton = (Button) findViewById(R.id.subscribeButton);
		subscribeButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// [START subscribe_topics]
				FirebaseMessaging.getInstance().subscribeToTopic("news");
				Logger.d(TAG, "Subscribed to news topic");
				// [END subscribe_topics]
			}
		});

		Button logTokenButton = (Button) findViewById(R.id.logTokenButton);
		logTokenButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Logger.d(TAG, "InstanceID token: " + FirebaseInstanceId.getInstance().getToken());
			}
		});
	}
}
