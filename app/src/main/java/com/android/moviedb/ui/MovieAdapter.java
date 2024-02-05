package com.android.moviedb.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.moviedb.R;
import com.android.moviedb.modals.MovieItem;
import com.android.moviedb.network.Constants;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;

import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieHolder> {

    private final List<MovieItem> items;
    private final MovieItemClickLister itemClickLister;

    public MovieAdapter(MovieItemClickLister lister) {
        this.itemClickLister = lister;
        this.items = new ArrayList<>();
    }

    @NonNull
    @Override
    public MovieHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_rv, parent, false);
        return new MovieHolder(v, itemClickLister);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieHolder holder, int position) {
        MovieItem item = items.get(position);
        holder.tvTitle.setText(item.getTitle());
        holder.tvDescription.setText(item.getOverview());
        Glide.with(holder.itemView)
                .asBitmap()
                .load(Constants.BASE_IMAGE_LINK + item.getBackdropPath())
                .override(holder.imgThumb.getHeight())
                .transform(new RoundedCorners(20))
                .into(holder.imgThumb);
        holder.imgThumb.setTransitionName("imgThumb" + position);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setItems(List<MovieItem> items) {
        this.items.clear();
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    public class MovieHolder extends RecyclerView.ViewHolder {

        private final ImageView imgThumb;
        private final TextView tvTitle;
        private final TextView tvDescription;

        public MovieHolder(@NonNull View itemView, MovieItemClickLister clickLister) {
            super(itemView);
            imgThumb = itemView.findViewById(R.id.imgThumb);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvDescription = itemView.findViewById(R.id.tvDes);

            itemView.setOnClickListener((v) -> {
                clickLister.onMovieClicked(getAdapterPosition(), imgThumb);
            });
        }
    }

    public interface MovieItemClickLister {
        void onMovieClicked(int pos, ImageView imgTrans);
    }
}
