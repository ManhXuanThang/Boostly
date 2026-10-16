package com.mt.boostgamesuper.view.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.google.android.gms.ads.MobileAds;
import com.mt.boostgamesuper.R;
import com.mt.boostgamesuper.event.GotoResultGameEvent;
import com.mt.boostgamesuper.model.AppInfo;
import com.mt.boostgamesuper.view.Constants;
import com.mt.boostgamesuper.view.fragment.boostgame.BoostingGameFragment;
import com.mt.boostgamesuper.view.fragment.boostgame.BoostingResultFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class ScanHomeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // don't look at this layout it's just a listView to show how to handle the keyboard
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            Window window = this.getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimary));
        }
        if (getIntent() != null && getIntent().getExtras() != null) {
            AppInfo  appInfo = (AppInfo) getIntent().getExtras().getSerializable(Constants.GAME_APP_SELECT);
            FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
            tx.replace(R.id.frame_container, BoostingGameFragment.newInstance(appInfo));
            tx.commit();
        }

    }


    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onGotoScreen(GotoResultGameEvent event) {
//        showVerifyCodeFragment(event.getIndex(), event.getBankCard(), event.getPaymentRes(), event.getQrCodedRes(), event.isPostToken(), event.getLevel());
        FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
        tx.replace(R.id.frame_container, BoostingResultFragment.newInstance(event.getAppInfo()));
        tx.commit();
        EventBus.getDefault().removeStickyEvent(event);
    }



    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onBackPressed() {

//            super.onBackPressed();
    }

}

