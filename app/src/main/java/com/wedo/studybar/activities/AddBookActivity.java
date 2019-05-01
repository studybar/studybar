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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class AddBookActivity extends AppCompatActivity {

    private ImageView imageView;
    public static final int REQUEST_CODE_OPEN = 1;
    Uri imageUri;
    private Button buttonAdd;
    private Button buttonCancle;
    private EditText addBookTitle;
    private Spinner spinner;
    private String bookTitle;
    private long bookCategory;

    private static final String LOG_TAG = Context.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_add_book);

        imageView = (ImageView)findViewById(R.id.add_book_cover);
        spinner = (Spinner)findViewById(R.id.add_book_category);
        addBookTitle=(EditText)findViewById(R.id.add_book_name);
        buttonAdd = (Button)findViewById(R.id.button_add);
        buttonCancle = (Button)findViewById(R.id.button_cancel);

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

        ArrayAdapter<CharSequence> categoryAdapter;

        String bookId;
        String mistake_item = getIntent().getStringExtra("MISTAKE_ITEM");
        /**
         * 区分修改信息/添加信息
         * */
        if(mistake_item != null && mistake_item.length() != 0){
            this.setTitle(R.string.correct_book_info);
            buttonAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    bookTitle = addBookTitle.getText().toString();
                    if(bookTitle.matches("")||bookCategory==0){
                        Toast.makeText(getApplicationContext(),R.string.plz_input_full_info,Toast.LENGTH_SHORT).show();
                    }
                    else{
                        //todo: process the info
                        finish();
                    }
                }
            });
            buttonCancle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
            switch(mistake_item) {
                case "COVER":
                    bookId = getIntent().getStringExtra("BOOK_ID");
                    Toast.makeText(this, String.valueOf(R.string.plz_correct_cover) + " " + bookId, Toast.LENGTH_SHORT).show();
                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            CropImage.activity()
                                    .setGuidelines(CropImageView.Guidelines.ON)
                                    .setAllowFlipping(false)
                                    .setAllowRotation(false)
                                    .setAspectRatio(1,1)
                                    .start(AddBookActivity.this);
                        }
                    });
                    addBookTitle.setEnabled(false);
                    //todo:put the right content in other element
                    break;
                case "TITLE":
                    bookId = getIntent().getStringExtra("BOOK_ID");
                    //todo:put the right content in other element
                    Toast.makeText(this, String.valueOf(R.string.plz_correct_title) + " " + bookId, Toast.LENGTH_SHORT).show();
                    break;
                case "CATEGORY":
                    bookId = getIntent().getStringExtra("BOOK_ID");
                    addBookTitle.setEnabled(false);
                    //todo:put the right content in other element
                    Toast.makeText(this, String.valueOf(R.string.plz_correct_category) + " " + bookId, Toast.LENGTH_SHORT).show();
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
                    break;
            }
        }
        else{
                    /**
                     * default state is add book
                     * */
                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            CropImage.activity()
                                    .setGuidelines(CropImageView.Guidelines.ON)
                                    .setAllowFlipping(false)
                                    .setAllowRotation(false)
                                    .setAspectRatio(1,1)
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
                            if(bookTitle.matches("")||bookCategory==0){
                                Toast.makeText(getApplicationContext(),R.string.plz_input_full_info,Toast.LENGTH_SHORT).show();
                            }
                            else{
                                /*
                                JSONObject newBook = new JSONObject();
                                try{
                                    newBook.put("name",bookTitle+" "+bookAuthor+" "+bookPublisher);
                                    newBook.put("types_category_id",String.valueOf(bookCategory));
                                    //todo:添加创建者 id
                                    newBook.put("user_id","5");

                                    new SendBookInfo().execute("http://39.97.181.175:8080/study/type_addtype.action",newBook.toString());
                                    //todo:send the json object
                                }catch (JSONException e){
                                    e.printStackTrace();
                                }
                                */
                                sendPost();
                                Toast.makeText(getApplicationContext(),"ok",Toast.LENGTH_SHORT).show();
                                //finish();
                            }
                        }
                    });
                    buttonCancle.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            finish();
                        }
                    });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                imageUri = result.getUri();
                imageView.setImageURI(imageUri);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

    private static class SendBookInfo extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... params) {

            String data = "";

            HttpURLConnection httpURLConnection = null;
            try{
                httpURLConnection = (HttpURLConnection)new URL(params[0]).openConnection();
                httpURLConnection.setRequestMethod("POST");

                httpURLConnection.setDoOutput(true);

                DataOutputStream dataOutputStream = new DataOutputStream(httpURLConnection.getOutputStream());
                dataOutputStream.writeBytes(params[1]);
                dataOutputStream.flush();
                dataOutputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

                int inputStreamData = inputStreamReader.read();
                while (inputStreamData != -1){
                    char current = (char) inputStreamData;
                    inputStreamData = inputStreamReader.read();
                    data += current;
                }
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                if(httpURLConnection != null){
                    httpURLConnection.disconnect();
                }
            }
            return data;
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
                    newBook.put("name",bookTitle/*+" "+bookAuthor+" "+bookPublisher*/);
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
