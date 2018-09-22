package com.example.rypta.imagen;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;

public class backgroundforupload extends AsyncTask<String,Void,JSONObject> {

    @Override
    protected JSONObject doInBackground(String... strings) {

        URL serverurl = null;
        HttpURLConnection req = null;
        String response = "";

        String url = "http://192.168.137.1/Upload.php";
        try {
            serverurl = new URL(url);
        } catch (java.net.MalformedURLException e) {
            e.printStackTrace();
        }

        try {
            req = (HttpURLConnection) serverurl.openConnection();
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }

        //  String fname = strings[0];
        //  String lname = strings[1];
        String uname = strings[0];
        //   String passwd = strings[3];
        //   String email = strings[4];
        String image = strings[1];
        //   Log.i("chutiya",image);
        JSONObject json = new JSONObject();
        Log.i("uname",uname);
        Log.i("image",image);
        try {
            json.put("username", uname);
            // json.put("password", passwd);
            json.put("uploadpic", image);
            // json.put("firstname",fname);
            //json.put("lastname", lname);
            // json.put("email", email);


        } catch (JSONException e) {
            e.printStackTrace();
        }


        try {
            req.setRequestMethod("POST");
            req.setDoInput(true);
            req.setDoOutput(true);


        } catch (ProtocolException e) {
            e.printStackTrace();
        }

        String jsonr = null;
        try {


            jsonr = URLEncoder.encode("json", "UTF-8") + "=" + URLEncoder.encode(json.toString(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        DataOutputStream wr = null;
        try {
            wr = new DataOutputStream(req.getOutputStream());
            wr.writeBytes(jsonr);
            wr.flush();
            wr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        JSONObject respj = null;
        try {


            if (req.getResponseCode() == 200) {
                InputStream inp = req.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inp, "iso-8859-1"));

                String line = "";

                while ((line = bufferedReader.readLine()) != null) {

                    response += line;
                }
                bufferedReader.close();
                inp.close();
            }

            Log.i("chutiya2",response);
            req.disconnect();


            respj = new JSONObject(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return respj;


    }
    @Override
    protected void onPostExecute(JSONObject s) {
        super.onPostExecute(s);
    }
}