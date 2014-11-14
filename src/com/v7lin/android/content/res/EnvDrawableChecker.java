package com.v7lin.android.content.res;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.v7lin.android.content.EnvRes;


import android.content.Context;
import android.content.res.Resources.NotFoundException;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsSeekBar;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 
 * 
 * @author v7lin E-mail:v7lin@qq.com
 * @since 2014-11-7 23:26:40
 */
class EnvDrawableChecker implements EnvChecker {

	@Override
	public void check(Context context, AttributeSet attrs, View view) {
		checkBackground(context, attrs, view);
		checkScrollBar(context, attrs, view);
		checkCompoundDrawable(context, attrs, view);
		checkCompoundButton(context, attrs, view);
		checkImageView(context, attrs, view);
		checkAbsListView(context, attrs, view);
		checkAbsSeekBar(context, attrs, view);
		checkMediaRouteButton(context, attrs, view);
	}

	/**
	 * @see View#setBackgroundResource(int)
	 */
	private void checkBackground(Context context, AttributeSet attrs, View view) {
		final TypedValue value = new TypedValue();
		TypedArray array = context.obtainStyledAttributes(attrs, com.android.internal.R.styleable.View, 0, 0);
		array.getValue(com.android.internal.R.styleable.View_background, value);
		EnvRes backgroundRes = new EnvRes(value.resourceId);
		if (backgroundRes.isValid()) {
			view.setBackgroundResource(backgroundRes.getResid());
		}
		array.recycle();
	}
	
	private void checkScrollBar(Context context, AttributeSet attrs, View view) {
		try {
			Field mScrollCacheField = View.class.getDeclaredField("mScrollCache");
			if (mScrollCacheField != null) {
				mScrollCacheField.setAccessible(true);
				Object mScrollCache = mScrollCacheField.get(view);
				if (mScrollCache != null) {
					Field scrollBarField = mScrollCache.getClass().getDeclaredField("scrollBar");
					if (scrollBarField != null) {
						Object scrollBar = scrollBarField.get(mScrollCache);
						if (scrollBar != null) {
							final TypedValue value = new TypedValue();
							TypedArray array = context.obtainStyledAttributes(attrs, com.android.internal.R.styleable.View, 0, 0);
							array.getValue(com.android.internal.R.styleable.View_scrollbarTrackHorizontal, value);
							EnvRes scrollbarTrackHorizontalRes = new EnvRes(value.resourceId);
							if (scrollbarTrackHorizontalRes.isValid()) {
								Method setHorizontalTrackDrawableMethod = scrollBar.getClass().getDeclaredMethod("setHorizontalTrackDrawable", Drawable.class);
								if (setHorizontalTrackDrawableMethod != null) {
									setHorizontalTrackDrawableMethod.setAccessible(true);
									setHorizontalTrackDrawableMethod.invoke(scrollBar, context.getResources().getDrawable(scrollbarTrackHorizontalRes.getResid()));
								}
							}
							array.getValue(com.android.internal.R.styleable.View_scrollbarThumbHorizontal, value);
							EnvRes scrollbarThumbHorizontalRes = new EnvRes(value.resourceId);
							if (scrollbarThumbHorizontalRes.isValid()) {
								Method setHorizontalThumbDrawableMethod = scrollBar.getClass().getDeclaredMethod("setHorizontalThumbDrawable", Drawable.class);
								if (setHorizontalThumbDrawableMethod != null) {
									setHorizontalThumbDrawableMethod.setAccessible(true);
									setHorizontalThumbDrawableMethod.invoke(scrollBar, context.getResources().getDrawable(scrollbarThumbHorizontalRes.getResid()));
								}
							}
							array.getValue(com.android.internal.R.styleable.View_scrollbarTrackVertical, value);
							EnvRes scrollbarTrackVerticalRes = new EnvRes(value.resourceId);
							if (scrollbarTrackVerticalRes.isValid()) {
								Method setVerticalTrackDrawableMethod = scrollBar.getClass().getDeclaredMethod("setVerticalTrackDrawable", Drawable.class);
								if (setVerticalTrackDrawableMethod != null) {
									setVerticalTrackDrawableMethod.setAccessible(true);
									setVerticalTrackDrawableMethod.invoke(scrollBar, context.getResources().getDrawable(scrollbarTrackVerticalRes.getResid()));
								}
							}
							array.getValue(com.android.internal.R.styleable.View_scrollbarThumbVertical, value);
							EnvRes scrollbarThumbVerticalRes = new EnvRes(value.resourceId);
							if (scrollbarThumbVerticalRes.isValid()) {
								Method setVerticalThumbDrawableMethod = scrollBar.getClass().getDeclaredMethod("setVerticalThumbDrawable", Drawable.class);
								if (setVerticalThumbDrawableMethod != null) {
									setVerticalThumbDrawableMethod.setAccessible(true);
									setVerticalThumbDrawableMethod.invoke(scrollBar, context.getResources().getDrawable(scrollbarThumbVerticalRes.getResid()));
								}
							}
							array.recycle();
						}
					}
				}
			}
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see TextView#setCompoundDrawablesWithIntrinsicBounds(int, int, int, int)
	 */
	private void checkCompoundDrawable(Context context, AttributeSet attrs, View view) {
		if (view instanceof TextView) {
			TextView textView = (TextView) view;
			final int drawablePadding = textView.getCompoundDrawablePadding();

			final TypedValue value = new TypedValue();
			TypedArray array = context.obtainStyledAttributes(attrs, com.android.internal.R.styleable.TextView, 0, 0);
			array.getValue(com.android.internal.R.styleable.TextView_drawableLeft, value);
			final int drawableLeftResId = value.resourceId;
			array.getValue(com.android.internal.R.styleable.TextView_drawableTop, value);
			final int drawableTopResId = value.resourceId;
			array.getValue(com.android.internal.R.styleable.TextView_drawableRight, value);
			final int drawableRightResId = value.resourceId;
			array.getValue(com.android.internal.R.styleable.TextView_drawableBottom, value);
			final int drawableBottomResId = value.resourceId;
			array.recycle();

			EnvRes drawableLeftRes = new EnvRes(drawableLeftResId);
			EnvRes drawableTopRes = new EnvRes(drawableTopResId);
			EnvRes drawableRightRes = new EnvRes(drawableRightResId);
			EnvRes drawableBottomRes = new EnvRes(drawableBottomResId);
			if (drawableLeftRes.isValid() || drawableTopRes.isValid() || drawableRightRes.isValid() || drawableBottomRes.isValid()) {
				textView.setCompoundDrawablesWithIntrinsicBounds(drawableLeftRes.getResid(), drawableTopRes.getResid(), drawableRightRes.getResid(), drawableBottomRes.getResid());
				textView.setCompoundDrawablePadding(drawablePadding);
			}
		}
	}

	/**
	 * @see CompoundButton#setButtonDrawable(int)
	 */
	private void checkCompoundButton(Context context, AttributeSet attrs, View view) {
		if (view instanceof CompoundButton) {
			CompoundButton button = (CompoundButton) view;
			final TypedValue value = new TypedValue();
			TypedArray array = context.obtainStyledAttributes(attrs, com.android.internal.R.styleable.CompoundButton, 0, 0);
			array.getValue(com.android.internal.R.styleable.CompoundButton_button, value);
			EnvRes buttonRes = new EnvRes(value.resourceId);
			if (buttonRes.isValid()) {
				button.setButtonDrawable(buttonRes.getResid());
			}
			array.recycle();
		}
	}

	/**
	 * @see ImageView#setImageResource(int)
	 */
	private void checkImageView(Context context, AttributeSet attrs, View view) {
		if (view instanceof ImageView) {
			ImageView imageView = (ImageView) view;
			final TypedValue value = new TypedValue();
			TypedArray array = context.obtainStyledAttributes(attrs, com.android.internal.R.styleable.ImageView, 0, 0);
			array.getValue(com.android.internal.R.styleable.ImageView_src, value);
			EnvRes srcRes = new EnvRes(value.resourceId);
			if (srcRes.isValid()) {
				imageView.setImageResource(srcRes.getResid());
			}
			array.recycle();
		}
	}
	
	private void checkAbsListView(Context context, AttributeSet attrs, View view) {
		if (view instanceof AbsListView) {
			AbsListView listView = (AbsListView) view;
			final TypedValue value = new TypedValue();
			TypedArray array = context.obtainStyledAttributes(attrs, com.android.internal.R.styleable.AbsListView, 0, 0);
			array.getValue(com.android.internal.R.styleable.AbsListView_listSelector, value);
			EnvRes listSelectorRes = new EnvRes(value.resourceId);
			if (listSelectorRes.isValid()) {
				listView.setSelector(context.getResources().getDrawable(listSelectorRes.getResid()));
			}
			array.recycle();
		}
	}
	
	private void checkAbsSeekBar(Context context, AttributeSet attrs, View view) {
		if (view instanceof AbsSeekBar) {
			AbsSeekBar seekBar = (AbsSeekBar) view;
			final TypedValue value = new TypedValue();
			TypedArray array = context.obtainStyledAttributes(attrs, com.android.internal.R.styleable.SeekBar, 0, 0);
			array.getValue(com.android.internal.R.styleable.SeekBar_thumb, value);
			EnvRes thumbRes = new EnvRes(value.resourceId);
			if (thumbRes.isValid()) {
				seekBar.setThumb(context.getResources().getDrawable(thumbRes.getResid()));
			}
			array.recycle();
		}
	}
	
	private void checkMediaRouteButton(Context context, AttributeSet attrs, View view) {
		EnvCheckerCompat.checkMediaRouteButtonDrawable(context, attrs, view);
	}
}
