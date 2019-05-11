package com.wedo.studybar.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.wedo.studybar.R;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ChangePassword extends AppCompatActivity {

    private TextInputEditText oldPasswordEditText;
    private TextInputEditText newPasswordEditText;
    private TextInputEditText newPasswordConfirmationEditText;
    private MaterialButton buttonCancel;
    private MaterialButton buttonOk;

    private String oldPassword;
    private String newPassword;
    private String newPasswordConfirmagion;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_change);

        oldPasswordEditText = findViewById(R.id.old_password_confirm);
        newPasswordEditText = findViewById(R.id.new_password);
        newPasswordConfirmationEditText = findViewById(R.id.new_password_confirm);
        buttonCancel = findViewById(R.id.change_cancel);
        buttonOk = findViewById(R.id.change_ok);

        sharedPreferences = getSharedPreferences("Login",MODE_PRIVATE);

        newPasswordEditText.setFocusable(false);
        newPasswordEditText.setClickable(true);
        newPasswordEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oldPassword = oldPasswordEditText.getText().toString();
                if (sharedPreferences.getString("Password","").equals(oldPassword)){
                    newPasswordEditText.setFocusableInTouchMode(true);
                    newPasswordEditText.requestFocus();
                    newPasswordEditText.setClickable(false);
                }else {
                    Toast.makeText(getApplicationContext(),R.string.check_your_password,Toast.LENGTH_SHORT).show();
                }
            }
        });

        newPasswordConfirmationEditText.setFocusable(false);
        newPasswordConfirmationEditText.setClickable(true);
        newPasswordConfirmationEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newPassword = newPasswordEditText.getText().toString();
                if(newPassword!=null&&!newPassword.equals("")){
                    newPasswordConfirmationEditText.setFocusableInTouchMode(true);
                    newPasswordConfirmationEditText.requestFocus();
                    newPasswordConfirmationEditText.setClickable(false);
                }else {
                    Toast.makeText(getApplicationContext(),R.string.password_null,Toast.LENGTH_SHORT).show();
                }
            }
        });

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newPasswordConfirmagion = newPasswordConfirmationEditText.getText().toString();
                if (newPassword.equals(newPasswordConfirmagion)){
                    try {
                        JSONObject base = new JSONObject();
                        base.put("password",newPassword);
                        new postAsyncTask().execute(base.toString());
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }else {
                    Toast.makeText(getApplicationContext(),R.string.check_your_intput,Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private class postAsyncTask extends AsyncTask<String,Void,String>{

        HttpURLConnection urlConnection = null;
        String response;

        @Override
        protected String doInBackground(String... strings) {
            try {
                URL url = new URL("http://39.97.181.175:8080/study/user_UpdatePass.action");

                urlConnection = (HttpURLConnection)url.openConnection();
                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                urlConnection.setRequestProperty("Accept","application/json");
                urlConnection.setRequestProperty("cookie",sharedPreferences.getString("SESSION_ID",""));
                urlConnection.setDoOutput(true);
                urlConnection.setDoInput(true);

                DataOutputStream dataOutputStream = new DataOutputStream(urlConnection.getOutputStream());
                dataOutputStream.writeBytes(strings[0]);

                dataOutputStream.flush();
                dataOutputStream.close();
                BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                String decodedString;
                StringBuilder stringBuilder = new StringBuilder();
                while ((decodedString = in.readLine()) != null) {
                    stringBuilder.append(decodedString);
                }
                in.close();
                //YOUR RESPONSE
                response = stringBuilder.toString();

                urlConnection.disconnect();
            }catch (Exception e){
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            String result="";
            try {
                JSONObject base = new JSONObject(response);

                Log.e("JSON",base.toString());

                result = base.getString("result");
                if (result.matches("success")){
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("LoginState",false);
                    editor.putString("Password","");
                    editor.apply();
                    finish();
                    Intent outIntent = new Intent(getApplicationContext(),
                            MainActivity.class);
                    outIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
                            | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(outIntent);
                    Toast.makeText(getApplicationContext(),R.string.plz_login_again,Toast.LENGTH_SHORT).show();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }


}
