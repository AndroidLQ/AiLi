package com.android.lq.p2p.lili.net.request.xutils;

import org.xutils.common.Callback;

/**
 * Created by a on 2016/12/21.
 */

public interface IRequestCallback<ResultType> {
    void sucess(ResultType resultType);
    void fialure(Throwable throwable);
    void onCancelled(Callback.CancelledException cex);
    void finish();
}
