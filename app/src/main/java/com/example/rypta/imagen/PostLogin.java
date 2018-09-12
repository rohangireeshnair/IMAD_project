package com.example.rypta.imagen;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class PostLogin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_login);

        TextView Name = (TextView)findViewById(R.id.Name);
        TextView followerc = (TextView)findViewById(R.id.followerc);
        TextView followingc = (TextView)findViewById(R.id.followingc);
        ImageView profilepic = (ImageView)findViewById(R.id.profilepic);

        backgroundforpostlogin getobj = new backgroundforpostlogin();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String key = preferences.getString("key", "defaultValue");
        String uname = preferences.getString("uname", "defaultValue");
        getobj.execute(key,uname);

        JSONObject response = null;

        try {
            response = getobj.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        try {
            String name = response.getString("firstname") +" "+ response.getString("lastname");
            String follower = response.getString("count1");
            String following = response.getString("count2");
            String imagestr = response.getString("profilepic");
            byte[] decodestring = android.util.Base64.decode(imagestr, android.util.Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodestring, 0,decodestring.length);
            profilepic.setImageBitmap(decodedByte);
            Name.setText(name);
            followerc.setText(follower);
            followingc.setText(following);




            

        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }


    }
}
