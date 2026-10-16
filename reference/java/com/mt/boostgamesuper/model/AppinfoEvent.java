package com.mt.boostgamesuper.model;

public class AppinfoEvent {
    private AppInfo appInfo;


    public AppinfoEvent(AppInfo appInfo) {
        this.appInfo = appInfo;
    }


    public AppInfo getAppInfo() {
        return appInfo;
    }

    public void setAppInfo(AppInfo appInfo) {
        this.appInfo = appInfo;
    }
}
