package com.android.lq.p2p.lili.net.request.xutils;

/**
 * Created by a on 2016/12/21.
 */

public class RequestFactory {
    public static IRequestManager getRequestManager(){
        return XutilRequestManager.getInstance();
    }
}
