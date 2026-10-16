package com.mt.boostgamesuper.view.adapter;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.like.LikeButton;
import com.like.OnLikeListener;
import com.mt.boostgamesuper.R;
import com.mt.boostgamesuper.model.AppInfo;
import com.mt.boostgamesuper.view.interfaces.IitemClickListener;

import java.util.List;

public class AppAdapter extends RecyclerView.Adapter<AppAdapter.ViewHolder> {

    private List<AppInfo> appInfoList;
    private Context context;
    IitemClickListener listener;
    private AppInfo appInfo;

    public AppAdapter(List<AppInfo> appInfoList, Context context, IitemClickListener listener) {
        this.appInfoList = appInfoList;
        this.context = context;
        this.listener = listener;
    }

    @Override
    public AppAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_app, parent, false);
        return new ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(final AppAdapter.ViewHolder holder, final int position) {
        appInfo = appInfoList.get(position);
            holder.appName.setText(appInfo.getName());

            Drawable icon = null;
            try {
                icon = context.getPackageManager().getApplicationIcon(appInfo.getPackageName());
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            holder.appImage.setImageDrawable(icon);

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
        TextView appName;
        RelativeLayout appItem;
        View mItemView;
        LikeButton heartButton;

        ViewHolder(View view) {
            super(view);
            mItemView = view;
            appImage = view.findViewById(R.id.appImage);
            appName = view.findViewById(R.id.appName);
            appItem = view.findViewById(R.id.appItem);
            heartButton = view.findViewById(R.id.heart_button);


            heartButton.setOnLikeListener(new OnLikeListener() {
                @Override
                public void liked(LikeButton likeButton) {
                    listener.onAddItem(appInfoList.get(getAdapterPosition()));
                }

                @Override
                public void unLiked(LikeButton likeButton) {
                    listener.onRemove(appInfoList.get(getAdapterPosition()));
                }
            });
        }
    }
}