package com.ad.pckManager;

import java.io.File;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;


/***
 * @author andy.xu
 * @version 0.1    
 * @time 2011/11/5
 * **/

public class ApkManager {

    // 安装APk文件
    public static void installApk(final Context context, final String filePath) {

        if (null == context || TextUtils.isEmpty(filePath))
            return;

        File file = new File(filePath);
        if (null == file || !file.exists())
            return;

        Uri uri = Uri.fromFile(file);
        if (null == uri)
            return;

        Intent intent = new Intent(Intent.ACTION_VIEW);
        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // 可以不适用
        intent.setDataAndType(uri, "application/vnd.android.package-archive");
        context.startActivity(intent);
    }

    // 卸载安装的APK
    public static void uninstallApk(final Context context, final String packageName) {

        if (null == context || TextUtils.isEmpty(packageName))
            return;

        Uri uri = Uri.parse("package:" + packageName);
        if (null == uri)
            return;

        Intent intent = new Intent(Intent.ACTION_DELETE, uri);
        context.startActivity(intent);
    }
}
