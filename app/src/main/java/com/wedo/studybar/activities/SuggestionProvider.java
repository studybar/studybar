package com.wedo.studybar.activities;

import android.content.SearchRecentSuggestionsProvider;

public class SuggestionProvider extends SearchRecentSuggestionsProvider {

    public final static String AUTHORITY ="com.wedo.studybar.Activities.SuggestionProvider";
    public final static int MODE = DATABASE_MODE_QUERIES;

    public SuggestionProvider(){
        setupSuggestions(AUTHORITY,MODE);
    }


}