package com.wedo.studybar.activities;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.text.InputFilter;
import android.text.Spanned;
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

import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;
import com.wedo.studybar.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

public class SignUpActivity extends AppCompatActivity {

    private static final String LOG_TAG = Context.class.getSimpleName();


    Uri imageUri;

    private ImageView imageViewAvatar;
    private EditText editTextEmail;
    private EditText editTextUsername;
    private EditText editTextNickname;
    private EditText editTextPassword;
    private EditText editTextProfession;
    private EditText editTextVerificationCode;
    private Button buttonGetVerificationCode;
    private CheckBox checkBox;
    private Spinner spinnerGender;
    private String gender = "";
    private String email = "";
    private String username = "";
    private String nickname = "";
    private String password = "";
    private String profession = "";
    private String verificationCode = "";
    private Boolean isAgreementChecked = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_close);

        imageViewAvatar = findViewById(R.id.sign_up_avatar);
        imageViewAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setAllowFlipping(false)
                        .setAllowRotation(false)
                        .setAspectRatio(1,1)
                        .start(SignUpActivity.this);

            }
        });
        editTextEmail = findViewById(R.id.sign_up_email);
        editTextUsername = findViewById(R.id.sign_up_username);
        editTextNickname = findViewById(R.id.sign_up_nickname);
        editTextPassword = findViewById(R.id.sign_up_password);
        editTextProfession = findViewById(R.id.sign_up_profession);
        editTextVerificationCode = findViewById(R.id.sign_up_verification_code);
        buttonGetVerificationCode = findViewById(R.id.sign_up_button_get_verification_code);
        checkBox = findViewById(R.id.sign_up_agreement);
        spinnerGender = findViewById(R.id.sign_up_gender);

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isAgreementChecked = isChecked;
            }
        });

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
        editTextUsername.setFilters(new InputFilter[]{inputFilter});
        editTextNickname.setFilters(new InputFilter[]{inputFilter});
        editTextPassword.setFilters(new InputFilter[]{inputFilter});
        editTextProfession.setFilters(new InputFilter[]{inputFilter});
        editTextVerificationCode.setFilters(new InputFilter[]{inputFilter});

        final ArrayAdapter<CharSequence> genderAdapter;
        genderAdapter = ArrayAdapter.createFromResource(this,R.array.gender_identity,android.R.layout.simple_spinner_item);
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGender.setAdapter(genderAdapter);
        spinnerGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                imageUri = result.getUri();
                imageViewAvatar.setImageURI(imageUri);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
            case R.id.menu_confirm_button:
                email = editTextEmail.getText().toString();
                username = editTextUsername.getText().toString();
                password = editTextPassword.getText().toString();
                verificationCode = editTextVerificationCode.getText().toString();
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
                    sendPost();
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
        return super.onCreateOptionsMenu(menu);    }

    private class SendUserInfo extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... params) {
            String data = "";

            HttpURLConnection httpURLConnection = null;
            try{
                httpURLConnection = (HttpURLConnection)new URL(params[0]).openConnection();
                httpURLConnection.setRequestMethod("POST");

                //httpURLConnection.setDoOutput(true);

                //httpURLConnection.setDoInput(true);
                httpURLConnection.setChunkedStreamingMode(0);
                httpURLConnection.addRequestProperty("Accept", "application/json");
                httpURLConnection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
                //httpURLConnection.setRequestProperty("Content-Length", "" + Integer.toString(params[1].getBytes().length)); // Get the json string length

                DataOutputStream dataOutputStream = new DataOutputStream(httpURLConnection.getOutputStream());
                //dataOutputStream.writeBytes(params[1]);
                Log.e(LOG_TAG,params[1]);
                dataOutputStream.writeUTF(params[1]);
                dataOutputStream.flush();
                dataOutputStream.close();

                int statusCode = httpURLConnection.getResponseCode();
                Log.e(LOG_TAG,String.valueOf(statusCode));

                InputStream inputStream = httpURLConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

                int inputStreamData = inputStreamReader.read();
                while (inputStreamData != -1){
                    char current = (char) inputStreamData;
                    inputStreamData = inputStreamReader.read();
                    data += current;
                }
                Toast.makeText(getApplicationContext(),"executeOk",Toast.LENGTH_SHORT).show();
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                if(httpURLConnection != null){
                    httpURLConnection.disconnect();
                }
            }
            Log.e(LOG_TAG,data);
            return data;
        }
    }

    private void sendPost(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    URL url = new URL("http://39.97.181.175:8080/study/user_Register.action");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                    conn.setRequestProperty("Accept","application/json");
                    conn.setDoOutput(true);
                    conn.setDoInput(true);

                    JSONObject newUser = new JSONObject();
                    newUser.put("username",username);
                    newUser.put("password",password);
                    newUser.put("nickname",nickname);
                    newUser.put("sex",gender);
                    newUser.put("email",email);
                    newUser.put("profession",profession);
                    //newUser.put("verification",verificationCode);

                    DataOutputStream dataOutputStream = new DataOutputStream(conn.getOutputStream());
                    dataOutputStream.writeBytes(newUser.toString());

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

                    Log.e("STATUS", String.valueOf(conn.getResponseCode()));
                    Log.e("MSG" , conn.getResponseMessage());
                    Log.e("RESPONSE",response);
                    conn.disconnect();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }
}
