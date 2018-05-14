package com.example.roman.booksexplorer.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * POJO for a downloaded JSON with list of books.
 */
public class BooksList {
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
}
