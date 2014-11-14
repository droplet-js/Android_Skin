package com.v7lin.android.content.dex;

import android.content.Context;
import android.os.Bundle;

import com.v7lin.android.app.SuperActivity;
import com.v7lin.android.content.res.EnvContextWrapper;

/**
 * 
 * 
 * @author v7lin E-mail:v7lin@qq.com
 * @since 2014-11-7 23:26:40
 */
public class EnvDexActivity extends SuperActivity implements EnvDexConst {

	/**
	 * 这里无法取到插件信息，这真是蛋碎啊 ...
	 */
	@Override
	protected void attachBaseContext(Context newBase) {
		super.attachBaseContext(new EnvDexContextWrapper(new EnvContextWrapper(newBase)));
	}

	@Override
	public Context getBaseContext() {
		Context context = super.getBaseContext();
		if (context instanceof EnvDexContextWrapper) {
			context = ((EnvDexContextWrapper) context).getBaseContext();
		}
		if (context instanceof EnvContextWrapper) {
			context = ((EnvContextWrapper) context).getBaseContext();
		}
		return context;
	}
	
	/**
	 * 蛋碎，这里竟是最早能获取插件信息的地方
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		String dexName = getIntent().getStringExtra(KEY_DEX_APP);
		Context context = super.getBaseContext();
		if (context instanceof EnvDexContextWrapper) {
			((EnvDexContextWrapper) context).setDexName(dexName);
		}
		super.onCreate(savedInstanceState);
	}
}
