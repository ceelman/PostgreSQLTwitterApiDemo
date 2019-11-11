package com.example.demo.models;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.data.annotation.Id;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "simple")
public class SimpleModel {

    @Id
    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", unique=true, nullable = false)
    private int id;
    @Column(name="username")
    private String username;
    @Column(name="text")
    private String text;
    @Column(name="date")
    @NotFound(action= NotFoundAction.IGNORE)
    private Date date;
    @Column(name="favorite_count")
    private int favoriteCount;


    public SimpleModel() {}

    public SimpleModel(String username, String text, Date date, int favoriteCount) {
        this.username = username;
        this.text = text;
        this.date = date;
        this.favoriteCount = favoriteCount;
    }

    public String toString() {
        return "{\nid: " + this.id + ",\nScreen Name: " + this.username + ",\nText: " + this.text + ",\nDate: " + this.date + "\n}";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
