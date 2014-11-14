package com.v7lin.android.content.res;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 
 * 
 * @author v7lin E-mail:v7lin@qq.com
 * @since 2014-11-7 23:26:40
 */
class EnvSetupImpl extends EnvSetup {

	private static final String ENV_SETUP = "env_setup";
	
	private static final String KEY_SKIN_NAME = "skin_name";
	private static final String KEY_FONT_NAME = "font_name";

	@Override
	public String getSkinName(Context context) {
		SharedPreferences preferences = context.getSharedPreferences(ENV_SETUP, Context.MODE_PRIVATE);
		return preferences.getString(KEY_SKIN_NAME, "");
	}

	@Override
	public void setSkinName(Context context, String skinName) {
		SharedPreferences preferences = context.getSharedPreferences(ENV_SETUP, Context.MODE_PRIVATE);
		preferences.edit().putString(KEY_SKIN_NAME, skinName).commit();
	}

	@Override
	public String getFontName(Context context) {
		SharedPreferences preferences = context.getSharedPreferences(ENV_SETUP, Context.MODE_PRIVATE);
		return preferences.getString(KEY_FONT_NAME, "");
	}

	@Override
	public void setFontName(Context context, String fontName) {
		SharedPreferences preferences = context.getSharedPreferences(ENV_SETUP, Context.MODE_PRIVATE);
		preferences.edit().putString(KEY_FONT_NAME, fontName).commit();
	}
}
