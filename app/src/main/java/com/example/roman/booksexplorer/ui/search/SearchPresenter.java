package com.example.roman.booksexplorer.ui.search;

import android.util.Log;

import com.example.roman.booksexplorer.R;
import com.example.roman.booksexplorer.data.BooksRepository;
import com.example.roman.booksexplorer.data.model.RetrievedBooks;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;


public class SearchPresenter implements SearchContract.Presenter {

    private String TAG = SearchPresenter.class.getSimpleName();
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

        searchView.setEmptyView(false);
        searchView.setProgressBar(true);
        mDisposable = mBooksRepository.fetchBooks(query)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<RetrievedBooks>() {

                    @Override
                    public void onError(Throwable e) {
                        searchView.setProgressBar(false);
                        Log.e(TAG, e.getMessage());
                        searchView.displayError(R.string.error_loading);
                    }

                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onNext(RetrievedBooks books) {
                        searchView.setProgressBar(false);
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


