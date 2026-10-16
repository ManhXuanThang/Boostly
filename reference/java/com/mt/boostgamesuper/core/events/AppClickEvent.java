package com.mt.boostgamesuper.core.events;

import com.mt.boostgamesuper.model.AppInfo;

public class AppClickEvent {
    private AppInfo appInfo;

    public AppClickEvent(AppInfo appInfo) {
        this.appInfo = appInfo;
    }

    public AppInfo getAppInfo() {
        return appInfo;
    }

    public void setAppInfo(AppInfo appInfo) {
        this.appInfo = appInfo;
    }
}
