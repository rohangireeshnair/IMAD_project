package com.example.rypta.imagen;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static com.google.android.gms.internal.zzben.NULL;

public class PostLogin extends AppCompatActivity {
    ArrayList<Profile> bitmapArray = new ArrayList<Profile>();

    RecyclerView lv;
    InstaFeed adap;
    String image="";
    String uname;
    Button but,but1;
    Button butter;
    Button logout;

    private DatabaseReference dbr = FirebaseDatabase.getInstance().getReference().getRoot();
    public Bitmap bitmap=null;
    //final TextView lname;
    public void imagechose(){
        startActivityForResult(new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI), 1);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==1 && resultCode == Activity.RESULT_OK) {
            Uri selectedImage = data.getData();

            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            if(bitmap!=null) {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] b = baos.toByteArray();
                image = android.util.Base64.encodeToString(b, android.util.Base64.DEFAULT);
            }
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_login);
        ImageButton searchbutton = (ImageButton)findViewById(R.id.searchb1);
        searchbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PostLogin.this, Search.class);
                startActivity(intent);

            }
        });
        logout = (Button)findViewById(R.id.logout);
        butter = (Button)findViewById(R.id.but);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences1 = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = preferences1.edit();
                editor.remove("key");
                editor.commit();
                Intent ii1 = new Intent(PostLogin.this,MainActivity.class);
                startActivity(ii1);
            }
        });
        final SwipeRefreshLayout refresh = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh.setRefreshing(true);
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(PostLogin.this);
                String key = preferences.getString("key", "defaultValue");
                uname = preferences.getString("uname", "defaultValue");
                ShowImages obj = new ShowImages();
                obj.execute(key, uname, uname);
                JSONObject response1 = new JSONObject();
                try {

                    response1 = obj.get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                try {

                    JSONArray ss = new JSONArray();
                    String size = response1.getString("index");
                    int siz = Integer.parseInt(size);
                    ss = response1.getJSONArray("images");
                    int i;
                    bitmapArray.clear();
                    for (i = 0; i < siz; i++) {

                        String imagestring = ss.getString(i);
                        byte[] decodedstring = android.util.Base64.decode(imagestring, android.util.Base64.DEFAULT);
                        Bitmap decodedB = BitmapFactory.decodeByteArray(decodedstring, 0, decodedstring.length);
                        Profile p = new Profile(decodedB);
                        bitmapArray.add(p);
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

                adap=new InstaFeed(PostLogin.this,bitmapArray);

                lv.setHasFixedSize(true);
                lv.setLayoutManager(new LinearLayoutManager(PostLogin.this,LinearLayoutManager.VERTICAL,false));

                lv.setAdapter(adap);
                refresh.setRefreshing(false);

            }
        });

        TextView Name = (TextView) findViewById(R.id.Name);
       // TextView followerc = (TextView) findViewById(R.id.followerc);
       // TextView followingc = (TextView) findViewById(R.id.followingc);
        ImageView profilepic = (ImageView) findViewById(R.id.profilepic);
        but = (Button) findViewById(R.id.uploadButton);
        but1 = (Button) findViewById(R.id.follow);
        but1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ii=new Intent(PostLogin.this,FollowActivity.class);
                startActivity(ii);
            }
        });
        backgroundforpostlogin getobj = new backgroundforpostlogin();
        ShowImages obj = new ShowImages();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String key = preferences.getString("key", "defaultValue");
        uname = preferences.getString("uname", "defaultValue");
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
        butter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(PostLogin.this,"clicked",Toast.LENGTH_SHORT).show();
                Intent i = new Intent(PostLogin.this, DiscussionActivity.class);
                //i.putExtra("selected_topic", "Android App Development");
                i.putExtra("user_name", uname);
                startActivity(i);
            }
        });
        try {
            String name = response.getString("firstname") +" "+ response.getString("lastname");
            String follower = response.getString("count1");
            String following = response.getString("count2");
            String imagestr = response.getString("profilepic");
            byte[] decodestring = android.util.Base64.decode(imagestr, android.util.Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodestring, 0,decodestring.length);
            profilepic.setImageBitmap(decodedByte);
            Name.setText(name);
         //   followerc.setText(follower);
         //   followingc.setText(following);

            Bitmap myBitMap;
            int i;
            lv= (RecyclerView) findViewById(R.id.recyclerV);
            String size = response1.getString("index");
            int siz=Integer.parseInt(size);
            Log.i("size",size);
            int g=Integer.parseInt(size);
            JSONArray ss= new JSONArray();
            ss=response1.getJSONArray("images");
            bitmapArray.clear();
            for(i=0;i<siz;i++)
            {

                String imagestring = ss.getString(i);
                byte[] decodedstring = android.util.Base64.decode(imagestring, android.util.Base64.DEFAULT);
                Bitmap decodedB = BitmapFactory.decodeByteArray(decodedstring, 0,decodedstring.length);
                Profile p=new Profile(decodedB);
                bitmapArray.add(p);
            }
            ImageButton imageb = (ImageButton)findViewById(R.id.imageButton);
            Log.i("size",bitmapArray.size()+"");
            adap=new InstaFeed(this,bitmapArray);

            lv.setHasFixedSize(true);
            lv.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));

            lv.setAdapter(adap);
            but.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    backgroundforupload back = new backgroundforupload();
                    back.execute(uname,image);
                    JSONObject response11 = null;
                    boolean responseb=false;
                    try {
                        response11 = back.get();
                        responseb=response11.getBoolean("response");
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }

                    if (responseb)
                    {
                        Toast.makeText(PostLogin.this, "Image uploaded successfully", Toast.LENGTH_SHORT).show();
                       // Intent i = new Intent(PostLogin.this, PostLogin.class);
                       // startActivity(i);
                    }
                    else
                    {
                        Toast.makeText(PostLogin.this, "Image not uploaded", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            imageb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    imagechose();

                }
            });
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }


    }
}
