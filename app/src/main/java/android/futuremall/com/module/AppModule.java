package android.futuremall.com.module;


import android.futuremall.com.app.App;
import android.futuremall.com.http.RetrofitHelper;
import android.futuremall.com.http.api.MallApis;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by codeest on 16/8/7.
 */

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
