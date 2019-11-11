package com.example.demo.models;

import com.example.demo.models.SimpleModel;
import java.util.Date;

public class SimpleModelBuilder {

    private String _id;
    private String username;
    private String text;
    private Date date;
    private int favoriteCount;

    public SimpleModelBuilder() {}

    public SimpleModel build() {
        return new SimpleModel(this.username,
                this.text,
                this.date,
                this.favoriteCount);
    }

    public String getId() {
        return _id;
    }

    public void setId(String id) {
        this._id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) { this.text = text; }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getFavoriteCount() {
        return favoriteCount;
    }

    public void setFavoriteCount(int favoriteCount) {
        this.favoriteCount = favoriteCount;
    }
}
