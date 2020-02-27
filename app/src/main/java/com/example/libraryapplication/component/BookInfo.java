package com.example.libraryapplication.component;

import android.util.Log;

import java.io.Serializable;
import java.util.Date;

public class BookInfo implements Serializable {

    private String title;
    private String author;
    private String description;
    private String publisher;
    private Date pDate;
    private float version;
    private Long isbn;

    public BookInfo(){

    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public Date getpDate() {
        return pDate;
    }

    public void setpDate(Date pDate) {
        this.pDate = pDate;
    }

    public float getVersion() {
        return version;
    }

    public void setVersion(float version) {
        this.version = version;
    }

    public Long getIsbn() {
        return isbn;
    }

    public void setIsbn(Long isbn) {
        this.isbn = isbn;
    }



    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }


    public String getDescription() {
        return description;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String title) {
        this.author = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void print() {
        Log.d("OcrDetectorProcessor ", "book's title: " + getTitle() + ", book's Author " + getAuthor());

    }


}

