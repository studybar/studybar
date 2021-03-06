package com.wedo.studybar.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.provider.MediaStore;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;
import com.wedo.studybar.Fragments.UserFragment;
import com.wedo.studybar.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import com.wedo.studybar.Fragments.UserFragment;

public class SignUpActivity extends AppCompatActivity {

    private static final String LOG_TAG = Context.class.getSimpleName();

    private TextInputEditText editTextEmail;
    private TextInputEditText editTextNickname;
    private TextInputEditText editTextPassword;
    private TextInputEditText editTextProfession;
    private Spinner spinnerGender;

    private String gender = "";
    private String email = "";
    private String nickname = "";
    private String password = "";
    private String profession = "";
    private String check = "";
    private JSONObject base;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_close);

        editTextEmail = findViewById(R.id.sign_up_email);
        editTextNickname = findViewById(R.id.sign_up_nickname);
        editTextPassword = findViewById(R.id.sign_up_password);
        editTextProfession = findViewById(R.id.sign_up_profession);
        spinnerGender = findViewById(R.id.sign_up_gender);

        InputFilter inputFilter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                for(int i = start;i < end; i++){
                    if(Character.isWhitespace((source.charAt(i)))){
                        return "";
                    }
                }
                return null;
            }
        };

        editTextEmail.setFilters(new InputFilter[]{inputFilter});
        editTextNickname.setFilters(new InputFilter[]{inputFilter});
        editTextPassword.setFilters(new InputFilter[]{inputFilter});
        editTextProfession.setFilters(new InputFilter[]{inputFilter});

        final ArrayAdapter<CharSequence> genderAdapter;
        genderAdapter = ArrayAdapter.createFromResource(this,R.array.gender_identity,android.R.layout.simple_spinner_item);
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGender.setAdapter(genderAdapter);
        spinnerGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("NewApi")
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (Math.toIntExact(id)){
                    case 0:
                        gender = "";
                        break;
                    case 1:
                        gender = getString(R.string.male);
                        break;
                    case 2:
                        gender = getString(R.string.female);
                        break;
                    case 3:
                        gender = getString(R.string.agender);
                        break;
                        default:
                            gender = "";
                            break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
            case R.id.menu_confirm_button:
                email = editTextEmail.getText().toString();
                //username = editTextUsername.getText().toString();
                password = editTextPassword.getText().toString();
                profession = editTextProfession.getText().toString();
                nickname = editTextNickname.getText().toString();
                /*
                if(email.matches("")||username.matches("")||password.matches("")||verificationCode.matches("")||!isAgreementChecked){
                    Toast.makeText(getApplicationContext(),R.string.sign_up_info_not_null,Toast.LENGTH_SHORT).show();
                }
                else {*/
                    /*
                    JSONObject newUser = new JSONObject();
                    try{
                        newUser.put("username",username);
                        newUser.put("password",password);
                        newUser.put("nickname",nickname);
                        newUser.put("sex",gender);
                        newUser.put("email",email);
                        newUser.put("profession",profession);
                        newUser.put("verification",verificationCode);

                        Log.e(LOG_TAG,newUser.toString());

                        //new SendUserInfo().execute("http://39.97.181.175:8080/study/user_Register.action",newUser.toString());

                    }catch (JSONException e){
                        e.printStackTrace();
                    }*/
                    //sendPost();
                    new signUpAsyncTask().execute();
                    Toast.makeText(this,"ok",Toast.LENGTH_SHORT).show();

                //}
                //finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_confirm,menu);
        return super.onCreateOptionsMenu(menu);
    }

    private class signUpAsyncTask extends AsyncTask<Void,Void,String>{

        @Override
        protected String doInBackground(Void... voids) {
            try{
                URL url = new URL("http://39.97.181.175/study/user_Register.action");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                conn.setRequestProperty("Accept","application/json");
                conn.setDoOutput(true);
                conn.setDoInput(true);

                JSONObject newUser = new JSONObject();
                    newUser.put("username",email);
                    newUser.put("password",password);
                    newUser.put("nickname",nickname);
                    newUser.put("sex",gender);
                    newUser.put("email",email);
                    newUser.put("profession",profession);
                    //newUser.put("verification",verificationCode);

                DataOutputStream dataOutputStream = new DataOutputStream(conn.getOutputStream());
                byte[] JsonString = newUser.toString().getBytes(StandardCharsets.UTF_8);
                dataOutputStream.write(JsonString,0,JsonString.length);

                Log.e(LOG_TAG,newUser.toString());

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
                String response = stringBuilder.toString();

                base = new JSONObject(response);
                check = base.getString("result");

                Log.e("STATUS", String.valueOf(conn.getResponseCode()));
                Log.e("MSG" , conn.getResponseMessage());
                Log.e("RESPONSE",response);
                conn.disconnect();
            }catch (Exception e){
                e.printStackTrace();
            }
            return check;
        }

        @Override
        protected void onPostExecute(String response) {
            if(response.matches("success")){
                SharedPreferences sharedPreferences = getSharedPreferences("Login",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("Email",email);
                editor.putString("Password",password);
                editor.putBoolean("LoginState",true);
                editor.apply();
                finish();
                Intent outIntent = new Intent(getApplicationContext(),
                        MainActivity.class);
                outIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
                        | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(outIntent);
            }
        }
    }
}
