package com.mt.boostgamesuper.view.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mt.boostgamesuper.AnimatedCircleLoadingView;
import com.mt.boostgamesuper.R;
import com.mt.boostgamesuper.model.AppInfo;
import com.mt.boostgamesuper.view.Constants;
import com.mt.boostgamesuper.view.activity.BoostGameActivity;
import com.mt.boostgamesuper.view.activity.GameSelectActivity;
import com.mt.boostgamesuper.view.adapter.AppMainAdapter;
import com.mt.boostgamesuper.view.interfaces.IitemClickItemMainListener;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

import static android.app.Activity.RESULT_CANCELED;

public class BootgameFragment extends Fragment implements IitemClickItemMainListener {
    // Store instance variables
    private AnimatedCircleLoadingView animatedCircleLoadingView;
    LinearLayout lntFloat;
    LinearLayout lntFloat2;
    AppCompatButton bootsGame;
    RecyclerView recyclerview;

    List<AppInfo> appInfos;
    List<AppInfo> newList;
    AppMainAdapter appAdapter;
    SharedPreferences sharedPrefs;
    FloatingActionButton button;
    FloatingActionButton button2;
    boolean flag = false;
    String PACKAGE_NAME = "com.mtmobile.boostgamepro";
    private static final String TAG = "BootgameFragment";

    // newInstance constructor for creating fragment with arguments
    public static BootgameFragment newInstance(AppInfo appInfo) {
        BootgameFragment fragmentFirst = new BootgameFragment();
        Bundle args = new Bundle();
        args.putSerializable("appInfo", appInfo);
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }

    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Intent intent = new Intent(activity, GameSelectActivity.class);
//        startActivityForResult(intent, 2);
    }

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_game, container, false);
        if (getContext() != null) {
            recyclerview = (RecyclerView) view.findViewById(R.id.recyclerview);
            lntFloat = (LinearLayout) view.findViewById(R.id.lntFloat);
            lntFloat2 = (LinearLayout) view.findViewById(R.id.lntFloat2);
            bootsGame = (AppCompatButton) view.findViewById(R.id.boots);
            button = (FloatingActionButton) view.findViewById(R.id.ic_float);
            button2 = (FloatingActionButton) view.findViewById(R.id.ic_float2);
            appInfos = new ArrayList<>();
            newList = new ArrayList<>();
            animatedCircleLoadingView = (AnimatedCircleLoadingView) view.findViewById(R.id.circle_loading_view);
            lntFloat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e(TAG, "lntFloat.getVisibility() == View.VISIBLE");
                    Intent intent = new Intent(getContext(), GameSelectActivity.class);
                    startActivityForResult(intent, 2);
                }
            });
            lntFloat2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (appAdapter != null && newList.size() > 0) {
                        List<AppInfo> list2 = new ArrayList<>(appInfos);
                        for (AppInfo appInfo : appInfos) {
                            for (AppInfo app : newList) {
                                if (app.getName().equalsIgnoreCase(appInfo.getName())) {
                                    list2.remove(app);
                                }
                            }

                        }
                        for (AppInfo app : list2) {
                            app.setList(false);
                        }
                        appInfos.clear();
                        appInfos.addAll(list2);
                        appAdapter.notifyDataSetChanged();
                        recyclerview.setAdapter(appAdapter);
                    }
                    saveObjectData(appInfos);

                    setViewAddShow();
                }
            });


            if (getObjectData() != null) {

                appInfos.clear();
                appInfos.addAll(getObjectData());
                if (appAdapter == null) {
                    appAdapter = new AppMainAdapter(appInfos, getContext(), this);
                    recyclerview.setLayoutManager(new GridLayoutManager(getContext(), 5));
                    recyclerview.setAdapter(appAdapter);
                }
            }
        }
        return view;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent datas) {
        super.onActivityResult(requestCode, resultCode, datas);
        if (getContext() != null) {
            if (resultCode != RESULT_CANCELED) {
                if (requestCode == 2) {
                    Timber.e("onActivityResult");
                    List<AppInfo> list = (ArrayList<AppInfo>) datas.getSerializableExtra(Constants.GAME_APP_SELECT);
//                lntFloat.setVisibility(View.GONE);
//                bootsGame.setVisibility(View.VISIBLE);

                    List<AppInfo> list2 = new ArrayList<>(list);
                    Log.e(TAG, list.size() + "");
                    for (AppInfo appInfo : appInfos) {
                        for (AppInfo app : list) {
                            Log.e(TAG, "list: " + app.getName() + "");
                            if (app.getName().equalsIgnoreCase(appInfo.getName())) {
                                list2.remove(app);
                            }
                        }
                    }

                    appInfos.addAll(list2);

                    if (appInfos.size() > 0) {
                        if (appAdapter != null) {
                            appAdapter.notifyDataSetChanged();
                            recyclerview.setAdapter(appAdapter);
//                        saveObjectData(appInfos);
                        } else {
                            appAdapter = new AppMainAdapter(appInfos, getContext(), this);
                            recyclerview.setLayoutManager(new GridLayoutManager(getContext(), 5));
                            recyclerview.setAdapter(appAdapter);
                        }
                        saveObjectData(appInfos);
                    }
                }
            }
        }

    }

    @Override
    public void onCardItemClick(AppInfo bankCard, int pos) {
        if (getContext() != null) {
            Intent intent = new Intent(getContext(), BoostGameActivity.class);
            intent.putExtra(Constants.GAME_APP_SELECT, bankCard);
            startActivity(intent);
        }
    }

    @Override
    public void onDeleteItem(AppInfo bankCard, int pos) {
        setViewDeleteShow();
        newList.add(bankCard);
    }

    @Override
    public void onDeleteShowIcon(List<AppInfo> list) {
        for (AppInfo appInfo : appInfos) {
            appInfo.setList(true);
        }
        appAdapter.notifyDataSetChanged();
        recyclerview.setAdapter(appAdapter);
        setViewDeleteShow();
    }


    private void setViewDeleteShow() {
        lntFloat2.setVisibility(View.VISIBLE);
        lntFloat.setVisibility(View.GONE);
    }

    private void setViewAddShow() {
        lntFloat.setVisibility(View.VISIBLE);
        lntFloat2.setVisibility(View.GONE);
    }

    @Override
    public void onRevertItem(AppInfo bankCard) {
        setViewDeleteShow();
        appInfos.add(bankCard);
    }


    private void saveObjectData(List<AppInfo> appInfos) {
        if (getContext() != null) {
            sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getContext());
            SharedPreferences.Editor editor = sharedPrefs.edit();
            Gson gson = new Gson();

            String json = gson.toJson(appInfos);

            editor.putString(TAG, json);
            editor.apply();
        }
    }

    private List<AppInfo> getObjectData() {
        if (getContext() != null) {
            sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getContext());
            Gson gson = new Gson();
            String json = sharedPrefs.getString(TAG, "");
            Type type = new TypeToken<List<AppInfo>>() {
            }.getType();
            return gson.fromJson(json, type);
        } else return null;

    }

}
