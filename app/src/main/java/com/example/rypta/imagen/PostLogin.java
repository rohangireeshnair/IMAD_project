package com.example.rypta.imagen;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

    RecyclerView lv;
    InstaFeed adap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_login);

        TextView Name = findViewById(R.id.Name);
        TextView followerc = findViewById(R.id.followerc);
        TextView followingc = findViewById(R.id.followingc);
        ImageView profilepic = findViewById(R.id.profilepic);

        backgroundforpostlogin getobj = new backgroundforpostlogin();
        ShowImages obj = new ShowImages();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String key = preferences.getString("key", "defaultValue");
        String uname = preferences.getString("uname", "defaultValue");
        getobj.execute(key,uname);
        obj.execute(key,uname,uname);
        JSONObject response = null;
        JSONObject response1 = null;
        try {
            response = getobj.get();
            response1 = obj.get();
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
            ArrayList<Profile> bitmapArray = new ArrayList<Profile>();
            Bitmap myBitMap;
            int i;
            lv=findViewById(R.id.recyclerV);
            String size = response1.getString("index");
            int siz=Integer.parseInt(size);
            Log.i("size",size);
            for(i=0;i<siz;i++)
            {

                String imagestring = response1.getString("images");
                byte[] decodedstring = android.util.Base64.decode(imagestring, android.util.Base64.DEFAULT);
                Bitmap decodedB = BitmapFactory.decodeByteArray(decodedstring, 0,decodedstring.length);
                Profile p=new Profile(decodedB);
                bitmapArray.add(p);
            }
            Log.i("size",bitmapArray.size()+"");
            adap=new InstaFeed(this,bitmapArray);

            lv.setHasFixedSize(true);
            lv.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));

            lv.setAdapter(adap);
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }


    }
}
