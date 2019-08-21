package com.hosnydev.recyclerviewjson;

public class Model {

    private String imageUrl, Name;
    private int Like;

    public Model(String imageUrl, String name, int like) {
        this.imageUrl = imageUrl;
        Name = name;
        Like = like;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getName() {
        return Name;
    }

    public int getLike() {
        return Like;
    }
}
