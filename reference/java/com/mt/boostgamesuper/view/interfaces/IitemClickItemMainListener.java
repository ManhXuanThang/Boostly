package com.mt.boostgamesuper.view.interfaces;

import com.mt.boostgamesuper.model.AppInfo;

import java.util.List;

public interface IitemClickItemMainListener {
    void onCardItemClick(AppInfo bankCard, int pos);

    void onDeleteItem(AppInfo bankCard, int pos);

    void onDeleteShowIcon(List<AppInfo> list);

    void onRevertItem(AppInfo bankCard);
}
