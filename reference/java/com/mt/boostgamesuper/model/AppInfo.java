package com.mt.boostgamesuper.model;

import java.io.Serializable;

public class AppInfo implements Serializable {
    private String packageName;
    private String name;
    private boolean isList;

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isList() {
        return isList;
    }

    public void setList(boolean list) {
        isList = list;
    }
}
