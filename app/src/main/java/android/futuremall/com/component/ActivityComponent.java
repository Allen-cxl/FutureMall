package android.futuremall.com.component;

import android.app.Activity;
import android.futuremall.com.module.ActivityModule;


import dagger.Component;

/**
 * Created by codeest on 16/8/7.
 */

@ActivityScope
@Component(dependencies = AppComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    Activity getActivity();

   // void inject(WelcomeActivity welcomeActivity);

    //void inject(MainActivity mainActivity);

}
