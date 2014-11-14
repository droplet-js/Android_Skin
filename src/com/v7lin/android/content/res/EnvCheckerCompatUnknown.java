package com.v7lin.android.content.res;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import android.annotation.TargetApi;
import android.app.MediaRouteButton;
import android.content.Context;
import android.content.res.Resources.NotFoundException;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.android.internal.widget.SubtitleView;
import com.v7lin.android.content.EnvRes;

/**
 * 未知 View 所在 API，所以暂定 API 级别为当前最高
 * 
 * @author v7lin E-mail:v7lin@qq.com
 * @since 2014-11-7 23:26:40
 */
@TargetApi(Build.VERSION_CODES.KITKAT)
class EnvCheckerCompatUnknown {
	
	public static void checkSubtitleViewText(Context context, AttributeSet attrs, View view) {
		if (view instanceof SubtitleView) {
			SubtitleView subtitleView = (SubtitleView) view;
			final TypedValue value = new TypedValue();
			TypedArray array = context.obtainStyledAttributes(attrs, com.android.internal.R.styleable.TextView, 0, 0);
			array.getValue(com.android.internal.R.styleable.TextView_text, value);
			EnvRes textRes = new EnvRes(value.resourceId);
			if (textRes.isValid()) {
				subtitleView.setText(textRes.getResid());
			}
			array.recycle();
		}
	}
	
	public static void checkSubtitleViewTypeface(Context context, AttributeSet attrs, View view, Typeface typeface) {
		if (view instanceof SubtitleView) {
			SubtitleView subtitleView = (SubtitleView) view;
			subtitleView.setTypeface(typeface);
		}
	}
	
	/**
	 * 需要反射来设置
	 */
	public static void checkMediaRouteButtonDrawable(Context context, AttributeSet attrs, View view) {
		if (view instanceof MediaRouteButton) {
			MediaRouteButton button = (MediaRouteButton) view;
			final TypedValue value = new TypedValue();
			TypedArray array = context.obtainStyledAttributes(attrs, com.android.internal.R.styleable.MediaRouteButton, 0, 0);
			array.getValue(com.android.internal.R.styleable.MediaRouteButton_externalRouteEnabledDrawable, value);
			EnvRes externalRouteEnabledDrawableRes = new EnvRes(value.resourceId);
			if (externalRouteEnabledDrawableRes.isValid()) {
				try {
					Method method = MediaRouteButton.class.getDeclaredMethod("setRemoteIndicatorDrawable", Drawable.class);
					if (method != null) {
						method.setAccessible(true);
						method.invoke(button, context.getResources().getDrawable(externalRouteEnabledDrawableRes.getResid()));
					}
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				} catch (NotFoundException e) {
					e.printStackTrace();
				}
			}
			array.recycle();
		}
	}
}
