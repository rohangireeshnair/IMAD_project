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
    public String image = "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";

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
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100, baos);
        byte[] b = baos.toByteArray();

        image = Base64.encodeToString(b,Base64.DEFAULT);

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





        Button registerb = (Button)findViewById(R.id.registerb);
        ImageButton imageb = (ImageButton)findViewById(R.id.imageButton2);

        registerb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fname.getText().toString().isEmpty() || lname.getText().toString() .isEmpty() || passwd.getText().toString().isEmpty()|| email.getText().toString().isEmpty()) {
                    Toast.makeText(Register.this, "Insert all the details", Toast.LENGTH_SHORT).show();
                } else {
                    backgroundforregister back = new backgroundforregister();
                    back.execute(fname.getText().toString(), lname.getText().toString(), uname.getText().toString(), passwd.getText().toString(), email.getText().toString(), image);
                    JSONObject response = null;
                    boolean responseb = false;
                    boolean check = false;

                    try {
                        response = back.get();
                        check=response.getBoolean("index");
                        responseb = response.getBoolean("response");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if(!check)
                    {
                        Toast.makeText(Register.this, "Username Already taken", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        if (responseb) {
                            Toast.makeText(Register.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(Register.this, MainActivity.class);
                            startActivity(i);
                        } else {
                            Toast.makeText(Register.this, "Registration Failed! Please try later", Toast.LENGTH_SHORT).show();
                        }
                    }
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
}
