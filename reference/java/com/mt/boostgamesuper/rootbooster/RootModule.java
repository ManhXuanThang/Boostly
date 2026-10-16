package com.mt.boostgamesuper.rootbooster;

import android.content.Context;
import android.content.SharedPreferences;

import com.mt.boostgamesuper.di.scope.FragmentScope;
import com.mt.boostgamesuper.notification.ModeNotification;
import com.mt.boostgamesuper.utils.BoosterModeManager;
import com.mt.boostgamesuper.utils.SpUtils;

import dagger.Module;
import dagger.Provides;

@Module
public class RootModule {
    private IRootView view;

    public RootModule(IRootView view) {
        this.view = view;
    }

    @FragmentScope
    @Provides
    IRootView provideView() {
        return view;
    }

    @FragmentScope
    @Provides
    SpUtils provideSpUtils(SharedPreferences preferences, SharedPreferences.Editor editor) {
        return new SpUtils(editor, preferences);
    }

    @FragmentScope
    @Provides
    BoosterModeManager provideBoosterModeManager() {
        return new BoosterModeManager();
    }

    @FragmentScope
    @Provides
    ModeNotification provideModeNotification(Context context) {
        return new ModeNotification(context);
    }
}