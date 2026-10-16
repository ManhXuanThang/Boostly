package com.mt.boostgamesuper.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.mt.boostgamesuper.BuildConfig;
import com.mt.boostgamesuper.R;
import com.mt.boostgamesuper.core.BoosterModes;

public class ModeNotification {
    private Context context;
    private NotificationManager notificationManager;

    public ModeNotification(Context context) {
        this.context = context;
        this.notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    }

    public void startNotification(int notifyMode) {
        String title = this.context.getString(R.string.notification_title);
        String description = BuildConfig.FLAVOR;
        switch (notifyMode) {
            case BoosterModes.NO_MODE: /*0*/
                this.notificationManager.cancelAll();
                break;
            case BoosterModes.HIGH_MODE: /*1*/
                this.notificationManager.cancelAll();
                description = this.context.getString(R.string.high_mode);
                break;
            case BoosterModes.ULTRA_MODE: /*2*/
                this.notificationManager.cancelAll();
                description = this.context.getString(R.string.ultra_mode);
                break;
            case 3: /*2*/
                this.notificationManager.cancelAll();
                description = "AI BOOST";
                break;
        }
        this.notificationManager.notify(notifyMode,
                new Notification.Builder(this.context)
                        .setContentTitle(title)
                        .setContentText(description).
                        setSmallIcon(R.drawable.ic_stat_my_logo_1)
                        .setPriority(Notification.PRIORITY_MAX)
                        .setOngoing(true)
                        .addAction(R.drawable.ic_clear_black_24dp,
                                this.context.getString(R.string.stop),
                                PendingIntent.getActivity(this.context, 1,
                                        new Intent(this.context, DisableModeHelperActivity.class),
                                        PendingIntent.FLAG_UPDATE_CURRENT))
                        .build());

    }
}