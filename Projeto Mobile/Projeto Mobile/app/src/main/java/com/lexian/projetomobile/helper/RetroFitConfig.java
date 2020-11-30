package com.lexian.projetomobile.helper;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroFitConfig {

    public static Retrofit getRetrofit(){
        return  new Retrofit.Builder()
                .baseUrl(YoutubeConfig.URL_BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
