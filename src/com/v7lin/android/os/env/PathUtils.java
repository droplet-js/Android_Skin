package com.v7lin.android.os.env;

import java.io.File;

import android.content.Context;

import com.v7lin.android.os.storage.StorageUtils;

/**
 * 
 * 
 * @author v7lin E-mail:v7lin@qq.com
 * @since 2014-11-7 23:26:40
 */
public final class PathUtils implements PathConst {

	private PathUtils() {
		super();
	}

	private static File makeDir(Context context, boolean isPrivate) {
		File storageDir = StorageUtils.getStorageDir(context, isPrivate);
		File appDir = new File(storageDir, PATH_APP_DIR);
		if (!appDir.exists()) {
			appDir.mkdirs();
		}
		return appDir;
	}

	private static File makeSubDir(Context context, String subPath, boolean isPrivate) {
		File appDir = PathUtils.makeDir(context, isPrivate);
		File subDir = new File(appDir, subPath);
		if (!subDir.exists()) {
			subDir.mkdirs();
		}
		return subDir;
	}

	/**
	 * 为了测试方便，暂时先放在SD卡上
	 * 
	 * 这个需要保护起来，避免用户误删
	 */
	public static File getSkinDir(Context context) {
		return PathUtils.makeSubDir(context, PATH_SUB_DIR_SKIN, false);
	}

	public static File getFontDir(Context context) {
		return PathUtils.makeSubDir(context, PATH_SUB_DIR_FONT, false);
	}

	public static File getCrashDir(Context context) {
		return PathUtils.makeSubDir(context, PATH_SUB_DIR_CRASH, false);
	}

	public static File getCacheDir(Context context) {
		return PathUtils.makeSubDir(context, PATH_SUB_DIR_CACHE, false);
	}

	/**
	 * Android 4.1.2 Api 中有对 Dex 做了限制，Dex 文件只能放在私有目录下
	 */
	public static File getDexDir(Context context) {
		return PathUtils.makeSubDir(context, PATH_SUB_DIR_DEX, true);
	}

	/**
	 * 为了测试方便，暂时先放在SD卡上
	 * 
	 * 这个需要保护起来，避免用户误删
	 */
	public static File getDexAppDir(Context context) {
		return PathUtils.makeSubDir(context, PATH_SUB_DIR_DEX_APP, false);
	}

	public static File getAppDir(Context context) {
		return PathUtils.makeSubDir(context, PATH_SUB_DIR_APP, false);
	}
}
