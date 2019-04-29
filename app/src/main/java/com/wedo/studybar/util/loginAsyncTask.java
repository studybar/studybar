package com.wedo.studybar.util;

import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public abstract class loginAsyncTask extends AsyncTask<String,Void,String> {
    //String check;
    String response;
    @Override
    protected String doInBackground(String... strings) {
        try{
            URL url = new URL("http://39.97.181.175:8080/study/user_Login.action");
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            conn.setRequestProperty("Accept","application/json");
            conn.setDoOutput(true);
            conn.setDoInput(true);

            JSONObject user = new JSONObject(strings[0]);

            DataOutputStream dataOutputStream = new DataOutputStream(conn.getOutputStream());
            dataOutputStream.writeBytes(user.toString());

            dataOutputStream.flush();
            dataOutputStream.close();

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String decodedString;
            StringBuilder stringBuilder = new StringBuilder();
            while ((decodedString = in.readLine()) != null) {
                stringBuilder.append(decodedString);
            }
            in.close();
            //YOUR RESPONSE
            response = stringBuilder.toString();

            //JSONObject base = new JSONObject(response);
            //check = base.getString("result");

            Log.e("STATUS", String.valueOf(conn.getResponseCode()));
            Log.e("MSG" , conn.getResponseMessage());
            //Log.e("RESULT",check);
            conn.disconnect();
            //Log.e("RESPONSE",response);
        }catch (Exception e){
            e.printStackTrace();
        }
        return response;
    }
}
