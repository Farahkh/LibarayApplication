package com.example.libraryapplication.utility;

public interface VolleyResponseListener {
    void onError(String message);

    void onResponse(Object response);
}
