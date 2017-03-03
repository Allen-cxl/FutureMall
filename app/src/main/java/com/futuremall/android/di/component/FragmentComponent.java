package com.futuremall.android.di.component;

import android.app.Activity;
import com.futuremall.android.di.module.FragmentModule;
import com.futuremall.android.di.scope.FragmentScope;
import com.futuremall.android.ui.fragment.ShoppingCarFragment;
import com.futuremall.android.ui.fragment.UserFragment;

import dagger.Component;

/**
 * Created by codeest on 16/8/7.
 */

@FragmentScope
@Component(dependencies = AppComponent.class, modules = FragmentModule.class)
public interface FragmentComponent {

    Activity getActivity();

    void inject(ShoppingCarFragment shoppingCarFragment);

    void inject(UserFragment userFragment);
}
