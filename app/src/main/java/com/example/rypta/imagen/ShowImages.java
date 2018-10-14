package com.example.rypta.imagen;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
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

public class ShowImages extends AsyncTask <String,Void,JSONObject> {

    @Override
    protected JSONObject doInBackground(String... strings) {
        URL serverurl =null;
        HttpURLConnection req=null;
        String response = "";

        String url = "http://192.168.137.1/GetImages.php";
        try {
            serverurl = new URL(url);
        }
        catch (java.net.MalformedURLException e)
        {
            e.printStackTrace();
        }

        try {
            req = (HttpURLConnection) serverurl.openConnection();
        }
        catch (java.io.IOException e)
        {
            e.printStackTrace();
        }

        String key = strings[0];
        String uname = strings[1];
        String uname2= strings[2];
        JSONObject puller = new JSONObject();


        try {
            puller.put("key", key);
            puller.put("uname",uname);
            puller.put("uname1",uname2);

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

        String json = null;
        try {


            json = URLEncoder.encode("json", "UTF-8") + "=" + URLEncoder.encode(puller.toString(), "UTF-8");
        }

        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }

        DataOutputStream wr = null;
        try {
            wr = new DataOutputStream(req.getOutputStream());
            wr.writeBytes(json);
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

            req.disconnect();

            Log.i("reuest",response);

            respj = new JSONObject(response);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return respj;
    }

    @Override
    protected void onPostExecute(JSONObject s) {
        super.onPostExecute(s);
    }
}
