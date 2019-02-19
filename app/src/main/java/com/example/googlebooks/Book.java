package com.example.googlebooks;

public class Book
{
    private String mTitle;
    private String mAuthor;
    private Double mRating;
    private String mImageLink;
    private String mDescription;
    public Book(String Title,String Author,Double Rating,String ImageLink,String description)
    {
        mAuthor=Author;
        mImageLink=ImageLink;
        mRating=Rating;
        mTitle=Title;
        mDescription=description;
    }
    public Book(String Title,String Author,Double Rating,String ImageLink)
    {
        mAuthor=Author;
        mImageLink=ImageLink;
        mRating=Rating;
        mTitle=Title;
    }

    public String getmAuthor() {
        return mAuthor;
    }

    public String getmImageLink() {
        return mImageLink;
    }

    public double getmRating() {
        return mRating;
    }

    public String getmTitle() {
        return mTitle;
    }

    public String getmDescription() {
        return mDescription;
    }
}
