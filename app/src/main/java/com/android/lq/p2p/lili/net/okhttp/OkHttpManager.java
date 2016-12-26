package com.android.lq.p2p.lili.net.okhttp;

import android.content.Context;
import android.os.Environment;

import com.android.lq.p2p.lili.util.Util;
import com.squareup.okhttp.Cache;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLHandshakeException;

/**
 * Created by a on 2016/12/26.
 */

public class OkHttpManager {

    public static final int DEFAULT_TIME_OUT = 15;

    /**
     * okhttp至少需要android2.3以上才能运行
     */
    public static final int MIN_SDK = 9;

    private static OkHttpManager instance;

    /**
     * 服务器列表(默认服务器和备用服务器)
     */
    private ArrayList<String> serverUrls;

    private static Context context;

    OkHttpClient client;

    /**
     * 当前使用的服务器,在服务器列表中的位置
     */
    private int currServerIndex = 0;


    public static void init(Context context, ArrayList<String> urls) {
        OkHttpManager.context = context;
        instance = new OkHttpManager(urls);
    }

    public static OkHttpManager getInstance() {
        return instance;
    }

    private OkHttpManager(ArrayList<String> urls) {
        serverUrls = urls;
        client = new OkHttpClient();
        client.setConnectTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS);

        client.setWriteTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS);

        client.setReadTimeout(10, TimeUnit.SECONDS);

        int cacheSize = 10 * 1024 * 1024; // 10 MiB

        if (Util.getAvailableSize() > cacheSize) {
            File cachFile = new File(Environment.getExternalStorageDirectory()
                    .getPath() + File.separator + "Aili");
            if (!cachFile.exists()) {
                cachFile.mkdirs();
            }
            Cache cache = new Cache(cachFile, cacheSize);

            client.setCache(cache);
        }
        // GetCerTask task = new GetCerTask(context);
        // task.execute();
    }


    public String startGet() {
        String host = serverUrls.get(currServerIndex);
        Request request = new Request.Builder().url(host).build();
        try {
            return execute(request);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    public String startPost(String postData) {
        if (!Util.isNetworkAvailable(context)) {
            return null;
        }
        String host = serverUrls.get(currServerIndex);

        String strResult = postJSON(host, postData);
        if (strResult == null) {// 换备用服务器重试
            if (currServerIndex + 1 >= serverUrls.size()) {
                currServerIndex = 0;
            } else {
                currServerIndex++;
            }
            host = serverUrls.get(currServerIndex);
            strResult = postJSON(host, postData);
        }
        return strResult;
    }

    public String postJSON(String url, String json) {
        //        DLog.d(TAG, getTID() + "postJSON()#host=" + url + ",postData=" + json);
        if (!Util.isNetworkAvailable(context)) {
            return null;
        }
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                //                .addHeader(SESSION_ID, DataCollectUtil.getSession_id())
                .addHeader("Connection", "keep-alive")
                // .addHeader("Keep-alive", "300")
                .url(url).post(body).build();
        try {
            return execute(request);
        } catch (SSLHandshakeException se) {// 如果捕捉到证书错误,则从接口上获取新证书,并添加到白名单.
            //            DLog.d(TAG, "捕捉到证书异常错误,并获取新证书" + res);
            try {// 如果还是失败,则直接返回null,不再进行尝试
                return execute(request);
            } catch (Exception e) {
                //                DLog.e(TAG, "postJSON():catch (SSLHandshakeException se)", e);
                return null;
            }
        } catch (Exception e) {
            //            DLog.e(TAG, "postJSON()", e);
            return null;
        }
    }

    private String execute(Request request) throws IOException {
        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            String strResult = response.body().string();

            if (strResult != null) {
                if (strResult.length() > 2048) {
                    //                    DLog.d("OkHttpManager",
                    //                            "respond==" + strResult.substring(0, 2048));
                } else {
                    //                    DLog.d("OkHttpManager", "respond==" + strResult);
                }
            } else {
                //                DLog.d("OkHttpManager", "respond==null");
            }
            return strResult;
        } else {
            throw new IOException("Unexpected code " + response);
        }
    }


}
