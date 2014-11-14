package com.v7lin.android.content.res;

import android.content.Context;

import com.v7lin.android.app.SuperService;

/**
 * 
 * 
 * @author v7lin E-mail:v7lin@qq.com
 * @since 2014-11-7 23:26:40
 */
public class EnvService extends SuperService {

	@Override
	protected void attachBaseContext(Context base) {
		super.attachBaseContext(new EnvContextWrapper(base));
	}

	@Override
	public Context getBaseContext() {
		Context context = super.getBaseContext();
		if (context instanceof EnvContextWrapper) {
			context = ((EnvContextWrapper) context).getBaseContext();
		}
		return context;
	}
}
