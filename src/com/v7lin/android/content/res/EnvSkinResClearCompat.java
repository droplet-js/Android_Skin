package com.v7lin.android.content.res;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Drawable.ConstantState;
import android.os.Build;
import android.util.LongSparseArray;

/**
 * 清理 {@link Resources} 中有关 {@link ColorStateList}、{@link Drawable}的
 * {@link ConstantState} 的缓存
 * 
 * 虽然，{@link Drawable}的{@link ConstantState} 大多是不需要清理的 但是，谁让它的子类中，出现了
 * {@link ColorDrawable}的{@link ColorState}呢
 * 
 * @author v7lin E-mail:v7lin@qq.com
 * @since 2014-11-7 23:26:40
 */
class EnvSkinResClearCompat {

	interface EnvResClearCompatImpl {
		public void clear(Resources res);
	}

	static abstract class BaseEnvResClearCompatImpl implements EnvResClearCompatImpl {

		protected void clearLongSparseArray(Resources res, String fieldName) {
			try {
				Class<?> clazz = Resources.class;
				Field field = clazz.getDeclaredField(fieldName);
				if (field != null) {
					field.setAccessible(true);
					Object object = null;
					if (Modifier.isStatic(field.getModifiers())) {
						object = field.get(null);
					} else {
						object = field.get(res);
					}
					if (object != null && object instanceof LongSparseArray<?>) {
						LongSparseArray<?> array = (LongSparseArray<?>) object;
						Method method = LongSparseArray.class.getDeclaredMethod("clear");
						if (method != null) {
							method.setAccessible(true);
							method.invoke(array);
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
			}
		}
	}

	static class EarlyEnvResClearCompatImpl extends BaseEnvResClearCompatImpl {

		@Override
		public void clear(Resources res) {
			// static
			// sPreloadedDrawables
			// mPreloadedColorStateLists
			clearLongSparseArray(res, "sPreloadedDrawables");
			clearLongSparseArray(res, "mPreloadedColorStateLists");

			// member
			// mDrawableCache
			// mColorStateListCache
			clearLongSparseArray(res, "mDrawableCache");
			clearLongSparseArray(res, "mColorStateListCache");
		}
	}

	/**
	 * 未知 自己找 Resource 源码，去看看这些成员的改变是从哪个 API 开始的
	 */
	static class IceCreamSandwichEnvResClearCompatImpl extends BaseEnvResClearCompatImpl {

		@Override
		public void clear(Resources res) {
			// static
			// sPreloadedDrawables
			// mPreloadedColorStateLists
			// sPreloadedColorDrawables
			clearLongSparseArray(res, "sPreloadedDrawables");
			clearLongSparseArray(res, "mPreloadedColorStateLists");
			clearLongSparseArray(res, "sPreloadedColorDrawables");

			// member
			// mDrawableCache
			// mColorStateListCache
			// mColorDrawableCache
			clearLongSparseArray(res, "mDrawableCache");
			clearLongSparseArray(res, "mColorStateListCache");
			clearLongSparseArray(res, "mColorDrawableCache");
		}
	}

	static class JellyBeanEnvResClearCompatImpl extends BaseEnvResClearCompatImpl {

		@Override
		public void clear(Resources res) {
			// static
			// sPreloadedDrawables
			// sPreloadedColorStateLists
			// sPreloadedColorDrawables
			clearLongSparseArray(res, "sPreloadedDrawables");
			clearLongSparseArray(res, "sPreloadedColorStateLists");
			clearLongSparseArray(res, "sPreloadedColorDrawables");

			// member
			// mDrawableCache
			// mColorStateListCache
			// mColorDrawableCache
			clearLongSparseArray(res, "mDrawableCache");
			clearLongSparseArray(res, "mColorStateListCache");
			clearLongSparseArray(res, "mColorDrawableCache");
		}
	}

	private static final EnvResClearCompatImpl IMPL;

	static {
		final int apiVersion = Build.VERSION.SDK_INT;
		if (apiVersion >= Build.VERSION_CODES.JELLY_BEAN) {
			IMPL = new JellyBeanEnvResClearCompatImpl();
		} else if (apiVersion >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
			IMPL = new IceCreamSandwichEnvResClearCompatImpl();
		} else {
			IMPL = new EarlyEnvResClearCompatImpl();
		}
	}

	public static void clear(Resources res) {
		IMPL.clear(res);
	}
}
