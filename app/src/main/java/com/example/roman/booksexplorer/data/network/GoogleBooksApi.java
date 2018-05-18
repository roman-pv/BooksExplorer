package com.example.roman.booksexplorer.data.network;

import com.example.roman.booksexplorer.data.model.RetrievedBooks;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * The interface that contains Rest Api requests for Retrofit.
 */
public interface GoogleBooksApi {

    String BASE_URL = "https://www.googleapis.com";
    int MAX_RESULTS = 40;

    @GET("/books/v1/volumes/")
    public Observable<RetrievedBooks> getBooksInformation(@Query("q") String searchTerm,
                                                          @Query("maxResults") int maxResults);


}
