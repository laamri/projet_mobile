package com.example.lab7_galeriedestars.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.lab7_galeriedestars.R;
import com.example.lab7_galeriedestars.model.Star;

import java.util.ArrayList;
import java.util.List;

public class StarAdapter extends RecyclerView.Adapter<StarAdapter.ViewHolder> {

    public interface OnStarClickListener {
        void onStarClick(Star star, int position);
    }

    private List<Star> stars;
    private final OnStarClickListener listener;

    public StarAdapter(List<Star> stars, OnStarClickListener listener) {
        this.stars    = new ArrayList<>(stars);
        this.listener = listener;
    }

    @NonNull @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_star, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder h, int position) {
        Star star = stars.get(position);

        h.tvNom.setText(star.getNom().toUpperCase());
        h.ratingBar.setRating(star.getRating());

        Glide.with(h.itemView.getContext())
                .load(star.getImageUrl())
                .apply(new RequestOptions().circleCrop()
                        .placeholder(R.drawable.ic_launcher_background)
                        .error(R.drawable.ic_launcher_background))
                .into(h.ivStar);

        h.itemView.setAnimation(
                AnimationUtils.loadAnimation(h.itemView.getContext(), R.anim.item_anim));

        h.itemView.setOnClickListener(v -> listener.onStarClick(star, position));
    }

    @Override public int getItemCount() { return stars.size(); }

    public void filter(String query, List<Star> fullList) {
        if (query.isEmpty()) {
            stars = new ArrayList<>(fullList);
        } else {
            List<Star> r = new ArrayList<>();
            for (Star s : fullList)
                if (s.getNom().toLowerCase().contains(query.toLowerCase()))
                    r.add(s);
            stars = r;
        }
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivStar;
        TextView  tvNom;
        RatingBar ratingBar;

        public ViewHolder(@NonNull View v) {
            super(v);
            ivStar    = v.findViewById(R.id.ivStar);
            tvNom     = v.findViewById(R.id.tvNom);
            ratingBar = v.findViewById(R.id.ratingBar);
        }
    }
}