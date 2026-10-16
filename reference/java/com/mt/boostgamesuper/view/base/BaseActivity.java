package com.mt.boostgamesuper.view.base;

import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.mt.boostgamesuper.App;
import com.mt.boostgamesuper.R;
import com.mt.boostgamesuper.view.interfaces.LogoutListener;

public class BaseActivity extends AppCompatActivity implements LogoutListener, ConnectivityChangeReceiver.OnConnectivityChangedListener {
    private ConnectivityChangeReceiver connectivityChangeReceiver;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {

            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = this.getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
                window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimary));
            }

        } catch (Exception e) {
        }
        ((App) getApplication()).registerSessionListener(this);
        ((App) getApplication()).startUserSession();
    }

    @Override
    public void onSessionLogout() {
        Log.e("base: ", "timout");
        saveObjectData("");
    }

    private void saveObjectData(String appInfos) {

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = sharedPrefs.edit();

        editor.putString("ScanActivity", appInfos);
        editor.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        connectivityChangeReceiver = new ConnectivityChangeReceiver(this);
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(connectivityChangeReceiver, filter);

    }

    @Override
    public void onConnectivityChanged(boolean isConnected) {

    }

    @Override
    protected void onPause() {
        super.onPause();
        saveObjectData("");
        if (connectivityChangeReceiver != null)
            unregisterReceiver(connectivityChangeReceiver);
    }
}
