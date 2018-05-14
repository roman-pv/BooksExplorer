package com.example.roman.booksexplorer.data;

import com.example.roman.booksexplorer.data.model.Book;
import com.example.roman.booksexplorer.data.model.BooksList;
import com.example.roman.booksexplorer.data.network.GoogleBooksApi;

import java.io.IOException;
import java.util.List;

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

        return Observable.defer(() -> booksApi.getBooksInformation(query, 40))

                .retryWhen(observable -> observable.flatMap(o -> {
                    if (o instanceof IOException) {
                        return Observable.just(null);
                    }
                    return Observable.error(o);
                }));
    }


}
