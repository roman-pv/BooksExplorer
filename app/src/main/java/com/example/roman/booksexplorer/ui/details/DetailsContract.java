package com.example.roman.booksexplorer.ui.details;

import com.example.roman.booksexplorer.data.model.Book;

public interface DetailsContract {

    interface View {

        void showBookDetails(Book book);

        void openBookInfoInBrowser(Book book);

    }

    interface Presenter {

        void loadBook();
    }
}
