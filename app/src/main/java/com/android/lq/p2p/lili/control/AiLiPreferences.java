package com.android.lq.p2p.lili.control;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.FileInputStream;

/**
 * Created by a on 2016/12/22.
 */

public class AiLiPreferences {

    /** 小数据配置存储类 */
    private static AiLiPreferences instance;
    private Context context;
    private static final String SP_NAME = "AiLi_setting_sp";
    private static final String SHOW_GPRS_TIPS = "SHOW_GPRS_TIPS";
    /** 提示是否创建快捷方式 */
    private static final String CREATED_SHORTCUT = "CREATED_SHORTCUT";

    private static final String FIRST_TAG = "FIRST_TAG";

    public AiLiPreferences(Context context) {
        this.context = context.getApplicationContext();
    }

    /**
     * 获得AiLiPreferences静态实例对象
     *
     * @param context
     * @return
     */
    public static AiLiPreferences getInstance(Context context)
    {
        if (instance == null)
        {
            instance = new AiLiPreferences(context);
        }
        return instance;
    }

    /**
     * 提示是否显示流量提示框
     */
    public boolean isShowGprsTips()
    {
        return getSharedPreferences().getBoolean(SHOW_GPRS_TIPS, true);
    }

    /**
     * get SharedPreferences
     *
     * @return
     */
    private SharedPreferences getSharedPreferences()
    {
        SharedPreferences sp = context.getSharedPreferences(SP_NAME,
                Context.MODE_PRIVATE | 4);
        return sp;
        // return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void setShowGprsTips(boolean show)
    {
        SharedPreferences.Editor editor = getEditor();
        editor.putBoolean(SHOW_GPRS_TIPS, show);
        editor.commit();
    }

    /**
     * 提示是否创建快捷方式
     */
    public boolean isCreatedShortcut()
    {
        return getSharedPreferences().getBoolean(CREATED_SHORTCUT, false);
    }

    public void setCreatedShortcut(boolean created)
    {
        SharedPreferences.Editor editor = getEditor();
        editor.putBoolean(CREATED_SHORTCUT, created);
        editor.commit();
    }

    /**
     * get Editor
     *
     * @return
     */
    private SharedPreferences.Editor getEditor()
    {
        SharedPreferences pref = getSharedPreferences();
        return pref.edit();
    }

    public boolean isFirst(){
        return getSharedPreferences().getBoolean(FIRST_TAG,false);
    }

    /**
     * 设置第一次进入标志
     * @param isFirst
     */
    public void setFirstTag(boolean isFirst){
        SharedPreferences.Editor editor = getEditor();
        editor.putBoolean(FIRST_TAG,isFirst);
        editor.commit();
    }


}
