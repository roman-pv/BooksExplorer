package com.example.roman.booksexplorer.di;

import android.app.Application;

import com.example.roman.booksexplorer.BooksApp;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;

@Singleton
@Component(modules = {
        ApplicationModule.class,
        DataModule.class,
        ActivityBuilder.class})
public interface ApplicationComponent extends AndroidInjector<BooksApp>{

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);

        ApplicationComponent build();
    }

    void inject(BooksApp app);

}
