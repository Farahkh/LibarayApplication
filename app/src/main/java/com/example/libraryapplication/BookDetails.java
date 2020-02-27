package com.example.libraryapplication;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.libraryapplication.component.BookInfo;
import com.example.libraryapplication.controlers.SpeakMetadata;
import com.example.libraryapplication.utility.TitleRectangle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import static com.example.libraryapplication.utility.VisualSearchAdapter.BOOK_INFO;
import static com.example.libraryapplication.utility.VisualSearchAdapter.BOX;
import static com.example.libraryapplication.utility.VisualSearchAdapter.IMAGE_BITMAP;

public class BookDetails extends AppCompatActivity {

    TextView title;
    TextView author;
    TextView description;
    TextView publisher;
    TextView isbn;
    TextView date;
    TextView section;
    TextView status;
    BookInfo bookdetails;
    SpeakMetadata speakMetadata;
    TitleRectangle rectangle;
    Bitmap image;
    ImageView shelf;
    Rect boundingbox;
    RelativeLayout layout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bookdetails);

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        title = findViewById(R.id.title);
        author = findViewById(R.id.author);
        description = findViewById(R.id.description);
        publisher = findViewById(R.id.publisher);
        isbn = findViewById(R.id.isbn);
        date = findViewById(R.id.pDate);
        section = findViewById(R.id.textView14);
        status = findViewById(R.id.textView16);
        shelf = findViewById(R.id.shelf_image);
        layout = findViewById(R.id.layout);

        speakMetadata = new SpeakMetadata(this);

        Intent intent = getIntent();
        bookdetails = (BookInfo) intent.getSerializableExtra(BOOK_INFO);
        boundingbox = (Rect) intent.getParcelableExtra(BOX);
        byte[] byteArray = getIntent().getByteArrayExtra(IMAGE_BITMAP);

        image = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);

        rectangle = new TitleRectangle(this, boundingbox, image.getHeight());

            title.setText(bookdetails.getTitle());
            author.setText(bookdetails.getAuthor());
            description.setText(bookdetails.getDescription());
            publisher.setText(bookdetails.getPublisher());
            isbn.setText("2557896334");
            date.setText("1995");
            shelf.setImageBitmap(image);

            layout.addView(rectangle);

    }

    public void speak(View view){

        if(speakMetadata.active())
            speakMetadata.stop();

        String viewTag = (String) view.getTag();

        switch (viewTag){
                case "title":
                    speakMetadata.speak("book title");
                    speakMetadata.speak(bookdetails.getTitle());
                    break;
                case "author_section":
                    speakMetadata.speak("book author");
                    speakMetadata.speak(bookdetails.getAuthor());
                    break;
                case "description_section":
                        speakMetadata.speak("book description");
                        speakMetadata.speak(bookdetails.getDescription());
                    break;
                case "publisher_section":
                        speakMetadata.speak("publisher");
                        speakMetadata.speak(bookdetails.getPublisher());
                    break;
                case "isbn_section":
                        speakMetadata.speak("book isbn number");
                        speakMetadata.speak("2557896334");
                    break;
                case "date_section":
                        speakMetadata.speak("publishing date");
                        speakMetadata.speak("1995");
                    break;
                case "section":
                    speakMetadata.speak("book section");
                    speakMetadata.speak("fiction");
                    break;
                case "status_section":
                    speakMetadata.speak("book status");
                    speakMetadata.speak("Can not be borrowed");
                    break;
            }
        }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        speakMetadata.sutDown();
    }

    public boolean onOptionsItemSelected(MenuItem item){

        return super.onOptionsItemSelected(item);
    }
}
