package com.android.lq.p2p.lili.base;

/**
 * Created by a on 2016/12/28.
 */

import java.util.Stack;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;

import com.android.lq.p2p.lili.R;

/**
 * 项目名称：Android_Study 类名称：AppManager 类描述： 创建人：lq 创建时间：2015年10月21日 下午3:13:20
 * 修改人：lq 修改时间：2015年10月21日 下午3:13:20 修改备注：
 * @version
 */
public class AppManager {
    private static Stack<Activity> activityStack;
    private static AppManager instance;
    private AppManager() {
    }
    /**
     * 单一实例
     */
    public static AppManager getAppManager() {
        if (instance == null) {
            instance = new AppManager();
        }
        return instance;
    }
    /**
     * 添加Activity到堆栈
     */
    public void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<Activity>();
        }
        activityStack.add(activity);
    }
    /**
     * 获取当前Activity（堆栈中最后一个压入的）
     */
    public Activity currentActivity() {
        Activity activity = activityStack.lastElement();
        return activity;
    }
    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    public void finishActivity() {
        Activity activity = activityStack.lastElement();
        finishActivity(activity);
    }
    /**
     * 结束指定的Activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            activity.finish();
            activity.overridePendingTransition(R.anim.in_form_left_back, R.anim.out_of_right_back);
            activity = null;
        }
    }
    /**
     * 结束指定类名的Activity
     */
    public void finishActivity(Class<?> cls) {
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
            }
        }
    }
    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (null != activityStack.get(i)) {
                activityStack.get(i).finish();
            }
        }
        activityStack.clear();
    }
    /**
     * 退出应用程序
     */
    public void AppExit(Context context) {
        try {
            // 退出应用
            finishAllActivity();
            // 退出应用
            ActivityManager activityMgr = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            activityMgr.restartPackage(context.getPackageName());
            // 退出应用
            System.exit(0);
        } catch (Exception e) {
        }
    }
}