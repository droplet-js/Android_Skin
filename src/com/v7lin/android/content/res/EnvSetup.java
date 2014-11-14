package com.v7lin.android.content.res;

import android.content.Context;

/**
 * 
 * 
 * @author v7lin E-mail:v7lin@qq.com
 * @since 2014-11-7 23:26:40
 */
public abstract class EnvSetup {
	
	public static EnvSetup getInstance() {
		return EnvSetupHolder.INSTANCE;
	}
	
	public abstract String getSkinName(Context context);
	
	public abstract void setSkinName(Context context, String skinName);
	
	public abstract String getFontName(Context context);
	
	public abstract void setFontName(Context context, String fontName);
	
	private static class EnvSetupHolder {
		private static final EnvSetup INSTANCE = new EnvSetupImpl();
	}
}
