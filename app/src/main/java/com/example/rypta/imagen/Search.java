package com.example.rypta.imagen;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class Search extends AppCompatActivity {

    EditText uname1;
    ImageView profilepic;
    RecyclerView lv;
    InstaFeed adap;
    backgroundforpostlogin getobj;
    ArrayList<Profile> bitmapArray = new ArrayList<Profile>();
    ShowImages obj;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);


       uname1 = (EditText) findViewById(R.id.searchtext);
        final ImageButton searchb = (ImageButton)findViewById(R.id.imageButton4);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
      final  String key = preferences.getString("key", "defaultValue");
       final String uname = preferences.getString("uname", "defaultValue");
       profilepic = (ImageView)findViewById(R.id.profilepic2);
        final TextView Name = (TextView)findViewById(R.id.sname);
        final RelativeLayout relativeLayout = (RelativeLayout)findViewById(R.id.rlayout);
        uname1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_UP){

                    // Do what you want
                    relativeLayout.setVisibility(View.INVISIBLE);
                    return false;
                }

                return true;
            }
        });

        searchb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                profilepic = (ImageView)findViewById(R.id.profilepic2);
                uname1 = (EditText) findViewById(R.id.searchtext);
               // this.Search.setOnGetMessageTask(new ShowImages());
                relativeLayout.setVisibility(View.VISIBLE);
                obj = new ShowImages();
                getobj = new backgroundforpostlogin();
                getobj.execute(key,uname1.getText().toString());
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
                    searchb.clearFocus();
                    InputMethodManager in = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    in.hideSoftInputFromWindow(searchb.getWindowToken(), 0);
                    String name = response.getString("firstname") + " " + response.getString("lastname");
                    Log.i("mass",name+"");
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
