package com.example.cosmeticreview.model;

public class RatingsComments {
    private String comment;
    private double rating;

    public RatingsComments(String comment, double rating) {
        this.setComment(comment);
        this.setRating(rating);
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
}
