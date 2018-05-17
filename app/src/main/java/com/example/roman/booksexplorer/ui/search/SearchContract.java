package com.example.roman.booksexplorer.ui.search;

import android.widget.SearchView;

import com.example.roman.booksexplorer.data.model.BooksList;

public interface SearchContract {

    interface View {
        void showToast(String str);
        void displayResult(BooksList booksList);
        void displayError(String s);
        void setProgressBar(Boolean isVisible);
    }

    interface Presenter {
        void getResultsBasedOnQuery(String query);
        void setView(View searchView);
        void unsubscribe();
        }

}
