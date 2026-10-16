package com.mt.boostgamesuper.app;

import com.mt.boostgamesuper.di.scope.FragmentScope;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    private IAppView view;

    public AppModule(IAppView view) {
        this.view = view;
    }

    @Provides
    @FragmentScope
    IAppView provideView() {
        return view;
    }
}