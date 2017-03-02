package android.futuremall.com.di.component;



import android.futuremall.com.app.App;
import android.futuremall.com.di.module.AppModule;
import android.futuremall.com.di.module.HttpModule;
import android.futuremall.com.http.RetrofitHelper;

import javax.inject.Singleton;

import dagger.Component;


/**
 * Created by codeest on 16/8/7.
 */

@Singleton
@Component(modules = {AppModule.class, HttpModule.class})
public interface AppComponent {

    App getContext();  // 提供App的Context

    RetrofitHelper retrofitHelper();  //提供http的帮助类
}
