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

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.jackshaw.proyekakhir.DetailActivity;
import com.jackshaw.proyekakhir.R;
import com.jackshaw.proyekakhir.model.TVShow;

import java.util.List;

public class TVShowAdapter extends RecyclerView.Adapter<TVShowAdapter.MyViewHolder> {

    Context aContext;
    List<TVShow> atv;

    public TVShowAdapter(Context aContext, List<TVShow> atv) {
        this.aContext = aContext;
        this.atv = atv;
    }

    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v;
        v = LayoutInflater.from(aContext).inflate(R.layout.row_item, viewGroup, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TVShowAdapter.MyViewHolder myViewHolder, final int i) {
        myViewHolder.tvJudul.setText(atv.get(i).getJudul());
        myViewHolder.tvRating.setText(atv.get(i).getRating());
        myViewHolder.tvPopularity.setText(atv.get(i).getPopularity());
//        myViewHolder.tvRelease.setText(atv.get(i).getRelease());
        final TVShow initv= atv.get(i);
        Glide.with(myViewHolder.baris_item.getContext())
                .load(initv.getPoster())
                .apply(new RequestOptions().override(200, 300))
                .into(myViewHolder.img_poster);
        myViewHolder.baris_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent inidetail = new Intent(context, DetailActivity.class);
                inidetail.putExtra(DetailActivity.EXTRA_TV, atv.get(i));
                context.startActivity(inidetail);
            }
        });
    }

    @Override
    public int getItemCount() {
        return atv.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private RelativeLayout baris_item;
        private TextView tvJudul, tvRating, tvRelease, tvPopularity;
        private ImageView img_poster;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            baris_item = itemView.findViewById(R.id.baris);
            tvJudul = itemView.findViewById(R.id.tv_judul);
            tvRating = itemView.findViewById(R.id.tv_rating);
            tvPopularity = itemView.findViewById(R.id.tv_popularity);
            tvRelease = itemView.findViewById(R.id.tv_release_date);
            img_poster = itemView.findViewById(R.id.img_poster);
        }
    }
}

