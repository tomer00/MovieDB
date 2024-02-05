package com.android.moviedb.viewmodals;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.moviedb.modals.MovieItem;
import com.android.moviedb.modals.NetworkResponse;
import com.android.moviedb.network.Constants;
import com.android.moviedb.network.Retro;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainViewModal extends ViewModel {

    private final MutableLiveData<List<MovieItem>> _items = new MutableLiveData<>();
    public LiveData<List<MovieItem>> items = _items;


    public void loadFromNetwork() {
        Retro.getInstance().api().getMovieList(Constants.API_KEY, "en-US", 1).enqueue(
                new Callback<NetworkResponse>() {
                    @Override
                    public void onResponse(Call<NetworkResponse> call, Response<NetworkResponse> response) {
                        if (response.isSuccessful()) {
                            assert response.body() != null;
                            if (response.body().getMovieItems() != null) {
                                _items.setValue(response.body().getMovieItems());
                            } else _items.setValue(new ArrayList<>());
                        } else _items.setValue(new ArrayList<>());
                    }

                    @Override
                    public void onFailure(Call<NetworkResponse> call, Throwable t) {
                        _items.setValue(new ArrayList<>());
                    }
                }
        );
    }

}
