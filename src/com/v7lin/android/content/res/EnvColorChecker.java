package com.v7lin.android.content.res;

import com.v7lin.android.content.EnvRes;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.AbsListView;
import android.widget.TextView;

/**
 * 
 * 
 * @author v7lin E-mail:v7lin@qq.com
 * @since 2014-11-7 23:26:40
 */
class EnvColorChecker implements EnvChecker {

	@Override
	public void check(Context context, AttributeSet attrs, View view) {
		checkTextColor(context, attrs, view);
		checkAbsListView(context, attrs, view);
	}

	private void checkTextColor(Context context, AttributeSet attrs, View view) {
		if (view instanceof TextView) {
			TextView textView = (TextView) view;
			final TypedValue value = new TypedValue();

			int textColorHighlightResid = 0; // color
			int textColorResid = 0; // colorstatelist
			int textColorHintResid = 0; // colorstatelist
			int textColorLinkResid = 0; // colorstatelist

			// 高版本 API
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
				TypedArray textViewAppearanceArray = context.obtainStyledAttributes(attrs, com.android.internal.R.styleable.TextViewAppearance, 0, 0);
				int textAppearance = textViewAppearanceArray.getResourceId(com.android.internal.R.styleable.TextViewAppearance_textAppearance, -1);
				if (textAppearance != -1) {
					TypedArray textAppearanceArray = context.getTheme().obtainStyledAttributes(textAppearance, com.android.internal.R.styleable.TextAppearance);
					final int count = textAppearanceArray.getIndexCount();
					for (int i = 0; i < count; i++) {
						int attr = textAppearanceArray.getIndex(i);
						textAppearanceArray.getValue(attr, value);
						switch (attr) {
						case com.android.internal.R.styleable.TextAppearance_textColorHighlight: {
							textColorHighlightResid = value.resourceId;
							break;
						}
						case com.android.internal.R.styleable.TextAppearance_textColor: {
							textColorResid = value.resourceId;
							break;
						}
						case com.android.internal.R.styleable.TextAppearance_textColorHint: {
							textColorHintResid = value.resourceId;
							break;
						}
						case com.android.internal.R.styleable.TextAppearance_textColorLink: {
							textColorLinkResid = value.resourceId;
							break;
						}
						default: {
							break;
						}
						}
					}
					textAppearanceArray.recycle();
				}
				textViewAppearanceArray.recycle();
			}

			TypedArray textViewArray = context.getTheme().obtainStyledAttributes(attrs, com.android.internal.R.styleable.TextView, 0, 0);
			final int count = textViewArray.getIndexCount();
			for (int i = 0; i < count; i++) {
				int attr = textViewArray.getIndex(i);
				textViewArray.getValue(attr, value);
				switch (attr) {
				case com.android.internal.R.styleable.TextView_textColorHighlight: {
					textColorHighlightResid = value.resourceId;
					break;
				}
				case com.android.internal.R.styleable.TextView_textColor: {
					textColorResid = value.resourceId;
					break;
				}
				case com.android.internal.R.styleable.TextView_textColorHint: {
					textColorHintResid = value.resourceId;
					break;
				}
				case com.android.internal.R.styleable.TextView_textColorLink: {
					textColorLinkResid = value.resourceId;
					break;
				}
				default: {
					break;
				}
				}
			}
			textViewArray.recycle();

			EnvRes textColorHighlightRes = new EnvRes(textColorHighlightResid);
			if (textColorHighlightRes.isValid()) {
				textView.setHighlightColor(context.getResources().getColor(textColorHighlightRes.getResid()));
			}
			EnvRes textColorRes = new EnvRes(textColorResid);
			if (textColorRes.isValid()) {
				textView.setTextColor(context.getResources().getColorStateList(textColorRes.getResid()));
			}
			EnvRes textColorHintRes = new EnvRes(textColorHintResid);
			if (textColorHintRes.isValid()) {
				textView.setHintTextColor(context.getResources().getColorStateList(textColorHintRes.getResid()));
			}
			EnvRes textColorLinkRes = new EnvRes(textColorLinkResid);
			if (textColorLinkRes.isValid()) {
				textView.setLinkTextColor(context.getResources().getColorStateList(textColorLinkRes.getResid()));
			}
		}
	}

	private void checkAbsListView(Context context, AttributeSet attrs, View view) {
		if (view instanceof AbsListView) {
			AbsListView listView = (AbsListView) view;
			final TypedValue value = new TypedValue();
			TypedArray array = context.obtainStyledAttributes(attrs, com.android.internal.R.styleable.AbsListView, 0, 0);
			array.getValue(com.android.internal.R.styleable.AbsListView_cacheColorHint, value);
			EnvRes cacheColorHintRes = new EnvRes(value.resourceId);
			if (cacheColorHintRes.isValid()) {
				listView.setCacheColorHint(context.getResources().getColor(cacheColorHintRes.getResid()));
			}
			array.recycle();
		}
	}
}
