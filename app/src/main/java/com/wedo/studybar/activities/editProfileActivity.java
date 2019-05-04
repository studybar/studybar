package com.wedo.studybar.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

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

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class editProfileActivity extends AppCompatActivity {

    private ImageView imageView;
    private EditText editTextNickname;
    private Spinner spinner;
    private EditText editTextProfession;

    private Uri imageUri;
    private Bitmap bitmap;

    private Boolean isAvatarChanged = false;
    private int countChanges = 0;

    private String URL_AVATAR = "http://39.97.181.175:8080/study/uploadUserPic.action";
    private String URL_INFO = "http://39.97.181.175:8080/study/user_UpdateInfo.action";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_close);

        imageView = findViewById(R.id.profile_edit_avatar);
        editTextNickname = findViewById(R.id.profile_edit_nickname);
        spinner = findViewById(R.id.profile_edit_gender);
        editTextProfession = findViewById(R.id.profile_edit_profession);

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
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
            case R.id.menu_confirm_button:
                if (isAvatarChanged){
                    new editProfileAsyncTask().execute(URL_AVATAR);
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
                    bitmap.compress(Bitmap.CompressFormat.PNG,100,byteArrayOutputStream);
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
                    bitmap.compress(Bitmap.CompressFormat.PNG,100,byteArrayOutputStream);
                    byte[] b = byteArrayOutputStream.toByteArray();
                    String encodedImage = Base64.encodeToString(b, Base64.DEFAULT);

                    JSONObject avatar = new JSONObject();
                    avatar.put("picture",encodedImage);
                    dataOutputStream.writeBytes(avatar.toString());

                    Log.e("POSTING",avatar.toString());

                }else {
                    //todo:pass user info
                }

                //JSONObject editedUser = new JSONObject(strings[1]);

                Log.e("POSTING","post1");

                dataOutputStream.flush();

                Log.e("POSTING","post2");

                dataOutputStream.close();

                Log.e("POSTING","post3");

                BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                String decodedString;

                Log.e("POSTING","post4");

                StringBuilder stringBuilder = new StringBuilder();
                while ((decodedString = in.readLine()) != null) {
                    stringBuilder.append(decodedString);
                }
                in.close();

                Log.e("POSTING","post5");

                //YOUR RESPONSE
                response = stringBuilder.toString();

                urlConnection.disconnect();

                Log.e("POSTING","post6");

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

                Log.e("POSTING","post7");

                result = base.getString("result");

                Log.e("POSTING","post8");


                Log.e("POSTING",result);

                if (result.matches("true")){
                    if (countChanges == 1){
                        finish();
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
