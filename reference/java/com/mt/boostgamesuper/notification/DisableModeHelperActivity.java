package com.mt.boostgamesuper.notification;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.mt.boostgamesuper.utils.BoosterModeManager;
import com.mt.boostgamesuper.utils.SpUtils;

public class DisableModeHelperActivity extends Activity {
    public static final String CLEAR_ACTION = "com.mtmobile.boostgamepro";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new ModeNotification(getApplicationContext()).startNotification(0);
        new BoosterModeManager().setMode(0);
        new SpUtils(getApplicationContext()).setCurrentMode(0);
        sendBroadcast(new Intent(CLEAR_ACTION));
        finish();
    }
}