package com.android.moviedb.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Retro {

    private static Retro retro;
    private static Retrofit ret;

    private Retro() {
        Gson gson = new GsonBuilder().setLenient().create();
        ret = new Retrofit.Builder().baseUrl(Constants.BASE_LINK)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    public static synchronized Retro getInstance() {
        if (retro == null)
            retro = new Retro();
        return retro;
    }

    public Api api() {
        return ret.create(Api.class);
    }
}
