package com.example.rypta.imagen;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow(). requestFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        final TextView uname, passwd;
        Button signup, login;

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String key = preferences.getString("key", null);
        if(key!=null)
        Log.i("mssg",key);
        if(key!=null)
        {
            Intent i = new Intent(MainActivity.this,PostLogin.class);
            startActivity(i);
        }
        uname = (TextView) findViewById(R.id.uname);
        passwd = (TextView) findViewById(R.id.passwd);
        uname.setText("");
        passwd.setText("");
        signup = (Button) findViewById(R.id.signupb);
        login = (Button) findViewById(R.id.loginb);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Register.class);
                startActivity(intent);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    background auth = new background();
                    auth.execute(uname.getText().toString(), passwd.getText().toString());

                    JSONObject authresponse = null;
                    try {

                         authresponse = auth.get();
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                    try {
                        boolean g=authresponse.getBoolean("response");
                        Log.i("bool","hello");
                        if (authresponse.getBoolean("response")) {
                            Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                            String key = authresponse.getString("key");
                            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putString("key",key);
                            editor.putString("uname",uname.getText().toString());
                            editor.apply();
                            Intent PostloginIntent = new Intent(MainActivity.this, PostLogin.class);
                            PostloginIntent.putExtra("key",key);
                            startActivity(PostloginIntent);


                        }
                       else
                        {
                            Toast.makeText(MainActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                        }

                    }
                    catch (Exception e )
                    {
                        e.printStackTrace();
                    }
                }

                });



    }
}