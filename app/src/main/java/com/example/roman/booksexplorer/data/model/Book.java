package com.example.roman.booksexplorer.data.model;

/**
 * POJO containing subclass with an informatioon about a book.
 */
public class Book {

    private VolumeInfo volumeInfo;

    public VolumeInfo getVolumeInfo() {
        return volumeInfo;
    }

    public void setVolumeInfo(VolumeInfo volumeInfo) {
        this.volumeInfo = volumeInfo;
    }
}
