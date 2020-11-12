package com.example.cosmeticreview.model;

import java.io.Serializable;

public class CosmeticReviewData implements Serializable {
    private String id;
    private  String title;
    private String description;
    private String comments;
    private String imageUrl;

    public CosmeticReviewData(){}

    public CosmeticReviewData(String title, String description, String imageUrl) {
        this.setId(id);
        this.setTitle(title);
        this.setDescription(description);
        this.setImageUrl(imageUrl);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
