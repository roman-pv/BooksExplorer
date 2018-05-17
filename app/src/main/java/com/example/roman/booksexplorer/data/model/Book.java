package com.example.roman.booksexplorer.data.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * POJO containing subclass with an informatioon about a book.
 */
public class Book implements Parcelable {

    private VolumeInfo volumeInfo;

    public VolumeInfo getVolumeInfo() {
        return volumeInfo;
    }

    public void setVolumeInfo(VolumeInfo volumeInfo) {
        this.volumeInfo = volumeInfo;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeParcelable(volumeInfo, flags);
    }

    public Book(Parcel in) {
        this.volumeInfo = in.readParcelable(VolumeInfo.class.getClassLoader());
    }

    public static final Parcelable.Creator<Book> CREATOR = new Parcelable.Creator<Book>(){
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        public Book[] newArray(int size) {
            return new Book[size];
        }
    };
}
