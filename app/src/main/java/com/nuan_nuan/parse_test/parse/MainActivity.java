package com.nuan_nuan.parse_test.parse;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.nuan_nuan.parse_test.R;
import com.nuan_nuan.parse_test.utils.Logger;
import com.nuan_nuan.parse_test.utils.json.Json;
import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.LogInCallback;
import com.parse.ParseAnonymousUtils;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.RequestPasswordResetCallback;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

import java.io.ByteArrayOutputStream;

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
    private Button restpwd;
    private Button queriesFile;


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
        restpwd = (Button) findViewById(R.id.restpwd);
        queriesFile = (Button) findViewById(R.id.queriesFile);

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
                    pUser.setEmail(username);
                    pUser.setPassword(password);
                    pUser.setUsername("测试,测试");

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
                // Locate the image in res > drawable-hdpi
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
                        R.mipmap.ic_launcher);
                // Convert it to byte
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                // Compress image to lower quality scale 1 - 100
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] image = stream.toByteArray();
                ParseFile file = new ParseFile("androidbegin.png", image);

                file.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e != null) {
                            Logger.d(TAG, e.getMessage() + "---" + e.getCode());
                        } else {
                            Logger.d(TAG, "++++ ok");
                            ParseObject jobApplication = new ParseObject("testfile");
                            jobApplication.put("applicantName", "kevin");
                            jobApplication.put("applicantFile", file);
                            jobApplication.saveInBackground();
                        }
                    }
                });
            }
        });
        //query file is ok
        queriesFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("testfile");
                query.getInBackground("UqlUiY0UZY", new GetCallback<ParseObject>() {
                    @Override
                    public void done(ParseObject object, ParseException e) {
                        if (e == null) {
                            ParseFile file = object.getParseFile("applicantFile");
                            if (file == null) {
                                return;
                            }
                            file.getDataInBackground(new GetDataCallback() {
                                @Override
                                public void done(byte[] data, ParseException e) {
                                    if (e == null) {
                                        if (data.length == 0) {
                                            return;
                                        }
                                        Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                                        ImageView image = (ImageView) findViewById(R.id.imageView);
                                        if (image != null) {
                                            image.setImageBitmap(bitmap);
                                        }
                                        Logger.d(TAG, "is ok");
                                    }
                                }
                            });
                        } else {
                            Logger.d(TAG, "-------");
                        }
                    }
                });
            }
        });
        restpwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseUser.requestPasswordResetInBackground("809997202@qq.com", new RequestPasswordResetCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            Logger.d(TAG, "is send ok ");
                        } else {
                            Logger.d(TAG, e.getMessage() + "" + e.getCode());    // get error code 1 parse error some warning
                        }
                    }
                });
            }
        });

    }

}