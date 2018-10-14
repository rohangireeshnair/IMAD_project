package com.example.rypta.imagen;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class FollowActivity extends AppCompatActivity {

    RecyclerView lv;
    InstaFeed2 adap;
    backgroundforfollow obj;
    ArrayList<Student> array=new ArrayList<Student>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("TAG","hello");
        setContentView(R.layout.activity_follow);
        obj = new backgroundforfollow();
        obj.execute();
        JSONObject response = null;
        try {
            response = obj.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        try{
            lv = (RecyclerView) findViewById(R.id.recyclerV);
            String size = response.getString("index");
            int siz = Integer.parseInt(size);
            JSONArray ss = new JSONArray();
            ss = response.getJSONArray("images");
            JSONArray ss1 = new JSONArray();
            ss1 = response.getJSONArray("firstname");
            JSONArray ss2 = new JSONArray();
            ss2 = response.getJSONArray("lastname");
            JSONArray ss3 = new JSONArray();
            ss3 = response.getJSONArray("username");

            array.clear();
            for (int i = 0; i < siz; i++) {

                String imagestring = ss.getString(i);

                String name = ss1.getString(i) + " " + ss2.getString(i);
                String username = ss3.getString(i);
                byte[] decodedstring = android.util.Base64.decode(imagestring, android.util.Base64.DEFAULT);
                Bitmap decodedB = BitmapFactory.decodeByteArray(decodedstring, 0, decodedstring.length);
                Student p = new Student(decodedB,username,name);
                array.add(p);
            }
        }
        catch(JSONException e)
        {
            e.printStackTrace();
        }


        lv = (RecyclerView) findViewById(R.id.recyclerview);
        adap = new InstaFeed2(FollowActivity.this, array);
        lv.setHasFixedSize(true);
        lv.setLayoutManager(new LinearLayoutManager(FollowActivity.this,LinearLayoutManager.VERTICAL,false));

        lv.setAdapter(adap);

    }
}
