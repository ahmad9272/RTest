package com.example.myapplicationtest;


import com.google.gson.annotations.SerializedName;

public class Post {
    private Integer id;
    private double locationManager;
    private double locationListener;
    private double textViewResult;

    
    public Post(double locationManager, double locationListener,double textViewResult) {
        this.locationManager = locationManager;
        this.locationListener = locationListener;
        this.textViewResult = textViewResult;

    }

    public Integer getId() {
        return id;
    }

    /*private int userId;

    private Integer id;

    private String title;


    @SerializedName("body")
    private String text;


    public Post(int userId, String title, String text) {
        this.userId = userId;
        this.title = title;
        this.text = text;
    }

    public int getUserId() {
        return userId;
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }*/
}
