package com.jackshaw.proyekakhir.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.jackshaw.proyekakhir.DetailActivity;
import com.jackshaw.proyekakhir.R;
import com.jackshaw.proyekakhir.model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Locale;

import static com.android.volley.VolleyLog.TAG;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MyViewHolder> {

    private Context mContext;
    private List<Movie> mData;
    private RequestQueue requestQueue;
    private String URL_SEARCH = "https://api.themoviedb.org/3/search/movie?api_key=1f92af9188f4f01778fa3a65d850ccdb&language=en-US&query=";

    public MovieAdapter(Context mContext, List<Movie> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, final int i) {

        View v;
        v = LayoutInflater.from(mContext).inflate(R.layout.row_item,viewGroup,false);
        final MyViewHolder iniholder = new MyViewHolder(v);

        iniholder.baris_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
//                Intent inidetail = new Intent(context, DetailActivity.class);
//                inidetail.putExtra(DetailActivity.EXTRA_MOVIE, mData.get(i));
//                context.startActivity(inidetail);
            }
        });
        return iniholder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {
        myViewHolder.tv_title.setText(mData.get(i).getTitle());
        myViewHolder.tv_rating.setText(mData.get(i).getRating());
        myViewHolder.tv_popularity.setText(mData.get(i).getPopularity());
        myViewHolder.tv_release.setText(mData.get(i).getRelease());
        final Movie iniMovieee= mData.get(i);
        Glide.with(myViewHolder.baris_item.getContext())
                .load(iniMovieee.getPoster())
                .apply(new RequestOptions().override(200, 300))
                .into(myViewHolder.img_poster);

        myViewHolder.baris_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent inidetail = new Intent(context, DetailActivity.class);
                inidetail.putExtra(DetailActivity.EXTRA_MOVIE, mData.get(i));
                context.startActivity(inidetail);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private RelativeLayout baris_item;
        private TextView tv_title, tv_rating, tv_popularity, tv_release;
        private ImageView img_poster;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            baris_item = itemView.findViewById(R.id.baris);
            tv_title = itemView.findViewById(R.id.tv_judul);
            tv_rating =itemView.findViewById(R.id.tv_rating);
            tv_popularity = itemView.findViewById(R.id.tv_popularity);
            tv_release = itemView.findViewById(R.id.tv_release_date);
            img_poster =  itemView.findViewById(R.id.img_poster);
        }
    }

    public List<Movie> filter(String keyword){
        keyword = keyword.toLowerCase(Locale.getDefault());
        mData.clear();
        String search = URL_SEARCH + keyword;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, search, new Response.Listener<String>() {
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
                        mData.add(movie);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e(TAG, "Error: " + error.getMessage());
//                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        return mData;
    }
}
