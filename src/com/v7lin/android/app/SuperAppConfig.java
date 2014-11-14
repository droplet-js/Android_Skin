package com.v7lin.android.app;

import java.lang.Thread.UncaughtExceptionHandler;

import android.app.Application;

import com.v7lin.android.crash.CrashUncaughtExceptionHandler;

/**
 * 
 * @author v7lin E-mail:v7lin@qq.com
 * @since 2014-11-7 23:26:40
 */
public class SuperAppConfig extends Application {
	
	@Override
	public void onCreate() {
		super.onCreate();

		// 设置堆利用率，优化 Dalvik 虚拟机的堆内存分配。适用：大型游戏或耗资源的应用
		// VMRuntime.getRuntime().setTargetHeapUtilization(0.75f);
		// 设置最小 heap 内存为 6MB 大小。
		// VMRuntime.getRuntime().setMinimumHeapSize(6* 1024* 1024);

		// 注册 App 异常崩溃处理器
		UncaughtExceptionHandler handler = new CrashUncaughtExceptionHandler(getApplicationContext(), Thread.getDefaultUncaughtExceptionHandler());
		Thread.setDefaultUncaughtExceptionHandler(handler);
	}
}
