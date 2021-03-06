package com.futuremall.android.di.module;

import com.futuremall.android.ui.fragment.FutureAddFragment;
import com.futuremall.android.ui.fragment.MainFragment;
import com.futuremall.android.ui.fragment.OrderFragment;
import com.futuremall.android.ui.fragment.ShoppingCartFragment;
import com.futuremall.android.ui.fragment.TypeFragment;
import com.futuremall.android.ui.fragment.UserFragment;


import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class PageModule {

    @Singleton
    @Provides
    MainFragment provideMain() {
        return new MainFragment();
    }

    @Singleton
    @Provides
    TypeFragment provideType() {
        return new TypeFragment();
    }

    @Singleton
    @Provides
    FutureAddFragment provideFutureAdd() {
        return new FutureAddFragment();
    }

    @Singleton
    @Provides
    ShoppingCartFragment provideShoppingCar() {
        return new ShoppingCartFragment();
    }

    @Singleton
    @Provides
    OrderFragment provideOrder() {
        return new OrderFragment();
    }

    @Singleton
    @Provides
    UserFragment provideUser() {
        return new UserFragment();
    }
}
