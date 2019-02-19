package com.example.googlebooks;

import android.content.Context;
import android.support.v4.app.LoaderManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;

public class BookAdapter extends ArrayAdapter<Book> {
    private ArrayList<Book> bookArrayList;
    public BookAdapter(Context context, ArrayList<Book> books)
    {
        super(context,0,books);
        bookArrayList=books;
    }
    @Override
    public View getView(int position,View convertView,ViewGroup parent) {
        Book book =getItem(position);

        if(convertView==null)
        {
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.books,parent,false);
            Log.v("yuvateja","here is the error");
        }
        TextView title =(TextView)convertView.findViewById(R.id.Title);
        TextView author =(TextView)convertView.findViewById(R.id.author);
        TextView rating =(TextView)convertView.findViewById(R.id.rating);
        if(book.getmRating()>0.0)
        {
         rating.setText(""+book.getmRating());
        }
        else {
            rating.setText("N/A");
        }
        title.setText(book.getmTitle());
        author.setText(book.getmAuthor());
        ImageView imageView = (ImageView)convertView.findViewById(R.id.image);
        imageView.setImageResource(R.drawable.book);
        return convertView;
    }
    @Override
    public Book getItem(int position) {
        return bookArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public void addAll(Book... collection) {
        clear();
        super.addAll(collection);
        notifyDataSetChanged();
    }

    @Override
    public void clear() {
        super.clear();
        notifyDataSetChanged();
    }
}
