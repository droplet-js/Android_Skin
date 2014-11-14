package com.v7lin.android.os.storage;

import java.lang.reflect.Method;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.storage.StorageManager;

/**
 * 
 * 
 * @author v7lin E-mail:v7lin@qq.com
 * @since 2014-11-7 23:26:40
 */
@TargetApi(Build.VERSION_CODES.GINGERBREAD)
class StorageManagerCompatGingerbread {
	
	public static Object getStorageManager(Context context) {
		StorageManager manager = (StorageManager) context.getSystemService(Context.STORAGE_SERVICE);
		return manager;
	}
	
	public static String[] getVolumePaths(Object manager) {
		String[] paths = null;
		try {
			Class<?> clazz = StorageManager.class;
			Method method = clazz.getMethod("getVolumePaths");
			method.setAccessible(true);
			Object invoke = method.invoke(manager);
			if (invoke != null && invoke instanceof String[]) {
				paths = (String[]) invoke;
			}
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return paths;
	}
}
