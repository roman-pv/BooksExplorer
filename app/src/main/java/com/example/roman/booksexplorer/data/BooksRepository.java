package com.example.roman.booksexplorer.data;

import com.example.roman.booksexplorer.data.model.RetrievedBooks;
import com.example.roman.booksexplorer.data.network.GoogleBooksApi;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

/**
 * The class that is a single point of access to the Model for the Presenter.
 * Allows to fetch requested information from any resource.
 * Currently it's only web-download with Retrofit.
 */
@Singleton
public class BooksRepository {

    private final GoogleBooksApi booksApi;

    @Inject
    public BooksRepository(GoogleBooksApi booksApi) {
        this.booksApi = booksApi;
    }


    public Observable<RetrievedBooks> fetchBooks(final String query) {
        return booksApi.getBooksInformation(query, GoogleBooksApi.MAX_RESULTS);

    }


}
