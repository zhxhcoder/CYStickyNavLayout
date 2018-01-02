package com.android.cy.pullrecyclerview.view.linescroll;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.DisplayMetrics;

import java.util.List;

/**
 * Created by zhxh on 2018/1/2.
 */

public class CommonDataManager {

    /**
     * 屏幕宽度
     */
    public static int screenWight;
    /**
     * 屏幕高度
     */
    public static int screenHeight;

    public static DisplayMetrics displayMetrics;


    public static void init(Context activity) {

        displayMetrics = activity.getResources().getDisplayMetrics();

        screenWight = displayMetrics.widthPixels;

        screenHeight = displayMetrics.heightPixels;


    }

    /***
     * 是否联网
     * @param activity
     * @return
     */
    public static boolean isNetworkConnected(Activity activity) {

        if (null == activity)
            return true;

        if (activity.isFinishing())
            return true;

        try {

            //获取当前网络
            ConnectivityManager cm = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo info = cm.getActiveNetworkInfo();

            //没有可用网络
            if (info == null || !info.isAvailable())
                return false;

        } catch (Exception ex) {

            ex.printStackTrace();
        }

        return true;
    }

    /***
     * wifi网络
     * @param activity
     * @return
     */
    public static boolean isWifi(Activity activity) {

        if (null == activity)
            return false;

        if (activity.isFinishing())
            return false;

        try {

            //获取当前网络
            ConnectivityManager cm = (ConnectivityManager) activity.getSystemService(
                    Context.CONNECTIVITY_SERVICE);

            NetworkInfo info = cm.getActiveNetworkInfo();

            //没有可用网络
            if (info == null || !info.isAvailable())
                return false;

            if (info.getTypeName().equalsIgnoreCase("WIFI"))
                return true;

        } catch (Exception ex) {

            ex.printStackTrace();
        }

        return false;
    }

    /***
     * 程序是否在前台运行
     * @param context
     * @return
     */
    public static boolean isAppOnForeground(Context context) {

        ActivityManager activityManager = (ActivityManager) context.getApplicationContext()
                .getSystemService(Context.ACTIVITY_SERVICE);

        String packageName = context.getApplicationContext().getPackageName();

        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager
                .getRunningAppProcesses();

        if (appProcesses == null)
            return false;

        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {

            if (appProcess.processName.equals(packageName)
                    && appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return true;
            }
        }

        return false;
    }

    /***
     * 当前剩余高度
     * @param height
     * @return
     */
    public static int getRectHeight(int height, Activity activity) {

        if (displayMetrics == null)
            init(activity);

        int statusBarHeight = (int) Math.ceil(25 * displayMetrics.density);

        return screenHeight - statusBarHeight - (int) Math.ceil(height * displayMetrics.density);
    }

    public static int getRectWidth(int width, Activity activity) {

        if (displayMetrics == null)
            init(activity);

        return screenWight - (int) Math.ceil(width * displayMetrics.density);
    }

    /***
     * 获取屏幕密度相对值
     * @param value
     * @param activity
     * @return
     */
    public static int getDensityValue(float value, Context activity) {

        if (displayMetrics == null)
            init(activity);

        return (int) Math.ceil(value * displayMetrics.density);
    }

    /**
     * 判断程序是否处于后台
     *
     * @param context
     * @return true-后台 false-前台
     */
    public static boolean isBackground(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();

        if (null != appProcesses && appProcesses.size() > 0) {

            ActivityManager.RunningAppProcessInfo appProcess = appProcesses.get(0);
            if (appProcess.processName.equals(context.getPackageName())) {

//				public static final int IMPORTANCE_BACKGROUND = 400//后台
//				public static final int IMPORTANCE_EMPTY = 500//空进程
//				public static final int IMPORTANCE_FOREGROUND = 100//在屏幕最前端、可获取到焦点 可理解为Activity生命周期的OnResume();
//				public static final int IMPORTANCE_SERVICE = 300//在服务中
//				public static final int IMPORTANCE_VISIBLE = 200//在屏幕前端、获取不到焦点可理解为Activity生命周期的OnStart();

                if (appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND
                        || appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_VISIBLE) {
                    //前台
                    return false;
                } else {
                    return true;
                }

            } else {

                return true;
            }
        }

        return false;
    }

}
