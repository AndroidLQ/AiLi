package com.android.lq.p2p.lili.util;

/**
 * Created by Administrator on 2016/12/17.
 */

import java.util.List;

/**
 * 3  * TODO: json工具类
 * 4  *
 * 5  * @author soyoungboy
 * 6  * @date 2014-11-8 下午2:32:24
 * 7
 */
public abstract class CommonJson {
    private static CommonJson commonJson;

    public static CommonJson get() {
        if (commonJson == null) {
            commonJson = new GsonImpl();
        }
        return commonJson;
    }

    public abstract String toJson(Object src);

    public abstract <T> T toObject(String json, Class<T> claxx);

    public abstract <T> T toObject(byte[] bytes, Class<T> claxx);

    public abstract <T> List<T> toList(String json, Class<T> claxx);
}