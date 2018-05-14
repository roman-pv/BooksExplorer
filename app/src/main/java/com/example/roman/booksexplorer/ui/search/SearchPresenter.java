package com.example.roman.booksexplorer.ui.search;

import android.util.Log;

import com.example.roman.booksexplorer.data.BooksRepository;
import com.example.roman.booksexplorer.data.model.BooksList;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;


public class SearchPresenter implements SearchContract.Presenter {

    private String TAG = "SearchPresenter";
    private SearchContract.View searchView;
    private BooksRepository mBooksRepository;
    private Disposable mDisposable;


    @Inject
    public SearchPresenter(BooksRepository booksRepository) {
        mBooksRepository = booksRepository;
    }

    @Override
    public void setView(SearchContract.View searchView) {
        this.searchView = searchView;
    }

    @Override
    public void getResultsBasedOnQuery(String query) {

        //searchView.setLoadingIndicator(true);
        mDisposable = mBooksRepository.fetchBooks(query)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<BooksList>() {

                    @Override
                    public void onError(Throwable e) {
                        //searchView.setLoadingIndicator(false);
                        Log.e(TAG, e.getMessage());
                        searchView.showToast(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onNext(BooksList books) {
                        //searchView.setLoadingIndicator(false);
                        //Log.d(TAG, movies.size() + " was fetched from MovieDb service");

                        searchView.displayResult(books);

                    }
                });
    }

    @Override
    public void unsubscribe() {
        if (mDisposable != null) {
            if (!mDisposable.isDisposed()) {
                mDisposable.dispose();
            }
        }
    }

}


