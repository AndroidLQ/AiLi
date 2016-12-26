package com.android.lq.p2p.lili.net.okhttp;

import android.content.Context;
import android.os.Build;

import com.android.lq.p2p.lili.util.StringUtil;
import com.android.lq.p2p.lili.util.Util;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by a on 2016/12/26.
 */

public class HttpRequest {

    public static final int DEFAULT_TIME_OUT = 15000;

    // private static final String SESSION = "HTTP_COOKIE";

    private static final String SESSION_ID = "Cookie";

    private static final String TAG = "HttpRequest";

    /** 服务器列表(默认服务器和备用服务器) */
    private ArrayList<String> serverUrls;

    /** 当前使用的服务器,在服务器列表中的位置 */
    private int currServerIndex = 0;

    private int timeOut;

    private static HttpRequest defaultHttpRequest;

    private static Context context;


    /**
     * 获取默认的网络连接实例
     * @return
     */
    public static HttpRequest getDefaultHttpRequest()
    {
        return defaultHttpRequest;
    }

    public static void init(Context aContext, ArrayList<String> urls)
    {
        //http://www.tuicool.com/articles/riy2Az
        //设置dns缓存时间,-1 表示永久缓存，0 表示从不缓存，其他表示缓存具体有效时间(单位秒)。
        //Security.setProperty("networkaddress.cache.ttl", String.valueOf(0));
        //Security.setProperty("networkaddress.cache.negative.ttl3", String.valueOf(0));

        context = aContext;
        defaultHttpRequest = new HttpRequest(urls);
        if(Build.VERSION.SDK_INT>=OkHttpManager.MIN_SDK)
        {
            OkHttpManager.init(aContext, urls);
        }
    }

    /**
     * 自定义服务器地址的构造方法,一般需要自定义服务器地址时候使用，默认超时时间30s
     * @param serverUrls 服务器地址
     */
    public HttpRequest(ArrayList<String> serverUrls)
    {
        this(serverUrls, DEFAULT_TIME_OUT);
    }

    /**
     * 自定义服务器地址的构造方法,一般需要自定义服务器地址时候使用
     * @param timeOut 超时时间
     */
    public HttpRequest(ArrayList<String> serverUrls, int timeOut)
    {
//        DLog.i(TAG, getTID() + "HttpRequest#serverUrl=" + serverUrls.toString()
//                + ", timeOut=" + timeOut);
        this.serverUrls = serverUrls;
        this.timeOut = timeOut;
    }

    public String startGet()
    {
        return startGet((String) null, true);
    }

    public String startGet(Map<String, String> params)
    {
        return startGet(StringUtil.mapToUrlParams(params), true);
    }

    /**
     * 网络连接方法GET，用于较短的数据返回时（一般是字串）,阻塞方法,注意从子线程里运行
     * @return 返回数据
     */
    public String startGet(String params, boolean isFirstTry)
    {
//        DLog.d(TAG, getTID() + "startGet#params=" + params);
        String host = serverUrls.get(currServerIndex);
        String strResult = connectByGet(host, params);
        if (strResult == null && isFirstTry)
        {// 换备用服务器重试
            if (currServerIndex + 1 >= serverUrls.size())
            {
                currServerIndex = 0;
            }
            else
            {
                currServerIndex++;
            }
            host = serverUrls.get(currServerIndex);
            strResult = connectByGet(host, params);
        }
        return strResult;
    }

    private String connectByGet(String host, String params)
    {
//        if (!Util.isNetworkAvailable(context))
//        {
//            return null;
//        }
//        String connectionUrl = host;
//        if (params != null)
//        {
//            connectionUrl = connectionUrl
//                    + (params.startsWith("?") ? params : "?" + params);
//        }
//        DLog.i(TAG, getTID() + "startGet#connectionUrl=" + connectionUrl);
//
//        String strResult = null;
//        try
//        {
//            HttpClient httpclient = HttpsUtil.getHttpsClient();
//            HttpGet httpGet = new HttpGet(connectionUrl);
//
//            httpGet.addHeader(SESSION_ID, DataCollectUtil.getSession_id());
//
//            HttpHost proxy = NetUtil.getDefaultProxy(context);
//            if (proxy != null)
//            {
//                DLog.i(TAG, getTID() + "startGet#DEFAULT_PROXY=" + proxy);
//                httpclient.getParams().setParameter(
//                        ConnRoutePNames.DEFAULT_PROXY, proxy);
//            }
//
//            httpclient.getParams().setParameter(
//                    CoreConnectionPNames.CONNECTION_TIMEOUT, timeOut);
//            httpclient.getParams().setParameter(
//                    CoreConnectionPNames.SO_TIMEOUT, timeOut);
//
//            HttpResponse response = httpclient.execute(httpGet);
//            int statusCode = response.getStatusLine().getStatusCode();
//            DLog.i(TAG, getTID() + "startGet#statusCode=" + statusCode);
//            if (statusCode == HttpURLConnection.HTTP_OK)
//            {
//                HttpEntity entity = response.getEntity();
//                strResult = EntityUtils.toString(entity, "UTF-8");
//                DLog.d(TAG, getTID() + "startGet#strResult=" + strResult);
//            }
//            else
//            {
//                DLog.e(TAG, getTID() + "startGet#net error, statusCode="
//                        + statusCode);
//            }
//        }
//        catch (Exception e)
//        {
//            strResult = null;
//            DLog.e(TAG, getTID() + "startGet#net error, Exception=", e);
//        }
        return "";
    }

    /**
     * 网络连接方法POST，用于上传一个字串,阻塞方法,注意从子线程里运行
     * @param postData 要上传的数据
     * @return 返回数据
     */
    public String startPost(String postData)
    {
//        DLog.d(TAG, getTID() + "startPost(String)#postData=" + postData);
        return startPost(null, postData.getBytes(), true);
    }

    /**
     * 网络连接post方法,用于上传一个字节数组,阻塞方法,注意从子线程运行
     * @return 响应数据，连接失败则返回null
     */
    private String startPost(String params, byte[] postData, boolean isFirstTry)
    {
        if (!Util.isNetworkAvailable(context))
        {
            return null;
        }
        String host = serverUrls.get(currServerIndex);

        String strResult = connectByPost(host, params, postData);
        if (strResult == null && isFirstTry)
        {// 换备用服务器重试
            if (currServerIndex + 1 >= serverUrls.size())
            {
                currServerIndex = 0;
            }
            else
            {
                currServerIndex++;
            }
            host = serverUrls.get(currServerIndex);
            strResult = connectByPost(host, params, postData);
        }
        return strResult;
    }

    /**
     * HttpClient 被google遗弃了，需要导入相关jar
     * @param host
     * @param params
     * @param postData
     * @return
     */
    private String connectByPost(String host, String params, byte[] postData)
    {
//        String connectionUrl = host;
//        if (params != null)
//        {
//            connectionUrl = connectionUrl
//                    + (params.startsWith("?") ? params : "?" + params);
//        }
////        DLog.i(TAG, getTID() + "startPost#connectionUrl=" + connectionUrl);
//        String strResult = null;
//        try
//        {
//            HttpClient httpclient = HttpsUtil.getHttpsClient();
//            HttpPost httppost = new HttpPost(connectionUrl);
//            httppost.addHeader("Content-Type",
//                    "application/x-www-form-urlencoded");
//
//            HttpHost proxy = NetUtil.getDefaultProxy(context);
//            if (proxy != null)
//            {
//                DLog.i(TAG, getTID() + "startPost#DEFAULT_PROXY=" + proxy);
//                httpclient.getParams().setParameter(
//                        ConnRoutePNames.DEFAULT_PROXY, proxy);
//            }
//
//            httpclient.getParams().setParameter(
//                    CoreConnectionPNames.CONNECTION_TIMEOUT, timeOut);
//            httpclient.getParams().setParameter(
//                    CoreConnectionPNames.SO_TIMEOUT, timeOut);
//
//            if (postData != null)
//            {
//                ByteArrayEntity byteArrayEntity = new ByteArrayEntity(postData);
//                httppost.setEntity(byteArrayEntity);
//            }
//
//            long start = System.currentTimeMillis();
//            HttpResponse response = httpclient.execute(httppost);
//            int statusCode = response.getStatusLine().getStatusCode();
//            DLog.i(TAG, getTID() + "startPost#statusCode=" + statusCode);
//            if (statusCode == HttpURLConnection.HTTP_OK)
//            {
//                HttpEntity entity = response.getEntity();
//                strResult = EntityUtils.toString(entity, "UTF-8");
//                DLog.d(TAG, "解析HttpResponse数据结束" + strResult.length() + "time:"
//                        + (System.currentTimeMillis() - start));
//                if (strResult.length() > 2048)
//                {
//                    DLog.d(TAG,
//                            getTID() + "startPost#server="+connectionUrl+",strResult="
//                                    + strResult.substring(0, 2048));
//                }
//                else
//                {
//                    DLog.d(TAG, getTID() + "startPost#server="+connectionUrl+",strResult=" + strResult);
//                }
//            }
//            else
//            {
//                DLog.e(TAG, getTID() + "startPost#server="+connectionUrl+",net error, statusCode="
//                        + statusCode);
//            }
//        }
//        catch (Exception e)
//        {
//            strResult = null;
//            DLog.e(TAG, getTID() + "startPost#server="+connectionUrl+",net error, Exception=", e);
//        }
        return "";
    }



}
