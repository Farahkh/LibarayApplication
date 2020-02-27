package com.example.libraryapplication.utility;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.libraryapplication.R;
import com.example.libraryapplication.component.BookInfo;
import com.example.libraryapplication.controlers.SpeakMetadata;

import java.util.ArrayList;
import java.util.List;
import androidx.recyclerview.widget.RecyclerView;

public class KeyWordSearchAdapter extends RecyclerView.Adapter<KeyWordSearchAdapter.ViewHolder> {

    private final List<BookInfo> bookTitle;
    private final Context context;

    public KeyWordSearchAdapter(ArrayList<BookInfo> bookTitle, Context context) {
        this.bookTitle = bookTitle;
        this.context = context;
    }

    @Override
    public KeyWordSearchAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.books_list, parent, false);
        return new KeyWordSearchAdapter.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(final KeyWordSearchAdapter.ViewHolder holder, int position) {
        final BookInfo bTitle = bookTitle.get(position);
        final SpeakMetadata speakMetadata = new SpeakMetadata(context);


        holder.title.setText(bTitle.getTitle());

        TextView nAuthor = (TextView) holder.book_info.findViewById(R.id.author);
        nAuthor.setText(bTitle.getAuthor());
        nAuthor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (speakMetadata.active()) speakMetadata.stop();
                speakMetadata.speak("book author");
                speakMetadata.speak(bTitle.getAuthor());
            }
        });

        TextView description = (TextView) holder.book_info.findViewById(R.id.description);
        description.setText(bTitle.getDescription());
        description.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (speakMetadata.active()) speakMetadata.stop();
                speakMetadata.speak("book description");
                speakMetadata.speak(bTitle.getDescription());
            }
        });

        TextView npublisher = (TextView) holder.book_info.findViewById(R.id.publisher);
        npublisher.setText(bTitle.getPublisher());
        npublisher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (speakMetadata.active()) speakMetadata.stop();

                speakMetadata.speak("publisher");
                speakMetadata.speak(bTitle.getPublisher());
            }
        });

        TextView nIsbn = (TextView) holder.book_info.findViewById(R.id.isbn);
        nIsbn.setText("2557896334");
        nIsbn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (speakMetadata.active()) speakMetadata.stop();

                speakMetadata.speak("book isbn number");
                speakMetadata.speak("2557896334");
            }
        });

        TextView nPDate = (TextView) holder.book_info.findViewById(R.id.pDate);
        nPDate.setText("1995");
        nPDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (speakMetadata.active()) speakMetadata.stop();

                speakMetadata.speak("publishing date");
                speakMetadata.speak("1995");
            }
        });

        TextView section = holder.book_info.findViewById(R.id.textView14);
        section.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (speakMetadata.active()) speakMetadata.stop();

                speakMetadata.speak("book section");
                speakMetadata.speak("Fiction");
            }
        });
        TextView status = holder.book_info.findViewById(R.id.textView16);
        status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (speakMetadata.active()) speakMetadata.stop();

                speakMetadata.speak("book status");
                speakMetadata.speak("Can not be borrowed");
            }
        });


        holder.setItemClickLister(new ItemClickLister(){

            @Override
            public void onClick(View view, int position) {

                if (view.getId() == R.id.book_title) {
                    if (holder.book_info.getVisibility() == View.VISIBLE) {
                        if (speakMetadata.active()) speakMetadata.stop();
                        holder.book_info.setVisibility(View.GONE);
                    } else {
                        holder.book_info.setVisibility(View.VISIBLE);
                        if (speakMetadata.active()) speakMetadata.stop();
                        speakMetadata.speak("book title");
                        speakMetadata.speak(bTitle.getTitle());
                    }
                }
            }});

    }



    @Override
    public int getItemCount() {
        return bookTitle.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private Context context;
        private TextView title;
        private LinearLayout book_info;

        private ItemClickLister itemClickLister;


        public ViewHolder(View itemView) {
            super(itemView);
            title= itemView.findViewById(R.id.book_title);
            book_info = itemView.findViewById(R.id.book_info);
            context = itemView.getContext();
            book_info.setVisibility(View.GONE);

            LayoutInflater inflater = LayoutInflater.from(context);
            View view2 = inflater.inflate(R.layout.bookinfo, book_info);

            title.setOnClickListener(this);

        }

        public void setItemClickLister(ItemClickLister itemClickLister){
            this.itemClickLister = itemClickLister;
        }

        @Override
        public void onClick(View v) {
            itemClickLister.onClick(v, getAdapterPosition());

        }
    }
}
