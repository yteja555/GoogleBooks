package com.example.googlebooks;

import android.support.v4.app.LoaderManager;
import android.util.Log;
import android.widget.ImageView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class QueryUtils {

    // converts string into url;
    public static URL createUrl(String url)
    {
        URL link = null;
        try {
            link = new URL(url);
        }catch (Exception e)
        {
            Log.e("Error in Query utils","while creating url",e);
        }
        return link;
    }
    public static String makeHttpRequest(URL url)throws Exception
    {
        String JsonResponse="";
        InputStream is=null;
        HttpURLConnection urlConnection=null;
        if(url==null)
        {
            return JsonResponse;
        }
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.connect();
            is = urlConnection.getInputStream();
            JsonResponse=readFromStream(is);

        }catch (Exception e)
        {
            Log.e("error in queryUtil","while url.opnConnection",e);
        }
        finally {
            if(urlConnection!=null)
            {
                urlConnection.disconnect();
            }
            if(is!=null)
            {
                is.close();
            }
        }
        return JsonResponse;

    }
//    public ImageView makeHttprequestForImage(String url)
//    {
//        ImageView imageView;
//        URL link=createUrl(url);
//        HttpURLConnection urlConnection;
//        InputStream is;
//        try {
//
//            urlConnection=(HttpURLConnection)link.openConnection();
//            urlConnection.setRequestMethod("GET");
//            urlConnection.setReadTimeout(10000);
//            urlConnection.setConnectTimeout(15000);
//            urlConnection.connect();
//            is=urlConnection.getInputStream();
//
//
//        }
//        catch (Exception e)
//        {
//            Log.e("in image http","error",e);
//        }
//    }
    public static String readFromStream(InputStream is)throws Exception
    {
        StringBuilder output = new StringBuilder();
        InputStreamReader inputStreamReader = new InputStreamReader(is, Charset.forName("UTF-8"));
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String line = bufferedReader.readLine();
        while (line!=null)
        {
            output.append(line);
            line=bufferedReader.readLine();
        }
        return output.toString();

    }
    public static ArrayList<Book> extractFromJson(String   data )
    {
        JSONObject baseJson=null;
        ArrayList<Book> list = new ArrayList<Book>();
        double rating=0.0;
        try {
            baseJson = new JSONObject(data);
            JSONArray items=baseJson.getJSONArray("items");
            for(int i=0;i<items.length();i++)
            {
                JSONObject info =items.getJSONObject(i);
                JSONObject volumeInfo=info.getJSONObject("volumeInfo");
                String title = volumeInfo.getString("title");
                try
                {
                    rating=volumeInfo.getDouble("averageRating");
                }catch (Exception e)
                {
                    Log.v("error in rating","rating is not available");
                }
                JSONArray authors =volumeInfo.getJSONArray("authors");
                String Author=authors.getString(0);
                JSONObject image=volumeInfo.getJSONObject("imageLinks");
                String imageLink=image.getString("thumbnail");
                String description=volumeInfo.getString("description");
                list.add(new Book(title,Author,rating,imageLink,description));
//                list.add(new Book(title,Author,rating,imageLink));
            }
        } catch (Exception e)
        {
            Log.e("error Json Extracting","json object",e);
        }
        return list;
    }
    public static ArrayList<Book> fetchFromUrl(String url)
    {
        String jsondata="";
        URL finalUrl=createUrl(url);
        try {
            jsondata = makeHttpRequest(finalUrl);
        }catch (Exception e)
        {
            Log.e("Error","Fetch from url",e);
        }
        ArrayList<Book> booksData=extractFromJson(jsondata);
        return booksData;

    }

}
