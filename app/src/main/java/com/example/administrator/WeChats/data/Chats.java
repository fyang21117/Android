package com.example.administrator.WeChats.data;

public class Chats {
    private String name;
    private int imageId;
    public Chats(String name,int imageId)
    {
        this.name=name;
        this.imageId=imageId;
    }

    public String getName() {
        return name;
    }

    public int getImageId() {
        return imageId;
    }
}
