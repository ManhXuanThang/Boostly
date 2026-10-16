package com.mt.boostgamesuper.view.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.mt.boostgamesuper.R;
import com.mt.boostgamesuper.model.AppInfo;
import com.mt.boostgamesuper.view.Constants;
import com.mt.boostgamesuper.view.adapter.AppAdapter;
import com.mt.boostgamesuper.view.base.BaseActivity;
import com.mt.boostgamesuper.view.interfaces.IitemClickListener;

import java.util.ArrayList;
import java.util.List;

public class GameSelectActivity extends BaseActivity implements IitemClickListener {
    RecyclerView appRecyclerView;
    LinearLayout button;


    ArrayList<AppInfo> appInfos = new ArrayList<>();

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_app);


        appRecyclerView = findViewById(R.id.recyclerview);
        appRecyclerView = findViewById(R.id.recyclerview);
        button = findViewById(R.id.lntFloat);
        getApplicationList();


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for (AppInfo appInfo : appInfos) {
                    Log.e("gameactivity : appInfo", appInfo.getName());
                }
                Intent returnIntent = new Intent();
                returnIntent.putExtra(Constants.GAME_APP_SELECT, appInfos);
                setResult(2, returnIntent);
                finish();
            }
        });


    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void getApplicationList() {
        final PackageManager packageManager = getPackageManager();
        List<ApplicationInfo> appList = packageManager.getInstalledApplications(0);

        List<AppInfo> appInfoList = new ArrayList<>();
        String PACKAGE_NAME = this.getPackageName();
        for (ApplicationInfo info : appList) {
            AppInfo appInfo = new AppInfo();
            if ((info.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
                String name = packageManager.getApplicationLabel(info).toString();

                appInfo.setName(name);
                appInfo.setPackageName(info.packageName);


//                appInfo.setIcon(info.loadIcon(packageManager));
                appInfoList.add(appInfo);
            }


        }

        AppAdapter appAdapter = new AppAdapter(appInfoList, this, this);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        appRecyclerView.setLayoutManager(manager);
        appRecyclerView.setAdapter(appAdapter);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onRemove(AppInfo app) {
        appInfos.remove(app);
    }

    @Override
    public void onAddItem(AppInfo app) {
        Log.e("gameactivity: ", app.getName());

        appInfos.add(app);
    }
}
