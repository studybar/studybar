package com.wedo.studybar.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.wedo.studybar.R;
import com.wedo.studybar.activities.ForgetPassword;
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

    private SwipeRefreshLayout swipeRefreshLayout;
    private LinearLayout loggedInLayout;
    private LinearLayout logInLayout;
    private TextInputEditText editTextEmail;
    private TextInputEditText editTextPassword;
    private Button buttonSignIn;
    private ProgressBar progressBar;

    private ImageView avatar;
    private TextView username;
    private TextView bio;

    private String email = "";
    private String password = "";
    private SharedPreferences sharedPreferences;
    private JSONObject user;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_user,container,false);

        swipeRefreshLayout = rootView.findViewById(R.id.logged_in_refresh);
        loggedInLayout = rootView.findViewById(R.id.logged_in_layout);
        logInLayout = rootView.findViewById(R.id.login_user);
        progressBar = rootView.findViewById(R.id.login_progress);

        editTextEmail = rootView.findViewById(R.id.email_address);
        editTextPassword = rootView.findViewById(R.id.login_user_password);
        buttonSignIn = rootView.findViewById(R.id.sign_in_button);

        avatar = rootView.findViewById(R.id.user_avatar);
        username = rootView.findViewById(R.id.user_name);
        bio = rootView.findViewById(R.id.user_bio);

        sharedPreferences = getActivity().getSharedPreferences("Login",Context.MODE_PRIVATE);

        /**
         * if LoginState is true
         * then show the User Info directly
         * else ask for logging in
         * */
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(!swipeRefreshLayout.isRefreshing()){
                    swipeRefreshLayout.setRefreshing(true);
                }
                loggedInLayout.setVisibility(View.GONE);
                try {
                    JSONObject user = new JSONObject();
                    user.put("username",sharedPreferences.getString("Email",""));
                    user.put("password",sharedPreferences.getString("Password",""));
                    new verifyAsyncTask().execute(user.toString());
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        if(sharedPreferences.getBoolean("LoginState",false)){
            logInLayout.setVisibility(View.GONE);
            swipeRefreshLayout.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
            String avatarString = sharedPreferences.getString("Avatar","");
            byte[] avatarBytesArray = Base64.decode(avatarString,Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(avatarBytesArray,0,avatarBytesArray.length);
            avatar.setImageBitmap(bitmap);
            username.setText(sharedPreferences.getString("Username",""));
            bio.setText(sharedPreferences.getString("Bio",""));
        }else {
            //第一次进入需要登陆
            swipeRefreshLayout.setVisibility(View.GONE);
            logInLayout.setVisibility(View.VISIBLE);
            editTextEmail.setText(sharedPreferences.getString("Email",""));
            editTextPassword.setText(sharedPreferences.getString("Password",""));
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

        MaterialButton buttonForget = rootView.findViewById(R.id.forget_password_button);
        buttonForget.setPaintFlags(buttonForget.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        buttonForget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ForgetPassword.class);
                startActivity(intent);
            }
        });


        //for logged in user
        Button myDiscussionsButton = rootView.findViewById(R.id.my_discussions);
        myDiscussionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(getActivity(), MyDiscussionsActivity.class);
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

                String cookieVal = conn.getHeaderField("set-cookie");
                String sessionId = "";
                if(cookieVal != null){
                    sessionId = cookieVal.substring(0,cookieVal.indexOf(";"));
                }
                SharedPreferences sharedPreferences =getActivity().getSharedPreferences("Login", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("SESSION_ID",sessionId);
                editor.apply();

                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String decodedString;
                StringBuilder stringBuilder = new StringBuilder();
                while ((decodedString = in.readLine()) != null) {
                    stringBuilder.append(decodedString);
                }
                in.close();
                //YOUR RESPONSE
                response = stringBuilder.toString();

                Log.e("STATUS", String.valueOf(conn.getResponseCode()));
                Log.e("MSG" , conn.getResponseMessage());
                conn.disconnect();
            }catch (Exception e){
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPostExecute(String response) {
            String result = "";
            try{
                JSONObject base = new JSONObject(response);
                result = base.getString("result");

            progressBar.setVisibility(View.GONE);
            if(result.equals("fail")){
                logInLayout.setVisibility(View.VISIBLE);
                swipeRefreshLayout.setVisibility(View.GONE);
                Toast.makeText(getActivity(),R.string.login_failed,Toast.LENGTH_SHORT).show();
            }
            else{
                swipeRefreshLayout.setVisibility(View.VISIBLE);
                swipeRefreshLayout.setRefreshing(false);
                loggedInLayout.setVisibility(View.VISIBLE);
                JSONObject userInfo = base.getJSONObject("user");

                Log.e("USER",userInfo.toString());

                String nickname = userInfo.getString("nickname");
                String introduction = userInfo.getString("introduction");
                String avatarString = userInfo.getString("picture");
                String profession = userInfo.getString("profession");
                byte[] avatarBytesArray = Base64.decode(avatarString,Base64.DEFAULT);
                Bitmap bitmap = BitmapFactory.decodeByteArray(avatarBytesArray,0,avatarBytesArray.length);
                avatar.setImageBitmap(bitmap);
                username.setText(nickname);
                bio.setText(introduction);

                sharedPreferences = getActivity().getSharedPreferences("Login",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("Email",userInfo.getString("username"));
                editor.putString("Password",userInfo.getString("password"));
                editor.putString("Username",nickname);
                editor.putString("Bio",introduction);
                editor.putString("Avatar",avatarString);
                editor.putString("Profession",profession);
                editor.putString("Gender",userInfo.getString("sex"));
                editor.putBoolean("LoginState",true);
                editor.apply();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
