package com.mt.boostgamesuper.view.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mt.boostgamesuper.view.fragment.scan.ScanActivity;
import com.mt.boostgamesuper.R;
import com.mt.boostgamesuper.circlebutotn.CircularButton;
import com.mt.boostgamesuper.shadow.MyBounceInterpolator;
import com.mt.boostgamesuper.view.adapter.AppScanScreenAdapter;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_CANCELED;

public class ScanphoneFragment extends Fragment {
    // Store instance variables
    private boolean isShowDone = false;
    private int page;
    CircularButton btnScan;
    TextView tvCount;
    RecyclerView rclItem;
    AppScanScreenAdapter adapter;
    AVLoadingIndicatorView iv;
    TextView tvScan;
    RelativeLayout tvScanResult;
    private boolean flag = false;

    // newInstance constructor for creating fragment with arguments
    public static ScanphoneFragment newInstance(int page, boolean isShow) {
        ScanphoneFragment fragmentFirst = new ScanphoneFragment();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putBoolean("isShow", isShow);
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }

    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            page = getArguments().getInt("someInt", 0);
            isShowDone = getArguments().getBoolean("isShow");
        }
    }

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_first, container, false);
        if (getContext() != null && isAdded()) {
            btnScan = (CircularButton) view.findViewById(R.id.scan_button);
            tvCount = (TextView) view.findViewById(R.id.txt_count);
            rclItem = (RecyclerView) view.findViewById(R.id.recyclerview);
            iv = (AVLoadingIndicatorView) view.findViewById(R.id.avi);
            tvScan = (TextView) view.findViewById(R.id.tv_scan);
            tvScanResult = view.findViewById(R.id.tv_scan_success);
//        animateButton();
            loadItemApp();
            if (getObjectData() != null && getObjectData().length() > 0) {
                rclItem.setVisibility(View.INVISIBLE);
                tvScanResult.setVisibility(View.VISIBLE);
                tvCount.setVisibility(View.INVISIBLE);
            }
            btnScan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (getObjectData() != null && getObjectData().length() > 0 && isShowDone) {
                        Toast.makeText(getContext(), "Device is already Boosted", Toast.LENGTH_SHORT).show();
                    } else {
                        Intent intent = new Intent(getActivity(), ScanActivity.class);
                        startActivityForResult(intent, 3);
                    }
                }
            });

            new CountDownTimer(1000, 1000) { // adjust the milli seconds here

                public void onTick(long millisUntilFinished) {

                }

                public void onFinish() {
                    flag = true;
                    tvScan.setVisibility(View.VISIBLE);
                    btnScan.setVisibility(View.VISIBLE);
                    iv.setVisibility(View.GONE);
                }
            }.start();

            if (flag) {
                tvScan.setVisibility(View.VISIBLE);
                btnScan.setVisibility(View.VISIBLE);
                iv.setVisibility(View.GONE);
            }
        }
        return view;
    }

    private String getObjectData() {
        if (getContext() != null) {
            SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getContext());
            return sharedPrefs.getString("ScanActivity", "");
        } else return null;

    }


    void animateButton() {
        // Load the animation
        final Animation myAnim = AnimationUtils.loadAnimation(getContext(), R.anim.bounce);
        double animationDuration = 2000.0;
        myAnim.setDuration((long) animationDuration);
        // Use custom animation interpolator to achieve the bounce effect
        MyBounceInterpolator interpolator = new MyBounceInterpolator(0.2, 20.0);

        myAnim.setInterpolator(interpolator);

        // Animate the button
        btnScan.startAnimation(myAnim);
        //  playSound();

        // Run button animation again after it finished
        myAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation arg0) {
            }

            @Override
            public void onAnimationRepeat(Animation arg0) {
            }

            @Override
            public void onAnimationEnd(Animation arg0) {
//                animateButton();
            }
        });
    }

    private void loadItemApp() {
        if (getContext() != null&& isAdded()) {
            PackageManager packageManager = getContext().getPackageManager();
            List<ApplicationInfo> appList = packageManager.getInstalledApplications(0);

            List<String> appInfoList = new ArrayList<>();
            String PACKAGE_NAME = "com.mtmobile.boostgamepro";
            for (ApplicationInfo info : appList) {
                if ((info.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
                    if (!PACKAGE_NAME.equalsIgnoreCase(info.packageName)) {
                        appInfoList.add(info.packageName);
                    }
                }
            }
            final int size = appInfoList.size();
            try {

                tvCount.setTextColor(getContext().getResources().getColor(R.color.colorPrimary));
                tvCount.setText(getString(R.string.caculate));
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (getContext() != null)
                            tvCount.setTextColor(getContext().getResources().getColor(R.color.md_grey900));
                        tvCount.setText(size + " " + getString(R.string.slow_app));
                    }
                }, 2000);
                adapter = new AppScanScreenAdapter(appInfoList, getContext());
                LinearLayoutManager layoutManager
                        = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                rclItem.setLayoutManager(layoutManager);
                rclItem.setAdapter(adapter);
            } catch (Exception e) {

            }
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent datas) {
        super.onActivityResult(requestCode, resultCode, datas);
        if (resultCode != RESULT_CANCELED) {
            if (requestCode == 3) {
                isShowDone = true;
                rclItem.setVisibility(View.INVISIBLE);
                tvScanResult.setVisibility(View.VISIBLE);
                tvCount.setVisibility(View.INVISIBLE);
            }
        }
    }
}
