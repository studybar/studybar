package com.wedo.studybar.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.wedo.studybar.R;
import com.wedo.studybar.activities.SettingsActivity;

public class UserFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_user,container,false);

        Button settingsButton = (Button) rootView.findViewById(R.id.settings_button);
        settingsButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent settingsIntent = new Intent(getActivity(), SettingsActivity.class);

                startActivity(settingsIntent);
            }
        });


        return rootView;
    }

    public UserFragment(){

    }
}
