package com.example.rypta.imagen;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow(). requestFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        TextView uname, passwd;
        Button signup, login;
        


    }
}
