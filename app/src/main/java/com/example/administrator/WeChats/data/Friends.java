package com.example.administrator.WeChats.data;

import org.litepal.crud.LitePalSupport;

public class Friends extends LitePalSupport{
    private int id;
    private String name;
    private int number;
    private boolean sex;

    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public int getNumber() {
        return number;
    }
    public boolean isSex() {
        return sex;
    }
    public void setId(int id){
        this.id=id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setNumber(int number) {
        this.number = number;
    }
    public void setSex(boolean sex) {
        this.sex = sex;
    }
}
