package com.example.roman.booksexplorer.data.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * POJO for images of a cover.
 */
public class ImageLinks implements Parcelable {
    public static final Parcelable.Creator<ImageLinks> CREATOR = new Parcelable.Creator<ImageLinks>() {
        public ImageLinks createFromParcel(Parcel in) {
            return new ImageLinks(in);
        }

        public ImageLinks[] newArray(int size) {
            return new ImageLinks[size];
        }
    };
    private String smallThumbnail;
    private String thumbnail;

    public ImageLinks(String smallThumbnail, String thumbnail) {
        this.smallThumbnail = smallThumbnail;
        this.thumbnail = thumbnail;
    }

    public ImageLinks(Parcel in) {
        this.smallThumbnail = in.readString();
        this.thumbnail = in.readString();
    }

    public String getSmallThumbnail() {
        return smallThumbnail;
    }

    public void setSmallThumbnail(String smallThumbnail) {
        this.smallThumbnail = smallThumbnail;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(smallThumbnail);
        out.writeString(thumbnail);
    }
}
