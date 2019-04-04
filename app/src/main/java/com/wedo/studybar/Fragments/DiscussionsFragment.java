package com.wedo.studybar.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.wedo.studybar.Adapter.DiscussionAdapter;
import com.wedo.studybar.Adapter.HorizontalBookAdapter;
import com.wedo.studybar.R;
import com.wedo.studybar.util.Discussion;

import java.util.ArrayList;

public class DiscussionsFragment extends Fragment {

    private HorizontalBookAdapter mHorizontalBookAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_discussions,container,false);

        /**
         * to show my books
         * */
        ArrayList<Integer> horizontalBookCovers = new ArrayList<>();
        horizontalBookCovers.add(R.drawable.test);
        horizontalBookCovers.add(R.drawable.test);
        horizontalBookCovers.add(R.drawable.test);
        horizontalBookCovers.add(R.drawable.test);
        horizontalBookCovers.add(R.drawable.test);
        horizontalBookCovers.add(R.drawable.test);

        ArrayList<String> horizontalBookNames = new ArrayList<>();
        horizontalBookNames.add("习近平谈治国理政");
        horizontalBookNames.add("习近平谈治国理政");
        horizontalBookNames.add("习近平谈治国理政");
        horizontalBookNames.add("习近平谈治国理政");
        horizontalBookNames.add("习近平谈治国理政");
        horizontalBookNames.add("习近平谈治国理政");

        ArrayList<String> horizontalBookAuthors = new ArrayList<>();
        horizontalBookAuthors.add("习近平");
        horizontalBookAuthors.add("习近平");
        horizontalBookAuthors.add("习近平");
        horizontalBookAuthors.add("习近平");
        horizontalBookAuthors.add("习近平");
        horizontalBookAuthors.add("习近平");

        RecyclerView horizontalBookRecyclerView = rootView.findViewById(R.id.discussion_books_recycler_view);
        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        horizontalBookRecyclerView.setLayoutManager(horizontalLayoutManager);
        mHorizontalBookAdapter = new HorizontalBookAdapter(getActivity(),horizontalBookCovers,horizontalBookNames,horizontalBookAuthors);
        mHorizontalBookAdapter.setClickListener(
                new HorizontalBookAdapter.ItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Toast.makeText(getActivity(),mHorizontalBookAdapter.getItem(position),Toast.LENGTH_SHORT).show();
                    }
                }
        );
        horizontalBookRecyclerView.setAdapter(mHorizontalBookAdapter);

        /**
         * to show list of discussions
         * */
        final ArrayList<Discussion> discussions = new ArrayList<Discussion>();

        discussions.add(new Discussion(getString(R.string.discussion_author_pre)+"nobody","Here is the title of this discussion.Here is the title of this discussion.","Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.","64","64"));
        discussions.add(new Discussion(getString(R.string.discussion_author_pre)+"nobody","Here is the title of this discussion.Here is the title of this discussion.","Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.","64","64"));
        discussions.add(new Discussion(getString(R.string.discussion_author_pre)+"nobody","Here is the title of this discussion.Here is the title of this discussion.","Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.","64","64"));
        discussions.add(new Discussion(getString(R.string.discussion_author_pre)+"nobody","Here is the title of this discussion.Here is the title of this discussion.","Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.","64","64"));
        discussions.add(new Discussion(getString(R.string.discussion_author_pre)+"nobody","Here is the title of this discussion.Here is the title of this discussion.","Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.","64","64"));
        discussions.add(new Discussion(getString(R.string.discussion_author_pre)+"nobody","Here is the title of this discussion.Here is the title of this discussion.","Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.","64","64"));
        discussions.add(new Discussion(getString(R.string.discussion_author_pre)+"nobody","Here is the title of this discussion.Here is the title of this discussion.","Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.","64","64"));
        discussions.add(new Discussion(getString(R.string.discussion_author_pre)+"nobody","Here is the title of this discussion.Here is the title of this discussion.","Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.","64","64"));
        discussions.add(new Discussion(getString(R.string.discussion_author_pre)+"nobody","Here is the title of this discussion.Here is the title of this discussion.","Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.","64","64"));
        discussions.add(new Discussion(getString(R.string.discussion_author_pre)+"nobody","Here is the title of this discussion.Here is the title of this discussion.","Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.","64","64"));
        discussions.add(new Discussion(getString(R.string.discussion_author_pre)+"nobody","Here is the title of this discussion.Here is the title of this discussion.","Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.","64","64"));

        DiscussionAdapter itemsAdapter = new DiscussionAdapter(getActivity(),discussions);
        ListView listView = (ListView)rootView.findViewById(R.id.my_discussion_list);

        //LayoutInflater mInflater = getLayoutInflater();
        //ViewGroup bookHeader = (ViewGroup)mInflater.inflate(R.layout.discussion_books_recycler_view,listView,false);
        //listView.addHeaderView(horizontalBookRecyclerView);

        listView.setAdapter(itemsAdapter);





        return rootView;
    }

    public DiscussionsFragment(){

    }
}
