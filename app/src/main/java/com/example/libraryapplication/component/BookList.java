package com.example.libraryapplication.component;

import java.util.ArrayList;

public class BookList {

    private static BookList mInstance;
    public static ArrayList<Book> bookList;
//    private Context context;


    private BookList(){
        bookList= new ArrayList<>();
    }

    public static synchronized BookList getInstance() {
        if (mInstance == null) {
            mInstance = new BookList();
            }
        return mInstance;
    }

    public ArrayList<Book> add(Book book){
        bookList.add(book);

       return bookList;
    }

    public ArrayList<Book> getBooks(){
        return bookList;
    }

    public void empty_list(){

        bookList.clear();
    }


    @Override
    public String toString() {
        if (bookList.isEmpty())
            return "the list is empty";
        else
            return "the list is full";
    }
}
