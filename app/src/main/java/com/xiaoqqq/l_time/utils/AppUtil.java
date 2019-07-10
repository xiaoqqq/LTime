package com.xiaoqqq.l_time.utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.Application;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import java.util.List;
import java.util.Locale;

/**
 * <pre>
 *     @author : xiaoqing
 *     e-mail : qing.xiao@getech.cn
 *     time   : 2017/08/31
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class AppUtil {
    private Application application;
    private static AppUtil INSTANCE;
    public static boolean DEBUG = false;
    public static boolean API_TEST = false;

    public static final AppUtil getInstance() {
        if (null == INSTANCE) {
            synchronized (AppUtil.class) {
                if (null == INSTANCE) {
                    INSTANCE = new AppUtil();
                }
            }
        }
        return INSTANCE;
    }

    private AppUtil() {
    }

    public void init(Application application) {
        this.application = application;
    }

    public Context getApplication() {
        return this.application;
    }

    public String getStringFromResouce(@StringRes int resId) {
        return application.getResources().getString(resId);
    }

    public Drawable getDrawableFromResouce(@DrawableRes int resId) {
        return ContextCompat.getDrawable(application, resId);
    }

    public int getColorFromResource(@ColorRes int resId) {
        return ContextCompat.getColor(application, resId);
    }

    /**
     * 获取app版本名
     */
    public static String getAppVersionName() {
        PackageManager pm = getInstance().application.getPackageManager();
        PackageInfo pi;
        try {
            pi = pm.getPackageInfo(getInstance().application.getPackageName(), 0);
            return pi.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取app版本号
     */
    public static int getAppVersionCode() {
        PackageManager pm = getInstance().getApplication().getPackageManager();
        PackageInfo pi;
        try {
            pi = pm.getPackageInfo(getInstance().getApplication().getPackageName(), 0);
            return pi.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 判断是否是android 5.0 以上
     *
     * @return
     */
    public static boolean isFiveAndroid() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

    /**
     * 判断当前应用程序处于前台还是后台
     *
     * @return 如果运行在后台就返回true
     */
    public static boolean isApplicationBroughtToBackground() {
        ActivityManager am = (ActivityManager) getInstance().getApplication().getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(1);
        if (!tasks.isEmpty()) {
            ComponentName topActivity = tasks.get(0).topActivity;
            if (!topActivity.getPackageName().equals(getInstance().getApplication().getPackageName())) {
                return true;
            }
        }
        return false;

    }

    /**
     * 用于得到最优的线程池数量大小
     *
     * @param max
     * @return
     */
    public static int getDefaultThreadPoolSize(int max) {
        int availableProcessors = 2 * Runtime.getRuntime().availableProcessors() + 1;
        return availableProcessors > max ? max : availableProcessors;
    }

    /**
     * 谷歌商店是否安装
     *
     * @return
     */
    public static boolean isInstallGooglePlay() {
        return isAppInstalled(getInstance().getApplication(), "com.android.vending");
    }

    /**
     * 谷歌服务是否安装
     *
     * @return
     */
    public static boolean isInstallGoogleServices() {
        return isAppInstalled(getInstance().application, "com.google.android.gms");
    }

    private static boolean isAppInstalled(Context context, String packageName) {
        List<PackageInfo> packages = context.getPackageManager().getInstalledPackages(0);
        if (packages != null && packages.size() > 0) {
            for (PackageInfo packageInfo : packages) {
                if (packageInfo.packageName.equals(packageName)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 获得手机型号信息
     *
     * @return
     */
    public static String getPhoneModel() {
        return Build.MANUFACTURER + " " + Build.MODEL;
    }


    /**
     * 五、判断是wifi还是3g网络,用户的体现性在这里了，wifi就可以建议下载或者在线播放。
     *
     * @return
     */

    public static boolean isWifi() {
        ConnectivityManager cm = (ConnectivityManager) getInstance().application
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        @SuppressLint("MissingPermission") NetworkInfo networkINfo = cm.getActiveNetworkInfo();
        return networkINfo != null
                && networkINfo.getType() == ConnectivityManager.TYPE_WIFI;
    }

    public static void goTocCallPhonePage(String phone) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));//直接跳转拨号页面
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (ActivityCompat.checkSelfPermission(getInstance().getApplication(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            GoToSysSetting();
            Toast.makeText(getInstance().application, "请设置拨号权限！", Toast.LENGTH_SHORT).show();
            return;
        }
        getInstance().getApplication().startActivity(intent);
    }

    public static void callPhone(String phone) {
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone));//直接拨号
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (ActivityCompat.checkSelfPermission(getInstance().getApplication(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            GoToSysSetting();
            Toast.makeText(getInstance().application, "请设置拨号权限！", Toast.LENGTH_SHORT).show();
            return;
        }
        getInstance().getApplication().startActivity(intent);
    }

    /**
     * 跳转到系统设置页面
     */
    public static void GoToSysSetting() {
        Intent intent = new Intent(Settings.ACTION_SETTINGS);
        getInstance().getApplication().startActivity(intent);
    }

    /**
     * 是否安装某个软件
     *
     * @param context 上下文
     * @param pkgName app 包名
     * @return true 已安装 false 反之
     */
    public static boolean isInstallPackage(Context context, String pkgName) {
        PackageInfo packageInfo;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(pkgName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            packageInfo = null;
            e.printStackTrace();
        }
        return packageInfo != null;
    }

    //添加联系人
    public static void AddContact(Context context, String name, String phone, String mobile) {
        try {
            Uri uri = Uri.parse("content://com.android.contacts/raw_contacts");
            ContentResolver resolver = context.getContentResolver();
            ContentValues values = new ContentValues();
            long contactid = ContentUris.parseId(resolver.insert(uri, values));
            uri = Uri.parse("content://com.android.contacts/data");
            //添加姓名
            values.put("raw_contact_id", contactid);
            values.put(ContactsContract.RawContacts.Data.MIMETYPE, "vnd.android.cursor.item/name");
            values.put("data1", name);
            resolver.insert(uri, values);
            values.clear();
            if (!TextUtils.isEmpty(mobile)) {
                //手机
                values.put("raw_contact_id", contactid);
                values.put(ContactsContract.RawContacts.Data.MIMETYPE, "vnd.android.cursor.item/phone_v2");
                values.put("data1", mobile);
                values.put("data2", "2");
                resolver.insert(uri, values);
                values.clear();
            }
            //单位电话
            if (!TextUtils.isEmpty(phone)) {
                values.put("raw_contact_id", contactid);
                values.put(ContactsContract.RawContacts.Data.MIMETYPE, "vnd.android.cursor.item/phone_v2");
                values.put("data1", phone);
                values.put("data2", "3");
                resolver.insert(uri, values);
                values.clear();
            }
        } catch (Exception e) {
        }
    }

    /**
     * @param name 联系人姓名
     * @return 是否存在与联系人列表中
     * @throws Exception
     */
    public static boolean hasDataByName(Context context, String name) {
        boolean hasData = false;
        Uri uri = Uri.parse("content://com.android.contacts/data/phones/filter/" + name);
        ContentResolver resolver = context.getContentResolver();
        Cursor cursor = resolver.query(uri, new String[]{ContactsContract.Data.DISPLAY_NAME}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            Log.e(name, "has");
            hasData = true;
            cursor.close();
        } else {
            Log.e(name, "no data");
            hasData = false;
        }
        return hasData;
    }

    /**
     * 获取当前语言文化
     *
     * @return
     */
    public static Locale getCurrentLocale(Context context) {
        Configuration config = context.getResources().getConfiguration();
        return config.locale;
    }


    /**
     * 检查手机上是否安装了指定的软件
     *
     * @param context
     * @param packageName：应用包名
     * @return
     */
    public static boolean isAvilible(Context context, String packageName) {
        if (TextUtils.isEmpty(packageName)) return false;
        PackageInfo packageInfo;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(packageName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            packageInfo = null;
        }

        if (packageInfo == null) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 拨打电话
     */
    public static void doCallAction(String telNum, Context context) {
        if (TextUtils.isEmpty(telNum)) {
            return;
        }
        try {
            Uri uri = Uri.parse("tel:" + telNum);
            Intent intent = new Intent(Intent.ACTION_DIAL, uri);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getCurrentProcessName(Context context) {
        int pid = android.os.Process.myPid();
        String processName = "";
        ActivityManager manager = (ActivityManager) context.getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> processInfos = manager.getRunningAppProcesses();
        if (processInfos == null) {
            return processName;
        }
        for (ActivityManager.RunningAppProcessInfo process : processInfos) {
            if (process.pid == pid) {
                processName = process.processName;
                break;
            }
        }
        return processName;
    }
}
