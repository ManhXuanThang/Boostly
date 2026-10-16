package com.mt.boostgamesuper.view.activity;

import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mt.boostgamesuper.AnimatedCircleLoadingView;
import com.mt.boostgamesuper.R;
import com.mt.boostgamesuper.notification.DisableModeHelperActivity;
import com.mt.boostgamesuper.ntb.NavigationTabBar;
import com.mt.boostgamesuper.utils.AppRater;
import com.mt.boostgamesuper.view.adapter.MyPagerAdapter;
import com.mt.boostgamesuper.view.base.BaseActivity;
import com.mt.boostgamesuper.view.fragment.BoostProTab;

import java.io.Serializable;
import java.util.ArrayList;

public class HomeActivity extends BaseActivity implements Serializable {
    private AnimatedCircleLoadingView animatedCircleLoadingView;
    MyPagerAdapter adapterViewPager;
    ViewPager viewPager;
    boolean isShow = false;

    private LinearLayout more;
    private RelativeLayout main;

    private NavigationTabBar navigationTabBar;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horizontal_ntb);

        initUI();
        animatedCircleLoadingView = (AnimatedCircleLoadingView) findViewById(R.id.circle_loading_view);
        try {
            AppRater.app_launched(this);
            startLoading();
            startPercentMockThread();
        } catch (Exception e) {

        }


        main = findViewById(R.id.main);
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            for (Fragment fragment : getSupportFragmentManager().getFragments()) {
                if (fragment instanceof BoostProTab) {
//                    ((BoostProTab) fragment).clearModes();
                }
            }
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        registerReceiver(receiver, new IntentFilter(DisableModeHelperActivity.CLEAR_ACTION));
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop()
    {
        unregisterReceiver(receiver);
        super.onStop();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void initUI() {
        viewPager = (ViewPager) findViewById(R.id.vp_horizontal_ntb);
        navigationTabBar = (NavigationTabBar) findViewById(R.id.ntb_horizontal);
        more = findViewById(R.id.llDeleteIcon);
        TextView title_text = findViewById(R.id.title_text);
        title_text.setText(getString(R.string.app_name));
        adapterViewPager = new MyPagerAdapter(getSupportFragmentManager(), isShow);
        ArrayList<NavigationTabBar.Model> models = new ArrayList<>();
        try {
            models.add(
                    new NavigationTabBar.Model.Builder(
                            getResources().getDrawable(R.drawable.ic_first),
                            getResources().getColor(R.color.rippelColor))
                            .selectedIcon(getResources().getDrawable(R.drawable.ic_sixth))
                            .title(getString(R.string.title1))
                            .build()
            );
            models.add(
                    new NavigationTabBar.Model.Builder(
                            getResources().getDrawable(R.drawable.ic_second),
                            getResources().getColor(R.color.rippelColor))
//                        .selectedIcon(getResources().getDrawable(R.drawable.ic_eighth))
                            .title("Boost")
                            .build()
            );
            models.add(
                    new NavigationTabBar.Model.Builder(
                            getResources().getDrawable(R.drawable.ic_third),
                            getResources().getColor(R.color.rippelColor))
                            .selectedIcon(getResources().getDrawable(R.drawable.ic_seventh))
                            .title("Pro")
                            .badgeTitle("Ai")
                            .build()
            );

            navigationTabBar.setModels(models);
            viewPager.setAdapter(adapterViewPager);
            navigationTabBar.setViewPager(viewPager, 0);
            navigationTabBar.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(final int position, final float positionOffset, final int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(final int position) {
                    navigationTabBar.getModels().get(position).hideBadge();


                }

                @Override
                public void onPageScrollStateChanged(final int state) {

                }
            });

            navigationTabBar.postDelayed(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < navigationTabBar.getModels().size(); i++) {
                        final NavigationTabBar.Model model = navigationTabBar.getModels().get(i);
                        navigationTabBar.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                model.showBadge();
                            }
                        }, i * 100);
                    }
                }
            }, 100);


            more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), SettingActivity.class);
                    startActivity(intent);
                }
            });
        } catch (Exception e) {
        }
    }

    private void startLoading() {
        animatedCircleLoadingView.startDeterminate();
    }

    private void startPercentMockThread() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1500);
                    for (int i = 0; i <= 100; i++) {
                        Thread.sleep(65);
                        changePercent(i);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        new Thread(runnable).start();
    }

    private void changePercent(final int percent) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                animatedCircleLoadingView.setPercent(percent);
            }
        });
    }

    public void resetLoading() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                animatedCircleLoadingView.resetLoading();
            }
        });
    }

    @Override
    public void onBackPressed() {

//            super.onBackPressed();
    }
}
