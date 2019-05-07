package com.example.myapplicationtest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface JsonPlaceHolderApi {

    @GET("posts")
    Call<List<Post>> getposts();



    @POST("posts")
    Call<Post> createPost(@Body Post posts);


    @FormUrlEncoded
    @POST("posts")
    Call<Post> createPost(
            @Field("locationManager") double locationManager,
            @Field("locationListener") double locationListener,
            @Field("body") String text

    );




}
