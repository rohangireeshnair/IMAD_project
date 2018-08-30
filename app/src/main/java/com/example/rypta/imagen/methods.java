package com.example.rypta.imagen;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.*;

public class methods {
    String myurl = "http://www.google.com ";

    public void authenticate(String uname, String passwd) throws Exception
    {
        URL serverurl = new URL(myurl);
        HttpURLConnection req = (HttpURLConnection)serverurl.openConnection();
        req.setRequestMethod("POST");
        req.setDoInput(true);
        req.setDoOutput(true);
        req.setRequestProperty("Content-Type", "application/json");
        req.setRequestProperty("Accept", "application/json");

        JSONObject auth = new JSONObject();
        auth.put("username", uname);
        auth.put("password", passwd);

        DataOutputStream wr = new DataOutputStream(req.getOutputStream());
        wr.writeBytes(auth.toString());
        wr.flush();

        if(req.getResponseCode()==200)
        {

        }

    }
}
