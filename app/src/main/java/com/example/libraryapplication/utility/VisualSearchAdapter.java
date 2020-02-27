package com.example.libraryapplication.utility;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.libraryapplication.BookDetails;
import com.example.libraryapplication.R;
import com.example.libraryapplication.component.Book;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class VisualSearchAdapter extends RecyclerView.Adapter<VisualSearchAdapter.ViewHolder> {
    public static final String BOOK_INFO = "bookInfo";
    public static final String TITLE_RECTANGLE = "rectangle";
    public static final String IMAGE_BITMAP = "shelf_image";
    public static final String BOX = "boundingbox";


    private final List<Book> bookTitle;
    private final Context context;
    private Bitmap bitmap;

    public VisualSearchAdapter(List<Book> bookTitle, Context context, Bitmap image) {
        this.bookTitle = bookTitle;
        this.context = context;
        this.bitmap = image;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.books_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final VisualSearchAdapter.ViewHolder holder, int position) {
        final Book bTitle = bookTitle.get(position);
        holder.title.setText(bTitle.get_title());

//        TextView nAuthor = (TextView) holder.book_info.findViewById(R.id.author);
//        nAuthor.setText(bTitle.get_author());
//
//        TextView description = (TextView) holder.book_info.findViewById(R.id.description);
//        description.setText(bTitle.get_description());
//
//        TextView npublisher = (TextView) holder.book_info.findViewById(R.id.publisher);
//        npublisher.setText(bTitle.get_Publisher());
//
//        TextView nIsbn = (TextView) holder.book_info.findViewById(R.id.isbn);
//        nIsbn.setText("2557896334");
//
//        TextView nPDate = (TextView) holder.book_info.findViewById(R.id.pDate);
//        nPDate.setText("1995");



//        final TitleRectangle rectangle = new TitleRectangle(context, bTitle.get_boundingBox(), bitmap.getHeight());
//        if (rectangle!=null)
//            bitmap.addView(rectangle);
//        final SpeakMetadata speakTitle = new SpeakMetadata(context);
        holder.setItemClickLister(new ItemClickLister(){

            @Override
            public void onClick(View view, int position) {


                    ByteArrayOutputStream bs = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 50, bs);
                    byte[] bytes = bs.toByteArray();

                    //Pop intent
                    Intent intent = new Intent(context, BookDetails.class);
                    intent.putExtra(BOOK_INFO, bTitle.get_Info());
                    intent.putExtra(BOX, bTitle.get_boundingBox());
                    intent.putExtra(IMAGE_BITMAP, bytes);
                    context.startActivity(intent);

//                if (view.getId() == R.id.book_title) {
//                    if (holder.book_info.getVisibility() == View.VISIBLE) {
//                        holder.book_info.setVisibility(View.GONE);
//                        bitmap.removeView(rectangle);
//                               } else {
//                        holder.book_info.setVisibility(View.VISIBLE);
//                        speakTitle.speak(bTitle.get_title());

//                    }            }
            }});

    }

    @Override
    public int getItemCount() {
        return bookTitle.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private Context context;
        private TextView title;
//        private LinearLayout book_info;
        private ItemClickLister itemClickLister;

        public ViewHolder(View itemView) {
            super(itemView);
            title= itemView.findViewById(R.id.book_title);
//            book_info = itemView.findViewById(R.id.book_info);
            context = itemView.getContext();
//            book_info.setVisibility(View.GONE);

//            LayoutInflater inflater = LayoutInflater.from(context);
//            View view = inflater.inflate(R.layout.bookinfo, book_info);

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
