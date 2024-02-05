package com.android.moviedb.network;

import com.android.moviedb.modals.NetworkResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Api {

    @GET("now_playing")
    Call<NetworkResponse> getMovieList(
            @Query("api_key") String key,
            @Query("language") String lang,
            @Query("page") Integer page
    );

}
