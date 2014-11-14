package com.v7lin.android.content.res;

import android.content.Context;

import com.v7lin.android.app.SuperActivity;

/**
 * 
 * 
 * @author v7lin E-mail:v7lin@qq.com
 * @since 2014-11-7 23:26:40
 */
public class EnvActivity extends SuperActivity {

	@Override
	protected void attachBaseContext(Context newBase) {
		super.attachBaseContext(new EnvContextWrapper(newBase));
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
