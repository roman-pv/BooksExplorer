package com.example.roman.booksexplorer;

import android.text.TextUtils;

import java.util.List;

public class BooksUtils {

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

}
