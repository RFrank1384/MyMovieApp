package com.example.mymovieapp.moviedb;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements INetworking {

    private boolean mTwoPane;
    private Networking networking;
    private MovieAdapter adapter;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        if (findViewById(R.id.movie_detail_container) != null) {
            mTwoPane = true;
        }

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.movie_list);
        adapter = new MovieAdapter(this, new ArrayList<Movie>(), mTwoPane);
        recyclerView.setAdapter(adapter);
    }

    private void fetchMovies(String title) {
        if (networking == null) {
            networking = new Networking(this);
        }
        if(title != null) {
            networking.searchMovies(title);
        } else {
            networking.discoverMovies();
        }

        findViewById(R.id.progressIndicator).setVisibility(View.VISIBLE);
    }

    @Override
    public void jsonResponseData(JSONObject response) {
        ArrayList<Movie> movies = MovieParser.moviesFromJSON(response);
        findViewById(R.id.progressIndicator).setVisibility(View.GONE);
        adapter.setMovies(movies);
    }

    @Override
    public void error(String errorMessage) {
        findViewById(R.id.progressIndicator).setVisibility(View.GONE);
        Toast.makeText(this, R.string.text_error_no_internet, Toast.LENGTH_SHORT).show();
        Log.d(this.getClass().getSimpleName(), errorMessage + "");
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(networking == null){
            networking = new Networking(this);
            fetchMovies(null);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        networking.cancelRequests();
        networking = null;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.list_activity_menu, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setIconifiedByDefault(true);
        searchView.setSearchableInfo( searchManager.getSearchableInfo(getComponentName()));

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            fetchMovies(query);
        }
    }
}
