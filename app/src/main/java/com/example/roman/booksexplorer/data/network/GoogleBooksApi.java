package com.example.roman.booksexplorer.data.network;

import com.example.roman.booksexplorer.data.model.BooksList;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GoogleBooksApi {

    String BASE_URL = "https://www.googleapis.com";
    int MAX_RESULTS = 40;

    @GET("/books/v1/volumes/")
    public Observable<BooksList> getBooksInformation(@Query("q") String searchTerm,
                                                   @Query("maxResults") int maxResults);


}
