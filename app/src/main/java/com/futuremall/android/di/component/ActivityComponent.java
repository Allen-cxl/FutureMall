package com.futuremall.android.di.component;

import android.app.Activity;
import com.futuremall.android.di.module.ActivityModule;
import com.futuremall.android.di.scope.ActivityScope;
import com.futuremall.android.ui.activity.MainActivity;

import dagger.Component;

@ActivityScope
@Component(dependencies = AppComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    Activity getActivity();

    void inject(MainActivity mainActivity);

}
