package com.wedo.studybar.Adapter;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.wedo.studybar.R;
import com.wedo.studybar.activities.DiscussionCommentActivity;
import com.wedo.studybar.activities.DiscussionDetailActivity;
import com.wedo.studybar.util.Discussion;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import katex.hourglass.in.mathlib.MathView;

public class MyCommentAdapter extends ArrayAdapter<Discussion> {

    Context context;
    String fileName="ABCDEFG";
    Random random;
    String path = null;
    ArrayList<String> pathList = new ArrayList<>();

    public MyCommentAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Discussion> comments) {
        super(context, 0, comments);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View commentItemView = convertView;
        if(commentItemView == null){
            commentItemView = LayoutInflater.from((getContext())).inflate(
                    R.layout.my_comment_list_item,parent,false
            );
        }
        final Discussion currentAndroidAdapter = getItem(position);

        TextView commentAuthor = (TextView)commentItemView.findViewById(R.id.my_comment_user_name);
        commentAuthor.setText(currentAndroidAdapter.getDiscussionAuthor());

        if (currentAndroidAdapter.getStatus() == 0) {
            MathView discussionContent = (MathView)commentItemView.findViewById(R.id.my_comment_content);
            discussionContent.setDisplayText(currentAndroidAdapter.getDiscussionContent());
            discussionContent.setTextSize(14);
            discussionContent.setClickable(true);
        }else if (currentAndroidAdapter.getStatus() == 1){
            try {
                ImageView buttonPlay = commentItemView.findViewById(R.id.my_comments_button_play);
                buttonPlay.setVisibility(View.VISIBLE);
                MathView discussionContent = commentItemView.findViewById(R.id.comment_content);
                discussionContent.setVisibility(View.GONE);

                random = new Random();
                fileName = CreateRandomAudioFileName(5) + "AudioRecording.3gp";
                path = getContext().getExternalCacheDir().getAbsolutePath() + "/" + fileName;
                File file = new File(path);
                FileOutputStream fos = new FileOutputStream(file);
                fos.write(Base64.decode(currentAndroidAdapter.getDiscussionContent().getBytes(), Base64.DEFAULT));
                fos.close();

            }catch (Exception e){
                e.printStackTrace();
            }
        }

        pathList.add(path);

        return commentItemView;
    }

    public String getPath(int position){
        return pathList.get(position);
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
}
