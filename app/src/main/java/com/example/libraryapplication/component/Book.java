package com.example.libraryapplication.component;

import android.graphics.Rect;

import java.io.Serializable;
import java.util.Date;

public class Book implements Serializable  {
    private BookInfo info;
    private Rect boundingBox;

    public Book(BookInfo info, Rect  boundingBox){
        this.info = info;
        this.boundingBox = boundingBox;
    }

    public void setInfo(BookInfo info) {
        this.info = info;
    }

    public void setBoundingBox(Rect boundingBox) {
        this.boundingBox = boundingBox;
    }

    public BookInfo get_Info() {
        return info;
    }

    public String get_title(){
        return info.getTitle();
    }

    public String get_author(){
        return info.getAuthor();
    }

    public String get_description(){
        return info.getDescription();
    }

    public String get_Publisher(){
        return info.getPublisher();
    }

    public Date get_pDate(){
        return info.getpDate();
    }

    public Number get_Version(){
        return info.getVersion();
    }

    public Long get_isbn(){ return info.getIsbn(); }

    public Rect get_boundingBox(){
        return boundingBox;
    }
}
