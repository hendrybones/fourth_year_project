package com.example.cosmeticreview;

public class CommentData {

    private String id;
    private  String comments;
    private  String rating;

    public CommentData(String id, String comments, String rating) {
        this.setId(id);
        this.setComments(comments);
        this.setRating(rating);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}
