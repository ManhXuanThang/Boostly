package com.mt.boostgamesuper;

import android.content.SharedPreferences;
import androidx.multidex.MultiDexApplication;

import com.mt.boostgamesuper.di.component.ApplicationComponent;
import com.mt.boostgamesuper.di.component.DaggerApplicationComponent;
import com.mt.boostgamesuper.di.module.ApplicationModule;
import com.mt.boostgamesuper.utils.analytics.AnalyticsTracker;
import com.mt.boostgamesuper.utils.log.CrashReportTree;
import com.mt.boostgamesuper.view.interfaces.LogoutListener;

import java.util.Timer;
import java.util.TimerTask;

import javax.inject.Inject;

import timber.log.Timber;

public class App extends MultiDexApplication {

    private static ApplicationComponent applicationComponent;
    @Inject
    SharedPreferences preferences;
    @Inject
    SharedPreferences.Editor editor;


    private LogoutListener listener;

    private Timer timer;
    private static App singleton;

    public static ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        singleton = this;

        // Initialize Dagger
        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();

        applicationComponent.inject(this);

        // Initialize Timber for logging
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        } else {
            Timber.plant(new CrashReportTree());
        }

        //Initialize Google Analytics
        AnalyticsTracker.initialize(this);

        // The commented out logic for root check can be re-enabled if needed
        // if (!preferences.getBoolean(EventCategories.ROOT_EVENT, false)) {
        //     AnalyticsUtils.trackEvent(EventCategories.ROOT_EVENT, "Root Check", "isRootAvailable : " + RootTools.isRootAvailable());
        //     editor.putBoolean(EventCategories.ROOT_EVENT, true);
        //     editor.apply();
        // }
    }

    public static App getInstance() {
        if (singleton == null) {
            singleton = new App();
        }
        return singleton;
    }


    public void startUserSession() {
        cancelTimer();

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if(listener != null) {
                    listener.onSessionLogout();
                }
            }
        }, 30000);
    }

    public void registerSessionListener(LogoutListener listener) {
        this.listener = listener;
    }


    private void cancelTimer() {
        if (timer != null) timer.cancel();
    }

    public void onUserInteracted() {
        startUserSession();
    }
}
