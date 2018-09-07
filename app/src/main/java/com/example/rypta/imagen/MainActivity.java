package com.example.rypta.imagen;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

        uname = (TextView)findViewById(R.id.uname);
        passwd = (TextView)findViewById(R.id.passwd);
        signup = (Button)findViewById(R.id.signupb);
        login = (Button)findViewById(R.id.loginb);

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

                        if (authresponse.getBoolean("response")) {
                           //Successful Authentication


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
