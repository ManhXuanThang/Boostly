package com.mt.boostgamesuper.view.fragment.boostgame;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.Toast;

import com.mt.boostgamesuper.R;
import com.mt.boostgamesuper.model.AppInfo;
import com.wang.avi.AVLoadingIndicatorView;

public class BoostingResultFragment extends Fragment {

    private Activity activity;
    private AVLoadingIndicatorView iv;
    private ImageView imageView;
    private TextView textView;
    private ImageView imgApp;
    private LinearLayout lntApp;
    private TextView txtlaunch;
    private ImageView imgClose;

    // newInstance constructor for creating fragment with arguments
    public static BoostingResultFragment newInstance(AppInfo appInfo) {
        BoostingResultFragment fragmentFirst = new BoostingResultFragment();
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
        View view = inflater.inflate(R.layout.scan_result, container, false);
        if (activity != null) {
            iv=view.findViewById(R.id.avi);
            imageView=view.findViewById(R.id.img_tick_v1);
            textView=view.findViewById(R.id.txt_clean);
            imgApp=view.findViewById(R.id.img_app);
            lntApp=view.findViewById(R.id.lntLnt);
            txtlaunch=view.findViewById(R.id.txt_launch);
            imgClose=view.findViewById(R.id.img_close);
        }
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getArguments() != null) {
            final AppInfo appInfo = (AppInfo) getArguments().getSerializable("appInfo");
            textView.setText(appInfo.getName() + " " + getString(R.string.optimized));
            Drawable icon = null;
            try {
                icon = activity.getPackageManager().getApplicationIcon(appInfo.getPackageName());
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            imgApp.setImageDrawable(icon);
            txtlaunch.setText("LAUNCH " + appInfo.getName().toUpperCase());
            new CountDownTimer(1000, 1000) { // adjust the milli seconds here

                public void onTick(long millisUntilFinished) {
                }

                public void onFinish() {
                    iv.setVisibility(View.GONE);
                    imageView.setVisibility(View.VISIBLE);
                    textView.setVisibility(View.VISIBLE);
                    lntApp.setVisibility(View.VISIBLE);
                    txtlaunch.setVisibility(View.VISIBLE);
                }
            }.start();


            txtlaunch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        try {
                            Intent intent = activity.getPackageManager().getLaunchIntentForPackage(appInfo.getPackageName());

                            assert intent != null;
                            intent.addCategory("android.intent.category.LAUNCHER");
                            startActivity(intent);
                            activity.finish();
                        } catch (Exception e) {

                        }
                    }else {
                        activity.finish();
                    }
                }
            });
            imgClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.finish();
                }
            });

        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Activity) {
            activity = (Activity) context;
        }
    }

}
