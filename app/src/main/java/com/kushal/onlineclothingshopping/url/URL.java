package com.kushal.onlineclothingshopping.url;


import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class URL {

    public static String BASE_URl = "http://10.0.2.2:3000/";

    public static Retrofit getRetrofitInstance() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit;
    }
}
