package com.example.dell.myapplication;

public class Data {
    private String name;
    private int imageId;
    public Data(String name,int imageId){
        this.name = name;
        this.imageId = imageId;
    }
    public String getName(){
        return name;
    }
    public int getImageId(){
        return imageId;
    }
}
