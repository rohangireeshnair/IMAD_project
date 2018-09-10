package com.example.rypta.imagen;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class PostLogin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_login);

        TextView Name = (TextView)findViewById(R.id.Name);
        TextView followerc = (TextView)findViewById(R.id.followerc);
        TextView followingc = (TextView)findViewById(R.id.followingc);
        ImageView profilepic = (ImageView)findViewById(R.id.profilepic);



    }
}
