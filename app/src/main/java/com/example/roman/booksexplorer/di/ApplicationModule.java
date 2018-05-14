package com.example.roman.booksexplorer.di;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;

@Module
public interface ApplicationModule {

    @Binds
    @Singleton
    Context provideContext(Application application);

}
