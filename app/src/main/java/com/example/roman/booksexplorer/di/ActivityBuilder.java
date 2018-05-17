package com.example.roman.booksexplorer.di;

import com.example.roman.booksexplorer.ui.details.DetailsActivity;
import com.example.roman.booksexplorer.ui.search.SearchActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuilder {

    @ContributesAndroidInjector()
    abstract SearchActivity bindSearchActivity();

    @ContributesAndroidInjector()
    abstract DetailsActivity bindDetailsActivity();


}
