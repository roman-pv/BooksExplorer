package com.example.roman.booksexplorer.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * POJO for a downloaded JSON with list of books.
 */
public class BooksList implements Parcelable{
    @SerializedName("items")
    private List<Book> books;

    public BooksList(List<Book> books) {
        this.books = books;
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

    public BooksList(Parcel in) {
        in.readTypedList(this.books, Book.CREATOR);
    }

    public static final Parcelable.Creator<BooksList> CREATOR = new Parcelable.Creator<BooksList>(){
        public BooksList createFromParcel(Parcel in) {
            return new BooksList(in);
        }

        public BooksList[] newArray(int size) {
            return new BooksList[size];
        }
    };
}
