package com.wedo.studybar.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

import com.wedo.studybar.R;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class DiscussionCommentActivity extends AppCompatActivity {

    private String discussionId;
    private String content;
    private String floor;
    private String commentAuthor;
    private String commentContent;
    private Boolean isComment;

    private EditText editText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discussion_comment);
        this.setTitle(null);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_close);

        isComment = getIntent().getBooleanExtra("DISCUSSION_COMMENT",true);
        discussionId = getIntent().getStringExtra("DISCUSSION_ID");
        if (!isComment){
            floor = getIntent().getStringExtra("COMMENT_FLOOR");
            commentAuthor = getIntent().getStringExtra("COMMENT_AUTHOR");
            commentContent = getIntent().getStringExtra("COMMENT_CONTENT");
        }

        editText = (EditText)findViewById(R.id.comment_edit_box);
        editText.requestFocus();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
            case R.id.menu_confirm_button:
                content = editText.getText().toString();
                if (!isComment){
                    content = getString(R.string.quote_content,floor,commentAuthor,commentContent,content);
                }
                if(content.length()>15){
                    try{
                        JSONObject base = new JSONObject();
                        JSONObject comment = new JSONObject();
                        JSONObject topic = new JSONObject();

                        comment.put("content",content);
                        topic.put("id",discussionId);
                        base.put("comment",comment);
                        base.put("topic",topic);

                        Log.e("JSON",base.toString());

                        new postCommentAsyncTask().execute(base.toString());
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

    private class postCommentAsyncTask extends AsyncTask<String,Void,String>{

        SharedPreferences sharedPreferences = getSharedPreferences("Login", Context.MODE_PRIVATE);
        HttpURLConnection urlConnection = null;
        String response;
        String postUrl = "http://39.97.181.175:8080/study/comment_newComment.action";

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
