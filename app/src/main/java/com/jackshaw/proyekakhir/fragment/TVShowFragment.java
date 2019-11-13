package com.jackshaw.proyekakhir.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

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
import com.jackshaw.proyekakhir.adapter.TVShowAdapter;
import com.jackshaw.proyekakhir.model.TVShow;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class TVShowFragment extends Fragment implements SearchView.OnQueryTextListener {

    View v;
    private final String JSON_URL = "https://api.themoviedb.org/3/discover/tv?api_key=1f92af9188f4f01778fa3a65d850ccdb&language=en-US";
    private final String SEARCH_URL ="https://api.themoviedb.org/3/search/tv?api_key=1f92af9188f4f01778fa3a65d850ccdb&language=en-US&query=";
    private RecyclerView iniRecyclerView;
    private List<TVShow> tvShows = new ArrayList<>();
    private RequestQueue requestQueue;
    private ProgressBar progressBar;
    private TextView notData;

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_bar, menu);
//        super.onCreateOptionsMenu(menu, inflater);
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

                loadtv();
                return true;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public TVShowFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_tvshow, container, false);
        loadtv();
        iniRecyclerView = v.findViewById(R.id.rvtvshow);
        progressBar = v.findViewById(R.id.progressBar);
        notData = v.findViewById(R.id.tv_kosong);
        return v;
    }

    private void loadtv() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, JSON_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    JSONArray tvarray = obj.getJSONArray("results");
                    for (int i=0; i<tvarray.length(); i++) {
                        JSONObject jsonObject = tvarray.getJSONObject(i);
                        TVShow tvShow = new TVShow();
                        tvShow.setJudul(jsonObject.getString("name"));
                        tvShow.setRating(jsonObject.getString("vote_average"));
                        tvShow.setRelease(jsonObject.getString("first_air_date"));
                        tvShow.setPopularity(jsonObject.getString("popularity"));
                        tvShow.setOverview(jsonObject.getString("overview"));
                        tvShow.setPoster("https://image.tmdb.org/t/p/w185/" + jsonObject.getString("poster_path"));
                        tvShow.setBackdrop("https://image.tmdb.org/t/p/w185/" + jsonObject.getString("backdrop_path"));
                        tvShows.add(tvShow);
                    }

                    TVShowAdapter initvshow = new TVShowAdapter(getContext(), tvShows);
                    if (initvshow == null){
                        showLoading(true);
                    }
                    else {
                        showLoading(false);
                        iniRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                        iniRecyclerView.setAdapter(initvshow);
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

    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        searchTv(newText);
        return true;
    }

    private void searchTv(String newText) {
        String search = SEARCH_URL + newText;
        tvShows.clear();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, search, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    JSONArray tvarray = obj.getJSONArray("results");
                    for (int i=0; i<tvarray.length(); i++) {
                        JSONObject jsonObject = tvarray.getJSONObject(i);
                        TVShow tvShow = new TVShow();
                        tvShow.setJudul(jsonObject.getString("name"));
                        tvShow.setRating(jsonObject.getString("vote_average"));
                        tvShow.setRelease(jsonObject.getString("first_air_date"));
                        tvShow.setPopularity(jsonObject.getString("popularity"));
                        tvShow.setOverview(jsonObject.getString("overview"));
                        tvShow.setPoster("https://image.tmdb.org/t/p/w185/" + jsonObject.getString("poster_path"));
                        tvShow.setBackdrop("https://image.tmdb.org/t/p/w185/" + jsonObject.getString("backdrop_path"));
                        tvShows.add(tvShow);
                    }

                    TVShowAdapter initvshow = new TVShowAdapter(getContext(), tvShows);
                    if (initvshow != null){
                        showLoading(false);
                        iniRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                        iniRecyclerView.setAdapter(initvshow);
                        initvshow.notifyDataSetChanged();
//                        showLoading(true);
//                        noData(true);
//                        notData.setVisibility(View.VISIBLE);
                    }else {
                        showLoading(true);
//                        notData.setVisibility(View.VISIBLE);
////                        showLoading(false);
////                        iniRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
////                        iniRecyclerView.setAdapter(initvshow);
////                        initvshow.notifyDataSetChanged();
//
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
