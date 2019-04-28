package com.wedo.studybar.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.view.MenuItem;

import com.wedo.studybar.R;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public static class settingsFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener{

        private SharedPreferences sharedPreferences;

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferences);

            Preference logout = findPreference("logout");
            logout.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    sharedPreferences = getActivity().getSharedPreferences("Login", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("Password","");
                    editor.putString("Username","");
                    editor.putString("Bio","");
                    editor.putBoolean("LoginState", false);
                    editor.apply();
                    Intent outIntent = new Intent(getActivity(),
                            MainActivity.class);
                    outIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
                            | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(outIntent);
                    return true;
                }
            });
        }

        @Override
        public boolean onPreferenceChange(Preference preference, Object newValue) {
            return false;
        }
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
}
