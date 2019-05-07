package com.example.locationtest;

import java.util.List;

//import io.futurestud.retrofit1.api.model.GitHubRepo;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GutHubClient {

    @GET("/user/{user}/repos")
    Call<List<GitHubRepo>> reposForUser(@Path("user") String user);
}
