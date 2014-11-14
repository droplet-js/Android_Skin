package com.v7lin.android.content.res;

import java.util.concurrent.atomic.AtomicBoolean;


import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

/**
 * 
 * 
 * @author v7lin E-mail:v7lin@qq.com
 * @since 2014-11-7 23:26:40
 */
class EnvTypefaceChecker implements EnvChecker {
	
	private final AtomicBoolean mInitTypeface = new AtomicBoolean(false);
	private String mCurFontName;
	private Typeface mTypeface;
	
	private void ensureTypeface(Context context) {
		if (mInitTypeface.compareAndSet(false, true) || EnvTypefaceManager.getInstance().isFontChanged(context, mCurFontName)) {
			mCurFontName = EnvTypefaceManager.getInstance().getCurFontName(context);
			mTypeface = EnvTypefaceManager.getInstance().getTopLevelTypeface(context);
		}
	}

	/**
	 * @see TextView#setTypeface(Typeface)
	 */
	@Override
	public void check(Context context, AttributeSet attrs, View view) {
		ensureTypeface(context);
		
		checkTextView(context, attrs, view);
		checkSwitch(context, attrs, view);
		checkSubtitleView(context, attrs, view);
	}
	
	private void checkTextView(Context context, AttributeSet attrs, View view) {
		if (view instanceof TextView) {
			TextView textView = (TextView) view;
			textView.setTypeface(mTypeface);
		}
	}
	
	private void checkSwitch(Context context, AttributeSet attrs, View view) {
		EnvCheckerCompat.checkSwitchTypeface(context, attrs, view, mTypeface);
	}
	
	private void checkSubtitleView(Context context, AttributeSet attrs, View view) {
		EnvCheckerCompat.checkSubtitleViewTypeface(context, attrs, view, mTypeface);
	}
}
