package com.v7lin.android.content.res;

import com.v7lin.android.content.EnvRes;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.os.Build;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.Switch;

/**
 * 
 * 
 * @author v7lin E-mail:v7lin@qq.com
 * @since 2014-11-7 23:26:40
 */
@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
class EnvCheckerCompatIceCreamSandwich {
	
	public static void checkSwitchText(Context context, AttributeSet attrs, View view) {
		if (view instanceof Switch) {
			Switch switchView = (Switch) view;
			final TypedValue value = new TypedValue();
			TypedArray array = context.obtainStyledAttributes(attrs, com.android.internal.R.styleable.Switch, 0, 0);
			array.getValue(com.android.internal.R.styleable.Switch_textOn, value);
			EnvRes textOnRes = new EnvRes(value.resourceId);
			if (textOnRes.isValid()) {
				switchView.setTextOn(context.getText(textOnRes.getResid()));
			}
			array.getValue(com.android.internal.R.styleable.Switch_textOff, value);
			EnvRes textOffRes = new EnvRes(value.resourceId);
			if (textOffRes.isValid()) {
				switchView.setTextOff(context.getText(textOffRes.getResid()));
			}
			array.recycle();
		}
	}
	
	public static void checkSwitchTypeface(Context context, AttributeSet attrs, View view, Typeface typeface) {
		if (view instanceof Switch) {
			Switch switchView = (Switch) view;
			switchView.setSwitchTypeface(typeface);
		}
	}
}
