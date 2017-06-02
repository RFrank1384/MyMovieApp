package com.example.mymovieapp.moviedb;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MovieParser {

    public static Movie movieFromJSON(JSONObject movie) {
        Movie newMovie;
        try {
            int id = movie.getInt("id");
            String title = movie.getString("title");
            String posterPath = movie.getString("backdrop_path");
            String overview = movie.getString("overview");
            String releaseDate = movie.getString("release_date");
            double rating = movie.getDouble("vote_average");
            newMovie = new Movie(id, title, posterPath, overview, releaseDate, rating);
        } catch (JSONException jsonException){
            newMovie = null;
        }
        return newMovie;
    }

    public static ArrayList<Movie> moviesFromJSON(JSONObject moviesJSONArray){
        ArrayList<Movie> movies;
        try {
            JSONArray moviesArray = moviesJSONArray.getJSONArray("results");
            movies = new ArrayList<Movie>();
            for(int i = 0; i < moviesArray.length(); i++){
                movies.add(movieFromJSON((JSONObject) moviesArray.get(i)));
            }
        } catch (JSONException jsonException){
            // Handle error
            movies = new ArrayList<Movie>();
        }
        return movies;
    }
}
