package com.jackshaw.proyekakhir.fragment;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jackshaw.proyekakhir.R;
import com.jackshaw.proyekakhir.adapter.TVShowAdapter;
import com.jackshaw.proyekakhir.db.TVShowDB;
import com.jackshaw.proyekakhir.model.TVShow;

import java.util.ArrayList;

public class TVShowFavourit extends Fragment {

    View v;
    private RecyclerView myrecyclerview;
    private ArrayList<TVShow> tvShows;
    private TVShowDB tvShowDB;
    private ProgressBar progressBar;
    private TextView cektvshow;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_movie, container, false);
        cektvshow = v.findViewById(R.id.tv_cekdata);
        loadMovie();
        myrecyclerview = v.findViewById(R.id.rv_movies);
        TVShowAdapter tvShowAdapter= new TVShowAdapter(getContext(), tvShows);
        myrecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        myrecyclerview.setAdapter(tvShowAdapter);

        progressBar = v.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
        return v;
    }

    public void loadMovie(){
        tvShowDB = new TVShowDB(getContext());
        SQLiteDatabase readData = tvShowDB.getReadableDatabase();
        Cursor cursor = readData.rawQuery("SELECT * FROM " + TVShowDB.MyColums.tableName, null);
        cursor.moveToFirst();
        tvShows = new ArrayList<>();
        if (cursor.getCount()<1){
            cektvshow.setVisibility(View.VISIBLE);
        }
        for (int i=0; i<cursor.getCount(); i++){
            cursor.moveToPosition(i);
            TVShow data = new TVShow();
            data.setJudul(cursor.getString(1));
            data.setRating(cursor.getString(2));
            data.setPopularity(cursor.getString(3));
            data.setRelease(cursor.getString(4));
            data.setOverview(cursor.getString(5));
            data.setPoster(cursor.getString(6));
            data.setBackdrop(cursor.getString(7));
            tvShows.add(data);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}

