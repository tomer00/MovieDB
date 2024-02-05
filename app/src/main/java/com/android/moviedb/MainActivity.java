package com.android.moviedb;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityOptionsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.android.moviedb.ui.MovieAdapter;
import com.android.moviedb.viewmodals.MainViewModal;
import com.google.gson.Gson;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieItemClickLister {


    private MainViewModal viewModal;


    private RecyclerView rvItems;
    private MovieAdapter adapter;
    private ProgressBar progressBar;
    private TextView tvNoInternet;
    private AppCompatButton btRetry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewModal = new ViewModelProvider(this).get(MainViewModal.class);
        rvItems = findViewById(R.id.rvItems);
        progressBar = findViewById(R.id.progress);
        tvNoInternet = findViewById(R.id.tvNoInternet);
        btRetry = findViewById(R.id.btRetry);

        adapter = new MovieAdapter(this);
        rvItems.setAdapter(adapter);

        btRetry.setOnClickListener((v) -> {
            viewModal.loadFromNetwork();
            progressBar.setVisibility(View.VISIBLE);
            btRetry.setVisibility(View.GONE);
            tvNoInternet.setVisibility(View.GONE);
        });

        viewModal.loadFromNetwork();
        viewModal.items.observe(this, (items) -> {
            if (items.isEmpty()) {
                progressBar.setVisibility(View.GONE);
                tvNoInternet.setVisibility(View.VISIBLE);
                btRetry.setVisibility(View.VISIBLE);
                adapter.setItems(new ArrayList<>());
            } else {
                adapter.setItems(items);
                progressBar.setVisibility(View.GONE);
                btRetry.setVisibility(View.GONE);
                tvNoInternet.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onMovieClicked(int pos, ImageView imageView) {
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra("movieDetail", new Gson().toJson(viewModal.items.getValue().get(pos)));
        intent.putExtra("pos", pos);
       ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(this,imageView,"imgThumb"+pos);
        startActivity(intent,optionsCompat.toBundle());
    }
}