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
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
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
            Log.e(LOG_TAG, "Problem retrieving the JSON results.", e);
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
     * @param context 为了从 SharedPreferences 中读取 sessionId
     * */
    private static String makeHttpRequest(Context context,URL url) throws IOException{
        String jsonResponse = "";
        SharedPreferences sharedPreferences = context.getSharedPreferences("Login",Context.MODE_PRIVATE);

        if(url == null){
            return jsonResponse;
        }
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try{
            urlConnection = (HttpURLConnection)url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setRequestProperty("cookie",sharedPreferences.getString("SESSION_ID",""));
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
            Log.e(LOG_TAG, "Problem retrieving the JSON results.", e);
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
     * @param id 向服务器发送查找内容相关 id 来获取内容
     * */
    private static String makeHttpRequest(Context context,URL url,String id) throws IOException{
        String jsonResponse = "";
        SharedPreferences sharedPreferences = context.getSharedPreferences("Login",Context.MODE_PRIVATE);

        if(url == null){
            return jsonResponse;
        }
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try{
            urlConnection = (HttpURLConnection)url.openConnection();

            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            urlConnection.setRequestProperty("Accept","application/json");
            urlConnection.setRequestProperty("cookie",sharedPreferences.getString("SESSION_ID",""));
            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(true);

            JSONObject idJSON = new JSONObject();
            idJSON.put("id",id);

            DataOutputStream dataOutputStream = new DataOutputStream(urlConnection.getOutputStream());
            dataOutputStream.writeBytes(idJSON.toString());

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
            jsonResponse = stringBuilder.toString();
        }catch (Exception e){
            Log.e(LOG_TAG, "Problem retrieving the JSON results.", e);
        }finally {
            if(urlConnection != null){
                urlConnection.disconnect();
            }
            //if(inputStream != null){
              //  inputStream.close();
            //}
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
                String bookCover = "";
                bookCover = book.getString("typespicture");
                String bookCommentsNum = book.getString("countTopics");

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

        try {
            topicsJSON = makeHttpRequest(context,url);
            if(TextUtils.isEmpty(topicsJSON)){
                return null;
            }
            JSONObject base = new JSONObject(topicsJSON);
            if (base.getString("result").matches("success")){
                JSONArray discussionsArray = base.getJSONArray("listtopic");

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

        try {
            discussionsJSON = makeHttpRequest(context,url);

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

    /**
     * 读取话题详情
     * */
    public static List<Discussion> extractDiscussionDetail(Context context,String discussionId){
        String discussionDetailUrl = "http://39.97.181.175:8080/study/topic_goTopic.action";

        URL url = createUrl(discussionDetailUrl);
        String discussionDetailJSON = null;
        List<Discussion> comments = new ArrayList<>();

        try {
            discussionDetailJSON = makeHttpRequest(context,url,discussionId);

            if (TextUtils.isEmpty(discussionDetailJSON)) {
                return null;
            }
            JSONObject base = new JSONObject(discussionDetailJSON);
            JSONArray commentsArray = base.getJSONArray("topiccomment");
            for (int i = 0; i < commentsArray.length(); i++){
                JSONObject comment = commentsArray.getJSONObject(i);

                String commentId = comment.getString("id");
                String commentContent = comment.getString("content");

                JSONObject author = comment.getJSONObject("commentsUser");
                String commentUser = author.getString("nickname");

                comments.add(new Discussion(commentId,commentUser,commentContent));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return comments;
    }

    /**
     * 读取书籍下话题列表
     * */
    public static List<Discussion> extractBookDetail(Context context,String bookId){
        String bookDetailUrl = "http://39.97.181.175:8080/study/type_goType.action";

        URL url = createUrl(bookDetailUrl);
        String bookDetailJSON = null;
        List<Discussion> discussions = new ArrayList<>();

        try {
            bookDetailJSON = makeHttpRequest(context,url,bookId);

            if (TextUtils.isEmpty(bookDetailJSON)) {
                return null;
            }
            JSONObject base = new JSONObject(bookDetailJSON);
            JSONArray discussionsArray = base.getJSONArray("listtopic");
            for (int i = 0;i<discussionsArray.length();i++){
                JSONObject discussion = discussionsArray.getJSONObject(i);
                String discussionId = discussion.getString("id");
                String discussionTitle = discussion.getString("title");
                String discussionContent = discussion.getString("content");
                String discussionCommentsNum = discussion.getString("countComment");

                JSONObject authorObject = discussion.getJSONObject("topicsUser");
                String discussionAuthor = authorObject.getString("nickname");

                discussions.add(new Discussion(discussionId,discussionAuthor,discussionTitle,discussionContent,discussionCommentsNum));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return discussions;
    }

    /**
     * 读取用户通知
     * */
    public static List<Notification> extractNotifications(Context context){
        String notificationUrl = "http://39.97.181.175:8080/study/user_GetNews.action";

        URL url = createUrl(notificationUrl);
        String notificationJSON = null;
        List<Notification> notifications = new ArrayList<>();

        try {
            notificationJSON = makeHttpRequest(context,url);
            if(TextUtils.isEmpty(notificationJSON)){
                return null;
            }
            JSONObject base = new JSONObject(notificationJSON);
            if(base.getString("result").matches("success")){
                JSONArray notificationsArray = base.getJSONArray("usernews");

                final int length = notificationsArray.length();

                for(int i = 0; i<length; i++){
                    JSONObject notification = notificationsArray.getJSONObject(i);
                    String notificationId = notification.getString("id");
                    JSONObject commentUser = notification.getJSONObject("newsCommentUser");
                    String notificationCommentUser = commentUser.getString("nickname");
                    JSONObject topic = notification.getJSONObject("newsTopic");
                    String notificationTopicId = topic.getString("id");
                    String notificationTopicTitle = topic.getString("title");
                    String notificationTopicContent = topic.getString("content");
                    String notificationTopicCommentNum = topic.getString("countComment");
                    String notificationTopicAuthor = topic.getJSONObject("topicsUser").getString("nickname");

                    notifications.add(new Notification(context,notificationId,notificationCommentUser,new Discussion(notificationTopicId,notificationTopicAuthor,notificationTopicTitle,notificationTopicContent,notificationTopicCommentNum)));
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return notifications;
    }

    /**
     * 用户搜索结果
     * */
    public static List<Discussion> extractSearchResult(Context context,String content){
        String searchurl = "http://39.97.181.175:8080/study/topic_Search.action";

        URL url = createUrl(searchurl);
        String topicsJSON = null;
        List<Discussion> topics = new ArrayList<>();

        try {
            HttpURLConnection urlConnection = null;
            try{
                urlConnection = (HttpURLConnection)url.openConnection();

                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                urlConnection.setRequestProperty("Accept","application/json");
                urlConnection.setDoOutput(true);
                urlConnection.setDoInput(true);

                JSONObject queryJSON = new JSONObject();
                queryJSON.put("content",content);
                DataOutputStream dataOutputStream = new DataOutputStream(urlConnection.getOutputStream());
                byte[] JsonString = queryJSON.toString().getBytes(StandardCharsets.UTF_8);
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
                topicsJSON = stringBuilder.toString();
            }catch (Exception e){
                Log.e(LOG_TAG, "Problem retrieving the JSON results.", e);
            }finally {
                if(urlConnection != null){
                    urlConnection.disconnect();
                }
                //if(inputStream != null){
                //  inputStream.close();
                //}
            }
            Log.e("JSON",topicsJSON);
            if(TextUtils.isEmpty(topicsJSON)){
                return null;
            }
            JSONObject base = new JSONObject(topicsJSON);
            if (base.getString("result").matches("success")){
                JSONArray discussionsArray = base.getJSONArray("listtopic");

                for (int i=0; i<discussionsArray.length(); i++){
                    JSONObject discussion = discussionsArray.getJSONObject(i);

                    String discussionId = discussion.getString("id");
                    String discussionTitle = discussion.getString("title");
                    String discussionContent = discussion.getString("content");
                    String discussionCommentsNum = discussion.getString("countComment");

                    JSONObject authorObject = discussion.getJSONObject("topicsUser");
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
