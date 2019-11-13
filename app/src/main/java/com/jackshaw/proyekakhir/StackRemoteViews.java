package com.jackshaw.proyekakhir;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.jackshaw.proyekakhir.db.MovieDB;
import com.jackshaw.proyekakhir.model.Movie;

import java.util.ArrayList;
import java.util.List;

//
//
public class StackRemoteViews implements RemoteViewsService.RemoteViewsFactory {

    private final List<Bitmap> mWidgetItems = new ArrayList<>();

 //   private List<Movie> movies = new ArrayList<>();
    private final Context mContext;
//    private MovieDB movieDB;
private RecyclerView myrecyclerview;
    private ArrayList<Movie> movies;
    private MovieDB movieDB;
    private ProgressBar progressBar;
    private TextView tv_cek;
    StackRemoteViews(Context context) {
        mContext = context;
    }
//
    @Override
    public void onCreate() {
//        MovieAdapter movieadapter = new MovieAdapter(mContext(), movies);
    }

    @Override
    public void onDataSetChanged() {

//        movieDB = new MovieDB(ge());
//        SQLiteDatabase readData = movieDB.getReadableDatabase();
//        Cursor cursor = readData.rawQuery("SELECT * FROM " + MovieDB.MyColums.tableName, null);
//        cursor.moveToFirst();
//        movies = new ArrayList<>();
//        if (cursor.getCount()<1){
//            tv_cek.setVisibility(View.VISIBLE);
//        }
//        for (int i=0; i<cursor.getCount(); i++){
////            mWidgetItems.add(BitmapFactory.decodeResource(mContext.getResources(), cursor.getString(6));
//            cursor.moveToPosition(i);
//            Movie movie = new Movie();
//            movie.setTitle(cursor.getString(1));
//            movie.setRating(cursor.getString(2));
//            movie.setPopularity(cursor.getString(3));
//            movie.setRelease(cursor.getString(4));
//            movie.setOverview(cursor.getString(5));
//            movie.setPoster(cursor.getString(6));
//            movie.setBackdrop(cursor.getString(7));
//            movies.add(movie);
//        }
        mWidgetItems.add(BitmapFactory.decodeResource(mContext.getResources(), R.drawable.darth_vader));
        mWidgetItems.add(BitmapFactory.decodeResource(mContext.getResources(), R.drawable.star_wars_logo));
        mWidgetItems.add(BitmapFactory.decodeResource(mContext.getResources(), R.drawable.storm_trooper));
        mWidgetItems.add(BitmapFactory.decodeResource(mContext.getResources(), R.drawable.starwars));
        mWidgetItems.add(BitmapFactory.decodeResource(mContext.getResources(), R.drawable.falcon));
        mWidgetItems.add(BitmapFactory.decodeResource(mContext.getResources(), R.drawable.darth_vader));
        mWidgetItems.add(BitmapFactory.decodeResource(mContext.getResources(), R.drawable.star_wars_logo));
        mWidgetItems.add(BitmapFactory.decodeResource(mContext.getResources(), R.drawable.storm_trooper));
        mWidgetItems.add(BitmapFactory.decodeResource(mContext.getResources(), R.drawable.starwars));
        mWidgetItems.add(BitmapFactory.decodeResource(mContext.getResources(), R.drawable.falcon));

///* menampilkan data dari database agar tidak force close dan data akan muncul secara realtime:
// if (cursor != null){
//        cursor.close();
//    }
//
//    final long identityToken = Binder.clearCallingIdentity();
//
//    // querying ke database
//    cursor = context.getContentResolver().query(CONTENT_URI, null, null, null, null);
//
//    Binder.restoreCallingIdentity(identityToken);
// */

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return mWidgetItems.size();
    }

//    // mencari posisi gambar
    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.widget_item);
        rv.setImageViewBitmap(R.id.imageView,mWidgetItems.get(position));

        Bundle extras = new Bundle();
        extras.putInt(StackWidgetFavorite.EXTRA_ITEM, position);
        Intent fillInIntent = new Intent();
        fillInIntent.putExtras(extras);

        rv.setOnClickFillInIntent(R.id.imageView, fillInIntent);
        return rv;
    }
//
    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
