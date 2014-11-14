package com.v7lin.android.content.res;

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
 * @since 2014-11-7 23:26:40
 */
public class EnvSkinResourcesManager {

	private final Map<String, WeakReference<Resources>> mActiveResources = new HashMap<String, WeakReference<Resources>>();

	public static EnvSkinResourcesManager getInstance() {
		return EnvResourcesManagerHolder.INSTANCE;
	}

	private EnvSkinResourcesManager() {
		super();
	}

	public String getCurSkinName(Context context) {
		String skinName = EnvSetup.getInstance().getSkinName(context);
		return checkSkinLegal(context, skinName) ? skinName : "";
	}

	private boolean checkSkinLegal(Context context, String skinName) {
		boolean legal = false;
		if (!TextUtils.isEmpty(skinName)) {
			File skinFile = new File(PathUtils.getSkinDir(context), skinName);
			if (skinFile.exists() && skinFile.isFile()) {
				PackageManager manager = context.getPackageManager();
				PackageInfo info = manager.getPackageArchiveInfo(skinFile.getAbsolutePath(), PackageManager.GET_ACTIVITIES);
				legal = TextUtils.equals(context.getPackageName(), info.packageName);
			}
		}
		return legal;
	}
	
	public boolean isSkinChanged(Context context, String skinName) {
		return !TextUtils.equals(getCurSkinName(context), skinName);
	}
	
	public synchronized Resources getTopLevelResources(Context context) {
		Resources res = null;
		try {
			String skinName = getCurSkinName(context);
			if (!TextUtils.isEmpty(skinName)) {
				WeakReference<Resources> wr = mActiveResources.get(skinName);
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
					File skinFile = new File(PathUtils.getSkinDir(context), skinName);
					if (skinFile.exists() && skinFile.isFile()) {
						Class<?> clazz = AssetManager.class;
						AssetManager skinAsset = (AssetManager) clazz.newInstance();
						Method method = clazz.getDeclaredMethod("addAssetPath", String.class);
						method.invoke(skinAsset, skinFile.getAbsolutePath());
						res = new Resources(skinAsset, context.getResources().getDisplayMetrics(), context.getResources().getConfiguration());
						mActiveResources.put(skinName, new WeakReference<Resources>(res));
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

	private static class EnvResourcesManagerHolder {
		private static final EnvSkinResourcesManager INSTANCE = new EnvSkinResourcesManager();
	}
}
