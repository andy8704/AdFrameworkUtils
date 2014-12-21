package com.ad.pckManager;

import android.graphics.drawable.Drawable;

/***
 * @author andy.xu
 * @version 0.1    
 * @time 2011/11/5
 * **/

public class MyPackageInfo {

    public Drawable icon;
    public String  name;
    public String packageName;
    public String apkFilePath;
    public long size;
    public String version;
    
    public int versionCode;
    public MyPackageInfo(){
        icon = null;
        name = null;
        packageName = null;
        apkFilePath = null;
        size = 0;
        version = null;
        versionCode = -1;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((packageName == null) ? 0 : packageName.hashCode());
        return result;
    }

    // ֻ�������жϰ����Ƿ���ͬ

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        MyPackageInfo other = (MyPackageInfo) obj;
        if (packageName == null) {
            if (other.packageName != null)
                return false;
        } else if (!packageName.equals(other.packageName))
            return false;
        return true;
    }

}
