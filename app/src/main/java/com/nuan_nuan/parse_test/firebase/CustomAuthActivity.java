package com.nuan_nuan.parse_test.firebase;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.nuan_nuan.parse_test.R;
import com.nuan_nuan.parse_test.utils.Logger;

/**
 * Created by kevin .
 */
public class CustomAuthActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = CustomAuthActivity.class.getSimpleName();


    private FirebaseAuth mAuth;


    private FirebaseAuth.AuthStateListener mAuthListener;


    private String mCustomToken;
    private TokenBroadcastReceiver mTokenReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_firebase_custom);


        findViewById(R.id.button_sign_in).setOnClickListener(this);


        mTokenReceiver = new TokenBroadcastReceiver() {
            @Override
            public void onNewToken(String token) {
                Logger.d(TAG, "onNewToken:" + token);
                setCustomToken(token);
            }
        };


        mAuth = FirebaseAuth.getInstance();


        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // signed in
                    Logger.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    //  signed out
                    Logger.d(TAG, "onAuthStateChanged:signed_out");
                }

                updateUI(user);

            }
        };

    }


    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);

        registerReceiver(mTokenReceiver, TokenBroadcastReceiver.getIntentFilter());

    }


    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }

        unregisterReceiver(mTokenReceiver);

    }


    private void startSignIn() {
        //  sign in with custom token

        mAuth.signInWithCustomToken(mCustomToken)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Logger.d(TAG, "signInWithCustomToken:onComplete:" + task.isSuccessful());

                        if (!task.isSuccessful()) {
                            Logger.d(TAG, "signInWithCustomToken" + task.getException());
                            Toast.makeText(CustomAuthActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            ((TextView) findViewById(R.id.text_sign_in_status)).setText(
                    "User ID: " + user.getUid());
        } else {
            ((TextView) findViewById(R.id.text_sign_in_status)).setText(
                    "Error: sign in failed.");
        }
    }

    private void setCustomToken(String token) {
        mCustomToken = token;

        String status;
        if (mCustomToken != null) {
            status = "Token:" + mCustomToken;
        } else {
            status = "Token: null";
        }

        //sign-in button and show the token
        findViewById(R.id.button_sign_in).setEnabled((mCustomToken != null));
        ((TextView) findViewById(R.id.text_token_status)).setText(status);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_sign_in:
                startSignIn();
                break;
        }
    }
}
