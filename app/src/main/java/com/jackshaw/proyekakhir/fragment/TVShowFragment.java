package com.jackshaw.proyekakhir.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
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
public class TVShowFragment extends Fragment {

    View v;
    private final String JSON_URL = "https://api.themoviedb.org/3/discover/tv?api_key=1f92af9188f4f01778fa3a65d850ccdb&language=en-US";
    private RecyclerView iniRecyclerView;
    private List<TVShow> tvShows = new ArrayList<>();
    private RequestQueue requestQueue;
    private ProgressBar progressBar;

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
                    if (initvshow == null)
                        showLoading(true);
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
}
