package com.jackshaw.proyekakhir.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class MovieDB extends SQLiteOpenHelper {

    public static final String DatabaseName = "movie.db";
    private static final int DatabaseVersion = 9;

    public static abstract class MyColums implements BaseColumns {
        public static final String tableName = "movie";
        public static final String title = "title";
        public static final String rating = "rating";
        public static final String popularity = "popularity";
        public static final String release = "release";
        public static final String overview = "overview";
        public static final String poster = "poster";
        public static final String backdrop = "backdrop";
    }

    //make a table
    private static final String SQL_CREATE_TABEL_MOVIE = String.format("CREATE TABLE %s" +
                    "(%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL)",
            MyColums.tableName,
            MyColums._ID,
            MyColums.title,
            MyColums.rating,
            MyColums.popularity,
            MyColums.release,
            MyColums.overview,
            MyColums.poster,
            MyColums.backdrop
    );

    private static final String SQL_DELETE_ENTRIES =  "DROP TABLE IF EXISTS" + MyColums.tableName;

    public MovieDB(Context context){
        super(context, DatabaseName, null, DatabaseVersion);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABEL_MOVIE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + MyColums.tableName);
        onCreate(db);
    }
}
