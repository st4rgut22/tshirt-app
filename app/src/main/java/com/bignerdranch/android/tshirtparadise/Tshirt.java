package com.bignerdranch.android.tshirtparadise;

import android.widget.ImageView;

import java.io.Serializable;

public class Tshirt implements Serializable{
    public Tshirt(int id,float price,String title,String image){
        this.price=price;
        this.title=title;
        this.id=id;
        this.previewImg = image;
        this.quantity=1;
    }
    private String previewImg; //name of the drawable
    private String title;
    private int id;
    private float price;
    private boolean checked;
    private int quantity;

    public boolean getChecked(){
        return checked;
    }

    public void addTee(){
        quantity++;
    }

    public void removeTee(){
        quantity--;
    }

    public int getQuantity(){
        return quantity;
    }

    public void setQuantity(int quantity){
        this.quantity=quantity;
    }

    public void setChecked(boolean check){
        this.checked=check;
    }
    public String getPreviewImg(){
        return previewImg;
    }
    public Float getPrice() {
        return price;
    }

    public String getTitle() {
        return title;
    }

    public int getId() {
        return id;
    }



}
