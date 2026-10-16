package com.mt.boostgamesuper.view.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mt.boostgamesuper.AnimatedCircleLoadingView;
import com.mt.boostgamesuper.R;
import com.mt.boostgamesuper.core.BoosterModes;
import com.mt.boostgamesuper.gifloadingview.GifLoadingView;
import com.mt.boostgamesuper.model.AppInfo;
import com.mt.boostgamesuper.notification.ModeNotification;
import com.mt.boostgamesuper.rootbooster.BoosterService;
import com.mt.boostgamesuper.switchbutton.SwitchButton;
import com.mt.boostgamesuper.utils.BoosterModeManager;

public class BoostProTab extends Fragment {
    // Store instance variables
    private AnimatedCircleLoadingView animatedCircleLoadingView;
    boolean flag = false;
    private static final String TAG = "BootgameFragment";
    private BoosterModeManager boosterModeManager = new BoosterModeManager();

    private SwitchButton rootModeSwitch;
    private SwitchButton rootModeSwitch2;
    private SwitchButton rootModeSwitch3;
    private CardView layoutPro;
    private CardView layoutPro1;
    private ModeNotification modeNotification;

    private boolean statusLocked;
    private boolean statusLocked1;
    private boolean statusLocked2;

    SharedPreferences prefs;
    private GifLoadingView mGifLoadingView;

    // newInstance constructor for creating fragment with arguments
    public static BoostProTab newInstance(AppInfo appInfo) {
        BoostProTab fragmentFirst = new BoostProTab();
        Bundle args = new Bundle();
        args.putSerializable("appInfo", appInfo);
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }

    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Intent intent = new Intent(getContext(), GameSelectActivity.class);
//        startActivityForResult(intent, 2);
    }

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tabpro, container, false);
        if (getContext() != null && getActivity() != null) {
            rootModeSwitch = view.findViewById(R.id.rootModeSwitch);

            rootModeSwitch2 = view.findViewById(R.id.rootModeSwitch2);

            rootModeSwitch3 = view.findViewById(R.id.rootModeSwitch3);
            modeNotification = new ModeNotification(getContext());

            layoutPro = view.findViewById(R.id.versionPro);

            layoutPro1 = view.findViewById(R.id.versionPro2);
            mGifLoadingView = new GifLoadingView();
            prefs = PreferenceManager.getDefaultSharedPreferences(getContext());

            statusLocked = prefs.getBoolean("checked0", false);
            statusLocked1 = prefs.getBoolean("checked1", false);
            statusLocked2 = prefs.getBoolean("checked2", false);

            rootModeSwitch.setChecked(statusLocked);
            rootModeSwitch2.setChecked(statusLocked1);
            rootModeSwitch3.setChecked(statusLocked2);

            rootModeSwitch.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                    if (isChecked) {
                        new Thread(new Runnable() {
                            public void run() {
                                for (int i = 0; i <= 9; i++) {
                                    try {
                                        Thread.sleep(150);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }
                                if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                    mGifLoadingView.dismiss();
                                }
                            }
                        }).start();
                        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            mGifLoadingView.show(getActivity().getFragmentManager(), "");
                            mGifLoadingView.setCancelable(false);
                        }
                        new AsyncNetWork(0).execute();
                    } else {
                        statusLocked = prefs.edit().putBoolean("checked0", false).commit();
                    }
                }
            });


            rootModeSwitch2.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                    if (isChecked) {
                        new Thread(new Runnable() {
                            public void run() {
                                for (int i = 0; i <= 9; i++) {
                                    try {
                                        Thread.sleep(150);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }
                                if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                    mGifLoadingView.dismiss();
                                }
                            }
                        }).start();
                        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            mGifLoadingView.show(getActivity().getFragmentManager(), "");
                            mGifLoadingView.setCancelable(false);
                        }
                        new AsyncNetWork(1).execute();
                    } else {
                        statusLocked1 = prefs.edit().putBoolean("checked1", false).commit();
                        Toast.makeText(getContext(), "turned off high mode!", Toast.LENGTH_SHORT).show();
                        modeNotification.startNotification(BoosterModes.NO_MODE);
                    }
                }
            });

            rootModeSwitch3.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                    if (isChecked) {
                        new Thread(new Runnable() {
                            public void run() {
                                for (int i = 0; i <= 9; i++) {
                                    try {
                                        Thread.sleep(150);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }
                                if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                    mGifLoadingView.dismiss();
                                }
                            }
                        }).start();
                        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            mGifLoadingView.show(getActivity().getFragmentManager(), "");
                            mGifLoadingView.setCancelable(false);
                        }
                        new AsyncNetWork(2).execute();
                    } else {
                        statusLocked1 = prefs.edit().putBoolean("checked2", false).commit();
                        Toast.makeText(getContext(), "turned off AI mode!", Toast.LENGTH_SHORT).show();
                        modeNotification.startNotification(BoosterModes.NO_MODE);
                    }
                }
            });

            rootModeSwitch2.setEnabled(true);
        }
        return view;
    }

    private void startRootMode(int mode) {
        if (getContext() != null) {
            getContext().startService(new Intent(getContext().getApplicationContext(), BoosterService.class));
            boosterModeManager.setMode(mode);
        }
    }


    public class AsyncNetWork extends AsyncTask<Void, Void, String> {

        private int mode;

        public AsyncNetWork(int mode) {
            this.mode = mode;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String httpResponse) {
            super.onPostExecute(httpResponse);
            if (mode == 1) {
                Toast.makeText(getContext(), "turned on high mode!", Toast.LENGTH_SHORT).show();
                modeNotification.startNotification(1);
            } else if (mode == 2) {
                Toast.makeText(getContext(), "turned on AI mode!", Toast.LENGTH_SHORT).show();
                modeNotification.startNotification(3);
            }

            statusLocked = prefs.edit().putBoolean("checked" + mode, true).commit();
        }

        @Override
        protected String doInBackground(Void... voids) {
            startRootMode(mode);
            return null;
        }
    }

}
