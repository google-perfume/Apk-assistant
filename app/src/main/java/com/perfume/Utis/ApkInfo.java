package com.perfume.Utis;

import android.content.pm.*;
import android.util.*;
import com.perfume.*;
import java.lang.reflect.*;
import java.util.*;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import java.io.File;
/*
获取APK信息
*/
public class ApkInfo
{
   public static List<String> GetApkInfo(String path)
   {
      List<String> map = new ArrayList<String>();
      PackageManager pm =App.thzz. getPackageManager();  
      PackageInfo info = pm.getPackageArchiveInfo(path, PackageManager.GET_ACTIVITIES);  
      if(info != null){  
            ApplicationInfo appInfo = info.applicationInfo;  
            String appName = pm.getApplicationLabel(appInfo).toString();  
            //pm.getLaunchIntentForPackage(appName).toString();
            String packageName = appInfo.packageName; 
            String version=info.versionName;    
            Drawable icon = pm.getApplicationIcon(appInfo);
           map.add(appName);
           map.add(packageName);
           map.add(version);
           }
           return map;
   }
      public static Drawable getApkIcon(Context context, String apkPath) {
            PackageManager pm = context.getPackageManager();
            PackageInfo info = pm.getPackageArchiveInfo(apkPath,
                                                        PackageManager.GET_ACTIVITIES);
            if (info != null) {
                  ApplicationInfo appInfo = info.applicationInfo;
                  appInfo.sourceDir = apkPath;
                  appInfo.publicSourceDir = apkPath;
                  try {
                        return appInfo.loadIcon(pm);
                     } catch (OutOfMemoryError e) {
                        
                     }
               }
            return null;
         }
      
            public static Drawable showUninstallAPKIcon(String apkPath) {  
                  String PATH_PackageParser = "android.content.pm.PackageParser";  
                  String PATH_AssetManager = "android.content.res.AssetManager";  
                  try {  
                        // apk包的文件路径  
                        // 这是一个Package 解释器, 是隐藏的  
                        // 构造函数的参数只有一个, apk文件的路径  
                        // PackageParser packageParser = new PackageParser(apkPath);  
                        Class pkgParserCls = Class.forName(PATH_PackageParser);  
                        Class[] typeArgs = new Class[1];  
                        typeArgs[0] = String.class;  
                        Constructor pkgParserCt = pkgParserCls.getConstructor(typeArgs);  
                        Object[] valueArgs = new Object[1];  
                        valueArgs[0] = apkPath;  
                        Object pkgParser = pkgParserCt.newInstance(valueArgs);  
                        Log.d("ANDROID_LAB", "pkgParser:" + pkgParser.toString());  
                        // 这个是与显示有关的, 里面涉及到一些像素显示等等, 我们使用默认的情况  
                        DisplayMetrics metrics = new DisplayMetrics();  
                        metrics.setToDefaults();  
                        // PackageParser.Package mPkgInfo = packageParser.parsePackage(new  
                        // File(apkPath), apkPath,  
                        // metrics, 0);  
                        typeArgs = new Class[4];  
                        typeArgs[0] = File.class;  
                        typeArgs[1] = String.class;  
                        typeArgs[2] = DisplayMetrics.class;  
                        typeArgs[3] = Integer.TYPE;  
                        Method pkgParser_parsePackageMtd = pkgParserCls.getDeclaredMethod("parsePackage",  
                                                                                          typeArgs);  
                        valueArgs = new Object[4];  
                        valueArgs[0] = new File(apkPath);  
                        valueArgs[1] = apkPath;  
                        valueArgs[2] = metrics;  
                        valueArgs[3] = 0;  
                        Object pkgParserPkg = pkgParser_parsePackageMtd.invoke(pkgParser, valueArgs);  
                        // 应用程序信息包, 这个公开的, 不过有些函数, 变量没公开  
                        // ApplicationInfo info = mPkgInfo.applicationInfo;  
                        Field appInfoFld = pkgParserPkg.getClass().getDeclaredField("applicationInfo");  
                        ApplicationInfo info = (ApplicationInfo) appInfoFld.get(pkgParserPkg);  
                        // uid 输出为"-1"，原因是未安装，系统未分配其Uid。  
                        Log.d("ANDROID_LAB", "pkg:" + info.packageName + " uid=" + info.uid);  
                        // Resources pRes = getResources();  
                        // AssetManager assmgr = new AssetManager();  
                        // assmgr.addAssetPath(apkPath);  
                        // Resources res = new Resources(assmgr, pRes.getDisplayMetrics(),  
                        // pRes.getConfiguration());  
                        Class assetMagCls = Class.forName(PATH_AssetManager);  
                        Constructor assetMagCt = assetMagCls.getConstructor((Class[]) null);  
                        Object assetMag = assetMagCt.newInstance((Object[]) null);  
                        typeArgs = new Class[1];  
                        typeArgs[0] = String.class;  
                        Method assetMag_addAssetPathMtd = assetMagCls.getDeclaredMethod("addAssetPath",  
                                                                   typeArgs);  
                        valueArgs = new Object[1];  
                        valueArgs[0] = apkPath;  
                        assetMag_addAssetPathMtd.invoke(assetMag, valueArgs);  
                        Resources res =App.thzz. getResources();  
                        typeArgs = new Class[3];  
                        typeArgs[0] = assetMag.getClass();  
                        typeArgs[1] = res.getDisplayMetrics().getClass();  
                        typeArgs[2] = res.getConfiguration().getClass();  
                        Constructor resCt = Resources.class.getConstructor(typeArgs);  
                        valueArgs = new Object[3];  
                        valueArgs[0] = assetMag;  
                        valueArgs[1] = res.getDisplayMetrics();  
                        valueArgs[2] = res.getConfiguration();  
                        res = (Resources) resCt.newInstance(valueArgs);  
                        CharSequence label = null;  
                        if (info.labelRes != 0) {  
                              label = res.getText(info.labelRes);  
                           }  
                        // if (label == null) {  
                        // label = (info.nonLocalizedLabel != null) ? info.nonLocalizedLabel  
                        // : info.packageName;  
                        // }  
                        Log.d("ANDROID_LAB", "label=" + label);  
                        // 这里就是读取一个apk程序的图标  
                        if (info.icon != 0) {  
                              Drawable icon = res.getDrawable(info.icon);  
                              return icon; 
                           }  
                     } catch (Exception e) {  
                        e.printStackTrace();  
                     }  
                     return null;
               }  
         
         
         
}
