package com.mt.boostgamesuper.view.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;
import com.mt.boostgamesuper.R;
import com.mt.boostgamesuper.gifloadingview.GifLoadingView;
import com.mt.boostgamesuper.rootbooster.BoosterService;
import com.mt.boostgamesuper.switchbutton.SwitchButton;
import com.mt.boostgamesuper.utils.BoosterModeManager;

public class SettingActivity extends AppCompatActivity {

    private TextView aboutMe;
    private TextView rateMe;
    private TextView proVersion;
    private TextView phanhoi;
    private TextView title_text;
    private GifLoadingView mGifLoadingView;
    private SwitchButton switchButton;
    private LinearLayout lntBack;

    private BoosterModeManager boosterModeManager = new BoosterModeManager();

    private boolean statusLocked;

    MaterialStyledDialog materialStyledDialog;
    SharedPreferences prefs;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            Window window = this.getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimary));
        }
        aboutMe = findViewById(R.id.tvAbout);
        rateMe = findViewById(R.id.rateMe);
        proVersion = findViewById(R.id.proVersion);
        phanhoi = findViewById(R.id.phanhoi);

        lntBack = findViewById(R.id.llBackIcon);
        title_text = findViewById(R.id.title_text);
        switchButton = findViewById(R.id.rootModeSwitch);
        title_text.setText(getString(R.string.settings));
        mGifLoadingView = new GifLoadingView();
        aboutMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
        rateMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                }
            }
        });
        proVersion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), getString(R.string.comming_soon), Toast.LENGTH_SHORT).show();
            }
        });
        phanhoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        lntBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        prefs = PreferenceManager.getDefaultSharedPreferences(this);

        statusLocked = prefs.getBoolean("checked1", false);

        switchButton.setChecked(statusLocked);

        switchButton.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
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
                        mGifLoadingView.show(getFragmentManager(), "");
                        mGifLoadingView.setCancelable(false);
                    }
                    new AsyncNetWork(1).execute();
                } else {
                    statusLocked = prefs.edit().putBoolean("checked1", false).commit();
                }
            }
        });

    }

    public void showDialog() {

        materialStyledDialog = new MaterialStyledDialog.Builder(this)
                .setIcon(getResources().getDrawable(R.drawable.rocket))
                .withDialogAnimation(true)
                .setTitle(R.string.about_me)
                .setDescription(R.string.version)
                .setHeaderColor(R.color.colorPrimary)
                .setPositiveText(R.string.uninstall)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                    }
                }).show();

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
            statusLocked = prefs.edit().putBoolean("checked1", true).commit();
            Toast.makeText(getApplicationContext(), getString(R.string.enable_utra_success), Toast.LENGTH_SHORT).show();
        }

        @Override
        protected String doInBackground(Void... voids) {
            startRootMode(mode);
            return null;
        }
    }

    private void startRootMode(int mode) {
        startService(new Intent(getApplicationContext(), BoosterService.class));
        boosterModeManager.setMode(mode);
    }

}
