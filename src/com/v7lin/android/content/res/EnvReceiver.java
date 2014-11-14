package com.v7lin.android.content.res;

import android.content.Context;
import android.content.Intent;

import com.v7lin.android.app.SuperReceiver;

/**
 * 
 * 
 * @author v7lin E-mail:v7lin@qq.com
 * @since 2014-11-7 23:26:40
 */
public abstract class EnvReceiver extends SuperReceiver {
	
	@Override
	public final void onReceive(Context context, Intent intent) {
		onReceiveEnv(new EnvContextWrapper(context), intent);
	}
	
	public abstract void onReceiveEnv(Context context, Intent intent);
}
