package com.jackshaw.proyekakhir;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.jackshaw.proyekakhir.db.MovieDB;
import com.jackshaw.proyekakhir.db.TVShowDB;
import com.jackshaw.proyekakhir.model.Movie;
import com.jackshaw.proyekakhir.model.TVShow;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_MOVIE = "extra_movie";
    public static final String EXTRA_TV = "extra_tv";
    private TextView tvTitle, tvRating, tvPopularyty, tvRelease, tvOverview;
    private ImageView imgPoster, imgBackdrop;
    private ProgressBar progressBar;
    private Movie pilihFilm;
    private TVShow acara;
    private MovieDB movieDB;
    private TVShowDB tvShowDB;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //menambahkan menu ke action bar
        getMenuInflater().inflate(R.menu.favourite, menu);
        return true;
    }


    public void onComposeAction(MenuItem mi){
        addFavourite();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        movieDB = new MovieDB(getBaseContext());
        tvShowDB = new TVShowDB(getBaseContext());
        progressBar = findViewById(R.id.progressBar);

        pilihFilm = getIntent().getParcelableExtra(EXTRA_MOVIE);
        acara = getIntent().getParcelableExtra(EXTRA_TV);
        if (pilihFilm == null && acara == null){
            showLoading(true);
        }
        else {
            showLoading(false);
            inisialisasi();
            setData();
        }
    }
    public void addImage(String data, ImageView letak) {
        Glide.with(DetailActivity.this)
                .load(data)
                .into(letak);
    }
    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    private void inisialisasi() {
        tvTitle = findViewById(R.id.tv_judul);
        tvRating = findViewById(R.id.tv_rating);
        tvPopularyty = findViewById(R.id.tv_popularity);
        tvRelease = findViewById(R.id.tv_rilis);
        tvOverview = findViewById(R.id.tv_content);
        imgPoster = findViewById(R.id.img_poster);
        imgBackdrop = findViewById(R.id.img_backdrop);
    }
    private void setData() {
        if (pilihFilm != null) {
            tvTitle.setText(pilihFilm.getTitle());
            tvRating.setText(pilihFilm.getRating());
            tvRelease.setText(pilihFilm.getRelease());
            tvOverview.setText(pilihFilm.getOverview());

            addImage(pilihFilm.getPoster(), imgPoster);
            addImage(pilihFilm.getBackdrop(), imgBackdrop);
        }
        if (acara != null) {
            tvTitle.setText(acara.getJudul());
            tvRating.setText(acara.getRating());
            tvRelease.setText(acara.getRelease());
            tvOverview.setText(acara.getOverview());
            addImage(acara.getPoster(), imgPoster);
            addImage(acara.getPoster(), imgBackdrop);
        }
    }
    public void addFavourite(){
        if (pilihFilm!=null){
            addMovieFavourite();
        }
        if (acara!=null){
            addTvFavourite();
        }
    }

    private void addMovieFavourite() {
        SQLiteDatabase cek = movieDB.getReadableDatabase();
        String filter = MovieDB.MyColums.title + " = " + "'" +  pilihFilm.getTitle() +  "'";
        Cursor cursor = cek.rawQuery("SELECT " + MovieDB.MyColums.title + " FROM " + MovieDB.MyColums.tableName +
                " WHERE " + filter, null);
        SQLiteDatabase db = movieDB.getWritableDatabase();
        if (cursor.getCount()>0){
            db.delete(MovieDB.MyColums.tableName, MovieDB.MyColums.title + " = '" + pilihFilm.getTitle() + "'", null);
            Toast.makeText(this, R.string.hapus, Toast.LENGTH_SHORT).show();
        }
        else {
            ContentValues values = new ContentValues();
            values.put(MovieDB.MyColums.title, pilihFilm.getTitle());
            values.put(MovieDB.MyColums.rating, pilihFilm.getRating());
            values.put(MovieDB.MyColums.popularity, pilihFilm.getPopularity());
            values.put(MovieDB.MyColums.release, pilihFilm.getRelease());
            values.put(MovieDB.MyColums.overview, pilihFilm.getOverview());
            values.put(MovieDB.MyColums.poster, pilihFilm.getPoster());
            values.put(MovieDB.MyColums.backdrop, pilihFilm.getBackdrop());
            db.insert(MovieDB.MyColums.tableName, null, values);
            db.close();
            Toast.makeText(this, R.string.berhasil, Toast.LENGTH_SHORT).show();
        }
    }
    private void addTvFavourite() {
        SQLiteDatabase cek = tvShowDB.getReadableDatabase();
        String filter = TVShowDB.MyColums.title + " = " + "'" +  acara.getJudul() +  "'";
        Cursor cursor = cek.rawQuery("SELECT " + TVShowDB.MyColums.title + " FROM " + TVShowDB.MyColums.tableName +
                " WHERE " + filter, null);
        SQLiteDatabase db = tvShowDB.getWritableDatabase();
        if (cursor.getCount()>0){
            db.delete(TVShowDB.MyColums.tableName, TVShowDB.MyColums.title + " = '" + acara.getJudul() + "'", null);
            Toast.makeText(this, R.string.hapus, Toast.LENGTH_SHORT).show();
        }
        else {
            ContentValues values = new ContentValues();
            values.put(TVShowDB.MyColums.title, acara.getJudul());
            values.put(TVShowDB.MyColums.rating, acara.getRating());
            values.put(TVShowDB.MyColums.popularity, acara.getPopularity());
            values.put(TVShowDB.MyColums.release, acara.getRelease());
            values.put(TVShowDB.MyColums.overview, acara.getOverview());
            values.put(TVShowDB.MyColums.poster, acara.getPoster());
            values.put(TVShowDB.MyColums.backdrop, acara.getBackdrop());
            db.insert(TVShowDB.MyColums.tableName, null, values);
            db.close();
            Toast.makeText(this, R.string.berhasil, Toast.LENGTH_SHORT).show();
        }
    }

}
