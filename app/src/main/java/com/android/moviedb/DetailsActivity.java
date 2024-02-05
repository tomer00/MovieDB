package com.android.moviedb;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.palette.graphics.Palette;

import com.android.moviedb.modals.MovieItem;
import com.android.moviedb.network.Constants;
import com.android.moviedb.ui.BgOval;
import com.android.moviedb.viewmodals.MainViewModal;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.gson.Gson;

public class DetailsActivity extends AppCompatActivity {

    private TextView tvReleaseDate, tvRating, tvPopularity, tvTitle, tvOverview, btBack;
    private ImageView imgPoster;
    private BgOval bgPoster;

    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        String jsonData = getIntent().getStringExtra("movieDetail");
        int pos = getIntent().getIntExtra("pos",0);

        MovieItem detail = new Gson().fromJson(jsonData, MovieItem.class);

        imgPoster = findViewById(R.id.imgThumb);
        tvOverview = findViewById(R.id.tvOverview);
        tvPopularity = findViewById(R.id.tvPopularity);
        tvRating = findViewById(R.id.tvRating);
        tvReleaseDate = findViewById(R.id.tvReleaseDate);
        tvTitle = findViewById(R.id.tvTitle);
        btBack = findViewById(R.id.btBack);

        bgPoster = findViewById(R.id.bgOval);

        tvTitle.setText(detail.getTitle());
        tvOverview.setText(detail.getOverview());
        tvRating.setText("\uD83C\uDF1FRating\n" + String.format("%.1f", detail.getVoteAverage()));
        tvReleaseDate.setText("📅Release Date\n" + detail.getReleaseDate());
        tvPopularity.setText("❤Popularity\n" + detail.getPopularity());


        imgPoster.setTransitionName("imgThumb"+pos);

        Glide.with(this)
                .asBitmap()
                .load(Constants.BASE_IMAGE_LINK + detail.getBackdropPath())
                .transform(new RoundedCorners(60))
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        imgPoster.setImageBitmap(resource);
                        Palette.from(resource)
                                .generate(palette -> {

                                    Palette.Swatch mutedSwatch = palette.getMutedSwatch();
                                    if (mutedSwatch != null) {
                                        int color = mutedSwatch.getRgb();
                                        bgPoster.setBackgroundColor(color);
                                        getWindow().setStatusBarColor(color);
                                        return;
                                    }
                                    Palette.Swatch lightSwatch = palette.getLightMutedSwatch();
                                    if (lightSwatch != null) {
                                        int color = lightSwatch.getRgb();
                                        bgPoster.setBackgroundColor(color);
                                        getWindow().setStatusBarColor(color);
                                    }
                                });
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {

                    }
                });


        btBack.setOnClickListener(view -> {
            super.onBackPressed();
        });

    }
}