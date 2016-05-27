package com.nuan_nuan.parse_test.firebase;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.nuan_nuan.parse_test.R;
import com.nuan_nuan.parse_test.utils.Logger;

/**
 * Created by kevin .
 */
public class AnonymousAuthActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = AnonymousAuthActivity.class.getSimpleName();

    private FirebaseAuth mAuth;


    private FirebaseAuth.AuthStateListener mAuthListener;


    private EditText mEmailField;
    private EditText mPasswordField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_firebase_anonymous);


        mAuth = FirebaseAuth.getInstance();


        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // signed in
                    Logger.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // signed out
                    Logger.d(TAG, "onAuthStateChanged:signed_out");
                }

                updateUI(user);

            }
        };

        // Fields
        mEmailField = (EditText) findViewById(R.id.field_email);
        mPasswordField = (EditText) findViewById(R.id.field_password);

        // Click listeners
        findViewById(R.id.button_anonymous_sign_in).setOnClickListener(this);
        findViewById(R.id.button_anonymous_sign_out).setOnClickListener(this);
        findViewById(R.id.button_link_account).setOnClickListener(this);
    }


    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }


    private void signInAnonymously() {
        showProgressDialog();

        mAuth.signInAnonymously()
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Logger.d(TAG, "signInAnonymously:onComplete:" + task.isSuccessful());

                        if (!task.isSuccessful()) {
                            Logger.w(TAG, "signInAnonymously" + task.getException());
                            Toast.makeText(AnonymousAuthActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }


                        hideProgressDialog();

                    }
                });

    }

    private void signOut() {
        mAuth.signOut();
        updateUI(null);
    }

    private void linkAccount() {

        if (!validateLinkForm()) {
            return;
        }

        String email = mEmailField.getText().toString();
        String password = mPasswordField.getText().toString();

        // Create EmailAuthCredential with email and password
        AuthCredential credential = EmailAuthProvider.getCredential(email, password);

        // Link the anonymous user to the email credential
        showProgressDialog();

        mAuth.getCurrentUser().linkWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Logger.d(TAG, "linkWithCredential:onComplete:" + task.isSuccessful());

                        if (!task.isSuccessful()) {
                            Toast.makeText(AnonymousAuthActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }


                        hideProgressDialog();

                    }
                });

    }

    private boolean validateLinkForm() {
        boolean valid = true;

        String email = mEmailField.getText().toString();
        if (TextUtils.isEmpty(email)) {
            mEmailField.setError("Required.");
            valid = false;
        } else {
            mEmailField.setError(null);
        }

        String password = mPasswordField.getText().toString();
        if (TextUtils.isEmpty(password)) {
            mPasswordField.setError("Required.");
            valid = false;
        } else {
            mPasswordField.setError(null);
        }

        return valid;
    }

    private void updateUI(FirebaseUser user) {
        hideProgressDialog();

        TextView idView = (TextView) findViewById(R.id.anonymous_status_id);
        TextView emailView = (TextView) findViewById(R.id.anonymous_status_email);
        boolean isSignedIn = (user != null);

        // Status text
        if (isSignedIn) {
            idView.setText("" + user.getUid());
            emailView.setText("" + user.getEmail());
        } else {
            idView.setText("signed_out");
            emailView.setText(null);
        }


        findViewById(R.id.button_anonymous_sign_in).setEnabled(!isSignedIn);
        findViewById(R.id.button_anonymous_sign_out).setEnabled(isSignedIn);
        findViewById(R.id.button_link_account).setEnabled(isSignedIn);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_anonymous_sign_in:
                signInAnonymously();
                break;
            case R.id.button_anonymous_sign_out:
                signOut();
                break;
            case R.id.button_link_account:
                linkAccount();
                break;
        }
    }
}
