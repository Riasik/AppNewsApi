package com.ryasik.appnewsapi.API;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

        public  static  final String Base_URL = "https://newsapi.org/v2/";
        public static Retrofit retrofit = null;

        public static Retrofit getClient(){
            if (retrofit==null){
                retrofit = new Retrofit.Builder()
                        .baseUrl(Base_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
            }

            return retrofit;
        }
    }

