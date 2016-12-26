package com.android.lq.p2p.lili.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.os.Environment;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by a on 2016/12/26.
 */

public class PropertiesUtil {

    /**
     * 获取配置url
     *
     * @param context
     * @return
     */
    public static Properties initProperties(Context context) {
        Properties properties = new Properties();
        InputStream is = null;
        try {
            String packageName = context.getPackageName();
            AssetManager am = context.getAssets();
            is = am.open(packageName + ".ini");
            properties.load(is);
        } catch (Exception e) {
            //            DLog.e("PropertiesUtil#init. System Properties init faild. Exception=",
            //                    e);
            throw new RuntimeException(
                    "PropertiesUtil#init. System Properties init faild.");
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    //                    DLog.e("PropertiesUtil#init. System Properties init faild. IOException=",
                    //                            e);
                    throw new RuntimeException(
                            "PropertiesUtil#init. System Properties init faild.");
                }
            }
        }

        return properties;
    }
}
