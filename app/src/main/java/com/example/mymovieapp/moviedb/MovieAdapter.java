package com.example.mymovieapp.moviedb;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import java.util.ArrayList;
import android.view.ViewGroup;
import com.squareup.picasso.Picasso;

public class MovieAdapter extends RecyclerView.Adapter<MovieViewHolder> {

    private ArrayList<Movie> mValues;
    private Context mContext;
    private boolean mTwoPane;

    public MovieAdapter(Context context, ArrayList<Movie> items, boolean twoPane) {
        mContext = context;
        mValues = items;
        mTwoPane = twoPane;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_list_child, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MovieViewHolder holder, int position) {

        Movie movie = mValues.get(position);
        holder.mItem = movie;
        holder.mReleased.setText(movie.getRelease());
        holder.mTitleView.setText(movie.getTitle());
        Picasso.with(mContext).load(Networking.posterUrl + movie.getPoster()).into(holder.mImageView);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTwoPane) {
                    Bundle arguments = new Bundle();
                    arguments.putParcelable(MovieDetailFragment.MOVIE_EXTRA, holder.mItem);
                    MovieDetailFragment fragment = new MovieDetailFragment();
                    fragment.setArguments(arguments);
                    ((AppCompatActivity)mContext).getSupportFragmentManager().beginTransaction()
                            .replace(R.id.movie_detail_container, fragment).commit();
                } else {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, DetailsActivity.class);
                    intent.putExtra(MovieDetailFragment.MOVIE_EXTRA, holder.mItem);
                    context.startActivity(intent);
                }
            }
        });
    }

    public void setMovies(ArrayList<Movie> movies){
        mValues = movies;
        this.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }
}
