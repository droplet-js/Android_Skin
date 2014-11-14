package com.v7lin.android.content.dex;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Resources;
import android.content.res.Resources.Theme;
import android.view.LayoutInflater;

import com.v7lin.android.content.res.EnvLayoutInflaterWrapper;

/**
 * 功能插件 APK 就嫑签名，防止用户错误安装插件 APK ...
 * 
 * @author v7lin E-mail:v7lin@qq.com
 * @since 2014-11-7 23:26:40
 */
public class EnvDexContextWrapper extends ContextWrapper {

	private Resources mResources;
	private Theme mTheme;
	private LayoutInflater mLayoutInflater;

	public EnvDexContextWrapper(Context base) {
		super(base);
	}

	@Override
	public Resources getResources() {
		if (mResources == null) {
			mResources = new EnvDexResourcesWrapper(this, getBaseContext().getResources());
		}
		return mResources;
	}
	
	public void setDexName(String dexName) {
		Resources res = getResources();
		if (res != null && res instanceof EnvDexResourcesWrapper) {
			EnvDexResourcesWrapper wrapper = (EnvDexResourcesWrapper) res;
			wrapper.setDexName(dexName);
		}
	}

	@Override
	public Theme getTheme() {
		if (mTheme == null) {
			mTheme = getResources().newTheme();
			mTheme.setTo(getBaseContext().getTheme());
		}
		return mTheme;
	}

	@Override
	public Object getSystemService(String name) {
		if (Context.LAYOUT_INFLATER_SERVICE.equals(name)) {
			if (mLayoutInflater == null) {
				mLayoutInflater = new EnvLayoutInflaterWrapper(LayoutInflater.from(getBaseContext()), this);
			}
			return mLayoutInflater;
		}
		return super.getSystemService(name);
	}
}
