package com.wedo.studybar.activities;

import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.wedo.studybar.Adapter.CommentAdapter;
import com.wedo.studybar.R;
import com.wedo.studybar.loader.discussionDetailLoader;
import com.wedo.studybar.util.Discussion;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import katex.hourglass.in.mathlib.MathView;

public class DiscussionDetailActivity extends AppCompatActivity implements androidx.loader.app.LoaderManager.LoaderCallbacks<List<Discussion>> {

    private SwipeRefreshLayout swipeRefreshLayout;
    private ListView listView;
    private FloatingActionButton floatingActionButton;
    private TextView emptyStateTextView;
    private ProgressBar progressBar;
    private CommentAdapter commentAdapter;
    private String discussionId;
    private String discussionAuthor;
    private String discussionTitle;
    private String discussionContent;

    private MediaPlayer mediaPlayer;

    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            releaseMediaPlayer();
        }
    };

    private AudioManager am;
    AudioManager.OnAudioFocusChangeListener afChangeListener =
            new AudioManager.OnAudioFocusChangeListener() {
                public void onAudioFocusChange(int focusChange) {
                    if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                        // Permanent loss of audio focus
                        // Pause playback immediately
                        releaseMediaPlayer();
                    }
                    else if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT || focusChange==AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                        // Pause playback
                        mediaPlayer.pause();
                        mediaPlayer.seekTo(0);
                    } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                        // Your app has been granted audio focus again
                        // Raise volume to normal, restart playback if necessary
                        mediaPlayer.start();
                    }
                }
            };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discussion_detail);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        am = (AudioManager)getSystemService(Context.AUDIO_SERVICE);

        swipeRefreshLayout = findViewById(R.id.discussion_detail_refresh_layout);
        listView = findViewById(R.id.comments_of_discussion);
        floatingActionButton = findViewById(R.id.comment_floating_action_button);
        emptyStateTextView = findViewById(R.id.discussions_detail_empty_view);
        progressBar = findViewById(R.id.discussions_detail_load_progress);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(!swipeRefreshLayout.isRefreshing()){
                    swipeRefreshLayout.setRefreshing(true);
                }
                listView.setVisibility(View.GONE);
                floatingActionButton.setVisibility(View.GONE);
                loadDiscussionDetail();
            }
        });

        discussionId = getIntent().getStringExtra("DISCUSSION_ID");
        discussionAuthor = getIntent().getStringExtra("DISCUSSION_AUTHOR");
        discussionTitle = getIntent().getStringExtra("DISCUSSION_TITLE");
        discussionContent = getIntent().getStringExtra("DISCUSSION_CONTENT");

        loadDiscussionDetail();

        final ArrayList<Discussion> comments = new ArrayList<>();
        commentAdapter = new CommentAdapter(this,comments,discussionId);
        toggleEmptyView(commentAdapter);
        listView.setAdapter(commentAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                position--;
                if (comments.get(position).getStatus() == 1){
                    releaseMediaPlayer();

                    int result = am.requestAudioFocus(afChangeListener,AudioManager.STREAM_MUSIC,AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                    if(result==AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                        try {
                            mediaPlayer = new MediaPlayer();

                            mediaPlayer.setDataSource(commentAdapter.getPath(position));
                            mediaPlayer.prepare();
                            mediaPlayer.start();
                            mediaPlayer.setOnCompletionListener(mCompletionListener);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }
            }
        });

        LayoutInflater mInflater = getLayoutInflater();
        ViewGroup bookHeader = (ViewGroup)mInflater.inflate(R.layout.comment_topic_header_view,listView,false);
        listView.addHeaderView(bookHeader);
        listView.setHeaderDividersEnabled(true);

        TextView commentTopicAuthor = (TextView)findViewById(R.id.comment_topic_author);
        TextView commentTopicTitle = (TextView)findViewById(R.id.comment_topic_title);
        MathView commentTopicContent = findViewById(R.id.comment_topic_content);

        commentTopicAuthor.setText(discussionAuthor);
        commentTopicTitle.setText(discussionTitle);
        commentTopicContent.setDisplayText(discussionContent);
        commentTopicContent.setTextSize(14);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DiscussionCommentActivity.class);
                intent.putExtra("DISCUSSION_COMMENT",true);
                intent.putExtra("DISCUSSION_ID",discussionId);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void loadDiscussionDetail(){
        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        // If there is a network connection, fetch data
        if (networkInfo != null && networkInfo.isConnected()) {
            // 引用 LoaderManager，以便与 loader 进行交互。
            LoaderManager loaderManager = getSupportLoaderManager();

            if(swipeRefreshLayout.isRefreshing()){
                loaderManager.restartLoader(8, null, this);
            }else {
                // 初始化 loader。传递上面定义的整数 ID 常量并为为捆绑
                // 传递 null。为 LoaderCallbacks 参数（由于
                // 此活动实现了 LoaderCallbacks 接口而有效）传递此活动。
                loaderManager.initLoader(8, null, this);
            }
        }
        else{
            progressBar.setVisibility(View.GONE);
            emptyStateTextView.setText(R.string.no_internet);
        }
    }

    @NonNull
    @Override
    public Loader<List<Discussion>> onCreateLoader(int id, @Nullable Bundle args) {
        return new discussionDetailLoader(this,discussionId);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<Discussion>> loader, List<Discussion> comments) {
        progressBar.setVisibility(View.GONE);
        swipeRefreshLayout.setRefreshing(false);
        listView.setVisibility(View.VISIBLE);
        emptyStateTextView.setText(R.string.no_discussion);
        if(commentAdapter !=null){
            commentAdapter.clear();
        }
        if(comments != null && !comments.isEmpty()){
            commentAdapter.addAll(comments);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<Discussion>> loader) {
        commentAdapter.clear();
    }

    /**
     * Custom empty view handling because we don't want the
     * list to be hidden when the empty view is displayed,
     * since the list must always display the header.
     */
    private void toggleEmptyView(final Adapter adapter){
        //final View emptyView = findViewById(R.id.empty_view);
        adapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                emptyStateTextView.setVisibility(adapter.getCount() == 0 ? View.VISIBLE : View.GONE);
            }
        });
    }

    @Override
    protected void onStop(){
        super.onStop();
        releaseMediaPlayer();
    }

    /**
     * Clean up the media player by releasing its resources.
     */
    private void releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (mediaPlayer != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mediaPlayer.release();

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mediaPlayer = null;

            // Regardless of whether or not we were granted audio focus, abandon it. This also
            // unregisters the AudioFocusChangeListener so we don't get anymore callbacks.
            // mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
            am.abandonAudioFocus(afChangeListener);
        }
    }
}
