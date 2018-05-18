package com.example.roman.booksexplorer.ui.search;

import com.example.roman.booksexplorer.data.model.RetrievedBooks;

public interface SearchContract {

    interface View {

        void showSnackbar(String str);

        void displayResult(RetrievedBooks retrievedBooks);

        void displayError(int stringResourseId);

        void setProgressBar(Boolean isVisible);

        void setEmptyView(Boolean isVisible);

        void clearSearch();
    }

    interface Presenter {

        void getResultsBasedOnQuery(String query);

        void setView(View searchView);

        void unsubscribe();
    }

}
