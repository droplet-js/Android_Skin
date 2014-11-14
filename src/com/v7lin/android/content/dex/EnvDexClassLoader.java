package com.v7lin.android.content.dex;

import android.content.Context;

import com.v7lin.android.os.env.PathUtils;

import dalvik.system.DexClassLoader;

/**
 * Android 插件式开发要领
 * 
 * 1.反射式：
 * 		绝对的万能神器 ... 不过，据说 ... 反射需要点时间 ...
 * 2.接口式：
 * 		主工程和插件工程必须能引用到公共的接口。
 * 接口式中的接口限制：
 * 		要么是存在于 SDK 的 jar 包中 ...
 * 		要么是存在于主工程的 jar 包中，且不存在于插件工程的 jar 包中（如：我们不能将 Android SDK 的 jar 包放入工程中 libs 文件夹下） ...
 * 
 * @author v7lin E-mail:v7lin@qq.com
 * @since 2014-11-7 23:26:40
 */
public class EnvDexClassLoader extends DexClassLoader {
	
	/**
	 * 耗时动作，请在主线程中执行
	 */
	public static EnvDexClassLoader newInstance(Context context, String dexPath, ClassLoader parent) {
		// ClassLoader 会报 java.lang.IllegalAccessError: Class ref in pre-verified class resolved to unexpected implementation 错误，即有两个同包名同类名接口，可避免
		// ClassLoader.getSystemClassLoader();
		// VMStack.getCallingClassLoader(); 高级 API 中被废弃了
		// ClassLoader.getSystemClassLoader().getParent(); 强制类型转换错误
		return new EnvDexClassLoader(dexPath, PathUtils.getDexDir(context).getAbsolutePath(), null, parent);
	}

	private EnvDexClassLoader(String dexPath, String optimizedDirectory, String libraryPath, ClassLoader parent) {
		super(dexPath, optimizedDirectory, libraryPath, parent);
	}
}
