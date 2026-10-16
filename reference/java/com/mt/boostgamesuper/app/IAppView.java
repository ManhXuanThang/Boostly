package com.mt.boostgamesuper.app;

import com.mt.boostgamesuper.model.AppInfo;

import java.util.List;

public interface IAppView {
    void showApplications(List<AppInfo> appInfoList);

    void startBoostingProgress(AppInfo appInfo);
}
