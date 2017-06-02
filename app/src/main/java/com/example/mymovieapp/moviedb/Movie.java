package com.example.mymovieapp.moviedb;

import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable{
    private int id;
    private String title;
    private String poster;
    private String plot;
    private String release;
    private double average;

    public Movie(int id, String title, String posterPath, String overview, String releaseDate, double rating){
        this.id = id;
        this.title = title;
        poster = posterPath;
        plot = overview;
        release = releaseDate;
        average = rating;
    }




    // Parcelable implementation
    private Movie(Parcel parcel){
        id = parcel.readInt();
        title = parcel.readString();
        poster = parcel.readString();
        plot = parcel.readString();
        release = parcel.readString();
        average = parcel.readDouble();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(poster);
        dest.writeString(plot);
        dest.writeString(release);
        dest.writeDouble(average);
    }

    public static final Parcelable.Creator<Movie> CREATOR
            = new Parcelable.Creator<Movie>() {
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public String getRelease() {
        return release;
    }

    public void setRelease(String release) {
        this.release = release;
    }

    public double getAverage() {
        return average;
    }

    public void setAverage(float average) {
        this.average = average;
    }
}
