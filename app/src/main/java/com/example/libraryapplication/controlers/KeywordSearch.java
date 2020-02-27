package com.example.libraryapplication.controlers;

import android.content.Context;
import android.util.SparseArray;

import com.example.libraryapplication.utility.LibraryConnection;
import com.example.libraryapplication.utility.VolleyResponseListener;
import com.google.android.gms.vision.text.TextBlock;

import androidx.constraintlayout.widget.ConstraintLayout;


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
