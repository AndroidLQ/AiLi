package com.android.lq.p2p.lili.net.okhttp;

import android.text.TextUtils;

import com.android.lq.p2p.lili.model.InvestBean;
import com.android.lq.p2p.lili.util.GsonImpl;

import org.json.JSONObject;

/**
 * Created by a on 2016/12/26.
 */

public class InvestNet {
    private static String INVEST_BEAN = "invest_bean";
    /***
     * 获取精品游戏中banner的数据(顶部banner)
     *
     */
    public static InvestBean getBannerInfos()
    {
        try
        {
            // 获取公共JSON，包含必须的公共字段
            JSONObject result = BaseNet.doRequestHandleResultCode(INVEST_BEAN);
            if(!TextUtils.isEmpty(result.toString())){
                InvestBean investBean = GsonImpl.get().toObject(result.toString(), InvestBean.class);
                return  investBean;
            }else{
                return null;
            }
        }
        catch (Exception e)
        {
//            DLog.e("BoutiqueGameNet", "getBannerInfos()#exception", e);
        }
        return null;
    }
}
