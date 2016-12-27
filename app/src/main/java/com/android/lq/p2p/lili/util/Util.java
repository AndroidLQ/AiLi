package com.android.lq.p2p.lili.util;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.android.lq.p2p.lili.ui.NetErrorDialogActivity;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by a on 2016/12/9.
 */

public class Util {

    private static final String TAG = "Util";

    /**
     * @param context
     * @param light 是否是轻颜色背景，假如是则修改状态栏图标颜色，如果不是则不需要修改
     */
    @TargetApi(19)
    public static void setStatusBar(Activity context, boolean light)
    {
        if (Build.VERSION.SDK_INT >= 21)
        {
            Window window = context.getWindow();
            window.clearFlags(0x4000000
            /* | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION */);
            // window.getDecorView().setSystemUiVisibility(
            // 0x400/* View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN */
            // | 0x200/* View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION */
            // | 0x100/* View.SYSTEM_UI_FLAG_LAYOUT_STABLE */);
            try
            {
                Class<?> classView = Class.forName("android.view.View");
                Method setSystemUiVisibility = classView.getMethod(
                        "setSystemUiVisibility", int.class);
                setSystemUiVisibility.setAccessible(true);
                if (light && Build.VERSION.SDK_INT >= 23)
                {
                    setSystemUiVisibility.invoke(window.getDecorView(), 0x400
                            // | 0x200
                            | 0x100 | 0x00002000);
                }
                else
                {
                    setSystemUiVisibility.invoke(window.getDecorView(), 0x400
                            // | 0x200
                            | 0x100);
                }
            }
            catch (Exception e)
            {
                Log.d(TAG, "setSystemUiVisibility:" + e);
            }
            window.addFlags(0x80000000);
            // window.setStatusBarColor(0x0d000000/* Color.TRANSPARENT */);
            // window.setNavigationBarColor(Color.TRANSPARENT);
            try
            {
                Class<?> clazz = Class.forName("android.view.Window");
                Method setColor = clazz.getDeclaredMethod("setStatusBarColor",
                        int.class);
                setColor.setAccessible(true);
                // setColor.invoke(window, 0x00000000);
                if (Build.VERSION.SDK_INT >= 23)
                {
                    setColor.invoke(window, 0x00000000);
                }
                else
                {
                    setColor.invoke(window, 0x33000000);
                }
            }
            catch (Exception e)
            {
                Log.d(TAG, "setStatusBar:" + e);
            }
        }
    }

    /**
     * 获取状态栏的高度
     * @param context
     * @return
     */
    public static int getStatusBarHight(Context context)
    {
        int sbar = 0;
        try
        {
            Class<?> c = Class.forName("com.android.internal.R$dimen");
            Object obj = c.newInstance();
            Field field = c.getField("status_bar_height");
            int x = Integer.parseInt(field.get(obj).toString());
            sbar = context.getResources().getDimensionPixelSize(x);
        }
        catch (Exception e1)
        {
            sbar = 0;
            Log.e(TAG, "getStatusBarHight", e1);
        }

        return sbar;
    }

    public static boolean isNetworkAvailable(Context c)
    {
        ConnectivityManager connectivity = (ConnectivityManager) c
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivity != null)
        {
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (info != null)
            {
                if (info.getState() == NetworkInfo.State.CONNECTED)
                {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 显示网络连接失败
     * @param context
     */
    public static void showNetErrorDialog(Context context)
    {
        Intent intent = new Intent();
        intent.setClass(context, NetErrorDialogActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /**
     * @Title: getAvailableSize
     * @Description: 获取sd卡剩余空间.没有sd卡,或者sd卡未挂载,则返回0
     * @return long 剩余空间,单位byte
     * @throws
     */
    public static long getAvailableSize()
    {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state))
        {
            File sdcardDir = Environment.getExternalStorageDirectory();
            StatFs sf = new StatFs(sdcardDir.getPath());
            long blockSize = sf.getBlockSize();
            long availCount = sf.getAvailableBlocks();
            return availCount * blockSize;
        }
        else
        {
            return 0;
        }
    }

    /**
     * @Author: kobe
     * @CreteDate: 2015-4-1 下午6:25:29
     * @Title:
     * @Description:获取屏幕宽度
     * @ModifiedBy:
     * @param context
     * @return
     */
    public static int getScreenPiexWidth(Context context)
    {
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        manager.getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }

}
