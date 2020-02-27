package com.example.libraryapplication.utility;

import android.graphics.Rect;

import com.google.android.gms.vision.text.Line;

import java.util.Map;
import java.util.TreeMap;

public class KeyWordObject {

    private final int height;
    private final int top;
    private final int left;
    private TreeMap<Integer, String> text = new TreeMap<>();
    private Rect boundingbox;



    public String getText() {
        StringBuilder word = new StringBuilder();

        for (Map.Entry entry : text.entrySet()){
            String reg = ".*" + entry.getValue() + ".*";
            word.append(reg);
        }

        return word.toString();
    }

    public Rect getBoundingbox() {
        return boundingbox;
    }

    public KeyWordObject(Line line){
        boundingbox = line.getBoundingBox();
        height = boundingbox.height();
        top = boundingbox.top;
        left = boundingbox.left;
        text.put(left, line.getValue());
    }

    public boolean containsIn(Line line){

        Rect lineRect = line.getBoundingBox();

        boolean test = false;
        if (lineRect.top >= this.top-5 && lineRect.top<= this.top+5)
            if (lineRect.height() <= this.height && lineRect.height() >= (height/2))
                test = true;

        return test;
    }




    public void append(KeyWordObject keyword, Line line){

        int topLine = line.getBoundingBox().top;

//       calculate the final text

        text.put(topLine ,line.getValue());

//        calculate the final bounding box

        boundingbox.right = boundingbox.right + line.getBoundingBox().width();
        boundingbox.bottom = boundingbox.bottom + line.getBoundingBox().width();

    }

}
