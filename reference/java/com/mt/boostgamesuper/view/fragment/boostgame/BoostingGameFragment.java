package com.mt.boostgamesuper.view.fragment.boostgame;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextSwitcher;
import android.widget.Toast;

import com.dynamitechetan.flowinggradient.FlowingGradientClass;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.mt.boostgamesuper.R;
import com.mt.boostgamesuper.core.EventCategories;
import com.mt.boostgamesuper.event.GotoResultGameEvent;
import com.mt.boostgamesuper.model.AppInfo;
import com.mt.boostgamesuper.utils.RAMBooster;
import com.mt.boostgamesuper.utils.analytics.AnalyticsUtils;
import com.wang.avi.AVLoadingIndicatorView;

import org.greenrobot.eventbus.EventBus;

public class BoostingGameFragment extends Fragment {
    private ImageView imageView;
    private RAMBooster ramBooster;
    private LinearLayout boost1;
    private LinearLayout boost2;
    private LinearLayout boost3;
    private LinearLayout boost4;
    private LinearLayout main;

    private ImageView img1;
    private ImageView img2;
    private ImageView img3;
    private ImageView img4;
    private ImageView img5;

    private AVLoadingIndicatorView av;
    private AVLoadingIndicatorView av1;
    private AVLoadingIndicatorView av2;
    private AVLoadingIndicatorView av3;


    private TextSwitcher textView;
    private TextSwitcher textView1;
    private TextSwitcher textView2;
    private TextSwitcher textView3;

    private Activity activity;

    private InterstitialAd interstitialAd;

    // newInstance constructor for creating fragment with arguments
    public static BoostingGameFragment newInstance(AppInfo appInfo) {
        BoostingGameFragment fragmentFirst = new BoostingGameFragment();
        Bundle args = new Bundle();
        args.putSerializable("appInfo", appInfo);
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_boostapp_app, container, false);
        imageView = view.findViewById(R.id.img_app);
        if (activity != null && isAdded()) {
            ramBooster = new RAMBooster(activity);
            // Create the InterstitialAd and set the adUnitId.
            interstitialAd = new InterstitialAd(activity);
            // Defined in res/values/strings.xml
            interstitialAd.setAdUnitId(getString(R.string.ad_unit_id));

            textView = view.findViewById(R.id.textView);

            textView1 = view.findViewById(R.id.textView_2);

            textView2 = view.findViewById(R.id.textView_3);

            textView3 = view.findViewById(R.id.textView_4);

            main=view.findViewById(R.id.main);

            boost1 = view.findViewById(R.id.boost1);
            boost2 = view.findViewById(R.id.boost2);
            boost3 = view.findViewById(R.id.boost3);
            boost4 = view.findViewById(R.id.boost4);

            img1 = view.findViewById(R.id.img_tick_v1);
            img2 = view.findViewById(R.id.img_tick_v2);
            img3 = view.findViewById(R.id.img_tick_v3);
            img4 = view.findViewById(R.id.img_tick_v4);
            img5 = view.findViewById(R.id.img_icon);

            av = view.findViewById(R.id.avi);
            av1 = view.findViewById(R.id.avi_2);
            av2 = view.findViewById(R.id.avi_3);
            av3 = view.findViewById(R.id.avi_4);
        }
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);



        loadAnimations(textView);
        loadAnimations(textView1);
        loadAnimations(textView2);
        loadAnimations(textView3);



        if (getArguments() != null) {
            final AppInfo appInfo = (AppInfo) getArguments().getSerializable("appInfo");

            interstitialAd.setAdListener(new AdListener() {
                @Override
                public void onAdClosed() {
                    EventBus.getDefault().postSticky(new GotoResultGameEvent(appInfo));
                }
            });
            // Request a new ad if one isn't already loaded, hide the button, and kick off the timer.
            if (!interstitialAd.isLoading() && !interstitialAd.isLoaded()) {
                AdRequest adRequest = new AdRequest.Builder().build();
                interstitialAd.loadAd(adRequest);
            }
            FlowingGradientClass fg = new FlowingGradientClass()
                    .setBackgroundResource(R.drawable.trans)
                    .onLinearLayout(main)
                    .setTransitionDuration(4000)
                    .start();

            Drawable icon = null;
            try {
                icon = activity.getPackageManager().getApplicationIcon(appInfo.getPackageName());
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            imageView.setImageDrawable(icon);


            AlphaAnimation animation1 = new AlphaAnimation(0.2f, 1.0f);
            animation1.setDuration(1000);
            animation1.setStartOffset(8000);
            animation1.setFillAfter(true);
            img5.startAnimation(animation1);


            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    boost1.setVisibility(View.VISIBLE);
                    textView.setText(getText(R.string.boost1));

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            img1.setVisibility(View.VISIBLE);
                            av.setVisibility(View.INVISIBLE);
                        }
                    }, 1000);
                }
            }, 2000);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    boost2.setVisibility(View.VISIBLE);
                    textView1.setText(getText(R.string.boost2));
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            img2.setVisibility(View.VISIBLE);
                            av1.setVisibility(View.INVISIBLE);
                        }
                    }, 2000);
                }
            }, 4000);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    boost3.setVisibility(View.VISIBLE);
                    textView2.setText(getText(R.string.boost3));
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            img3.setVisibility(View.VISIBLE);
                            av2.setVisibility(View.INVISIBLE);
                        }
                    }, 2000);
                }
            }, 6000);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    boost4.setVisibility(View.VISIBLE);
                    textView3.setText(getText(R.string.boost4));
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            img4.setVisibility(View.VISIBLE);
                            av3.setVisibility(View.INVISIBLE);


                            new AsyncBootsGameSelect(appInfo).execute();
                        }
                    }, 3000);
                }
            }, 8000);
        }
    }

    void loadAnimations(TextSwitcher textSwitcher) {

        // Declare the in and out animations and initialize them
        Animation in = AnimationUtils.loadAnimation(activity,
                android.R.anim.slide_in_left);
        Animation out = AnimationUtils.loadAnimation(activity,
                android.R.anim.slide_out_right);

        // set the animation type of textSwitcher
        textSwitcher.setInAnimation(in);
        textSwitcher.setOutAnimation(out);
    }



    public class AsyncBootsGameSelect extends AsyncTask<String, Void, Void> {

        private AppInfo appInfo;
        public AsyncBootsGameSelect(AppInfo appInfo) {

            this.appInfo=appInfo;
        }

        @Override
        protected Void doInBackground(String... strings) {
            ramBooster.clearRAM();
            AnalyticsUtils.trackEvent(EventCategories.CLICK_EVENT, "Click App Booster", "Rootsuz app boostlama tıklandı");
//            Toast.makeText(getContext(), R.string.app_boosted, Toast.LENGTH_SHORT).show();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
           showInterstitial(appInfo);

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Activity) {
            activity = (Activity) context;
        }
    }

        private void showInterstitial(AppInfo appInfo) {
        // Show the ad if it's ready. Otherwise toast and restart the game.
        if (interstitialAd != null && interstitialAd.isLoaded()) {
            interstitialAd.show();
        } else {
            EventBus.getDefault().postSticky(new GotoResultGameEvent(appInfo));
        }
    }


}
