package com.example.mymovieapp.moviedb;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

interface INetworking {
    void jsonResponseData(JSONObject jsonObject);
    void error(String error);
}


class Networking {
    private final String key = "20bf9b634197a69f61284240be64930f";
    private final String basePath = "https://api.themoviedb.org/3";
    public static final String posterUrl = "http://image.tmdb.org/t/p/w185/";
    private final String movieSearchPath = "/search/movie";
    private Context mContext;
    private RequestQueue requestQueue;


    public Networking(Context context) {
            mContext = context;
            requestQueue = Volley.newRequestQueue(mContext);
    }
    public void searchMovies(String query){
        String searchUrl = basePath + movieSearchPath
                + "?api_key=" + key
                + "&language=en-US"+"&query=" + query
                + "&page=" + 1
                + "&include_adult=" + true;

        Response.Listener listener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                ((INetworking) mContext).jsonResponseData(response);
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ((INetworking) mContext).error(error.toString());
            }
        };

        JSONObject jsonRequest = new JSONObject();

        JsonRequest searchRequest = new JsonObjectRequest(
                Request.Method.GET,
                searchUrl,
                jsonRequest,
                listener,
                errorListener
        );
        searchRequest.setTag(mContext.getClass());
        requestQueue.add(searchRequest);
    }

    public void discoverMovies(){
        String discoverURL = basePath + "/discover/movie" + "?api_key=" + key + "&sort_by=popularity.desc";
        JSONObject jsonRequest = new JSONObject();
        JsonRequest discoverRequest = new JsonObjectRequest(Request.Method.GET, discoverURL,jsonRequest,
                new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                ((INetworking) mContext).jsonResponseData(response);
            }
        },
                new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ((INetworking) mContext).error(error.getMessage());
            }
        });
        discoverRequest.setTag(mContext.getClass());
        requestQueue.add(discoverRequest);
    }

    public void cancelRequests(){
        requestQueue.cancelAll(mContext.getClass());
    }

}
