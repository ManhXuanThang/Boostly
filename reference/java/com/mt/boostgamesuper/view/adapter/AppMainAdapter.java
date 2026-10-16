package com.mt.boostgamesuper.view.adapter;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.mt.boostgamesuper.R;
import com.mt.boostgamesuper.model.AppInfo;
import com.mt.boostgamesuper.view.interfaces.IitemClickItemMainListener;

import java.util.List;

public class AppMainAdapter extends RecyclerView.Adapter<AppMainAdapter.ViewHolder> {

    private List<AppInfo> appInfoList;
    private Context context;
    IitemClickItemMainListener listener;

    public AppMainAdapter(List<AppInfo> appInfoList, Context context, IitemClickItemMainListener listener) {
        this.appInfoList = appInfoList;
        this.context = context;
        this.listener = listener;
    }

    @Override
    public AppMainAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_app_main, parent, false);
        return new ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(final AppMainAdapter.ViewHolder holder, final int position) {
//        holder.appName.setText(appInfoList.getName());
        AppInfo appInfo = appInfoList.get(position);
        Drawable icon = null;
        try {
            icon = context.getPackageManager().getApplicationIcon(appInfo.getPackageName());
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        holder.appImage.setImageDrawable(icon);

        if (appInfo.isList()) {
            holder.checkBox.setVisibility(View.VISIBLE);
        } else {
            holder.checkBox.setVisibility(View.GONE);
        }

        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Log.e("adapter: ", appInfoList.size()+"");
                    listener.onDeleteItem(appInfoList.get(position), position);
                } else {
                    listener.onRevertItem(appInfoList.get(position));
                }
            }
        });

        holder.appItem.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                listener.onDeleteShowIcon(appInfoList);
                return true;
            }
        });


        holder.appItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!appInfoList.get(position).isList())
                    listener.onCardItemClick(appInfoList.get(position), position);
            }
        });
    }


    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return appInfoList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView appImage;
        //        TextView appName;
        RelativeLayout appItem;
        CheckBox checkBox;
        View mItemView;

        ViewHolder(View view) {
            super(view);
            mItemView = view;
            appImage = view.findViewById(R.id.appImage);
//            appName = view.findViewById(R.id.appName);
            appItem = view.findViewById(R.id.appItem);
            checkBox = view.findViewById(R.id.ckDelete);
        }

    }
}