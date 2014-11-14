package com.v7lin.android.content.res;

import android.content.Context;

import com.v7lin.android.app.SuperAppConfig;

/**
 * 
 * 
 * @author v7lin E-mail:v7lin@qq.com
 * @since 2014-11-7 23:26:40
 */
public class EnvAppConfig extends SuperAppConfig {

	@Override
	protected void attachBaseContext(Context base) {
		super.attachBaseContext(new EnvContextWrapper(base));
	}

	/**
	 * 在 ActivityThread 接收广播时，会取出 baseContext作一次强制类型转换，转换为 ContextImpl
	 */
	@Override
	public Context getBaseContext() {
		Context context = super.getBaseContext();
		if (context instanceof EnvContextWrapper) {
			context = ((EnvContextWrapper) context).getBaseContext();
		}
		return context;
	}
}
