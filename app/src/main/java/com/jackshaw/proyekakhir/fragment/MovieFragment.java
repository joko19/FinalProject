package com.jackshaw.proyekakhir.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFragment extends Fragment implements SearchView.OnQueryTextListener{
//    ini coba
    View v;
    private RecyclerView myrecyclerview;
    private List<Movie> movies = new ArrayList<>();
    private final String JSON_URL = "https://api.themoviedb.org/3/discover/movie?api_key=1f92af9188f4f01778fa3a65d850ccdb&language=en-US";
    private String URL_SEARCH = "https://api.themoviedb.org/3/search/movie?api_key=1f92af9188f4f01778fa3a65d850ccdb&language=en-US&query=";
    private RequestQueue requestQueue;
    private ProgressBar progressBar;
    private MovieAdapter movieAdapter;
    private JSONArray film;
    private StringRequest stringRequest;


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_bar, menu);
        final MenuItem item = menu.findItem(R.id.search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(this);
        item.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem menuItem) {
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem menuItem) {
                loadMovie();
                return true;
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);

    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        searchData(newText);
        return true;
    }



    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_movie, container, false);
        myrecyclerview = (RecyclerView) v.findViewById(R.id.rv_movies);
        loadMovie();
        myrecyclerview = v.findViewById(R.id.rv_movies);
        progressBar = v.findViewById(R.id.progressBar);
//
        movieAdapter = new MovieAdapter(getContext(), movies);
        myrecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        myrecyclerview.setAdapter(movieAdapter);
        movieAdapter.notifyDataSetChanged();

        return v;
    }

    private void loadMovie() {
                stringRequest = new StringRequest(Request.Method.GET, JSON_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    film = obj.getJSONArray("results");
                    movies.clear();
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
                        inimovieadapter.notifyDataSetChanged();
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
        setHasOptionsMenu(true);
    }


    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    private void searchData(String newText) {
            String search = URL_SEARCH + newText;
            movies.clear();
            stringRequest = new StringRequest(Request.Method.GET, search, new Response.Listener<String>() {
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
                        loadMovie();
                    }
                    else {
                        showLoading(false);
                        myrecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
                        myrecyclerview.setAdapter(inimovieadapter);
                        inimovieadapter.notifyDataSetChanged();
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
}
