package com.example.googlebooks;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;

import java.util.ArrayList;

public class BookLoader extends AsyncTaskLoader<ArrayList<Book>> {
    public String url;
    public BookLoader(Context context,String url)
    {
        super(context);
        this.url=url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public ArrayList<Book> loadInBackground() {
        if(url==null)
        {
            return null;
        }
        ArrayList<Book> books=QueryUtils.fetchFromUrl(url);
        return books;
    }
}
