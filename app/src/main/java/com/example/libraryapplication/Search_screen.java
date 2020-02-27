package com.example.libraryapplication;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.libraryapplication.component.Book;
import com.example.libraryapplication.component.BookInfo;
import com.example.libraryapplication.controlers.KeywordSearch;
import com.example.libraryapplication.utility.KeyWordSearchAdapter;
import com.example.libraryapplication.utility.LibraryConnection;
import com.example.libraryapplication.utility.VisualSearchAdapter;
import com.example.libraryapplication.utility.VolleyResponseListener;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class Search_screen extends Fragment {

    TextInputEditText form;
    Button search;
    RecyclerView list;
    private ArrayList<BookInfo> books;
    private String text;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.search_screen,null);

        form = view.findViewById(R.id.search_input);
        search = view.findViewById(R.id.search);
        list = view.findViewById(R.id.books_list);

        list.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        list.setLayoutManager(layoutManager);
        list.addItemDecoration(new DividerItemDecoration(getContext(),
                DividerItemDecoration.VERTICAL));


        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text = form.getText().toString();

                KeywordSearch keySearch = new KeywordSearch(getContext());
                keySearch.search(text,listener);
//                LibraryConnection connection = new LibraryConnection(getContext());

//                connection.sendKeyWord(text,listener);
            }
        });


        return view;
    }

    VolleyResponseListener listener = new VolleyResponseListener() {
        @Override
        public void onError(String message) {
            // do something...
        }

        @Override
        public void onResponse(Object response) {
            books = (ArrayList<BookInfo>) response;
            displayTitles(books);
        }
    };


    private void displayTitles(ArrayList<BookInfo> books) {
        KeyWordSearchAdapter booksListAdapter = new KeyWordSearchAdapter(books, getContext());
        list.setAdapter(booksListAdapter);
    }

}
