package com.jackshaw.proyekakhir.model;

import android.os.Parcel;
import android.os.Parcelable;

public class TVShow implements Parcelable {
    private String judul, rating, popularity, release, overview, poster, backdrop;

    public TVShow() {
    }

    protected TVShow(Parcel in) {
        judul = in.readString();
        rating = in.readString();
        popularity = in.readString();
        release = in.readString();
        overview = in.readString();
        poster = in.readString();
        backdrop = in.readString();
    }

    public static final Creator<TVShow> CREATOR = new Creator<TVShow>() {
        @Override
        public TVShow createFromParcel(Parcel in) {
            return new TVShow(in);
        }

        @Override
        public TVShow[] newArray(int size) {
            return new TVShow[size];
        }
    };

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getPopularity() {
        return popularity;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }

    public String getRelease() {
        return release;
    }

    public void setRelease(String release) {
        this.release = release;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getBackdrop() {
        return backdrop;
    }

    public void setBackdrop(String backdrop) {
        this.backdrop = backdrop;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(judul);
        dest.writeString(rating);
        dest.writeString(popularity);
        dest.writeString(release);
        dest.writeString(overview);
        dest.writeString(poster);
        dest.writeString(backdrop);
    }
}
