package com.wedo.studybar.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import katex.hourglass.in.mathlib.MathView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.android.material.textfield.TextInputEditText;
import com.wedo.studybar.R;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class BookCommentActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private TextInputEditText editTextTitle;
    private TextInputEditText editTextContent;
    private MathView mathView;

    private String bookId;
    private String title;
    private String content;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setTitle(null);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_close);
        setContentView(R.layout.activity_book_comment);

        bookId = getIntent().getStringExtra("BOOK_ID");

        progressBar = findViewById(R.id.book_comment_post_progress);
        editTextTitle = findViewById(R.id.book_comment_title);
        editTextContent = findViewById(R.id.book_comment_content);
        mathView = findViewById(R.id.book_comment_preview);
        editTextTitle.requestFocus();
        mathView.setVerticalScrollBarEnabled(true);
        mathView.setHorizontalScrollBarEnabled(true);

        editTextContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mathView.setTextSize(14);
                mathView.setDisplayText(editTextContent.getText().toString());
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
                title = editTextTitle.getText().toString();
                content = editTextContent.getText().toString();

                if(title.length()>5 && content.length()>15){
                    try {
                        JSONObject base = new JSONObject();
                        JSONObject topic = new JSONObject();
                        topic.put("title",title);
                        topic.put("content",content);

                        JSONObject type = new JSONObject();
                        type.put("id",bookId);

                        base.put("topic",topic);
                        base.put("type",type);

                        Log.e("BASE",base.toString());

                        new postTopicAsyncTask().execute(base.toString());
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
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

    private class postTopicAsyncTask extends AsyncTask<String,Void,String>{

        SharedPreferences sharedPreferences = getSharedPreferences("Login", Context.MODE_PRIVATE);
        HttpURLConnection urlConnection = null;
        String response;
        String postUrl = "http://39.97.181.175:8080/study/topic_add.action";

        @Override
        protected String doInBackground(String... strings) {
            try{
                URL url = new URL(postUrl);

                urlConnection = (HttpURLConnection)url.openConnection();
                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                urlConnection.setRequestProperty("Accept","application/json");
                urlConnection.setRequestProperty("cookie",sharedPreferences.getString("SESSION_ID",""));
                urlConnection.setDoOutput(true);
                urlConnection.setDoInput(true);

                DataOutputStream dataOutputStream = new DataOutputStream(urlConnection.getOutputStream());
                byte[] JsonString = strings[0].getBytes(StandardCharsets.UTF_8);
                dataOutputStream.write(JsonString,0,JsonString.length);

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
            String result = "";
            try {
                JSONObject base = new JSONObject(response);
                result = base.getString("result");

                Log.e("POST",result);
                if(result.matches("success")){
                    finish();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

}
