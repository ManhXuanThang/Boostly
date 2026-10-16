package com.mt.boostgamesuper.event;

import com.mt.boostgamesuper.model.AppInfo;

public class GotoResultGameEvent {
    private AppInfo appInfo;

    public AppInfo getAppInfo() {
        return appInfo;
    }

    public void setAppInfo(AppInfo appInfo) {
        this.appInfo = appInfo;
    }

    public GotoResultGameEvent(AppInfo appInfo) {
        this.appInfo = appInfo;
    }
}
