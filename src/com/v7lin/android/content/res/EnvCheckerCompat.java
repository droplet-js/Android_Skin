package com.v7lin.android.content.res;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

/**
 * 
 * 
 * @author v7lin E-mail:v7lin@qq.com
 * @since 2014-11-7 23:26:40
 */
class EnvCheckerCompat {

	interface EnvTextCheckerCompatImpl {
		public void checkSwitchText(Context context, AttributeSet attrs, View view);
		
		public void checkSwitchTypeface(Context context, AttributeSet attrs, View view, Typeface typeface);

		public void checkTextClockText(Context context, AttributeSet attrs, View view);

		public void checkSubtitleViewText(Context context, AttributeSet attrs, View view);
		
		public void checkSubtitleViewTypeface(Context context, AttributeSet attrs, View view, Typeface typeface);

		public void checkMediaRouteButtonDrawable(Context context, AttributeSet attrs, View view);
	}

	static class EarlyEnvTextCheckerCompatImpl implements EnvTextCheckerCompatImpl {

		@Override
		public void checkSwitchText(Context context, AttributeSet attrs, View view) {

		}

		@Override
		public void checkSwitchTypeface(Context context, AttributeSet attrs, View view, Typeface typeface) {
			
		}

		@Override
		public void checkTextClockText(Context context, AttributeSet attrs, View view) {

		}

		@Override
		public void checkSubtitleViewText(Context context, AttributeSet attrs, View view) {

		}

		@Override
		public void checkSubtitleViewTypeface(Context context, AttributeSet attrs, View view, Typeface typeface) {
			
		}

		@Override
		public void checkMediaRouteButtonDrawable(Context context, AttributeSet attrs, View view) {
			
		}
	}
	
	static class IceCreamSandwichEnvTextCheckerCompatImpl extends EarlyEnvTextCheckerCompatImpl {
		
		@Override
		public void checkSwitchText(Context context, AttributeSet attrs, View view) {
			EnvCheckerCompatIceCreamSandwich.checkSwitchText(context, attrs, view);
		}
		
		@Override
		public void checkSwitchTypeface(Context context, AttributeSet attrs, View view, Typeface typeface) {
			EnvCheckerCompatIceCreamSandwich.checkSwitchTypeface(context, attrs, view, typeface);
		}
	}
	
	static class JellyBeanMr1EnvTextCheckerCompatImpl extends IceCreamSandwichEnvTextCheckerCompatImpl {
		
		@Override
		public void checkTextClockText(Context context, AttributeSet attrs, View view) {
			EnvCheckerCompatJellyBeanMr1.checkTextClockText(context, attrs, view);
		}
	}

	static class UnknownEnvTextCheckerCompatImpl extends JellyBeanMr1EnvTextCheckerCompatImpl {

		@Override
		public void checkSubtitleViewText(Context context, AttributeSet attrs, View view) {
			EnvCheckerCompatUnknown.checkSubtitleViewText(context, attrs, view);
		}

		@Override
		public void checkSubtitleViewTypeface(Context context, AttributeSet attrs, View view, Typeface typeface) {
			EnvCheckerCompatUnknown.checkSubtitleViewTypeface(context, attrs, view, typeface);
		}

		@Override
		public void checkMediaRouteButtonDrawable(Context context, AttributeSet attrs, View view) {
			EnvCheckerCompatUnknown.checkMediaRouteButtonDrawable(context, attrs, view);
		}
	}

	private static final EnvTextCheckerCompatImpl IMPL;

	static {
		final int apiVersion = Build.VERSION.SDK_INT;
		if (apiVersion >= Build.VERSION_CODES.KITKAT) {
			IMPL = new UnknownEnvTextCheckerCompatImpl();
		} else if (apiVersion >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
			IMPL = new JellyBeanMr1EnvTextCheckerCompatImpl();
		} else if (apiVersion >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
			IMPL = new IceCreamSandwichEnvTextCheckerCompatImpl();
		} else {
			IMPL = new EarlyEnvTextCheckerCompatImpl();
		}
	}

	public static void checkSwitchText(Context context, AttributeSet attrs, View view) {
		IMPL.checkSwitchText(context, attrs, view);
	}
	
	public static void checkSwitchTypeface(Context context, AttributeSet attrs, View view, Typeface typeface) {
		IMPL.checkSwitchTypeface(context, attrs, view, typeface);
	}

	public static void checkTextClockText(Context context, AttributeSet attrs, View view) {
		IMPL.checkTextClockText(context, attrs, view);
	}

	public static void checkSubtitleViewText(Context context, AttributeSet attrs, View view) {
		IMPL.checkSubtitleViewText(context, attrs, view);
	}
	
	public static void checkSubtitleViewTypeface(Context context, AttributeSet attrs, View view, Typeface typeface) {
		IMPL.checkSubtitleViewTypeface(context, attrs, view, typeface);
	}

	public static void checkMediaRouteButtonDrawable(Context context, AttributeSet attrs, View view) {
		IMPL.checkMediaRouteButtonDrawable(context, attrs, view);
	}
}
