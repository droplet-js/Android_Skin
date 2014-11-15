package com.v7lin.android.content.dex;

import java.io.File;
import java.lang.ref.WeakReference;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.text.TextUtils;

import com.v7lin.android.os.env.PathUtils;

/**
 * 
 * 
 * @author v7lin E-mail:v7lin@qq.com
 * @since 2014-11-9 22:55:20
 */
public class EnvDexResourcesManager {
	
	private final Map<String, WeakReference<Resources>> mActiveResources = new HashMap<String, WeakReference<Resources>>();
	
	public static EnvDexResourcesManager getInstance() {
		return EnvDexResourcesManagerHolder.INSTANCE;
	}
	
	private EnvDexResourcesManager() {
		super();
	}
	
	public String getDexPackageName(Context context, String dexName) {
		String packageName = null;
		if (!TextUtils.isEmpty(dexName)) {
			File skinFile = new File(PathUtils.getDexAppDir(context), dexName);
			if (skinFile.exists() && skinFile.isFile()) {
				PackageManager manager = context.getPackageManager();
				PackageInfo info = manager.getPackageArchiveInfo(skinFile.getAbsolutePath(), PackageManager.GET_ACTIVITIES);
				packageName = info.packageName;
			}
		}
		return packageName;
	}
	
	public synchronized Resources getTopLevelResources(Context context, String dexName) {
		Resources res = null;
		try {
			if (!TextUtils.isEmpty(dexName)) {
				WeakReference<Resources> wr = mActiveResources.get(dexName);
				res = wr != null ? wr.get() : null;
				boolean isValid = false;
				if (res != null && res.getAssets() != null) {
					AssetManager assets = res.getAssets();
					Class<?> clazz = assets.getClass();
					Method method = clazz.getDeclaredMethod("isUpToDate");
					Object object = method.invoke(assets);
					isValid = Boolean.valueOf(String.valueOf(object)).booleanValue();
				}
				if (!isValid) {
					File dexFile = new File(PathUtils.getDexAppDir(context), dexName);
					if (dexFile.exists() && dexFile.isFile()) {
						Class<?> clazz = AssetManager.class;
						AssetManager dexAsset = (AssetManager) clazz.newInstance();
						Method method = clazz.getDeclaredMethod("addAssetPath", String.class);
						method.invoke(dexAsset, dexFile.getAbsolutePath());
						res = new Resources(dexAsset, context.getResources().getDisplayMetrics(), context.getResources().getConfiguration());
						mActiveResources.put(dexName, new WeakReference<Resources>(res));
					}
				}
			}
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}
	
	private static class EnvDexResourcesManagerHolder {
		private static final EnvDexResourcesManager INSTANCE = new EnvDexResourcesManager();
	}
}
