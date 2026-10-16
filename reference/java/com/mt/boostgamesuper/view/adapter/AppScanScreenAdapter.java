package com.mt.boostgamesuper.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;
import com.mt.boostgamesuper.R;

import java.util.List;

public class AppScanScreenAdapter extends RecyclerView.Adapter<AppScanScreenAdapter.ViewHolder> {

    private List<String> appInfoList;
    private Context context;

    public AppScanScreenAdapter(List<String> appInfoList, Context context) {
        this.appInfoList = appInfoList;
        this.context = context;
    }

    @Override
    public AppScanScreenAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_app_main, parent, false);
        return new ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(final AppScanScreenAdapter.ViewHolder holder, final int position) {
//        holder.appName.setText(appInfoList.getName());
        final String appInfo = appInfoList.get(position);
        Drawable icon = null;
        String PACKAGE_NAME = context.getPackageName();
        if (!PACKAGE_NAME.equalsIgnoreCase(appInfo)) {
            try {
                icon = context.getPackageManager().getApplicationIcon(appInfo);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            holder.appImage.setImageDrawable(icon);


            final Drawable finalIcon = icon;
            holder.appImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new MaterialStyledDialog.Builder(context)
                            .setIcon(finalIcon)
                            .withDialogAnimation(true)
                            .setTitle(R.string.remove_title)
                            .setDescription(R.string.remove_des)
                            .setHeaderColor(R.color.colorPrimary)
                            .setPositiveText(R.string.uninstall)
                            .onPositive(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    Uri packageUri = Uri.parse("package:" + appInfo);
                                    Intent uninstallIntent =
                                            new Intent(Intent.ACTION_UNINSTALL_PACKAGE, packageUri);
                                    context.startActivity(uninstallIntent);
                                }
                            })
                            .setNegativeText(R.string.later).show();
                }
            });
        }
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