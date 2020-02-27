package com.example.libraryapplication.utility;

import android.util.SparseArray;

import com.google.android.gms.vision.text.Line;
import com.google.android.gms.vision.text.TextBlock;

import java.util.List;

public class KeyWordBuilder {

    SparseArray<TextBlock> items;
    private TextBlock textBlock;
    private List<?> item;
    KeyWordObject keyword = null;
    SparseArray<KeyWordObject> keywordsList = new SparseArray<>();


    public KeyWordBuilder(SparseArray<TextBlock> detections){
        items = detections;
    }



    public SparseArray<KeyWordObject> build(){

        for (int i = 0; i < items.size(); ++i) {

            textBlock = items.valueAt(i);
            item = textBlock.getComponents();
            if(item.get(0) instanceof Line) {
                for (Object l : item) {
                    keywordsList = analyse(l);
                }
            }else{
                keywordsList = analyse(textBlock);
            }

        }
//
        return keywordsList;
    }

    private <T> SparseArray<KeyWordObject> analyse(T line){


        if (keywordsList.size()==0){

            keyword = new KeyWordObject((Line) line);
            keywordsList.put(0, keyword);

        } else {
            for(int i =0; i<= keywordsList.size()-1; ++i){
                KeyWordObject word = keywordsList.get(i);
                if (word.containsIn((Line) line)){
                    word.append(word, (Line) line);
                    keywordsList.put(i, word);
                }
            }
        }
        return keywordsList;
    }


}
