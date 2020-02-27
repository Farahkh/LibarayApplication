package com.example.libraryapplication.controlers;

import android.app.Activity;
import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.util.Log;

import java.util.Locale;

public class SpeakMetadata {

    private TextToSpeech tts;

    public SpeakMetadata(Context context){
        TextToSpeech.OnInitListener listener =
                new TextToSpeech.OnInitListener() {
                    @Override
                    public void onInit(final int status) {
                        if (status == TextToSpeech.SUCCESS) {
                            Log.d("OnInitListener", "Text to speech engine started successfully.");
                            tts.setLanguage(Locale.US);
                        } else {
                            Log.d("OnInitListener", "Error starting the text to speech engine.");
                        }
                    }
                };
        tts = new TextToSpeech(context, listener);
    }

    public void speak(String word){
        Log.d("OnInitListener", "spoken text " + word);

        tts.speak(word, TextToSpeech.QUEUE_ADD, null, "DEFAULT");
    }

    public void sutDown(){
        tts.shutdown();
    }

    public void stop(){
        tts.stop();
    }


    public boolean active() {
      return tts.isSpeaking();

    }
}
