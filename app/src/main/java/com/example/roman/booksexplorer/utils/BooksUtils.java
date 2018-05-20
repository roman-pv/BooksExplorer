package com.example.roman.booksexplorer.utils;

import android.content.Context;
import android.text.TextUtils;

import com.example.roman.booksexplorer.R;
import com.example.roman.booksexplorer.data.model.Book;
import com.example.roman.booksexplorer.data.model.VolumeInfo;

import java.util.List;

/**
 * Provides a few static methods to format strings to display book information.
 */
public class BooksUtils {

    /**
     * Formats a list of string (specifically authors) into a one string,
     * placing either each author on new line or separating with a comma.
     */
    public static String formatStringList(List<String> authors, Boolean oneLine) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String author : authors) {
            if (!TextUtils.isEmpty(author)) {
                stringBuilder.append(author);
                if (oneLine) {
                    stringBuilder.append(", ");
                } else {
                    stringBuilder.append("\n");
                }
            }
        }

        return stringBuilder.toString()
                .trim()
                .replaceAll(",$", "");
    }

    /**
     * Creates formatted text (using string resources)
     * to be used as a text for 'Share' functionality .
     */
    public static String getTextToShare(Context context, Book book) {
        VolumeInfo bookInformation = book.getVolumeInfo();

        //Handles null fields in JSON
        String title = context.getString(R.string.empty);
        if (!TextUtils.isEmpty(bookInformation.getTitle())) {
            title = bookInformation.getTitle();
        }
        String authors = context.getString(R.string.empty);
        if (bookInformation.getAuthors() != null && bookInformation.getAuthors().size() > 0) {
            authors = BooksUtils.formatStringList(bookInformation.getAuthors(), true);
        }

        return context.getString(
                R.string.share_message,
                title,
                authors,
                book.getVolumeInfo().getInfoLink());

    }

}
