package com.wedo.studybar.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.text.InputFilter;
import android.text.Spanned;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;
import com.wedo.studybar.R;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class AddBookActivity extends AppCompatActivity {

    private ImageView imageView;
    private EditText addBookTitle;
    private EditText addBookAuthor;
    private EditText addBookPublisher;
    private Spinner spinner;
    private Button buttonCancel;
    private Button buttonAdd;

    private Uri imageUri;
    private Bitmap bitmap;
    private String bookTitle;
    private String bookAuthor;
    private String bookPublisher;
    private long bookCategory;

    private String bookId;

    private Boolean isCoverChanged = false;
    private Boolean isCoverPosted = false;
    private Boolean isInfoPosted = false;

    private String URL_COVER = "http://39.97.181.175:8080/study/uploadTypePic.action";
    private String URL_INFO = "http://39.97.181.175:8080/study/type_addtype.action";

    private static final String LOG_TAG = Context.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_add_book);

        imageView = findViewById(R.id.add_book_cover);
        spinner = findViewById(R.id.add_book_category);
        addBookTitle=findViewById(R.id.add_book_name);
        addBookAuthor=findViewById(R.id.add_book_author);
        addBookPublisher =findViewById(R.id.add_book_press);
        buttonAdd = findViewById(R.id.button_add);
        buttonCancel = findViewById(R.id.button_cancel);

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

        addBookTitle.setFilters(new InputFilter[]{
                inputFilter
        });
        addBookAuthor.setFilters(new InputFilter[]{
                inputFilter
        });
        addBookPublisher.setFilters(new InputFilter[]{
                inputFilter
        });

        ArrayAdapter<CharSequence> categoryAdapter;

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setAllowFlipping(false)
                        .setAllowRotation(false)
                        .start(AddBookActivity.this);
            }
        });

        categoryAdapter = ArrayAdapter.createFromResource(this,
                R.array.category_array, android.R.layout.simple_spinner_item);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(categoryAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                bookCategory = id;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookTitle = addBookTitle.getText().toString();
                bookAuthor = addBookAuthor.getText().toString();
                bookPublisher = addBookPublisher.getText().toString();
                if(bookTitle.matches("")||bookAuthor.matches("")||bookPublisher.matches("")||bookCategory==0 ||!isCoverChanged){
                    Toast.makeText(getApplicationContext(),R.string.plz_input_full_info,Toast.LENGTH_SHORT).show();
                }
                else{
                    new addBookAsyncTask().execute(URL_COVER);
                    new addBookAsyncTask().execute(URL_INFO);
                }
            }
        });
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookTitle = addBookTitle.getText().toString();
                bookAuthor = addBookAuthor.getText().toString();
                bookPublisher = addBookPublisher.getText().toString();
                if(bookTitle.matches("")||bookAuthor.matches("")||bookPublisher.matches("")||bookCategory==0){
                    Toast.makeText(getApplicationContext(),R.string.plz_input_full_info,Toast.LENGTH_SHORT).show();
                }
                else{
                    //todo: process the info
                    finish();
                }
            }
        });
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                imageUri = result.getUri();
                imageView.setImageURI(imageUri);
                try {
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG,100,byteArrayOutputStream);
                    byte[] b = byteArrayOutputStream.toByteArray();
                    isCoverChanged = true;
                }catch (Exception e){
                    e.printStackTrace();
                }
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

    private class addBookAsyncTask extends AsyncTask<String,Void,String>{

        HttpURLConnection urlConnection = null;
        String response;
        Boolean isCover = false;

        @Override
        protected String doInBackground(String... strings) {

            try{
                URL url = new URL(strings[0]);

                urlConnection = (HttpURLConnection)url.openConnection();
                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                urlConnection.setRequestProperty("Accept","application/json");
                urlConnection.setDoOutput(true);
                urlConnection.setDoInput(true);

                DataOutputStream dataOutputStream = new DataOutputStream(urlConnection.getOutputStream());
                if(strings[0].matches(URL_COVER)){
                    isCover = true;
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG,60,byteArrayOutputStream);
                    byte[] b = byteArrayOutputStream.toByteArray();
                    String encodedImage = Base64.encodeToString(b, Base64.DEFAULT);

                    JSONObject cover = new JSONObject();
                    cover.put("picture",encodedImage);
                    dataOutputStream.writeBytes(cover.toString());
                }else {
                    JSONObject book = new JSONObject();
                    book.put("name",bookTitle+" "+bookAuthor+" "+bookPublisher);
                    JSONObject bookCategory = new JSONObject();
                    bookCategory.put("id",String.valueOf(bookCategory));
                    book.put("typesCategory",bookCategory);

                    byte[] JsonString = book.toString().getBytes(StandardCharsets.UTF_8);
                    dataOutputStream.write(JsonString,0,JsonString.length);

                    Log.e("POSTING",book.toString());
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

                Log.e("RESULT",result);
                if (result.matches("success")){
                    if (isCoverChanged && isCoverPosted){
                        finish();
                    }else {
                        if(isCover){
                            isCoverPosted = true;
                        }else {
                            isInfoPosted = true;
                        }
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
    }

    private void sendPost(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    URL url = new URL("http://39.97.181.175:8080/study/type_addtype.action");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                    conn.setRequestProperty("Accept","application/json");
                    conn.setDoOutput(true);
                    conn.setDoInput(true);

                    JSONObject newBook = new JSONObject();
                    newBook.put("name",bookTitle+" "+bookAuthor+" "+bookPublisher);
                    JSONObject bookCategory = new JSONObject();
                    bookCategory.put("types_category_id",String.valueOf(bookCategory));
                    //newBook.put("")
                    //newBook.put("types_category_id",String.valueOf(bookCategory));
                    //todo:添加创建者 id
                    //newBook.put("user_id","5");

                    DataOutputStream dataOutputStream = new DataOutputStream(conn.getOutputStream());
                    dataOutputStream.writeBytes(newBook.toString());

                    Log.e(LOG_TAG,newBook.toString());

                    dataOutputStream.flush();
                    dataOutputStream.close();

                    /*
                    BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    String decodedString;
                    StringBuilder stringBuilder = new StringBuilder();
                    while ((decodedString = in.readLine()) != null) {
                        stringBuilder.append(decodedString);
                    }
                    in.close();
                    YOUR RESPONSE
                    String response = stringBuilder.toString();
                    */
                    Log.e("STATUS", String.valueOf(conn.getResponseCode()));
                    Log.e("MSG" , conn.getResponseMessage());
                    //Log.e("RESPONSE",response);
                    conn.disconnect();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }
}