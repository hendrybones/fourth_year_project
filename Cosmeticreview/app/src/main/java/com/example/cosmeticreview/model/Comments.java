package com.example.cosmeticreview.model;

import java.util.Date;

public class Comments {
    private String comment;
    private Date date;


    public Comments(String comment) {
        this.comment = comment;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
