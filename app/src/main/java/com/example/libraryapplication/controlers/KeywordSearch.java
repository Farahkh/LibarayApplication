package com.example.libraryapplication.controlers;

import android.content.Context;

import com.example.libraryapplication.utility.LibraryConnection;
import com.example.libraryapplication.utility.VolleyResponseListener;


public class KeywordSearch {

    Context context;

    public KeywordSearch(Context context){
        this.context = context;
    }

    public void search(String text, VolleyResponseListener listener){
        LibraryConnection connection = new LibraryConnection(context);

        connection.sendKeyWord(text,listener);
    }
}
