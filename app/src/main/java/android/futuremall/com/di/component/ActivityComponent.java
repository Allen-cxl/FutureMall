package android.futuremall.com.di.component;

import android.app.Activity;
import android.futuremall.com.di.module.ActivityModule;
import android.futuremall.com.di.scope.ActivityScope;
import android.futuremall.com.ui.activity.MainActivity;
import dagger.Component;

@ActivityScope
@Component(dependencies = AppComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    Activity getActivity();

    void inject(MainActivity mainActivity);

}
