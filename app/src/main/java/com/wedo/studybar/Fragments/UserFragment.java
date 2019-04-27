package com.wedo.studybar.Fragments;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;

import com.wedo.studybar.R;
import com.wedo.studybar.activities.MyBooksActivity;
import com.wedo.studybar.activities.MyDiscussionsActivity;
import com.wedo.studybar.activities.SettingsActivity;
import com.wedo.studybar.activities.SignUpActivity;

public class UserFragment extends Fragment {

    private Switch switchChangeLayout;
    private LinearLayout loggedInUser;
    private LinearLayout logInLayout;
    private Boolean isChecked;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_user,container,false);

        loggedInUser = (LinearLayout)rootView.findViewById(R.id.logged_in_user);
        logInLayout = (LinearLayout)rootView.findViewById(R.id.login_user);
        switchChangeLayout = (Switch) rootView.findViewById(R.id.switch_change_layout);
        /*
        * 初次显示时不会出现重叠
        * */
        isChecked = switchChangeLayout.isChecked();
        if(isChecked){
            loggedInUser.setVisibility(View.VISIBLE);
            logInLayout.setVisibility(View.GONE);
        }
        else{
            loggedInUser.setVisibility(View.GONE);
            logInLayout.setVisibility(View.VISIBLE);
        }


        switchChangeLayout.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    loggedInUser.setVisibility(View.VISIBLE);
                    logInLayout.setVisibility(View.GONE);
                }
                else{
                    loggedInUser.setVisibility(View.GONE);
                    logInLayout.setVisibility(View.VISIBLE);
                }
            }
        });

        Button myBooksButton = rootView.findViewById(R.id.my_books);
        myBooksButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MyBooksActivity.class);
                startActivity(intent);
            }
        });

        Button myDiscussionsButton = rootView.findViewById(R.id.my_discussions);
        myDiscussionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MyDiscussionsActivity.class);
                startActivity(intent);
            }
        });

        Button settingsButton = (Button) rootView.findViewById(R.id.settings_button);
        settingsButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent settingsIntent = new Intent(getActivity(), SettingsActivity.class);

                startActivity(settingsIntent);
            }
        });

        Button buttonSignUp = rootView.findViewById(R.id.sign_up_button);
        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signupIntent = new Intent(getActivity(), SignUpActivity.class);
                startActivity(signupIntent);
            }
        });

        return rootView;
    }

    public UserFragment(){

    }
}
