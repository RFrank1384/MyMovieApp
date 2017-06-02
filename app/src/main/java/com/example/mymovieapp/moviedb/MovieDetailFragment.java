package com.example.mymovieapp.moviedb;

import android.app.Activity;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.Locale;


public class MovieDetailFragment extends Fragment implements INetworking {

    public static final String MOVIE_EXTRA = "MOVIE_EXTRA";
    private CollapsingToolbarLayout appBarLayout;
    private Movie mItem;

    public MovieDetailFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments().containsKey(MOVIE_EXTRA)) {
            mItem = getArguments().getParcelable(MOVIE_EXTRA);

            Activity activity = this.getActivity();
            appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(mItem.getTitle());
                ImageView imageView = (ImageView) appBarLayout.findViewById(R.id.movie_image);
                Picasso.with(getContext())
                        .load(Networking.posterUrl + mItem.getPoster())
                        .into(imageView);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.movie_detail, container, false);

        if (mItem != null) {
            ((TextView) rootView.findViewById(R.id.movie_detail)).setText(mItem.getPlot());
            ((TextView) rootView.findViewById(R.id.text_release_date)).setText(mItem.getRelease());
            String popularity = String.format(Locale.getDefault(),"%,.2f", mItem.getAverage());
            ((TextView) rootView.findViewById(R.id.text_popularity)).setText(popularity);
        }

        return rootView;
    }

    @Override
    public void jsonResponseData(JSONObject response) {
        if (appBarLayout != null) {
            appBarLayout.setTitle(mItem.getTitle());
        }
    }

    @Override
    public void error(String errorMessage) {
        // Handle error
    }
}
