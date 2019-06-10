package com.wedo.studybar.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.os.Environment;
import android.util.Base64;
import android.util.Base64OutputStream;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.wedo.studybar.R;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Random;

import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class DiscussionCommentActivity extends AppCompatActivity {

    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;
    public static final int RequestPermissionCode = 1;

    private String discussionId;
    private String content;
    private String floor;
    private String commentAuthor;
    private String commentContent;
    private Boolean isComment;

    private EditText editText;
    private Button fab;

    private MediaRecorder recorder = null;
    private String fileName = "VOICE";

    Random random;
    private Boolean isPressed = false;
    private boolean permissionToRecordAccepted = false;
    private String [] permissions = {RECORD_AUDIO};

    private Button stopButton;
    MediaRecorder mediaRecorder ;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        SharedPreferences sharedPreferences = getSharedPreferences("Login",MODE_PRIVATE);
        if(!sharedPreferences.getBoolean("LoginState",false)){
            Toast.makeText(this,R.string.plz_login_first,Toast.LENGTH_SHORT).show();
            finish();
        }

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

        editText = findViewById(R.id.comment_edit_box);
        fab = findViewById(R.id.record_fab);

        stopButton = findViewById(R.id.stop_record);

        random = new Random();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkPermission()) {

                    fileName = getExternalCacheDir().getAbsolutePath() + "/" + CreateRandomAudioFileName(5) + "AudioRecording.3gp";

                    MediaRecorderReady();

                    try {
                        mediaRecorder.prepare();
                        mediaRecorder.start();
                    } catch (IllegalStateException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    fab.setEnabled(false);
                    stopButton.setEnabled(true);
                }
                else {

                    requestPermission();

                }
            }
        });
        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaRecorder.stop();

                File audioFile = new File(fileName);
                long fileSize = audioFile.length();

                InputStream inputStream = null;//You can get an inputStream using any IO API
                try {
                    inputStream = new FileInputStream(fileName);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                byte[] buffer = new byte[8192];
                int bytesRead;
                ByteArrayOutputStream output = new ByteArrayOutputStream();
                Base64OutputStream output64 = new Base64OutputStream(output, Base64.DEFAULT);
                try {
                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        output64.write(buffer, 0, bytesRead);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    output64.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                String attachedFile = output.toString();

                try {
                    JSONObject base = new JSONObject();
                    JSONObject topic = new JSONObject();

                    topic.put("id",discussionId);
                    base.put("topic",topic);
                    base.put("voice",attachedFile);

                    Log.e("VOICE",base.toString());

                    new postVoiceAsyncTask().execute(base.toString());

                }catch (Exception e){
                    e.printStackTrace();
                }

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
                else{
                    Toast.makeText(this,R.string.content_too_short,Toast.LENGTH_SHORT).show();
                }
                return true;
                default:
                    return super.onOptionsItemSelected(item);
        }
    }

    public void MediaRecorderReady(){

        mediaRecorder=new MediaRecorder();

        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);

        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);

        mediaRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);

        mediaRecorder.setOutputFile(fileName);

    }

    public String CreateRandomAudioFileName(int string){

        StringBuilder stringBuilder = new StringBuilder( string );
        int i = 0 ;
        while(i < string ) {
            stringBuilder.append(fileName.charAt(random.nextInt(fileName.length())));
            i++ ;
        }
        return stringBuilder.toString();
    }

    private void onRecord(boolean start) {
        if (start) {
            Log.e("VOICE","S2");
            startRecording();
        } else {
            stopRecording();
        }
    }

    private void startRecording() {
        Log.e("VOICE","S3");

        //fileName = getExternalCacheDir().getAbsolutePath();
        //fileName += "/audiorecordtest.3gp";
        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        recorder.setOutputFile(fileName);

        try {
            recorder.prepare();
            recorder.start();
        }catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void stopRecording() {
        recorder.stop();
        recorder.release();
        recorder = null;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{WRITE_EXTERNAL_STORAGE, RECORD_AUDIO}, RequestPermissionCode);    }

    public boolean checkPermission() {

        int result = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(), RECORD_AUDIO);

        return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case REQUEST_RECORD_AUDIO_PERMISSION:
                permissionToRecordAccepted  = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                break;
        }
        if (!permissionToRecordAccepted ) finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_confirm,menu);
        return super.onCreateOptionsMenu(menu);
    }

    private class postVoiceAsyncTask extends  AsyncTask<String,Void,String>{

        SharedPreferences sharedPreferences = getSharedPreferences("Login", Context.MODE_PRIVATE);
        HttpURLConnection urlConnection = null;
        String response;
        String postUrl = "http://39.97.181.175/study/user_VoiceUpload.action";

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

    private class postCommentAsyncTask extends AsyncTask<String,Void,String>{

        SharedPreferences sharedPreferences = getSharedPreferences("Login", Context.MODE_PRIVATE);
        HttpURLConnection urlConnection = null;
        String response;
        String postUrl = "http://39.97.181.175/study/comment_newComment.action";

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
