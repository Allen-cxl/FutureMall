package com.futuremall.android.di.component;



import com.futuremall.android.app.App;
import com.futuremall.android.di.module.AppModule;
import com.futuremall.android.di.module.HttpModule;
import com.futuremall.android.di.module.PageModule;
import com.futuremall.android.http.RetrofitHelper;
import com.futuremall.android.ui.fragment.FutureAddFragment;
import com.futuremall.android.ui.fragment.MainFragment;
import com.futuremall.android.ui.fragment.ShoppingCartFragment;
import com.futuremall.android.ui.fragment.TypeFragment;
import com.futuremall.android.ui.fragment.UserFragment;

import javax.inject.Singleton;

import dagger.Component;


/**
 * Created by codeest on 16/8/7.
 */

@Singleton
@Component(modules = {AppModule.class, HttpModule.class, PageModule.class})
public interface AppComponent {

    App getContext();  // 提供App的Context

    RetrofitHelper retrofitHelper();  //提供http的帮助类

    MainFragment mainFragment();

    TypeFragment typeFragment();

    FutureAddFragment futureAddFragment();

    ShoppingCartFragment shoppingCarFragment();

    UserFragment userFragment();
}
