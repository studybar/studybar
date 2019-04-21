package com.wedo.studybar.Fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.wedo.studybar.Adapter.DiscussionAdapter;
import com.wedo.studybar.Adapter.HorizontalBookAdapter;
import com.wedo.studybar.R;
import com.wedo.studybar.activities.BookDetailActivity;
import com.wedo.studybar.activities.DiscussionDetailActivity;
import com.wedo.studybar.util.Discussion;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class DiscussionsFragment extends Fragment {

    private HorizontalBookAdapter mHorizontalBookAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    private DiscussionAdapter itemsAdapter;
    private Boolean flag_loading = false;
    private DiscussionAsyncTaskWait asyncTaskWait;
    private ProgressBar listFooterView;
    private ListView listView;

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            listViewLoadMore();
        }
    };

    @Override
    public void onResume(){
        super.onResume();
        IntentFilter intentFilter = new IntentFilter("DISCUSSION_LOAD_MORE");
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(broadcastReceiver, intentFilter);
    }

    @Override
    public void onPause(){
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(broadcastReceiver);
        super.onPause();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_discussions,container,false);

        swipeRefreshLayout = rootView.findViewById(R.id.discussion_refresh_layout);

        /**
         * to show list of discussions
         * */
        final ArrayList<Discussion> discussions = new ArrayList<Discussion>();

        discussions.add(new Discussion("34728774660001",getString(R.string.discussion_author_pre)+"nobody","Here is the title of this discussion.Here is the title of this discussion.","Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.","64","64"));
        discussions.add(new Discussion("34728774660002",getString(R.string.discussion_author_pre)+"nobody","Here is the title of this discussion.Here is the title of this discussion.","Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.","64","64"));
        discussions.add(new Discussion("34728774660003",getString(R.string.discussion_author_pre)+"nobody","Here is the title of this discussion.Here is the title of this discussion.","Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.","64","64"));
        discussions.add(new Discussion("34728774660004",getString(R.string.discussion_author_pre)+"nobody","Here is the title of this discussion.Here is the title of this discussion.","Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.","64","64"));
        discussions.add(new Discussion("34728774660005",getString(R.string.discussion_author_pre)+"nobody","Here is the title of this discussion.Here is the title of this discussion.","Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.","64","64"));
        discussions.add(new Discussion("34728774660006",getString(R.string.discussion_author_pre)+"nobody","Here is the title of this discussion.Here is the title of this discussion.","Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.","64","64"));
        discussions.add(new Discussion("34728774660007",getString(R.string.discussion_author_pre)+"nobody","Here is the title of this discussion.Here is the title of this discussion.","Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.","64","64"));
        discussions.add(new Discussion("34728774660008",getString(R.string.discussion_author_pre)+"nobody","Here is the title of this discussion.Here is the title of this discussion.","Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.","64","64"));
        discussions.add(new Discussion("34728774660009",getString(R.string.discussion_author_pre)+"nobody","Here is the title of this discussion.Here is the title of this discussion.","Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.","64","64"));
        discussions.add(new Discussion("34728774660010",getString(R.string.discussion_author_pre)+"nobody","Here is the title of this discussion.Here is the title of this discussion.","Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.","64","64"));
        discussions.add(new Discussion("34728774660011",getString(R.string.discussion_author_pre)+"nobody","Here is the title of this discussion.Here is the title of this discussion.","Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.","64","64"));

        itemsAdapter = new DiscussionAdapter(getActivity(),discussions);
        listView = (ListView)rootView.findViewById(R.id.my_discussion_list);

        LayoutInflater mInflater = getLayoutInflater();
        ViewGroup bookHeader = (ViewGroup)mInflater.inflate(R.layout.discussion_fragment_header,listView,false);
        listView.addHeaderView(bookHeader);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Discussion discussion = discussions.get(position);
                //todo:如何与后台传值待写
                Intent intent = new Intent(getActivity(), DiscussionDetailActivity.class);
                Toast.makeText(getActivity(),"1321",Toast.LENGTH_SHORT).show();
                intent.putExtra("DISCUSSION_TITLE",discussion.getDiscussionTitle());
                startActivity(intent);
            }
        });

        listView.setAdapter(itemsAdapter);
        setListViewFooter();
        listView.setFooterDividersEnabled(false);
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if(scrollState == SCROLL_STATE_IDLE){
                    if(listView.getLastVisiblePosition() >= listView.getCount() - 1){
                        if(!flag_loading){
                            flag_loading = true;
                            listFooterView.setVisibility(View.VISIBLE);
                            //todo:add items
                            asyncTaskWait = new DiscussionAsyncTaskWait(new WeakReference<Context>(getContext()));
                            asyncTaskWait.execute();
                        }
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });


        /**
         * to show my books
         * */
        ArrayList<String> horizontalBookIds = new ArrayList<>();
        horizontalBookIds.add("8378001");
        horizontalBookIds.add("8378002");
        horizontalBookIds.add("8378003");
        horizontalBookIds.add("8378004");
        horizontalBookIds.add("8378005");
        horizontalBookIds.add("8378006");

        ArrayList<Integer> horizontalBookCovers = new ArrayList<>();
        horizontalBookCovers.add(R.drawable.test);
        horizontalBookCovers.add(R.drawable.test);
        horizontalBookCovers.add(R.drawable.test);
        horizontalBookCovers.add(R.drawable.test);
        horizontalBookCovers.add(R.drawable.test);
        horizontalBookCovers.add(R.drawable.test);

        ArrayList<String> horizontalBookNames = new ArrayList<>();
        horizontalBookNames.add("习近平谈治国理");
        horizontalBookNames.add("习近平谈治国理政");
        horizontalBookNames.add("习近平谈治国理政");
        horizontalBookNames.add("习近平谈治国理政");
        horizontalBookNames.add("习近平谈治国理政");
        horizontalBookNames.add("习近平谈治国理政");

        ArrayList<String> horizontalBookAuthors = new ArrayList<>();
        horizontalBookAuthors.add("习近");
        horizontalBookAuthors.add("习近平");
        horizontalBookAuthors.add("习近平");
        horizontalBookAuthors.add("习近平");
        horizontalBookAuthors.add("习近平");
        horizontalBookAuthors.add("习近平");

        RecyclerView horizontalBookRecyclerView = rootView.findViewById(R.id.discussion_books_recycler_view);
        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        horizontalBookRecyclerView.setLayoutManager(horizontalLayoutManager);
        mHorizontalBookAdapter = new HorizontalBookAdapter(getActivity(),horizontalBookIds,horizontalBookCovers,horizontalBookNames,horizontalBookAuthors);
        mHorizontalBookAdapter.setClickListener(
                new HorizontalBookAdapter.ItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent intent = new Intent(getActivity(), BookDetailActivity.class);
                        intent.putExtra("BOOK_ID",mHorizontalBookAdapter.getBookId(position));
                        intent.putExtra("BOOK_NAME",mHorizontalBookAdapter.getBookName(position));
                        startActivity(intent);
                    }
                }
        );
        horizontalBookRecyclerView.setAdapter(mHorizontalBookAdapter);


        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(!swipeRefreshLayout.isRefreshing()){
                    swipeRefreshLayout.setRefreshing(true);
                }

                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    public DiscussionsFragment(){

    }

    /*
    *  itemsAdapter.add(new Discussion("34728774660012",getString(R.string.discussion_author_pre)+"nobody","Hello There!","General Kenobi!","89","64"));
                        itemsAdapter.add(new Discussion("34728774660012",getString(R.string.discussion_author_pre)+"nobody","Hello There!","General Kenobi!","89","64"));
                        itemsAdapter.add(new Discussion("34728774660012",getString(R.string.discussion_author_pre)+"nobody","Hello There!","General Kenobi!","89","64"));
                        itemsAdapter.add(new Discussion("34728774660012",getString(R.string.discussion_author_pre)+"nobody","Hello There!","General Kenobi!","89","64"));
                        itemsAdapter.add(new Discussion("34728774660012",getString(R.string.discussion_author_pre)+"nobody","Hello There!","General Kenobi!","89","64"));
                        flag_loading = false;
    * */

    private void listViewLoadMore(){
        itemsAdapter.add(new Discussion("34728774660012",getString(R.string.discussion_author_pre)+"nobody","Hello There!","General Kenobi!","89","64"));
        itemsAdapter.add(new Discussion("34728774660012",getString(R.string.discussion_author_pre)+"nobody","Hello There!","General Kenobi!","89","64"));
        itemsAdapter.add(new Discussion("34728774660012",getString(R.string.discussion_author_pre)+"nobody","Hello There!","General Kenobi!","89","64"));
        itemsAdapter.add(new Discussion("34728774660012",getString(R.string.discussion_author_pre)+"nobody","Hello There!","General Kenobi!","89","64"));
        itemsAdapter.add(new Discussion("34728774660012",getString(R.string.discussion_author_pre)+"nobody","Hello There!","General Kenobi!","89","64"));
        itemsAdapter.notifyDataSetChanged();
        flag_loading = false;
        listFooterView.setVisibility(View.GONE);
    }

    private void setListViewFooter(){
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.footer_view_load_animation,null);
        listFooterView = view.findViewById(R.id.footer_view_progressbar);
        listView.addFooterView(listFooterView);
    }

    public static class DiscussionAsyncTaskWait extends AsyncTask<Void, Void, Void>{

        private WeakReference<Context> context;

        private DiscussionAsyncTaskWait(WeakReference<Context> context){
            this.context = context;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void nothing){
            Intent intent = new Intent("DISCUSSION_LOAD_MORE");
            LocalBroadcastManager.getInstance(context.get().getApplicationContext()).sendBroadcast
                    (intent);
        }
    }
}
