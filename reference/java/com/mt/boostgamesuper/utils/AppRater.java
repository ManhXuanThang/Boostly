package com.mt.boostgamesuper.utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;
import com.mt.boostgamesuper.R;

public class AppRater {
    private final static String APP_TITLE = "BOOST SUPER 2019";// App Name
    private final static String APP_PNAME = "com.mt.boostgamesuper";// Package Name

    private final static int DAYS_UNTIL_PROMPT = 3;//Min number of days
    private final static int LAUNCHES_UNTIL_PROMPT = 3;//Min number of launches

    public static void app_launched(Context mContext) {
        Log.e("AppRater", "  loading");
        SharedPreferences prefs = mContext.getSharedPreferences("apprater", 0);
        if (prefs.getBoolean("dontshowagain", false)) {
            return;
        }

        SharedPreferences.Editor editor = prefs.edit();

        // Increment launch counter
        long launch_count = prefs.getLong("launch_count", 0) + 1;
        editor.putLong("launch_count", launch_count);

        // Get date of first launch
        Long date_firstLaunch = prefs.getLong("date_firstlaunch", 0);
        if (date_firstLaunch == 0) {
            date_firstLaunch = System.currentTimeMillis();
            editor.putLong("date_firstlaunch", date_firstLaunch);
            Log.e("AppRater", "  date_firstLaunch");
        }

        // Wait at least n days before opening
        if (launch_count >= LAUNCHES_UNTIL_PROMPT) {
            if (System.currentTimeMillis() >= date_firstLaunch +
                    (DAYS_UNTIL_PROMPT * 24 * 60 * 60 * 1000)) {
                showRateDialog(mContext, editor);
                Log.e("AppRater", "  Wait at least n days before opening");
            }
        }
        editor.apply();
    }

    public static void showRateDialog(final Context mContext, final SharedPreferences.Editor editor) {


        MaterialStyledDialog materialStyledDialog = new MaterialStyledDialog.Builder(mContext)
                .setIcon(mContext.getResources().getDrawable(R.drawable.rocket))
                .withDialogAnimation(true)
                .setTitle("Rate BOOST SUPER 2019")
                .setDescription("If you enjoy using BOOST SUPER 2019, please take a moment to rate it. Thanks for your support!")
                .setHeaderColor(R.color.colorPrimary)
                .setPositiveText("Rate BOOST SUPER 2019")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        try {
                            mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + "com.mtmobile.boostgamesuper")));
                        } catch (android.content.ActivityNotFoundException anfe) {
                            mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + "com.mtmobile.boostgamesuper")));
                        }
                    }
                })
                .setNegativeText("Remind me later").onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                    }
                })
                .setNeutralText("No, thanks").onNeutral(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        editor.putBoolean("dontshowagain", true);
                        editor.commit();
                    }
                }).show();
    }
}