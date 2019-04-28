package com.wedo.studybar.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
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

    private LinearLayout loggedInUser;
    private LinearLayout logInLayout;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button buttonSignIn;
    private ProgressBar progressBar;

    private ImageView avatar;
    private TextView username;
    private TextView bio;

    private String email = "";
    private String password = "";
    private String response = "";
    private String check = "";
    private JSONObject base;

    private SharedPreferences sharedPreferences;

    private JSONObject user;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_user,container,false);

        loggedInUser = (LinearLayout)rootView.findViewById(R.id.logged_in_user);
        logInLayout = (LinearLayout)rootView.findViewById(R.id.login_user);
        progressBar = rootView.findViewById(R.id.login_progress);

        editTextEmail = rootView.findViewById(R.id.email_address);
        editTextPassword = rootView.findViewById(R.id.login_user_password);
        buttonSignIn = rootView.findViewById(R.id.sign_in_button);

        avatar = rootView.findViewById(R.id.user_avatar);
        username = rootView.findViewById(R.id.user_name);
        bio = rootView.findViewById(R.id.user_bio);

        sharedPreferences = getActivity().getSharedPreferences("Login",Context.MODE_PRIVATE);

        SharedPreferences share = getActivity().getSharedPreferences("Login",Context.MODE_PRIVATE);

        if(share.getBoolean("LoginState",false)){
            logInLayout.setVisibility(View.GONE);
            loggedInUser.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
            //todo:set avatar
            //username.setText(share.getString("Username",""));
            //bio.setText(share.getString("Bio",""));

            editTextEmail.setText(share.getString("Email",""));
            editTextPassword.setText(share.getString("Password",""));

            try {
                user = new JSONObject();
                user.put("username",share.getString("Email",""));
                user.put("password",share.getString("Password",""));
                new verifyAsyncTask().execute(user.toString());
            }catch (Exception e){
                e.printStackTrace();
            }
        }else {
            //第一次进入需要登陆
            loggedInUser.setVisibility(View.GONE);
            logInLayout.setVisibility(View.VISIBLE);
            editTextEmail.setText(share.getString("Email",""));
            editTextPassword.setText(share.getString("Password",""));
        }

        //sign in
        buttonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = editTextEmail.getText().toString();
                password = editTextPassword.getText().toString();
                if(email.equals("")){
                    Toast.makeText(getActivity(),R.string.email_null,Toast.LENGTH_SHORT).show();
                }else if(password.equals("")){
                    Toast.makeText(getActivity(),R.string.password_null,Toast.LENGTH_SHORT).show();
                }else{
                    logInLayout.setVisibility(View.GONE);
                    progressBar.setVisibility(View.VISIBLE);
                    try {
                        user = new JSONObject();
                        user.put("username",email);
                        user.put("password",password);
                        new verifyAsyncTask().execute(user.toString());
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
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
                try {

                    Intent intent = new Intent(getActivity(), MyDiscussionsActivity.class);
                    intent.putExtra("DISCUSS",base.getJSONObject("user").getJSONArray("userTopics").toString());
                    startActivity(intent);
                }catch (Exception e){
                    e.printStackTrace();
                }

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

    private class verifyAsyncTask extends AsyncTask<String,Void,String>{


        @Override
        protected String doInBackground(String... string) {
            try{
                URL url = new URL("http://39.97.181.175:8080/study/user_Login.action");
                HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                conn.setRequestProperty("Accept","application/json");
                conn.setDoOutput(true);
                conn.setDoInput(true);

                user = new JSONObject(string[0]);

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
                //YOUR RESPONSE
                response = stringBuilder.toString();

                base = new JSONObject(response);
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
                loggedInUser.setVisibility(View.GONE);
                Toast.makeText(getActivity(),R.string.login_failed,Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(getActivity(),"ok",Toast.LENGTH_SHORT).show();
                loggedInUser.setVisibility(View.VISIBLE);

                try {
                    JSONObject userInfo = base.getJSONObject("user");
                    String nickname = userInfo.getString("nickname");
                    String introduction = userInfo.getString("introduction");
                    //todo:set avatar
                    username.setText(nickname);
                    bio.setText(introduction);

                    sharedPreferences = getActivity().getSharedPreferences("Login",Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("Email",userInfo.getString("username"));
                    editor.putString("Password",userInfo.getString("password"));
                    editor.putString("Username",nickname);
                    editor.putString("Bio",introduction);
                    editor.putBoolean("LoginState",true);
                    editor.commit();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }
}
