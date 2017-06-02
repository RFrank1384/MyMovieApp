package com.example.mymovieapp.moviedb;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MovieViewHolder extends RecyclerView.ViewHolder {
    public final View mView;
    public final TextView mTitleView;
    public final TextView mReleased;
    public final ImageView mImageView;
    public Movie mItem;

    public MovieViewHolder(View view) {
        super(view);
        mView = view;
        mTitleView = (TextView) view.findViewById(R.id.MovieTitle);
        mReleased = (TextView) view.findViewById(R.id.MovieRDate);
        mImageView = (ImageView) view.findViewById(R.id.MovieIMG);
    }
}
