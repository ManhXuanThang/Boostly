package com.mt.boostgamesuper.view.fragment.scan;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.mt.boostgamesuper.R;
import com.mt.boostgamesuper.RippleBackground;
import com.mt.boostgamesuper.event.GotoResultGameEvent;
import com.mt.boostgamesuper.model.AppInfo;
import com.mt.boostgamesuper.utils.RAMBooster;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ScanActivity extends AppCompatActivity {

    private ImageView foundDevice;
    private ImageView foundDevice2;
    private ImageView foundDevice3;
    private ImageView foundDevice4;
    private ImageView foundDevice5;

    private TextView tvTangtoc;
    private RippleBackground main;
    private RelativeLayout success;
    private TextView titleText;
    private ImageView imgBack;
    private TextView tv_back;
    Random ran = new Random();


    private InterstitialAd interstitialAd;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Create the InterstitialAd and set the adUnitId.
        interstitialAd = new InterstitialAd(this);
        // Defined in res/values/strings.xml
        interstitialAd.setAdUnitId(getString(R.string.ad_unit_id));
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // only for gingerbread and newer versions
            Window window = this.getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);


            try {
                window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimary));
            } catch (Exception e) {

            }
        }

        final RippleBackground rippleBackground = (RippleBackground) findViewById(R.id.content);

        final Handler handler = new Handler();

        foundDevice = (ImageView) findViewById(R.id.foundDevice);
        foundDevice2 = (ImageView) findViewById(R.id.foundDevice2);
        foundDevice3 = (ImageView) findViewById(R.id.foundDevice3);
        foundDevice4 = (ImageView) findViewById(R.id.foundDevice4);
        foundDevice5 = (ImageView) findViewById(R.id.foundDevice5);
        tvTangtoc = (TextView) findViewById(R.id.tv_tangtoc);
        tv_back = (TextView) findViewById(R.id.tv_back);
        imgBack = (ImageView) findViewById(R.id.imgBackIcon);
        main = (RippleBackground) findViewById(R.id.content);
        success = (RelativeLayout) findViewById(R.id.success_rlt);
        ImageView button = (ImageView) findViewById(R.id.centerImage);
        titleText = findViewById(R.id.title_text);


        AlphaAnimation animation1 = new AlphaAnimation(0.2f, 1.0f);
        animation1.setDuration(1000);
        animation1.setStartOffset(5000);
        animation1.setFillAfter(true);
        button.startAnimation(animation1);


        PackageManager packageManager = getPackageManager();
        List<ApplicationInfo> appList = packageManager.getInstalledApplications(0);

        List<String> appInfoList = new ArrayList<>();

        for (ApplicationInfo info : appList) {
            if ((info.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
                appInfoList.add(info.packageName);
            }
        }

        titleText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnIntent = new Intent();
                setResult(3, returnIntent);
                finish();
            }
        });
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnIntent = new Intent();
                setResult(3, returnIntent);
                finish();
            }
        });
        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnIntent = new Intent();
                setResult(3, returnIntent);
                finish();
            }
        });

        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                main.setVisibility(View.GONE);
                success.setVisibility(View.VISIBLE);
                tv_back.setVisibility(View.VISIBLE);
                imgBack.setImageDrawable(getResources().getDrawable(R.drawable.ic_back));
            }
        });
        // Request a new ad if one isn't already loaded, hide the button, and kick off the timer.
        if (!interstitialAd.isLoading() && !interstitialAd.isLoaded()) {
            AdRequest adRequest = new AdRequest.Builder().build();
            interstitialAd.loadAd(adRequest);
        }

        setImageRandom(appInfoList, foundDevice);
        setImageRandom(appInfoList, foundDevice2);
        setImageRandom(appInfoList, foundDevice3);
        setImageRandom(appInfoList, foundDevice4);
        setImageRandom(appInfoList, foundDevice5);

        new AsyncGetProfile(getApplication()).execute();
        rippleBackground.startRippleAnimation();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                foundDevice();
                tvTangtoc.setText(getString(R.string.prepare_scan));
//                foundDevice2.setVisibility(View.INVISIBLE);
//                foundDevice3.setVisibility(View.INVISIBLE);
//                foundDevice4.setVisibility(View.INVISIBLE);
//                foundDevice5.setVisibility(View.INVISIBLE);
//                        startActivity(
//                                new Intent(ScanActivity.this, HomeActivity.class)
//                        );
            }
        }, 3000);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                foundDevice2();
                tvTangtoc.setText(getString(R.string.tang_toc));
//                foundDevice.setVisibility(View.INVISIBLE);
//                foundDevice3.setVisibility(View.INVISIBLE);
//                foundDevice4.setVisibility(View.INVISIBLE);
//                foundDevice5.setVisibility(View.INVISIBLE);
//                        startActivity(
//                                new Intent(ScanActivity.this, HomeActivity.class)
//                        );
            }
        }, 4000);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                foundDevice3();
//                foundDevice2.setVisibility(View.INVISIBLE);
//                foundDevice.setVisibility(View.INVISIBLE);
//                foundDevice4.setVisibility(View.INVISIBLE);
//                foundDevice5.setVisibility(View.INVISIBLE);
//                        startActivity(
//                                new Intent(ScanActivity.this, HomeActivity.class)
//                        );
            }
        }, 5000);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                foundDevice4();
                tvTangtoc.setText(getString(R.string.remove_tit));
//                foundDevice2.setVisibility(View.INVISIBLE);
//                foundDevice3.setVisibility(View.INVISIBLE);
//                foundDevice.setVisibility(View.INVISIBLE);
//                foundDevice5.setVisibility(View.INVISIBLE);
//                        startActivity(
//                                new Intent(ScanActivity.this, HomeActivity.class)
//                        );
            }
        }, 6000);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                foundDevice5();
                tvTangtoc.setText(getString(R.string.done_thread));
//                foundDevice2.setVisibility(View.INVISIBLE);
//                foundDevice3.setVisibility(View.INVISIBLE);
//                foundDevice4.setVisibility(View.INVISIBLE);
//                foundDevice.setVisibility(View.INVISIBLE);
//                        startActivity(
//                                new Intent(ScanActivity.this, HomeActivity.class)
//                        );
            }
        }, 7000);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                showInterstitial();
            }
        }, 9000);
    }

    private void foundDevice() {
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(400);
        animatorSet.setInterpolator(new AccelerateDecelerateInterpolator());
        ArrayList<Animator> animatorList = new ArrayList<Animator>();
        ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(foundDevice, "ScaleX", 0f, 1.2f, 1f);
        animatorList.add(scaleXAnimator);
        ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(foundDevice, "ScaleY", 0f, 1.2f, 1f);
        animatorList.add(scaleYAnimator);
        animatorSet.playTogether(animatorList);
        foundDevice.setVisibility(View.VISIBLE);
        animatorSet.start();
    }
    private void showInterstitial() {
        // Show the ad if it's ready. Otherwise toast and restart the game.
        if (interstitialAd != null && interstitialAd.isLoaded()) {
            interstitialAd.show();
        } else {
            main.setVisibility(View.GONE);
            success.setVisibility(View.VISIBLE);
            tv_back.setVisibility(View.VISIBLE);
            imgBack.setImageDrawable(getResources().getDrawable(R.drawable.ic_back));
        }

    }
    public void setImageRandom(List<String> appInfoList, ImageView imageView) {
        int i = ran.nextInt(appInfoList.size());
        Drawable icon = null;
        try {
            icon = getPackageManager().getApplicationIcon(appInfoList.get(i));
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        imageView.setImageDrawable(icon);
    }

    private void foundDevice2() {
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(400);
        animatorSet.setInterpolator(new AccelerateDecelerateInterpolator());
        ArrayList<Animator> animatorList = new ArrayList<Animator>();
        ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(foundDevice2, "ScaleX", 0f, 1.2f, 1f);
        animatorList.add(scaleXAnimator);
        ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(foundDevice2, "ScaleY", 0f, 1.2f, 1f);
        animatorList.add(scaleYAnimator);
        animatorSet.playTogether(animatorList);
        foundDevice2.setVisibility(View.VISIBLE);
        animatorSet.start();
    }

    private void foundDevice3() {
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(400);
        animatorSet.setInterpolator(new AccelerateDecelerateInterpolator());
        ArrayList<Animator> animatorList = new ArrayList<Animator>();
        ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(foundDevice3, "ScaleX", 0f, 1.2f, 1f);
        animatorList.add(scaleXAnimator);
        ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(foundDevice3, "ScaleY", 0f, 1.2f, 1f);
        animatorList.add(scaleYAnimator);
        animatorSet.playTogether(animatorList);
        foundDevice3.setVisibility(View.VISIBLE);
        animatorSet.start();
    }

    private void foundDevice4() {
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(400);
        animatorSet.setInterpolator(new AccelerateDecelerateInterpolator());
        ArrayList<Animator> animatorList = new ArrayList<Animator>();
        ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(foundDevice4, "ScaleX", 0f, 1.2f, 1f);
        animatorList.add(scaleXAnimator);
        ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(foundDevice4, "ScaleY", 0f, 1.2f, 1f);
        animatorList.add(scaleYAnimator);
        animatorSet.playTogether(animatorList);
        foundDevice4.setVisibility(View.VISIBLE);
        animatorSet.start();
    }

    private void foundDevice5() {
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(400);
        animatorSet.setInterpolator(new AccelerateDecelerateInterpolator());
        ArrayList<Animator> animatorList = new ArrayList<Animator>();
        ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(foundDevice5, "ScaleX", 0f, 1.2f, 1f);
        animatorList.add(scaleXAnimator);
        ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(foundDevice5, "ScaleY", 0f, 1.2f, 1f);
        animatorList.add(scaleYAnimator);
        animatorSet.playTogether(animatorList);
        foundDevice5.setVisibility(View.VISIBLE);
        animatorSet.start();
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();

            Log.e("ScanActivity: ", "list  " + children.length);
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
            return dir.delete();
        } else if (dir != null && dir.isFile()) {
            return dir.delete();
        } else {
            return false;
        }
    }


    public class AsyncGetProfile extends AsyncTask<Void, Void, Boolean> {
        private Application application;

        public AsyncGetProfile(Application application) {
            this.application = application;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {

            try {
                File dir = application.getCacheDir();
                deleteDir(dir);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if (aBoolean) {
                saveObjectData("success");
            }
        }
    }


    private void saveObjectData(String appInfos) {

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = sharedPrefs.edit();

        editor.putString("ScanActivity", appInfos);
        editor.commit();
    }


    @Override
    public void onBackPressed() {

//            super.onBackPressed();
    }
}
