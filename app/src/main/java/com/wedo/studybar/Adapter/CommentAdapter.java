package com.wedo.studybar.Adapter;

import android.app.Activity;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import katex.hourglass.in.mathlib.MathView;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.wedo.studybar.R;
import com.wedo.studybar.activities.DiscussionCommentActivity;
import com.wedo.studybar.util.Discussion;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Random;

public class CommentAdapter extends ArrayAdapter<Discussion> {

    Context context;
    String topicId;
    String fileName="ABCDEFG";
    Random random;
    String path = null;
    ArrayList<String> pathList = new ArrayList<>();

    public CommentAdapter(Activity context, ArrayList<Discussion> commentArrayList,String topicId){
        super(context,0,commentArrayList);
        this.context=context;
        this.topicId = topicId;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View commentItemView = convertView;
        if(commentItemView == null){
            commentItemView = LayoutInflater.from((getContext())).inflate(
                    R.layout.comment_list_item,parent,false
            );
        }
        final Discussion currentAndroidAdapter = getItem(position);

        TextView commentAuthor = commentItemView.findViewById(R.id.comment_user_name);
        commentAuthor.setText(currentAndroidAdapter.getDiscussionAuthor());

        if (currentAndroidAdapter.getStatus() == 0){
            MathView discussionContent = (MathView)commentItemView.findViewById(R.id.comment_content);
            discussionContent.setDisplayText(currentAndroidAdapter.getDiscussionContent());
            discussionContent.setTextSize(14);
            discussionContent.setClickable(true);

            ImageButton commentButton = commentItemView.findViewById(R.id.comment_reply_button);
            commentButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), DiscussionCommentActivity.class);
                    intent.putExtra("DISCUSSION_COMMENT",false);
                    intent.putExtra("DISCUSSION_ID",topicId);
                    intent.putExtra("COMMENT_FLOOR",currentAndroidAdapter.getCommentFloor());
                    intent.putExtra("COMMENT_AUTHOR",currentAndroidAdapter.getDiscussionAuthor());
                    intent.putExtra("COMMENT_CONTENT",currentAndroidAdapter.getDiscussionContent());
                    context.startActivity(intent);
                }
            });
        }else if (currentAndroidAdapter.getStatus() == 1){
            try {
                ImageView buttonPlay = commentItemView.findViewById(R.id.button_play);
                buttonPlay.setVisibility(View.VISIBLE);
                MathView discussionContent = (MathView)commentItemView.findViewById(R.id.comment_content);
                discussionContent.setVisibility(View.GONE);

                Log.e("VMSG",currentAndroidAdapter.getDiscussionContent());


                random = new Random();
                fileName = CreateRandomAudioFileName(5) + "AudioRecording.3gp";
                path = getContext().getExternalCacheDir().getAbsolutePath() + "/" + fileName;
                File file = new File(path);
                FileOutputStream fos = new FileOutputStream(file);
                fos.write(Base64.decode(currentAndroidAdapter.getDiscussionContent().getBytes(), Base64.DEFAULT));
                fos.close();

            } catch (Exception e) {
                e.printStackTrace();
            }

            ImageButton commentButton = commentItemView.findViewById(R.id.comment_reply_button);
            commentButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), DiscussionCommentActivity.class);
                    intent.putExtra("DISCUSSION_COMMENT",false);
                    intent.putExtra("DISCUSSION_ID",topicId);
                    intent.putExtra("COMMENT_FLOOR",currentAndroidAdapter.getCommentFloor());
                    intent.putExtra("COMMENT_AUTHOR",currentAndroidAdapter.getDiscussionAuthor());
                    intent.putExtra("COMMENT_CONTENT",getContext().getResources().getString(R.string.voice_message));
                    context.startActivity(intent);
                }
            });
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
