package com.mt.boostgamesuper.rootbooster;

import com.mt.boostgamesuper.model.RootMode;

import java.util.List;

public interface IRootView {
    void closeRootError();

    void showBoosterMode(List<RootMode> rootModes);

    void startBoost();

    void showInterstitalAd();
}
