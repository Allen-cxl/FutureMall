package com.futuremall.android.di.module;


import com.futuremall.android.app.App;
import com.futuremall.android.http.RetrofitHelper;
import com.futuremall.android.http.api.MallApis;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;


@Module
public class AppModule {
    private final App application;

    public AppModule(App application) {
        this.application = application;
    }

    @Provides
    @Singleton
    App provideApplicationContext() {
        return application;
    }

    @Provides
    @Singleton
    RetrofitHelper provideRetrofitHelper(MallApis mallApisService) {
        return new RetrofitHelper(mallApisService);
    }
}
