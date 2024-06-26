package com.example.communnect;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;


public interface RetrofitAPI {
    @GET
    Call<NewsModal> getAllNews(@Url String url);

    @GET
    Call<NewsModal> getNewsByCategory(@Url String url);

    @GET
    Call<NewsModal> getNewsBySearch(@Url String url);

    @GET("favorite-articles")
    Call<NewsModal> getFavoriteArticles();

}