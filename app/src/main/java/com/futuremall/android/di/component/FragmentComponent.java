package com.futuremall.android.di.component;

import android.app.Activity;
import com.futuremall.android.di.module.FragmentModule;
import com.futuremall.android.di.scope.FragmentScope;
import com.futuremall.android.ui.fragment.MainFragment;
import com.futuremall.android.ui.fragment.OrderFragment;
import com.futuremall.android.ui.fragment.ShoppingCartFragment;
import com.futuremall.android.ui.fragment.UserFragment;

import dagger.Component;

/**
 * Created by codeest on 16/8/7.
 */

@FragmentScope
@Component(dependencies = AppComponent.class, modules = FragmentModule.class)
public interface FragmentComponent {

    Activity getActivity();

    void inject(MainFragment mainFragment);

    void inject(ShoppingCartFragment shoppingCartFragment);

    void inject(OrderFragment orderFragment);

    void inject(UserFragment userFragment);
}
