package com.nuan_nuan.parse_test;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.nuan_nuan.parse_test.utils.Logger;
import com.nuan_nuan.parse_test.utils.json.Json;
import com.parse.GetCallback;
import com.parse.LogInCallback;
import com.parse.ParseAnonymousUtils;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    private Button mButton;
    private Button Login;
    private Button setting_user_now;
    private EditText usernames;
    private EditText passwords;
    private Button anonymous;
    private Button Retrievings;
    private Button remove;
    private Button update;
    private Button file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        usernames = (EditText) findViewById(R.id.username);
        passwords = (EditText) findViewById(R.id.pwd);
        mButton = (Button) findViewById(R.id.create);
        Login = (Button) findViewById(R.id.login);
        setting_user_now = (Button) findViewById(R.id.setting_user);
        anonymous = (Button) findViewById(R.id.ann);
        Retrievings = (Button) findViewById(R.id.retrieving);
        remove = (Button) findViewById(R.id.remove);
        update = (Button) findViewById(R.id.update);
        file = (Button) findViewById(R.id.file);

        //注册
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernames.getText().toString();
                String password = passwords.getText().toString();

                //trims the spaces
                username = username.trim();
                password = password.trim();


                //if one of the text edits is empty send them a message with a title with an ok button.
                if (username.isEmpty() || password.isEmpty()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setMessage("")
                            .setTitle("")
                            .setPositiveButton(android.R.string.ok, null);

                    AlertDialog dialog = builder.create();
                    dialog.show();
                } else {
                    setProgressBarIndeterminateVisibility(true);//the progress circle is active.

                    //creating a new parse user.
                    ParseUser pUser = new ParseUser();
                    pUser.setUsername(username);
                    pUser.setPassword(password);

                    Logger.json(TAG, Json.get().toJson(pUser));

                    pUser.signUpInBackground(new SignUpCallback() {
                        @Override
                        public void done(ParseException e) {
                            setProgressBarIndeterminateVisibility(false);//the progress circle is not active.
                            if (e == null) {
                                Intent intent = new Intent(MainActivity.this, LoginManagerActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                                builder.setMessage(e.getMessage())
                                        .setTitle("")
                                        .setPositiveButton(android.R.string.ok, null);

                                AlertDialog dialog = builder.create();
                                dialog.show();
                            }
                        }
                    });

                }
            }
        });
        //登录
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseUser.logInInBackground("nuannuan", "nuannuan", new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException e) {
                        if (user != null) {
                            Logger.i(TAG, "login is ok");
                            Intent intent = new Intent(MainActivity.this, LoginManagerActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        } else {
                            Logger.i(TAG, "login error " + "  " + e.getCode() + "  " + e.getMessage());
                        }
                    }
                });
            }
        });
        //匿名登录
        anonymous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  ParseAnonymousUtils
                ParseAnonymousUtils.logIn(new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException e) {
                        if (e != null) {
                            Logger.e(TAG, "Anonymous login failed.");
                        } else {
                            Logger.d(TAG, "Anonymous user logged in");
                            if (ParseAnonymousUtils.isLinked(ParseUser.getCurrentUser())) {
                                user.put("username", "nuannuan");
                                user.put("password", "nuannuan");
                                user.saveInBackground(new SaveCallback() {
                                    @Override
                                    public void done(ParseException e) {
                                        if (e != null) {
                                            Logger.d(TAG, e.getMessage());
                                        } else {
                                            Logger.d(TAG, "-----");
                                        }
                                    }
                                });
                            } else {
                                Logger.d(TAG, "--------");
                            }
                        }
                    }
                });
            }
        });
        //
        Retrievings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseQuery<ParseObject> query = ParseQuery.getQuery("kk");
                //
                query.fromLocalDatastore(); //Retrieving Objects from the Local Datastore use this line

                query.getInBackground("m8Lo948lV8", new GetCallback<ParseObject>() {
                    @Override
                    public void done(ParseObject object, ParseException e) {
                        if (e == null) {
                            Logger.d(TAG, "" + object.get("names"));
                        } else {
                            Logger.d(TAG, "" + e.getMessage() + "---" + e.getCode());
                        }
                    }
                });
            }
        });
        //update
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseQuery<ParseObject> query = ParseQuery.getQuery("kk");
                // Retrieve the object by id
                query.getInBackground("m8Lo948lV8", new GetCallback<ParseObject>() {
                    public void done(ParseObject object, ParseException e) {
                        if (e == null) {
                            // Now let's update it with some new data. In this case, only cheatMode and score
                            // will get sent to the Parse Cloud. playerName hasn't changed.
                            object.put("score", 1338);
                            object.put("cheatMode", true);
                            object.saveInBackground(new SaveCallback() {
                                @Override
                                public void done(ParseException e) {
                                    if (e != null) {
                                        Logger.d(TAG, "" + e.getMessage() + "---" + e.getCode());
                                    } else {
                                        Logger.i(TAG, "-----");
                                    }
                                }
                            });
                        }
                    }
                });
            }
        });
        //remove
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseQuery<ParseObject> querys = ParseQuery.getQuery("kk");
                // Retrieve the object by id
                querys.getInBackground("m8Lo948lV8", new GetCallback<ParseObject>() {
                    public void done(ParseObject object, ParseException e) {
                        if (e == null) {
                            object.remove("score"); //remove
                            object.saveInBackground(new SaveCallback() {
                                @Override
                                public void done(ParseException e) {
                                    if (e != null) {
                                        Logger.d(TAG, "" + e.getMessage() + "---" + e.getCode());
                                    } else {
                                        Logger.i(TAG, "-----");
                                    }
                                }
                            });
                        }
                    }
                });
            }
        });
        file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                byte[] data = "Working at Parse is great!".getBytes();
                ParseFile file = new ParseFile("resume.txt", data);
                file.saveInBackground();
            }
        });


    }

}