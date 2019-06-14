package com.wedo.studybar.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;
import com.wedo.studybar.R;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class editProfileActivity extends AppCompatActivity {

    private ImageView imageView;
    private TextInputEditText editTextNickname;
    private Spinner spinner;
    private TextInputEditText editTextBio;
    private TextInputEditText editTextProfession;
    private ProgressBar progressBar;

    private Uri imageUri;
    private Bitmap bitmap;

    private Boolean isAvatarChanged = false;
    private Boolean isInfoChanged = false;
    private int countChanges = 0;

    private String encodedString;
    private String nickname;
    private String bio;
    private String profession;
    private String gender;

    private String URL_AVATAR = "http://39.97.181.175/study/uploadUserPic.action";
    private String URL_INFO = "http://39.97.181.175/study/user_UpdateInfo.action";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_close);

        imageView = findViewById(R.id.profile_edit_avatar);
        editTextNickname = findViewById(R.id.profile_edit_nickname);
        spinner = findViewById(R.id.profile_edit_gender);
        editTextBio = findViewById(R.id.profile_edit_bio);
        editTextProfession = findViewById(R.id.profile_edit_profession);
        progressBar = findViewById(R.id.profile_edit_post_progress);

        SharedPreferences sharedPreferences = getSharedPreferences("Login",Context.MODE_PRIVATE);
        encodedString = sharedPreferences.getString("Avatar","");
        nickname = sharedPreferences.getString("Username","");
        bio = sharedPreferences.getString("Bio","");
        profession = sharedPreferences.getString("Profession","");
        gender = sharedPreferences.getString("Gender","");

        final ArrayAdapter<CharSequence> genderAdapter;
        genderAdapter = ArrayAdapter.createFromResource(this,R.array.gender_identity,android.R.layout.simple_spinner_item);
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(genderAdapter);

        if(!encodedString.matches("")){
            byte[] avatarBytesArray = Base64.decode(encodedString,Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(avatarBytesArray,0,avatarBytesArray.length);
            imageView.setImageBitmap(bitmap);
        }

        if (!nickname.matches("")){
            editTextNickname.setText(nickname);
        }

        if (!bio.matches("")){
            editTextBio.setText(bio);
        }

        if (!profession.matches("")){
            editTextProfession.setText(profession);
        }

        int spinner_id;
        switch (gender){
            case "":
                spinner_id = 0;
                break;
            case "男":
                spinner_id = 1;
                break;
            case "女":
                spinner_id = 2;
                break;
            case "双":
                spinner_id = 3;
                break;
                default:
                    spinner_id = 0;
        }

        spinner.setSelection(spinner_id);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setAllowRotation(false)
                        .setAllowFlipping(false)
                        .setAspectRatio(1,1)
                        .start(editProfileActivity.this);
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

        editTextNickname.setFilters(new InputFilter[]{inputFilter});

        editTextNickname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isInfoChanged = true;
            }
        });

        editTextProfession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isInfoChanged = true;
            }
        });

        editTextBio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isInfoChanged = true;
            }
        });


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                isInfoChanged = true;
                switch (Math.toIntExact(id)){
                    case 0:
                        gender = "";
                        break;
                    case 1:
                        gender = "男";
                        break;
                    case 2:
                        gender = "女";
                        break;
                    case 3:
                        gender = "无";
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
                nickname = editTextNickname.getText().toString();
                bio = editTextBio.getText().toString();
                profession = editTextProfession.getText().toString();

                if (isInfoChanged){
                    if(!nickname.matches("")){
                        progressBar.setVisibility(View.GONE);
                        countChanges++;
                        new editProfileAsyncTask().execute(URL_INFO);

                        if (isAvatarChanged){
                            new editProfileAsyncTask().execute(URL_AVATAR);
                        }

                    }else {
                        Toast.makeText(this,R.string.nickname_not_null,Toast.LENGTH_SHORT).show();
                    }
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                try {
                    imageUri = result.getUri();
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                    imageView.setImageURI(imageUri);
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG,40,byteArrayOutputStream);
                    byte[] b = byteArrayOutputStream.toByteArray();
                    if(!isAvatarChanged){ countChanges++; }
                    isAvatarChanged = true;
                }catch (Exception e){
                    e.printStackTrace();
                }
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_confirm,menu);
        return super.onCreateOptionsMenu(menu);
    }

    private class editProfileAsyncTask extends AsyncTask<String,Void,String>{

        SharedPreferences sharedPreferences = getSharedPreferences("Login", Context.MODE_PRIVATE);
        HttpURLConnection urlConnection = null;
        String response;

        @Override
        protected String doInBackground(String... strings) {
            try{
                URL url = new URL(strings[0]);

                urlConnection = (HttpURLConnection)url.openConnection();
                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                urlConnection.setRequestProperty("Accept","application/json");
                urlConnection.setRequestProperty("cookie",sharedPreferences.getString("SESSION_ID",""));
                urlConnection.setDoOutput(true);
                urlConnection.setDoInput(true);

                DataOutputStream dataOutputStream = new DataOutputStream(urlConnection.getOutputStream());


                if(strings[0].matches(URL_AVATAR)){
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG,60,byteArrayOutputStream);
                    byte[] b = byteArrayOutputStream.toByteArray();
                    String encodedImage = Base64.encodeToString(b, Base64.DEFAULT);

                    JSONObject avatar = new JSONObject();
                    avatar.put("picture",encodedImage);
                    dataOutputStream.writeBytes(avatar.toString());
                }else {
                    JSONObject user = new JSONObject();
                    user.put("userIntro",bio);
                    user.put("userNic",nickname);
                    user.put("userProfe",profession);
                    user.put("userSex",gender);
                    byte[] JsonString = user.toString().getBytes(StandardCharsets.UTF_8);
                    dataOutputStream.write(JsonString,0,JsonString.length);
                }
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
        protected void onPostExecute(String response) {
            String result = "";
            try{
                JSONObject base = new JSONObject(response);
                result = base.getString("result");

                Log.e("COUNT",String.valueOf(countChanges));
                Log.e("RESULT",result);
                if (result.matches("success")){
                    progressBar.setVisibility(View.GONE);
                    if (countChanges == 1){
                        finish();
                    }else {
                        countChanges--;
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
