package com.vgrec.flickrimages.activities;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.provider.SearchRecentSuggestions;

import com.vgrec.flickrimages.R;
import com.vgrec.flickrimages.SuggestionProvider;
import com.vgrec.flickrimages.fragment.ThumbnailsFragment;


public class ImageSearchableActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_searchable);

        if (savedInstanceState == null) {
            handleIntent(getIntent());
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            recordQuery(query);
            ThumbnailsFragment imagesFragment = ThumbnailsFragment.newInstance(query);
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.add(R.id.container, imagesFragment);
            transaction.commit();
        }
    }

    private void recordQuery(String query) {
        SearchRecentSuggestions suggestions = new SearchRecentSuggestions(this,
                SuggestionProvider.AUTHORITY, SuggestionProvider.MODE);
        suggestions.saveRecentQuery(query, null);
    }
}
