package com.example.Luengas_Movie_Tickets;


// Set up getters and setters for MovieDB API
public class MovieModel {

    String title;
    String synopsis;
    String img;
    String rd;

    public MovieModel(String title, String synopsis, String img, String rd) {
        this.title = title;
        this.synopsis=synopsis;
        this.img = img;
        this.rd = rd;
    }

    public MovieModel() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getRd() {
        return rd;
    }

    public void setRd(String rd) {
        this.rd = rd;
    }
}
