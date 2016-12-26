package com.android.lq.p2p.lili.net.okhttp;

import android.os.Build;
import android.util.Log;

import org.json.JSONObject;

/**
 * Created by a on 2016/12/26.
 */

public class BaseNet {

    /**
     * 发起网络请求，并处理RESULT_CODE，RESULT_CODE标志为响应失败时抛出异常
     * @param requestTag 接口TAG
     * @return 返回响应json
     * @throws Exception 网络接口响应失败时抛出
     */
    public static JSONObject doRequestHandleResultCode(String requestTag) throws Exception
    {
        JSONObject result = doGetRequest(requestTag);
        if (result.has("PHP_EXE_TIME") && result.has("IP"))
        {
            Log.d("denglh",
                    requestTag + " ：后台处理时间：" + result.getString("PHP_EXE_TIME")
                            + "IP" + result.getString("IP"));
        }
        return result;
    }


    /**
     * 发起网络请求
     * @param requestTag 接口TAG
     * @param json 请求json
     * @return 返回响应json
     * @throws Exception 网络接口响应失败时抛出
     */
    public static JSONObject doPostRequest(String requestTag, JSONObject json)
            throws Exception
    {
        long ms1 = System.currentTimeMillis();
        String content = null;
        if (Build.VERSION.SDK_INT < OkHttpManager.MIN_SDK)
        {
            content = HttpRequest.getDefaultHttpRequest().startPost(
                    json.toString());
        }
        else
        {
            content = OkHttpManager.getInstance().startPost(json.toString());
        }

        if (content == null || content.equals(""))
        {
            throw new Exception("http interface [" + requestTag
                    + "] result error, content=" + content);
        }

        long ms2 = System.currentTimeMillis();
//        DLog.d("denglh", requestTag + " ：请求时间：" + (ms2 - ms1));

        return new JSONObject(content);
    }

    /**
     * 发起网络请求
     * @param requestTag 接口TAG
     * @param json 请求json
     * @return 返回响应json
     * @throws Exception 网络接口响应失败时抛出
     */
    public static JSONObject doGetRequest(String requestTag)
            throws Exception
    {
        long ms1 = System.currentTimeMillis();
        String content = null;
        if (Build.VERSION.SDK_INT < OkHttpManager.MIN_SDK)
        {
            content = HttpRequest.getDefaultHttpRequest().startGet();
        }
        else
        {
            content = OkHttpManager.getInstance().startGet();
        }

        if (content == null || content.equals(""))
        {
            throw new Exception("http interface [" + requestTag
                    + "] result error, content=" + content);
        }
        long ms2 = System.currentTimeMillis();

        //DLog.d("denglh", requestTag + " ：请求时间：" + (ms2 - ms1));

        return new JSONObject(content);
    }


}
