package com.wedo.studybar.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.Toast;

import com.wedo.studybar.R;
import com.wedo.studybar.activities.MyBooksActivity;
import com.wedo.studybar.activities.MyDiscussionsActivity;
import com.wedo.studybar.activities.SettingsActivity;
import com.wedo.studybar.activities.SignUpActivity;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class UserFragment extends Fragment {

    private static final String LOG_TAG = Context.class.getSimpleName();

    //private Switch switchChangeLayout;
    private LinearLayout loggedInUser;
    private LinearLayout logInLayout;
    //private Boolean isChecked;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button buttonSignIn;
    private ProgressBar progressBar;

    private String email = "";
    private String password = "";
    private String response = "";
    private String check = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_user,container,false);

        loggedInUser = (LinearLayout)rootView.findViewById(R.id.logged_in_user);
        logInLayout = (LinearLayout)rootView.findViewById(R.id.login_user);
        progressBar = rootView.findViewById(R.id.login_progress);
        //switchChangeLayout = (Switch) rootView.findViewById(R.id.switch_change_layout);

        editTextEmail = rootView.findViewById(R.id.email_address);
        editTextPassword = rootView.findViewById(R.id.login_user_password);
        buttonSignIn = rootView.findViewById(R.id.sign_in_button);

        //第一次进入需要登陆
        loggedInUser.setVisibility(View.GONE);
        logInLayout.setVisibility(View.VISIBLE);

        //sign in
        buttonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = editTextEmail.getText().toString();
                password = editTextPassword.getText().toString();
                logInLayout.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                //verify();
                new verifyAsyncTask().execute();
            }
        });

        //sign up
        Button buttonSignUp = rootView.findViewById(R.id.sign_up_button);
        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signupIntent = new Intent(getActivity(), SignUpActivity.class);
                startActivity(signupIntent);
            }
        });


        //for logged in user
        Button myBooksButton = rootView.findViewById(R.id.my_books);
        myBooksButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MyBooksActivity.class);
                startActivity(intent);
            }
        });

        Button myDiscussionsButton = rootView.findViewById(R.id.my_discussions);
        myDiscussionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MyDiscussionsActivity.class);
                startActivity(intent);
            }
        });

        Button settingsButton = (Button) rootView.findViewById(R.id.settings_button);
        settingsButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent settingsIntent = new Intent(getActivity(), SettingsActivity.class);

                startActivity(settingsIntent);
            }
        });

        return rootView;
    }

    public UserFragment(){

    }

    private void verify(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    URL url = new URL("http://39.97.181.175:8080/study/user_Login.action");
                    HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                    conn.setRequestProperty("Accept","application/json");
                    conn.setDoOutput(true);
                    conn.setDoInput(true);

                    JSONObject user = new JSONObject();
                    user.put("username",email);
                    user.put("password",password);

                    DataOutputStream dataOutputStream = new DataOutputStream(conn.getOutputStream());
                    dataOutputStream.writeBytes(user.toString());

                    Log.e(LOG_TAG,user.toString());

                    dataOutputStream.flush();
                    dataOutputStream.close();

                    BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    String decodedString;
                    StringBuilder stringBuilder = new StringBuilder();
                    while ((decodedString = in.readLine()) != null) {
                        stringBuilder.append(decodedString);
                    }
                    in.close();
                    /*YOUR RESPONSE */
                    response = stringBuilder.toString();

                    JSONObject base = new JSONObject(response);
                    //JSONObject result = base.getJSONObject("result");
                    check = base.getString("result");

                    Log.e("STATUS", String.valueOf(conn.getResponseCode()));
                    Log.e("MSG" , conn.getResponseMessage());
                    Log.e("RESULT",response);
                    conn.disconnect();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        if(check.equals("fail")){
            Toast.makeText(getActivity(),R.string.login_failed,Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(getActivity(),"ok",Toast.LENGTH_SHORT).show();
            loggedInUser.setVisibility(View.VISIBLE);
            logInLayout.setVisibility(View.GONE);
        }
    }

    private class verifyAsyncTask extends AsyncTask<Void,Void,String>{


        @Override
        protected String doInBackground(Void... voids) {
            try{
                URL url = new URL("http://39.97.181.175:8080/study/user_Login.action");
                HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                conn.setRequestProperty("Accept","application/json");
                conn.setDoOutput(true);
                conn.setDoInput(true);

                JSONObject user = new JSONObject();
                user.put("username",email);
                user.put("password",password);

                DataOutputStream dataOutputStream = new DataOutputStream(conn.getOutputStream());
                dataOutputStream.writeBytes(user.toString());

                Log.e(LOG_TAG,user.toString());

                dataOutputStream.flush();
                dataOutputStream.close();

                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String decodedString;
                StringBuilder stringBuilder = new StringBuilder();
                while ((decodedString = in.readLine()) != null) {
                    stringBuilder.append(decodedString);
                }
                in.close();
                /*YOUR RESPONSE */
                response = stringBuilder.toString();

                JSONObject base = new JSONObject(response);
                //JSONObject result = base.getJSONObject("result");
                check = base.getString("result");

                Log.e("STATUS", String.valueOf(conn.getResponseCode()));
                Log.e("MSG" , conn.getResponseMessage());
                Log.e("RESULT",check);
                conn.disconnect();
            }catch (Exception e){
                e.printStackTrace();
            }
            return check;
        }

        @Override
        protected void onPostExecute(String response) {
            progressBar.setVisibility(View.GONE);
            if(response.equals("fail")){
                logInLayout.setVisibility(View.VISIBLE);
                Toast.makeText(getActivity(),R.string.login_failed,Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(getActivity(),"ok",Toast.LENGTH_SHORT).show();
                loggedInUser.setVisibility(View.VISIBLE);
            }
        }
    }
}
