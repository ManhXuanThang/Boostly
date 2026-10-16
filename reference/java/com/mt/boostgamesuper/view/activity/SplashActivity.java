package com.mt.boostgamesuper.view.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.mt.boostgamesuper.R;
import com.mt.boostgamesuper.view.base.BaseActivity;

public class SplashActivity extends BaseActivity {
    private static final long DELAY_TIME = 1000;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        final TextView tvSplash = findViewById(R.id.tv_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                tvSplash.setVisibility(View.VISIBLE);
            }
        }, DELAY_TIME);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
              loadMain();
            }
        }, 2000);

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void loadMain(){
        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(intent);
        finish();
    }

}
