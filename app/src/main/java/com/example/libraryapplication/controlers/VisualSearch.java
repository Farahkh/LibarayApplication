package com.example.libraryapplication.controlers;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.SparseArray;

import com.example.libraryapplication.MainActivity;
import com.example.libraryapplication.component.Book;
import com.example.libraryapplication.utility.LibraryConnection;
import com.example.libraryapplication.utility.VolleyResponseListener;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

import java.util.ArrayList;

public class VisualSearch {

    Context context;

    public VisualSearch(Context context) {
        this.context = context;
    }

    public void recognizeText(Bitmap image_view, VolleyResponseListener listener) {
        Frame frame = new Frame.Builder().setBitmap(image_view).setRotation(Frame.ROTATION_90).build();

        TextRecognizer textRecognizer = new TextRecognizer.Builder(context)
                .build();

        //TODO add additional recognition session to a sliced image to optimize recognition result

        if (!textRecognizer.isOperational()) {
            new AlertDialog.Builder(context)
                    .setMessage("Text recognizer could not be set up on your device :(").show();
        }

        SparseArray<TextBlock> text = textRecognizer.detect(frame);
        search(text,listener);

    }


    public void search(SparseArray<TextBlock> detections,VolleyResponseListener listener){
        LibraryConnection connection = new LibraryConnection(context);

        connection.sendDetections(detections,listener);
    }

}
