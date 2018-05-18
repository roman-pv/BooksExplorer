package com.example.roman.booksexplorer.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * POJO for a downloaded JSON with list of books.
 */
public class RetrievedBooks implements Parcelable {
    public static final Parcelable.Creator<RetrievedBooks> CREATOR = new Parcelable.Creator<RetrievedBooks>() {
        public RetrievedBooks createFromParcel(Parcel in) {
            return new RetrievedBooks(in);
        }

        public RetrievedBooks[] newArray(int size) {
            return new RetrievedBooks[size];
        }
    };
    @SerializedName("items")
    private List<Book> books;

    public RetrievedBooks(List<Book> books) {
        this.books = books;
    }

    public RetrievedBooks(Parcel in) {
        in.readTypedList(this.books, Book.CREATOR);
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeTypedList(books);
    }
}
