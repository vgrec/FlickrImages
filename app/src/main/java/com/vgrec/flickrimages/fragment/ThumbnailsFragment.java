package com.vgrec.flickrimages.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.vgrec.flickrimages.Constants;
import com.vgrec.flickrimages.R;
import com.vgrec.flickrimages.Utils;
import com.vgrec.flickrimages.VolleySingleton;
import com.vgrec.flickrimages.adapter.ThumbnailsAdapter;
import com.vgrec.flickrimages.model.photo.Photo;
import com.vgrec.flickrimages.model.photo.PhotosResponse;

import java.util.ArrayList;
import java.util.List;


public class ThumbnailsFragment extends Fragment {
    private static final String KEY_QUERY = "query";

    private GridView gridView;
    private TextView errorTextView;
    private ProgressBar progressBar;
    private ThumbnailsAdapter adapter;
    private List<Photo> photos = new ArrayList<Photo>();
    private RequestQueue requestQueue;
    private String query;

    public ThumbnailsFragment() {
    }


    public static ThumbnailsFragment newInstance(String query) {
        Bundle bundle = new Bundle();
        bundle.putString(KEY_QUERY, query);
        ThumbnailsFragment fragment = new ThumbnailsFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        if (getArguments() != null) {
            query = getArguments().getString(KEY_QUERY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_image_searchable, container, false);
        gridView = (GridView) rootView.findViewById(R.id.grid_View);
        errorTextView = (TextView) rootView.findViewById(R.id.error_text);
        progressBar = (ProgressBar) rootView.findViewById(R.id.progress_bar);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        requestQueue = VolleySingleton.getInstance(getActivity()).getRequestQueue();
        adapter = new ThumbnailsAdapter(getActivity(), photos);
        gridView.setAdapter(adapter);

        if (Utils.hasInternetConnection(getActivity())) {
            searchPhotos(query);
        } else {
            displayError(getString(R.string.no_internet_connection));
        }
    }

    private void displayError(String message) {
        errorTextView.setVisibility(View.VISIBLE);
        errorTextView.setText(message);
    }

    private void searchPhotos(final String query) {
        showProgressBar();
        String tags = query.replace(" ", ", ");
        String url = String.format(Constants.PHOTO_SEARCH_URL, tags);
        StringRequest request = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String stringResponse) {
                hideProgressBar();
                PhotosResponse response = Utils.fromJson(PhotosResponse.class, stringResponse);
                // if it fails for some reason
                if (response == null) {
                    displayError(getString(R.string.could_not_load_images));
                    return;
                }

                List<Photo> newPhotos = response.getPhotosResponse().getPhotos();
                if (newPhotos.size() > 0) {
                    photos.addAll(newPhotos);
                    adapter.notifyDataSetInvalidated();
                } else {
                    displayError(getString(R.string.query_returned_zero_results));
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                hideProgressBar();
                displayError(getString(R.string.could_not_load_images));
            }
        });

        requestQueue.add(request);
    }


    private void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    public void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }
}
