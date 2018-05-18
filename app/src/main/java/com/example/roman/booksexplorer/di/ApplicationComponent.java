package com.example.roman.booksexplorer.di;

import android.app.Application;

import com.example.roman.booksexplorer.BooksApp;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;

/**
 * Dagger component, mapping the modules with dependencies.
 */
@Singleton
@Component(modules = {
        ApplicationModule.class,
        NetworkModule.class,
        ActivityBuilder.class})
public interface ApplicationComponent extends AndroidInjector<BooksApp> {

    void inject(BooksApp app);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);

        ApplicationComponent build();
    }

}
