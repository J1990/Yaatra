package com.tcd.yaatra;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.tcd.yaatra.di.DaggerAppComponent;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;

public class App extends Application implements HasActivityInjector {

    private static Context context;
    @Inject
    DispatchingAndroidInjector<Activity> dispatchingAndroidInjector;

    @Override
    public void onCreate() {
        super.onCreate();
        App.context = getApplicationContext();
        initDagger();
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return dispatchingAndroidInjector;
    }

    private void initDagger(){
        DaggerAppComponent.builder()
                .bindApplication(this)
                .build()
                .inject(this);
    }


    public static Context getAppContext() {
        return App.context;
    }

}