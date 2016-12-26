package com.android.lq.p2p.lili.util;

import java.net.URLEncoder;
import java.util.Map;

/**
 * Created by a on 2016/12/26.
 */

public class StringUtil {

    public static final String mapToUrlParams(Map<String, String> params)
    {
        if (params != null && !params.isEmpty())
        {
            StringBuffer buffer = new StringBuffer();
            for (String key : params.keySet())
            {
                buffer.append("&" + URLEncoder.encode(key) + "="
                        + URLEncoder.encode(params.get(key)));
            }

            return buffer.substring(1);
        }
        return "";
    }

}
