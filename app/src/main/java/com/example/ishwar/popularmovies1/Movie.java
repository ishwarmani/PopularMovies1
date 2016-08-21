package com.example.ishwar.popularmovies1;

import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable {

    private String name;
    private String bannerPath;
    private String releaseDate;
    private float userRating;
    private String overView;

    Movie( String name, String posterPath, String releaseDate, float userRating, String overView){

        this.name = name;
        this.bannerPath = posterPath ;
        this.releaseDate = releaseDate;
        this.userRating = userRating;
        this.overView = overView;
    }

    public String getName() {
        return name;
    }

    public String getBannerPath() {
        final String BASE_URL = "http://image.tmdb.org/t/p/w342/";
        return BASE_URL + bannerPath;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public float getUserRating() {
        return userRating;
    }

    public String getOverView() {
        return overView;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBannerPath(String bannerPath) {
        this.bannerPath = bannerPath;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setUserRating(float userRating) {
        this.userRating = userRating;
    }

    public void setOverView(String overView) {
        this.overView = overView;
    }

    protected Movie(Parcel in) {
        name = in.readString();
        bannerPath = in.readString();
        releaseDate = in.readString();
        userRating = in.readFloat();
        overView = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(bannerPath);
        dest.writeString(releaseDate);
        dest.writeFloat(userRating);
        dest.writeString(overView);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}