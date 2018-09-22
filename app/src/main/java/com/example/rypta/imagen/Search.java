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
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class Search extends AppCompatActivity {

    RecyclerView lv;
    InstaFeed adap;
    ArrayList<Profile> bitmapArray = new ArrayList<Profile>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
       final ShowImages obj = new ShowImages();
       final backgroundforpostlogin getobj = new backgroundforpostlogin();
       final TextView uname1 = (TextView)findViewById(R.id.searchtext);
        ImageButton searchb = (ImageButton)findViewById(R.id.imageButton4);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
      final  String key = preferences.getString("key", "defaultValue");
       final String uname = preferences.getString("uname", "defaultValue");
       final ImageView profilepic = (ImageView)findViewById(R.id.profilepic2);
        final TextView Name = (TextView)findViewById(R.id.sname);
        searchb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getobj.execute(key,uname);
                obj.execute(key,uname,uname1.getText().toString());
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
                    String name = response.getString("firstname") + " " + response.getString("lastname");
//                    String follower = response.getString("count1");
//                    String following = response.getString("count2");
                    String imagestr = response.getString("profilepic");
                    byte[] decodestring = android.util.Base64.decode(imagestr, android.util.Base64.DEFAULT);
                    Bitmap decodedByte = BitmapFactory.decodeByteArray(decodestring, 0, decodestring.length);
                    profilepic.setImageBitmap(decodedByte);
                    Name.setText(name);
//                    followerc.setText(follower);
//                    followingc.setText(following);

                    Bitmap myBitMap;
                    int i;
                    lv = findViewById(R.id.recyclerV);
                    String size = response1.getString("index");
                    int siz = Integer.parseInt(size);
                    Log.i("size", size);
                    int g = Integer.parseInt(size);
                    JSONArray ss = new JSONArray();
                    ss = response1.getJSONArray("images");
                    bitmapArray.clear();
                    for (i = 0; i < siz; i++) {

                        String imagestring = ss.getString(i);
                        byte[] decodedstring = android.util.Base64.decode(imagestring, android.util.Base64.DEFAULT);
                        Bitmap decodedB = BitmapFactory.decodeByteArray(decodedstring, 0, decodedstring.length);
                        Profile p = new Profile(decodedB);
                        bitmapArray.add(p);
                    }
                }
                    catch(JSONException e)
                    {
                        e.printStackTrace();
                    }


                    lv = findViewById(R.id.listid);
        adap = new InstaFeed(Search.this, bitmapArray);
        lv.setHasFixedSize(true);
        lv.setLayoutManager(new LinearLayoutManager(Search.this,LinearLayoutManager.VERTICAL,false));

        lv.setAdapter(adap);


            }
        });

    }
}
