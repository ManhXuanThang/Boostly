package com.mt.boostgamesuper.rootbooster;

import android.os.AsyncTask;

import com.mt.boostgamesuper.core.AdTypes;
import com.mt.boostgamesuper.core.BoosterModes;
import com.mt.boostgamesuper.core.events.ChangeRootModeEvent;
import com.mt.boostgamesuper.core.events.ShowAdEvent;
import com.mt.boostgamesuper.model.RootMode;
import com.mt.boostgamesuper.notification.ModeNotification;
import com.mt.boostgamesuper.utils.BoosterModeManager;
import com.mt.boostgamesuper.utils.RxBus;
import com.mt.boostgamesuper.utils.SpUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Subscription;
import rx.functions.Action1;
import rx.internal.schedulers.ExecutorScheduler;

class RootPresenter {

    private IRootView view;
    private SpUtils spUtils;
    private RxBus rxBus;
    private Subscription subscription;
    private BoosterModeManager boosterModeManager;
    private ModeNotification modeNotification;

    @Inject
    RootPresenter(IRootView view, SpUtils spUtils, RxBus rxBus, BoosterModeManager boosterModeManager, ModeNotification modeNotification) {
        this.view = view;
        this.spUtils = spUtils;
        this.rxBus = rxBus;
        this.boosterModeManager = boosterModeManager;
        this.modeNotification = modeNotification;
    }

    public void subscribe() {
        subscription = rxBus.toObserverable()
                .subscribeOn(new ExecutorScheduler(AsyncTask.THREAD_POOL_EXECUTOR))
                .subscribe(new Action1<Object>() {
                    @Override
                    public void call(Object o) {
                        if (o instanceof ChangeRootModeEvent) {
                            if (((ChangeRootModeEvent) o).getMode() == BoosterModes.NO_MODE) {
                                stopRootMode();
                            } else {
                                startRootMode(((ChangeRootModeEvent) o).getMode());
                            }
                        } else if (o instanceof ShowAdEvent) {
                            if (((ShowAdEvent) o).getAdType() == AdTypes.INTERSTITIAL) {
                                view.showInterstitalAd();
                            }
                        }
                    }
                });
    }

    private void stopRootMode() {
        spUtils.setCurrentMode(BoosterModes.NO_MODE);
        modeNotification.startNotification(BoosterModes.NO_MODE);
    }

    private void startRootMode(int mode) {
        view.startBoost();
        spUtils.setCurrentMode(mode);
        boosterModeManager.setMode(mode);
        modeNotification.startNotification(mode);
    }

//    void checkRoot() {
//        if (RootTools.isRootAvailable()) {
//            view.closeRootError();
//        }
//    }

    public void getBoosterModes() {
        List<RootMode> rootModes = new ArrayList<>();

        int mode = spUtils.getCurrentMode();

        RootMode ultraMode = new RootMode();
        ultraMode.setBootMode(BoosterModes.ULTRA_MODE);
        rootModes.add(ultraMode);

        RootMode highMode = new RootMode();
        highMode.setBootMode(BoosterModes.HIGH_MODE);
        rootModes.add(highMode);

        for (RootMode rootMode : rootModes) {
            if (rootMode.getBootMode() == mode) {
                rootMode.setIsActive(true);
            }
        }

        view.showBoosterMode(rootModes);
    }


    public void unsubscribe() {
        if (subscription != null) {
            subscription.unsubscribe();
        }
    }
}
