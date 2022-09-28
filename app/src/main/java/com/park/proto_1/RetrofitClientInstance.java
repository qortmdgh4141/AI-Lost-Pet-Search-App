package com.park.proto_1;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClientInstance {
    private static Retrofit retrofit;
    private static RetrofitClientInstance instance = null;
    private static RetrofitInterface retrofitInterface;
    // BaseUrl등록
    private static final String BASE_URL = "http://openapi.gg.go.kr";

    private RetrofitClientInstance() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retrofitInterface = retrofit.create(RetrofitInterface.class);
    }

    public static RetrofitClientInstance getInstance() {
        if (instance == null) {
            instance = new RetrofitClientInstance();
        }
        return instance;
    }

    public static RetrofitInterface getRetrofitInterface() {
        return retrofitInterface;
    }
}
