package com.v7lin.android.content.res;

import java.util.Arrays;
import java.util.List;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

/**
 * 
 * 
 * @author v7lin E-mail:v7lin@qq.com
 * @since 2014-11-7 23:26:40
 */
public class EnvLayoutInflaterWrapper extends LayoutInflater {

	private static final String[] sClassPrefixArray = {
			// widget
			"android.widget.",
			// webkit
			"android.webkit.",
			// app
			"android.app." };

	private static final List<String> sClassPrefixList = Arrays.asList(sClassPrefixArray);

	public EnvLayoutInflaterWrapper(LayoutInflater original, Context newContext) {
		super(original, newContext);

		setup();
	}

	private void setup() {
		Factory factory = getFactory();
		if (!(factory instanceof EnvFactory)) {
			setFactory(new EnvFactory(factory));
		}
	}

	@Override
	public LayoutInflater cloneInContext(Context newContext) {
		return new EnvLayoutInflaterWrapper(this, newContext);
	}

	static class EnvFactory implements Factory {
		
		private final LayoutInflater.Factory wrapped;
		
		private final EnvChecker mEnvTypefaceChecker = new EnvTypefaceChecker();
		private final EnvChecker mEnvTextChecker = new EnvTextChecker();
		private final EnvChecker mEnvColorChecker = new EnvColorChecker();
		private final EnvChecker mEnvDrawableChecker = new EnvDrawableChecker();

		public EnvFactory(Factory wrapped) {
			super();
			this.wrapped = wrapped;
		}

		@Override
		public View onCreateView(String name, Context context, AttributeSet attrs) {
			View view = null;
			if (context instanceof LayoutInflater.Factory) {
				view = ((LayoutInflater.Factory) context).onCreateView(name, context, attrs);
			}
			if (view == null && wrapped != null) {
				view = wrapped.onCreateView(name, context, attrs);
			}
			if (view == null) {
				view = createViewOrFailQuietly(name, context, attrs);
			}
			if (view != null) {
				checkEnvConfig(context, attrs, view);
			}
			return view;
		}

		private View createViewOrFailQuietly(String name, Context context, AttributeSet attrs) {
			if (name.contains(".")) {
				return createViewOrFailQuietly(name, null, context, attrs);
			}
			for (final String prefix : sClassPrefixList) {
				final View view = createViewOrFailQuietly(name, prefix, context, attrs);
				if (view != null) {
					return view;
				}
			}
			return null;
		}

		private View createViewOrFailQuietly(String name, String prefix, Context context, AttributeSet attrs) {
			try {
				return LayoutInflater.from(context).createView(name, prefix, attrs);
			} catch (Exception ignore) {
				return null;
			}
		}

		/**
		 * 关于系统性换肤的 View 还未完善，待续 ...
		 * 
		 * 这里强调，所有视图必须使用
		 * {@link LayoutInflater#inflate(int, android.view.ViewGroup)} 或者
		 * {@link View#inflate(Context, int, android.view.ViewGroup)} 创建
		 * 
		 * 换 Typeface，有一个 BUG，就是直接 new 的对象无法自动换字体，所以有上述强调 换 Drawable，避免
		 * {@link TypedArray#getDrawable(int)} 直接取资源，绕过既定换肤流程
		 */
		private void checkEnvConfig(Context context, AttributeSet attrs, View view) {
			// change typeface
			mEnvTypefaceChecker.check(context, attrs, view);
			// change text
			mEnvTextChecker.check(context, attrs, view);
			// change color
			mEnvColorChecker.check(context, attrs, view);
			// change drawable
			mEnvDrawableChecker.check(context, attrs, view);
		}
	}
}
