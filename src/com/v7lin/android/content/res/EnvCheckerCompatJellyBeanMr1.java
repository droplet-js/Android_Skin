package com.v7lin.android.content.res;

import com.v7lin.android.content.EnvRes;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextClock;

/**
 * 
 * 
 * @author v7lin E-mail:v7lin@qq.com
 * @since 2014-11-7 23:26:40
 */
@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
class EnvCheckerCompatJellyBeanMr1 {

	public static void checkTextClockText(Context context, AttributeSet attrs, View view) {
		if (view instanceof TextClock) {
			TextClock clock = (TextClock) view;
			final TypedValue value = new TypedValue();
			TypedArray array = context.obtainStyledAttributes(attrs, com.android.internal.R.styleable.TextClock, 0, 0);
			array.getValue(com.android.internal.R.styleable.TextClock_format12Hour, value);
			EnvRes format12HourRes = new EnvRes(value.resourceId);
			if (format12HourRes.isValid()) {
				clock.setFormat12Hour(context.getText(format12HourRes.getResid()));
			}
			array.getValue(com.android.internal.R.styleable.TextClock_format24Hour, value);
			EnvRes format24HourRes = new EnvRes(value.resourceId);
			if (format24HourRes.isValid()) {
				clock.setFormat24Hour(context.getText(format24HourRes.getResid()));
			}
			array.recycle();
		}
	}
}
