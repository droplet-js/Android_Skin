package com.v7lin.android.crash;

import java.util.Date;

import android.os.Build;

/**
 * 
 * 
 * @author v7lin E-mail:v7lin@qq.com
 * @since 2014-11-7 23:26:40
 */
class CrashReport {
	
	CrashReport() {
		super();
	}

	public String getCrashReport(Throwable ex) {
		StringBuilder builder = new StringBuilder();
		if (ex != null) {
			builder.append("------ " + new Date().toString() + " ------" + "\r\n");
			builder.append("Android: " + Build.VERSION.RELEASE + "(" + Build.MODEL + ")" + "\r\n");
			builder.append("\r\n");
			builder.append("Throwable: " + ex.getMessage() + "\r\n");
			builder.append("\r\n");
			StackTraceElement[] elements = ex.getStackTrace();
			for (int i = 0; i < elements.length; i++) {
				builder.append(elements[i].toString() + "\r\n");
			}
			builder.append("\r\n\r\n");
		}
		return builder.toString();
	}
}
