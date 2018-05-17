package com.example.roman.booksexplorer.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * POJO with an information about a book.
 */
public class VolumeInfo implements Parcelable {
    private String title;
    private String subtitle;
    private List<String> authors;
    private String publisher;
    private String description;
    private ImageLinks imageLinks;

    public VolumeInfo(String title, String subtitle, List<String> authors, String publisher, String description, ImageLinks imageLinks) {
        this.title = title;
        this.subtitle = subtitle;
        this.authors = authors;
        this.publisher = publisher;
        this.description = description;
        this.imageLinks = imageLinks;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public List<String> getAuthors() {
        return authors;
    }

    public void setAuthors(List<String> authors) {
        this.authors = authors;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ImageLinks getImageLinks() {
        return imageLinks;
    }

    public void setImageLinks(ImageLinks imageLinks) {
        this.imageLinks = imageLinks;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(title);
        out.writeString(subtitle);
        out.writeStringList(authors);
        out.writeString(publisher);
        out.writeString(description);
        out.writeParcelable(imageLinks, flags);
    }

    public VolumeInfo(Parcel in) {
        this.title = in.readString();
        this.subtitle = in.readString();
        authors = new ArrayList<>();
        in.readStringList(this.authors);
        this.publisher = in.readString();
        this.description = in.readString();
        this.imageLinks = in.readParcelable(ImageLinks.class.getClassLoader());
    }

    public static final Parcelable.Creator<VolumeInfo> CREATOR = new Parcelable.Creator<VolumeInfo>(){
        public VolumeInfo createFromParcel(Parcel in) {
            return new VolumeInfo(in);
        }

        public VolumeInfo[] newArray(int size) {
            return new VolumeInfo[size];
        }
    };
}