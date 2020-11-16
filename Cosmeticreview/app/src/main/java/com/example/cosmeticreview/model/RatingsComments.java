package com.example.cosmeticreview.model;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.cosmeticreview.R;
import com.example.cosmeticreview.views.CommentActivity;

import java.io.Serializable;
import java.util.Date;

public class RatingsComments implements Serializable {




    private String id;
    private String username;
    private String comment;
    private double rating;
    private String date;

    public RatingsComments(String username, String comment, double rating, String date) {
        this.setId(id);
        this.comment = comment;
        this.rating = rating;
        this.date = date;
        this.username = username;
    }
    public RatingsComments(){}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
