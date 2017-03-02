package android.futuremall.com.di.module;


import android.futuremall.com.app.App;
import android.futuremall.com.http.RetrofitHelper;
import android.futuremall.com.http.api.MallApis;

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
