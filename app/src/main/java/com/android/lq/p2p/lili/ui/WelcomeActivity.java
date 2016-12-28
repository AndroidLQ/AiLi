package com.android.lq.p2p.lili.ui;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;

import com.android.lq.p2p.lili.R;
import com.android.lq.p2p.lili.base.ProjectConfig;
import com.android.lq.p2p.lili.control.AiLiPreferences;
import com.android.lq.p2p.lili.view.MarketFloateDialogBuilder;

import java.util.ArrayList;
import java.util.List;

public class WelcomeActivity extends Activity {
    private AiLiPreferences pref;

    private long delaytime = 3000;


    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                //do thing....
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pref = AiLiPreferences.getInstance(this);
        setContentView(R.layout.main_splash);

        if (pref.isShowGprsTips()) {
            showGprsDialog();
        } else {
            login();
        }
    }


    private Boolean isShowGprs;

    /**
     * 弹出gprs流量提示框
     */
    private void showGprsDialog() {
        isShowGprs = pref.isShowGprsTips();

        MarketFloateDialogBuilder builder = new MarketFloateDialogBuilder(this);
        View view = View.inflate(this, R.layout.dialog_gprs, null);
        builder.setCancelable(false);
        builder.setCenterView(view, null);
        builder.setDialogHeight((int) getResources().getDimension(
                R.dimen.gprs_dialog_height));

        final Dialog dialog = builder.crteate();

        CheckBox gprs_checkbox = (CheckBox) view.findViewById(R.id.gprs_checkbox);
        gprs_checkbox .setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        isShowGprs = isChecked;
                    }
                });

        builder.setLeftButton("退出", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                WelcomeActivity.this.finish();
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(0);
            }
        });

        builder.setRightButton("确定", new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                pref.setShowGprsTips(!isShowGprs);
                dialog.dismiss();
                login();
            }
        });

        dialog.setOnCancelListener(new OnCancelListener() {

            @Override
            public void onCancel(DialogInterface dialog) {
                if (!isFinishing()) {
                    finish();
                    android.os.Process.killProcess(android.os.Process.myPid());
                    System.exit(0);
                }
            }
        });

        dialog.show();
    }


    private void login() {

        //DLog.i(Constants.DEBUG, "运行AiLi - 启动控制器监测器");
        /******* ANT *********/
        // 检测是否存在快捷方式
        if (!ProjectConfig.noshowCut) {
            if (!pref.isCreatedShortcut() && !hasShortcut()) {
                addShortCut();
                pref.setCreatedShortcut(true);
            }
        }

//        showStartAnimation();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isHome()) {
                    //DLog.i(TAG, "当前进程不是易用汇、不进入主页面");
                    finish();
                } else {
                    {
                        // DLog.i(TAG, "当前进程为易用汇、进入主页面");
                        handleLogin();
                    }
                }
            }
        }, delaytime);
    }

    private void handleLogin() {

        if(!pref.isFirst()){
            Intent it = new Intent(this, GuideActivity.class);
            startActivity(it);
            finish();
        }else{
            Intent it = new Intent(this, HomeActivity.class);
            startActivity(it);
            finish();
        }

    }

    @Override
    public void onBackPressed() {
        // super.onBackPressed();
    }


    /**
     * 判断当前界面是否是桌面
     */
    private boolean isHome() {
        ActivityManager mActivityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> rti = mActivityManager.getRunningTasks(1);
        return getHomes().contains(rti.get(0).topActivity.getPackageName());
    }

    /**
     * 获得属于桌面的应用的应用包名称
     *
     * @return 返回包含所有包名的字符串列表
     */
    private List<String> getHomes() {
        List<String> names = new ArrayList<String>();
        PackageManager packageManager = this.getPackageManager();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        List<ResolveInfo> resolveInfo = packageManager.queryIntentActivities(
                intent, PackageManager.MATCH_DEFAULT_ONLY);
        for (ResolveInfo ri : resolveInfo) {
            names.add(ri.activityInfo.packageName);
        }
        return names;
    }


    private void showStartAnimation() {
        final ImageView iView = (ImageView) findViewById(R.id.loading_anim);
        iView.setVisibility(View.VISIBLE);
        Animation anim = AnimationUtils.loadAnimation(getBaseContext(),
                R.anim.progressbar_animate);
        iView.startAnimation(anim);
    }


    /**
     * 检测是否已有快捷方式
     */
    private boolean hasShortcut() {
        String authority = getAuthorityFromPermission(this,
                "com.android.launcher.permission.READ_SETTINGS");
        if (authority == null) {
            authority = getAuthorityFromPermission(this,
                    "com.android.launcher.permission.WRITE_SETTINGS");
        }
        if (authority == null) {
            //            DLog.e(TAG, "hasShortcut()# no authority");
            return false;
        }
        //        DLog.i(TAG, "hasShortcut()#  authority=" + authority);

        boolean hasShortcut = false;
        try {
            String appName = getResources().getString(R.string.app_name);
            Uri uri = Uri.parse("content://" + authority
                    + "/favorites?notify=true");
            Cursor cursor = getContentResolver().query(uri, null, "title=?",
                    new String[]
                            {appName}, null);
            if (cursor != null) {
                if (cursor.getCount() > 0) {
                    hasShortcut = true;
                }
                cursor.close();
            }
        } catch (Exception e) {
            //            DLog.e(TAG, "hasShortcut()#excption", e);
            return false;
        }
        //        DLog.i(TAG, "hasShortcut()#  hasShortcut=" + hasShortcut);
        return hasShortcut;
    }

    private void addShortCut() {
        //        DLog.i(TAG, "addShortCut()");
        Intent shortcut = new Intent(
                "com.android.launcher.action.INSTALL_SHORTCUT");

        // 快捷方式的名称
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME,
                getString(R.string.app_name));
        shortcut.putExtra("duplicate", false); // 不允许重复创建
        Intent in = new Intent(Intent.ACTION_MAIN);
        in.addCategory("android.intent.category.LAUNCHER");
        in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        in.addFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
        in.setComponent(new ComponentName(getApplicationContext(),
                WelcomeActivity.class));

        shortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, in);
        // 快捷方式的图标
        Intent.ShortcutIconResource iconRes = Intent.ShortcutIconResource.fromContext(
                this, R.mipmap.app_logo);
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, iconRes);
        sendBroadcast(shortcut);
    }


    private String getAuthorityFromPermission(Context context, String permission) {
        if (permission == null)
            return null;
        List<PackageInfo> packs = context.getPackageManager()
                .getInstalledPackages(PackageManager.GET_PROVIDERS);
        if (packs != null) {
            for (PackageInfo pack : packs) {
                ProviderInfo[] providers = pack.providers;
                if (providers != null) {
                    for (ProviderInfo provider : providers) {
                        if (permission.equals(provider.readPermission))
                            return provider.authority;
                        if (permission.equals(provider.writePermission))
                            return provider.authority;
                    }
                }
            }
        }
        return null;
    }


}
