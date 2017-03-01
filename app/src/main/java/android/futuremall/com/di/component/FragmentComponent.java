package android.futuremall.com.di.component;

import android.app.Activity;
import android.futuremall.com.di.module.AppModule;
import android.futuremall.com.di.module.FragmentModule;
import android.futuremall.com.di.scope.FragmentScope;

import dagger.Component;

/**
 * Created by codeest on 16/8/7.
 */

@FragmentScope
@Component(dependencies = AppModule.class, modules = FragmentModule.class)
public interface FragmentComponent {

    Activity getActivity();

//    void inject(DailyFragment dailyFragment);
}
