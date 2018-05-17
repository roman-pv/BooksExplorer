package com.example.roman.booksexplorer.data;

import com.example.roman.booksexplorer.data.model.BooksList;
import com.example.roman.booksexplorer.data.network.GoogleBooksApi;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

@Singleton
public class BooksRepository {

    private final GoogleBooksApi booksApi;

    @Inject
    public BooksRepository(GoogleBooksApi booksApi) {
        this.booksApi = booksApi;
    }


    public Observable<BooksList> fetchBooks(final String query) {
        return booksApi.getBooksInformation(query, GoogleBooksApi.MAX_RESULTS);

    }


}
