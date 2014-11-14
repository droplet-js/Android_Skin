package com.v7lin.android.content.res;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.graphics.Typeface;
import android.text.TextUtils;

import com.v7lin.android.os.env.PathUtils;

/**
 * 
 * 
 * @author v7lin E-mail:v7lin@qq.com
 * @since 2014-11-7 23:26:40
 */
public class EnvTypefaceManager {
	
	private final Map<String, WeakReference<Typeface>> mActiveTypeface = new HashMap<String, WeakReference<Typeface>>();
	
	public static EnvTypefaceManager getInstance() {
		return EnvTypefaceManagerHolder.INSTANCE;
	}
	
	private EnvTypefaceManager() {
		super();
	}
	
	public String getCurFontName(Context context) {
		String fontName = EnvSetup.getInstance().getFontName(context);
		return checkFontExist(context, fontName) ? fontName : "";
	}
	
	private boolean checkFontExist(Context context, String fontName) {
		boolean exist = false;
		if (!TextUtils.isEmpty(fontName)) {
			File resFile = new File(PathUtils.getFontDir(context), fontName);
			exist = resFile.exists() && resFile.isFile();
		}
		return exist;
	}
	
	public boolean isFontChanged(Context context, String fontName) {
		return !TextUtils.equals(getCurFontName(context), fontName);
	}

	public synchronized Typeface getTopLevelTypeface(Context context) {
		Typeface typeface = null;
		String fontName = getCurFontName(context);
		if (!TextUtils.isEmpty(fontName)) {
			WeakReference<Typeface> wr = mActiveTypeface.get(fontName);
			typeface = wr != null ? wr.get() : null;
			if (typeface == null) {
				File fontFile = new File(PathUtils.getFontDir(context), fontName);
				if (fontFile.exists() && fontFile.isFile()) {
					typeface = Typeface.createFromFile(fontFile);
					mActiveTypeface.put(fontName, new WeakReference<Typeface>(typeface));
				}
			}
		}
		return typeface;
	}
	
	private static class EnvTypefaceManagerHolder {
		private static final EnvTypefaceManager INSTANCE = new EnvTypefaceManager();
	}
}
