package com.nuan_nuan.parse_test.firebase;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.nuan_nuan.parse_test.R;
import com.nuan_nuan.parse_test.firebase.push.PushActivity;
import com.nuan_nuan.parse_test.utils.Logger;

/**
 * Created by kevin .
 */
public class ChangeActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = ChangeActivity.class.getSimpleName();

    private Button emailpwd;
    private Button Anonymous;
    private Button push;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_activity);
        setViews();
    }

    private void setViews() {
        emailpwd = (Button) findViewById(R.id.emailpwd);
        emailpwd.setOnClickListener(this);
        //
        Anonymous = (Button) findViewById(R.id.Anonymous);
        Anonymous.setOnClickListener(this);
        push = (Button) findViewById(R.id.push);
        push.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.emailpwd:
                Intent intent = new Intent(ChangeActivity.this, EmailPasswordActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                Logger.d(TAG, "go to EmailPasswordActivity is ok");
                break;
            case R.id.Anonymous:
                Intent intent1 = new Intent(ChangeActivity.this, AnonymousAuthActivity.class);
                intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent1);
                Logger.d(TAG, "go to AnonymousAuthActivity is ok");
                break;
            case R.id.push:
                Intent intent2 = new Intent(ChangeActivity.this, PushActivity.class);
                intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent2);
                Logger.d(TAG, "go to Push Activity is ok");
                break;
        }
    }
}
