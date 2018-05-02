package com.ryasik.appnewsapi.API;

import com.ryasik.appnewsapi.Model.Source;
import com.ryasik.appnewsapi.Model.articlesResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("everything")
    Call<articlesResponse> getItems(@Query("sources") String source,
                                    @Query("page") int pageNumber,
                                    @Query("apiKey") String apiKey);
    @GET("sources")
    Call<Source> getListSource(@Query("language") String languange,
                               @Query("apiKey") String apiKey);

}
