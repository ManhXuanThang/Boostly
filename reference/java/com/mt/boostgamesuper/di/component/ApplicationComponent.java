package com.mt.boostgamesuper.di.component;

import android.content.Context;
import android.content.SharedPreferences;

import com.mt.boostgamesuper.App;
import com.mt.boostgamesuper.di.module.ApplicationModule;
import com.mt.boostgamesuper.utils.RxBus;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class})
public interface ApplicationComponent {
    SharedPreferences pref();

    App application();

    RxBus rxBus();

    Context context();

    SharedPreferences.Editor edit();

    void inject(App app);
}
