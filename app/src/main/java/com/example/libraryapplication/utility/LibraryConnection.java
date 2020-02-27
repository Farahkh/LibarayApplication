package com.example.libraryapplication.utility;

import android.content.Context;
import android.util.Log;
import android.util.SparseArray;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.libraryapplication.component.Book;
import com.example.libraryapplication.component.BookInfo;
import com.google.android.gms.vision.text.Line;
import com.google.android.gms.vision.text.TextBlock;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LibraryConnection {

Context context;
    public static final String LIBRARY_ADDRESS = "http://192.168.1.11:3000/library/";
//    public static final String LIBRARY_ADDRESS = "http://10.1.201.160:3000/library/";


    public LibraryConnection(Context context) {
        this.context = context;
    }

    public void sendDetections(SparseArray<TextBlock> detections, final VolleyResponseListener listener) {
        if (detections != null) {
            final ArrayList<Book> finalBooksList = new ArrayList<>();

            for (int i = 0; i < detections.size(); ++i) {
                final TextBlock block = detections.valueAt(i);
                List<Line> lines = (List<Line>) block.getComponents();
                Line line = null;
                for (int j = 0; j < lines.size(); ++j) {
                    line = lines.get(j);
                }
                String url = LIBRARY_ADDRESS + line.getValue();
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                        (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {

                                BookInfo bookIfo = new BookInfo();
                                Book book = null;
                                if (response != null) {
                                    try {
                                        bookIfo.setTitle(response.get("title").toString());
                                        bookIfo.setAuthor(response.get("author").toString());
                                        bookIfo.setDescription(response.get("description").toString());
                                        bookIfo.setPublisher(response.get("publisher").toString());


                                        Log.d("OcrDetectorProcessor", "Text detected! " +
                                                block.getValue());
//                                        Log.d("OcrDetectorProcessor", "Text bounding box: " +
//                                                "left_ " + block.getBoundingBox().left +
//                                                ", top_" + block.getBoundingBox().top +
//                                                ", right_" + block.getBoundingBox().right +
//                                                ", bottom_" + block.getBoundingBox().bottom);

                                        bookIfo.print();
                                        Log.d("OcrDetectorProcessor", "-------------------------------------------------------------------");
                                        book = new Book(bookIfo, block.getBoundingBox());
                                        finalBooksList.add(book);
                                        listener.onResponse(finalBooksList);

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                error.printStackTrace();
                            }
                        });
                DBConnection.getInstance(context).addToRequestQueue(jsonObjectRequest);
            }
            }
        }


    public void sendKeyWord(String text,final VolleyResponseListener listener) {

        final ArrayList<BookInfo> finalBooksList = new ArrayList<>();
        String url = LIBRARY_ADDRESS + text;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        BookInfo bookIfo = new BookInfo();
                        if (response != null) {
                            try {
                                bookIfo.setTitle(response.get("title").toString());
                                bookIfo.setAuthor(response.get("author").toString());
                                bookIfo.setDescription(response.get("description").toString());
                                bookIfo.setPublisher(response.get("publisher").toString());

                                finalBooksList.add(bookIfo);
                                listener.onResponse(finalBooksList);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });
        DBConnection.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }
}

