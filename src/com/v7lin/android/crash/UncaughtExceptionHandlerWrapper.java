package com.v7lin.android.crash;

import java.lang.Thread.UncaughtExceptionHandler;

/**
 * 全局异常捕获
 * 
 * @author v7lin E-mail:v7lin@qq.com
 * @since 2014-11-7 23:26:40
 */
public class UncaughtExceptionHandlerWrapper implements UncaughtExceptionHandler {

	private UncaughtExceptionHandler wrappedHandler;

	public UncaughtExceptionHandlerWrapper(UncaughtExceptionHandler wrapped) {
		super();

		if (wrapped == null) {
			throw new IllegalArgumentException("wrapped handler must not be null");
		}

		this.wrappedHandler = wrapped;
	}

	@Override
	public void uncaughtException(Thread thread, Throwable ex) {
		// 交还给默认的异常崩溃处理器处理
		wrappedHandler.uncaughtException(thread, ex);
	}
}
