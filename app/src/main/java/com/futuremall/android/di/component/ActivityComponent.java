package com.futuremall.android.di.component;

import android.app.Activity;
import com.futuremall.android.di.module.ActivityModule;
import com.futuremall.android.di.scope.ActivityScope;
import com.futuremall.android.ui.activity.MainActivity;
import com.futuremall.android.ui.activity.OperationRecordActivity;
import com.futuremall.android.ui.activity.OrderDetailActivity;

import dagger.Component;

@ActivityScope
@Component(dependencies = AppComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    Activity getActivity();

    void inject(MainActivity mainActivity);

    void inject(OrderDetailActivity orderDetailActivity);

    void inject(OperationRecordActivity operationRecordActivity);

}
