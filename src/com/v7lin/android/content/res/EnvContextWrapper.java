package com.v7lin.android.content.res;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Resources;
import android.content.res.Resources.Theme;
import android.view.LayoutInflater;


/**
 * 皮肤插件 APK 就嫑签名，防止用户错误安装皮肤插件 APK ...
 * 
 * Window 层级的 View，救不了 ... 
 * 非 inflater 的 View，救不了 ...
 * 
 * 特别的，要注意
 * <color name="white_delegate">@color/white</color> 取到的 resid 为 white，不会是 white_delegate
 * 
 * @author v7lin E-mail:v7lin@qq.com
 * @since 2014-11-7 23:26:40
 */
public class EnvContextWrapper extends ContextWrapper {

	private Resources mResources;
	private Theme mTheme;
	private LayoutInflater mLayoutInflater;

	public EnvContextWrapper(Context base) {
		super(base);
	}

	@Override
	public Resources getResources() {
		if (mResources == null) {
			mResources = new EnvSkinResourcesWrapper(this, getBaseContext().getResources());
		}
		return mResources;
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
