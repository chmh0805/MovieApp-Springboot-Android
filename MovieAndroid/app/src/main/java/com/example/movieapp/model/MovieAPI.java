package com.example.movieapp.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface MovieAPI {

    @GET("api/movie")
    Call<List<Movie>> initMovies();

    @GET("/movie/download/1")
    Call<Yts> downloadMovies();

    @DELETE("api/movie/{id}")
    Call<Void> deleteMovie(@Path("id") long id);

    @DELETE("api/movie/All")
    Call<Void> deleteAll();

    Gson gson = new GsonBuilder().setLenient().create();

    public static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8000/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build();
}
