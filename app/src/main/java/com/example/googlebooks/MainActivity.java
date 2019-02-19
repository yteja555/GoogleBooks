package com.example.googlebooks;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements android.support.v4.app.LoaderManager.LoaderCallbacks<ArrayList<Book>> {
    private static final int BOOKS_LOADER_ID=1;
    String url ="";
    String url1="https://www.googleapis.com/books/v1/volumes?q=";
    BookAdapter bookAdapter;
    TextView emptystate ;
    NetworkInfo networkInfo;
    public ConnectivityManager connectivityManager;
    android.support.v4.app.LoaderManager loaderManager;
    ProgressBar progressBar ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        emptystate =(TextView)findViewById(R.id.emptyView);
        emptystate.setText("");
        ListView listView =(ListView)findViewById(R.id.list);
        listView.setEmptyView(emptystate);
        bookAdapter=new BookAdapter(this,new ArrayList<Book>());
        listView.setAdapter(bookAdapter);
        SearchView searchView =(SearchView)findViewById(R.id.search);


        progressBar =(ProgressBar)findViewById(R.id.loading);
        progressBar.setVisibility(View.GONE);


        connectivityManager =(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        networkInfo = connectivityManager.getActiveNetworkInfo();


        progressBar =(ProgressBar)findViewById(R.id.loading);
        progressBar.setVisibility(View.VISIBLE);


        if (networkInfo != null && networkInfo.isConnected()) {
            // Get a reference to the LoaderManager, in order to interact with loaders.
//            LoaderManager loaderManager = getSupportLoaderManager();
            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).
//            loaderManager.initLoader(BOOKS_LOADER_ID, null, this);
            loaderManager=getSupportLoaderManager();
            loaderManager.initLoader(BOOKS_LOADER_ID,null,MainActivity.this);
        } else
        {
            progressBar =(ProgressBar)findViewById(R.id.loading);
            progressBar.setVisibility(View.GONE);
            emptystate.setText("No Internet Connection");
        }


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                networkInfo = connectivityManager.getActiveNetworkInfo();
                if(networkInfo!=null && networkInfo.isConnected()) {
                    progressBar.setVisibility(View.VISIBLE);
                    url = url1 + query;
                    bookAdapter.clear();
                    bookAdapter.addAll(new ArrayList<Book>());
                    loaderManager.restartLoader(BOOKS_LOADER_ID, null, MainActivity.this);
                }
                else
                {
                    emptystate.setText("No Internet Connection");
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Book currentBook=bookAdapter.getItem(position);
                String[] data = new String[4];
                data[0]=currentBook.getmTitle();
                data[1]=currentBook.getmAuthor();
                data[2]=""+currentBook.getmRating();
                data[3]="              "+currentBook.getmDescription();
                Intent intent=new Intent(MainActivity.this,Description.class);
                intent.putExtra("message",data);
                startActivity(intent);
            }
        });

    }

    @Override
    public Loader<ArrayList<Book>> onCreateLoader(int i, @Nullable Bundle bundle) {
        return new BookLoader(this,url);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<ArrayList<Book>> loader, ArrayList<Book> books) {
        progressBar =(ProgressBar)findViewById(R.id.loading);
        progressBar.setVisibility(View.GONE);
        bookAdapter.addAll(books);

    }

    @Override
    public void onLoaderReset(@NonNull Loader<ArrayList<Book>> loader) {
        bookAdapter.addAll(new ArrayList<Book>());

    }
    public void doEverything()
    {

    }
}