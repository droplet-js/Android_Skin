package com.v7lin.android.content;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;

/**
 * 
 * 
 * @author v7lin E-mail:v7lin@qq.com
 * @since 2014-11-12 下午8:03:24
 */
public abstract class EnvResourcesWrapper extends Resources {

	public EnvResourcesWrapper(Context context, Resources res) {
		super(res.getAssets(), res.getDisplayMetrics(), res.getConfiguration());

		checkSystemConfig();
	}

	private void checkSystemConfig() {
		// 设置字体大小不随系统设置而改变
		Configuration defConfig = new Configuration();
		Configuration config = getConfiguration();
		config.fontScale = defConfig.fontScale;

		// 暂未作处理
		// 有些机器（如：三星 N7100）屏幕达到 xhdpi 标准，却还是 hdpi，故而在此做一次校正
		DisplayMetrics metrics = new DisplayMetrics();
		metrics.setTo(getDisplayMetrics());

		updateConfiguration(config, metrics);
	}
}
