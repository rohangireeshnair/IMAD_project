package com.example.rypta.imagen;

import android.os.AsyncTask;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;



public class background extends AsyncTask <String, Void, JSONObject>{

    @Override
    protected JSONObject doInBackground(String... strings) {
        URL serverurl =null;
        HttpURLConnection req=null;
        String response = "";

        String url = "";
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
        String uname = strings[0];
        String passwd = strings[1];

        JSONObject authj = new JSONObject();
        try {
            authj.put("username", uname);
            authj.put("password", passwd);
        }

        catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            req.setRequestMethod("POST");
            req.setDoInput(true);
            req.setDoOutput(true);
            req.setRequestProperty("Content-Type", "application/json");
            req.setRequestProperty("Accept", "application/json");

        } catch (ProtocolException e) {
            e.printStackTrace();
        }

        DataOutputStream wr = null;
        try {
            wr = new DataOutputStream(req.getOutputStream());
            wr.writeBytes(authj.toString());
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
