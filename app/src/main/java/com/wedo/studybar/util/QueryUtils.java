package com.wedo.studybar.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.JsonReader;
import android.util.Log;

import com.wedo.studybar.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class QueryUtils {

    private static final String LOG_TAG = Context.class.getSimpleName();


    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private QueryUtils(){

    }

    private static URL createUrl(String stringUrl){
        URL url = null;
        try{
            url = new URL(stringUrl);
        }catch (MalformedURLException exception){
            Log.e(LOG_TAG,"Error with creating URL",exception);
            return null;
        }
        return url;
    }

    private static String makeHttpRequest(URL url) throws IOException{
        String jsonResponse = "";

        if(url == null){
            return jsonResponse;
        }
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try{
            urlConnection = (HttpURLConnection)url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.connect();
            if(urlConnection.getResponseCode() == 200){
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            }
            else{
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        }catch (IOException e){
            Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", e);
        }finally {
            if(urlConnection != null){
                urlConnection.disconnect();
            }
            if(inputStream != null){
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if(inputStream != null){
            InputStreamReader inputStreamReader = new InputStreamReader(
                    inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while(line != null){
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    public static List<Discussion> extractTopicsFromJson(String json){
        List<Discussion> discussions = new ArrayList<Discussion>();

        if(TextUtils.isEmpty(json)){
            return null;
        }
        try{
            //JSONObject userInfo = new JSONObject(json);
            JSONArray discussionsArray = new JSONArray(json);

            for(int i=0;i<discussionsArray.length();i++){
                JSONObject discussion = discussionsArray.getJSONObject(i);

                String id = discussion.getString("id");
                String content = discussion.getString("content");
                String countComment = discussion.getString("countComment");
                String title = discussion.getString("title");

                discussions.add(new Discussion(id,"",title,content,countComment));
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
        return discussions;
    }

    public static List<Discussion> extractCommentsFromJson(String json){
        List<Discussion> comments = new ArrayList<Discussion>();

        if(TextUtils.isEmpty(json)){
            return null;
        }
        try{
            JSONArray commentsArray = new JSONArray(json);

            for(int i = 0;i < commentsArray.length(); i++){
                JSONObject comment = commentsArray.getJSONObject(i);

                //String id = comment.getString("")
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return comments;
    }

    /**
     * 读取各个分类下图书数据
     * */
    public static List<Book> extractBooks(String category){
        String booksByCategory = "http://39.97.181.175:8080/study/cate_getCatetype.action";

        URL url = createUrl(booksByCategory);
        String booksJSON = null;
        List<Book> books = new ArrayList<Book>();

        try{
            booksJSON = makeHttpRequest(url);
            if(TextUtils.isEmpty(booksJSON)){
                return null;
            }
            JSONObject base = new JSONObject(booksJSON);
            JSONArray booksArray = base.getJSONArray(category);

            for (int i =0; i < booksArray.length(); i++){
                JSONObject book = booksArray.getJSONObject(i);

                String bookId = book.getString("id");
                String bookName = book.getString("name");
                String bookCover = book.getString("typespicture");
                String bookCommentsNum = book.getString("countComments");

                books.add(new Book(bookId,bookName,R.drawable.test,bookCommentsNum));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return books;
    }

    /**
     * 读取用户发起的话题
     * */
    public static List<Discussion> extractDiscussionsByUser(Context context){
        String discussionsByUser = "http://39.97.181.175:8080/study/user_GetTopics.action";

        URL url = createUrl(discussionsByUser);
        String topicsJSON = null;
        List<Discussion> discussions = new ArrayList<>();
        SharedPreferences sharedPreferences = context.getSharedPreferences("Login",Context.MODE_PRIVATE);

        try{
            HttpURLConnection urlConnection = null;
            InputStream inputStream = null;
            urlConnection = (HttpURLConnection)url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setRequestProperty("cookie",sharedPreferences.getString("SESSION_ID",""));

            Log.e("SESSION",sharedPreferences.getString("SESSION_ID",""));

            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.connect();

                if(urlConnection.getResponseCode() == 200){
                    inputStream = urlConnection.getInputStream();
                    topicsJSON = readFromStream(inputStream);
                }
                else{
                    Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
                }

            if(urlConnection != null){
                urlConnection.disconnect();
            }
            if(inputStream != null){
                inputStream.close();
            }

            if(TextUtils.isEmpty(topicsJSON)){
                return null;
            }
            JSONObject base = new JSONObject(topicsJSON);
            if (base.getString("result").matches("success")){
                JSONArray discussionsArray = base.getJSONArray("usertopic");

                for (int i=0; i<discussionsArray.length(); i++){
                    JSONObject discussion = discussionsArray.getJSONObject(i);

                    String discussionId = discussion.getString("id");
                    String discussionTitle = discussion.getString("title");
                    String discussionContent = discussion.getString("content");
                    String discussionCommentsNum = discussion.getString("countComment");

                    JSONObject authorObject = discussion.getJSONObject("topicsUser");
                    String discussionAuthor = authorObject.getString("nickname");

                    discussions.add(new Discussion(discussionId,discussionAuthor,discussionTitle,discussionContent,discussionCommentsNum));
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return discussions;
    }

    /**
     * 读取用户所参与的话题
     * */
    public static List<Discussion> extractDiscussions(Context context){
        String discussionsUrl = "http://39.97.181.175:8080/study/user_GetComments.action";

        URL url = createUrl(discussionsUrl);
        String discussionsJSON = null;
        List<Discussion> topics = new ArrayList<>();
        SharedPreferences sharedPreferences = context.getSharedPreferences("Login",Context.MODE_PRIVATE);

        try{
            HttpURLConnection urlConnection = null;
            InputStream inputStream = null;
            urlConnection = (HttpURLConnection)url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setRequestProperty("cookie",sharedPreferences.getString("SESSION_ID",""));

            Log.e("SESSION",sharedPreferences.getString("SESSION_ID",""));

            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.connect();

                if(urlConnection.getResponseCode() == 200){
                    inputStream = urlConnection.getInputStream();
                    discussionsJSON = readFromStream(inputStream);
                }
                else{
                    Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
                }

            if(urlConnection != null){
                urlConnection.disconnect();
            }
            if(inputStream != null){
                inputStream.close();
            }

            if(TextUtils.isEmpty(discussionsJSON)){
                return null;
            }
            JSONObject base = new JSONObject(discussionsJSON);
            if (base.getString("result").matches("success")){
                JSONArray commentsArray = base.getJSONArray("usercomment");

                for (int i=0; i<commentsArray.length(); i++){
                    JSONObject comment = commentsArray.getJSONObject(i);

                    JSONObject topic = comment.getJSONObject("commentsTopic");

                    String discussionId = topic.getString("id");
                    String discussionTitle = topic.getString("title");
                    String discussionContent = topic.getString("content");
                    String discussionCommentsNum = topic.getString("countComment");

                    JSONObject authorObject = topic.getJSONObject("topicsUser");
                    String discussionAuthor = authorObject.getString("nickname");

                    topics.add(new Discussion(discussionId,discussionAuthor,discussionTitle,discussionContent,discussionCommentsNum));
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return topics;

    }

}
