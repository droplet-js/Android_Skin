package com.v7lin.android.os.storage;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

/**
 * 
 * 
 * @author v7lin E-mail:v7lin@qq.com
 * @since 2014-11-7 23:26:40
 */
class StorageManagerCompat {

	interface StorageManagerCompatImpl {
		public Object getStorageManager(Context context);

		public String[] getVolumePaths(Object manager);
	}

	static class EarlyStorageManagerCompatImpl implements StorageManagerCompatImpl {

		@Override
		public Object getStorageManager(Context context) {
			return null;
		}

		@Override
		public String[] getVolumePaths(Object manager) {
			return null;
		}
	}

	static class GingerbreadStorageManagerCompatImpl implements StorageManagerCompatImpl {

		@Override
		public Object getStorageManager(Context context) {
			return StorageManagerCompatGingerbread.getStorageManager(context);
		}

		@Override
		public String[] getVolumePaths(Object manager) {
			return StorageManagerCompatGingerbread.getVolumePaths(manager);
		}
	}

	public static StorageManagerCompat get(Context context) {
		return new StorageManagerCompat(context);
	}

	private final Object manager;
	private final StorageManagerCompatImpl impl;

	private StorageManagerCompat(Context context) {
		this(Build.VERSION.SDK_INT, context);
	}
	
	private StorageManagerCompat(int apiVersion, Context context) {
		super();
		
		checkPermission(context);
		
		if (apiVersion >= Build.VERSION_CODES.GINGERBREAD) {
			impl = new GingerbreadStorageManagerCompatImpl();
		} else {
			impl = new EarlyStorageManagerCompatImpl();
		}
		manager = impl.getStorageManager(context);
	}
	
	private void checkPermission(Context context) {
		if (context.checkCallingOrSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
				&& context.checkCallingOrSelfPermission(Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS) != PackageManager.PERMISSION_GRANTED) {
//			throw new IllegalArgumentException("StorageManagerCompat Usage Error.");
		}
	}
	
	public String[] getVolumePaths() {
		return impl.getVolumePaths(manager);
	}
}
