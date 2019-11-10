package com.jackshaw.proyekakhir.fragment;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.jackshaw.proyekakhir.R;
import com.jackshaw.proyekakhir.adapter.MovieAdapter;
import com.jackshaw.proyekakhir.model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFragment extends Fragment implements View.OnClickListener {
    View v;
    private RecyclerView myrecyclerview;
    private List<Movie> movies = new ArrayList<>();
    private final String JSON_URL = "https://api.themoviedb.org/3/discover/movie?api_key=1f92af9188f4f01778fa3a65d850ccdb&language=en-US";
    private RequestQueue requestQueue;
    private ProgressBar progressBar;

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.search, menu);
        MenuItem search = menu.findItem(R.id.search);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_movie, container, false);
        loadMovie();
        myrecyclerview = v.findViewById(R.id.rv_movies);
        progressBar = v.findViewById(R.id.progressBar);
        return v;
    }

    private void loadMovie() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, JSON_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    JSONArray film = obj.getJSONArray("results");
                    for (int i=0; i<film.length(); i++) {
                        JSONObject jsonObject = film.getJSONObject(i);
                        Movie movie = new Movie();
                        movie.setTitle(jsonObject.getString("original_title"));
                        movie.setRating(jsonObject.getString("vote_average"));
                        movie.setPoster("https://image.tmdb.org/t/p/w185/" + jsonObject.getString("poster_path"));
                        movie.setPopularity(jsonObject.getString("popularity"));
                        movie.setRelease(jsonObject.getString("release_date"));
                        movie.setOverview(jsonObject.getString("overview"));
                        movie.setBackdrop("https://image.tmdb.org/t/p/w185/" + jsonObject.getString("backdrop_path"));
                        movies.add(movie);
                    }
                    MovieAdapter inimovieadapter = new MovieAdapter(getContext(), movies);
                    if (inimovieadapter == null){
                        showLoading(true);
                    }
                    else {
                        showLoading(false);
                        myrecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
                        myrecyclerview.setAdapter(inimovieadapter);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        requestQueue.add(stringRequest);
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public void onClick(View v) {

    }
    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

}
