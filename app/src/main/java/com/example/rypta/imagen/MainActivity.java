package com.example.rypta.imagen;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;



public class MainActivity extends AppCompatActivity {

    methods obj = new methods();
    @Override
    protected void onCreate(Bundle savedInstanceState) throws {
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
                try {
                    obj.authenticate(uname.toString(), passwd.toString());

                }
                catch (java.lang.Exception e)
                {
                    e.printStackTrace();
                }

                }
        });



    }
}
