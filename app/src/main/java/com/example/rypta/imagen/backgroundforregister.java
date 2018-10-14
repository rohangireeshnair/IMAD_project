package com.example.rypta.imagen;

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

public class backgroundforregister extends AsyncTask<String,Void,JSONObject> {

    @Override
    protected JSONObject doInBackground(String... strings) {

        URL serverurl = null;
        HttpURLConnection req = null;
        String response = "";

        String url = "http://192.168.137.1/Register.php";
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

        String fname = strings[0];
        String lname = strings[1];
        String uname = strings[2];
        String passwd = strings[3];
        String email = strings[4];
        String image = strings[5];

        JSONObject json = new JSONObject();

        try {
            json.put("username", uname);
            json.put("password", passwd);
            json.put("profilepic", image);
            json.put("firstname",fname);
            json.put("lastname", lname);
            json.put("email", email);


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
            Log.i("mseg",jsonr);
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
