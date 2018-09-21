package com.example.rypta.imagen;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Register extends AppCompatActivity {
    public  Bitmap bitmap = null;

    public void imagechose(){
        startActivityForResult(new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI), 3);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==3 && resultCode == Activity.RESULT_OK) {
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
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final TextView fname = (TextView)findViewById(R.id.fname);
        final TextView lname = (TextView)findViewById(R.id.lname);
        final TextView email = (TextView)findViewById(R.id.email);
        final TextView uname = (TextView)findViewById(R.id.uname);
        final TextView passwd = (TextView)findViewById(R.id.passwd);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100, baos);
        byte[] b = baos.toByteArray();

        final String image = Base64.encodeToString(b,Base64.DEFAULT);



        Button registerb = (Button)findViewById(R.id.registerb);
        ImageButton imageb = (ImageButton)findViewById(R.id.imageButton2);

        registerb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backgroundforregister back = new backgroundforregister();
                back.execute(fname.getText().toString(), lname.getText().toString(), uname.getText().toString(), passwd.getText().toString(), email.getText().toString(), image);
                JSONObject response = null;
                boolean responseb=false;
                try {
                    response = back.get();
                    responseb=response.getBoolean("response");
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

                if (responseb)
                {
                    Toast.makeText(Register.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(Register.this, MainActivity.class);
                    startActivity(i);
                }
                else
                {
                    Toast.makeText(Register.this, "Registration Failed! Please try later", Toast.LENGTH_SHORT).show();
                }
            }
        });





                imageb.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {




                    }
                });

    }
}
