package com.v7lin.android.content.res;

import com.v7lin.android.content.EnvRes;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.ToggleButton;

/**
 * 
 * 
 * @author v7lin E-mail:v7lin@qq.com
 * @since 2014-11-7 23:26:40
 */
class EnvTextChecker implements EnvChecker {

	@Override
	public void check(Context context, AttributeSet attrs, View view) {
		checkTag(context, attrs, view);
		checkText(context, attrs, view);
		checkCompletionHint(context, attrs, view);
		checkToggleButton(context, attrs, view);
		checkSwitch(context, attrs, view);
		checkSubtitleView(context, attrs, view);
	}

	private void checkTag(Context context, AttributeSet attrs, View view) {
		final TypedValue value = new TypedValue();
		TypedArray array = context.obtainStyledAttributes(attrs, com.android.internal.R.styleable.View, 0, 0);
		array.getValue(com.android.internal.R.styleable.View_tag, value);
		EnvRes tagRes = new EnvRes(value.resourceId);
		if (tagRes.isValid()) {
			view.setTag(context.getText(tagRes.getResid()));
		}
		array.recycle();
	}

	private void checkText(Context context, AttributeSet attrs, View view) {
		if (view instanceof TextView) {
			TextView textView = (TextView) view;
			final TypedValue value = new TypedValue();
			TypedArray array = context.obtainStyledAttributes(attrs, com.android.internal.R.styleable.TextView, 0, 0);
			array.getValue(com.android.internal.R.styleable.TextView_hint, value);
			EnvRes hintRes = new EnvRes(value.resourceId);
			if (hintRes.isValid()) {
				textView.setHint(hintRes.getResid());
			}
			array.getValue(com.android.internal.R.styleable.TextView_text, value);
			EnvRes textRes = new EnvRes(value.resourceId);
			if (textRes.isValid()) {
				textView.setText(textRes.getResid());
			}
			array.recycle();
		}
	}

	private void checkCompletionHint(Context context, AttributeSet attrs, View view) {
		if (view instanceof AutoCompleteTextView) {
			AutoCompleteTextView textView = (AutoCompleteTextView) view;
			final TypedValue value = new TypedValue();
			TypedArray array = context.obtainStyledAttributes(attrs, com.android.internal.R.styleable.AutoCompleteTextView, 0, 0);
			array.getValue(com.android.internal.R.styleable.AutoCompleteTextView_completionHint, value);
			EnvRes completionHintRes = new EnvRes(value.resourceId);
			if (completionHintRes.isValid()) {
				textView.setCompletionHint(context.getText(completionHintRes.getResid()));
			}
			array.recycle();
		}
	}

	private void checkToggleButton(Context context, AttributeSet attrs, View view) {
		if (view instanceof ToggleButton) {
			ToggleButton button = (ToggleButton) view;
			final TypedValue value = new TypedValue();
			TypedArray array = context.obtainStyledAttributes(attrs, com.android.internal.R.styleable.ToggleButton, 0, 0);
			array.getValue(com.android.internal.R.styleable.ToggleButton_textOn, value);
			EnvRes textOnRes = new EnvRes(value.resourceId);
			if (textOnRes.isValid()) {
				button.setTextOn(context.getText(textOnRes.getResid()));
			}
			array.getValue(com.android.internal.R.styleable.ToggleButton_textOff, value);
			EnvRes textOffRes = new EnvRes(value.resourceId);
			if (textOffRes.isValid()) {
				button.setTextOff(context.getText(textOffRes.getResid()));
			}
			array.recycle();
		}
	}

	private void checkSwitch(Context context, AttributeSet attrs, View view) {
		EnvCheckerCompat.checkSwitchText(context, attrs, view);
	}

	private void checkSubtitleView(Context context, AttributeSet attrs, View view) {
		EnvCheckerCompat.checkSubtitleViewText(context, attrs, view);
	}
}
