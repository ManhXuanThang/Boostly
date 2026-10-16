package com.mt.boostgamesuper.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.view.Window;

import com.mt.boostgamesuper.R;

public class ViewDialog {

    public void showDialog(Activity activity) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_about_me);

        dialog.show();

    }
}