package com.v7lin.android.test;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.SparseArray;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.v7lin.android.R;
import com.v7lin.android.content.dex.EnvDexConst;
import com.v7lin.android.content.res.EnvActivity;
import com.v7lin.android.content.res.EnvSetup;

/**
 * 
 * 
 * @author v7lin E-mail:v7lin@qq.com
 * @since 2014-11-7 23:26:40
 */
public class TestActivity extends EnvActivity implements EnvDexConst {

	private static final String SKIN_TEST = "LibAndroid.apk";
	private static final String FONT_TEST = "Oswald-Stencbab.ttf";
	private static final String DEX_APP_TEST = "StyleDex.apk";
	
	private final int mLayoutResID = R.layout.layout_main;
	private View mContentView;
	
	@Override
	protected void attachBaseContext(Context newBase) {
		EnvSetup.getInstance().setSkinName(newBase, "");
		EnvSetup.getInstance().setFontName(newBase, "");
		super.attachBaseContext(newBase);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		mContentView = View.inflate(TestActivity.this, mLayoutResID, null);
		
		mContentView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (mContentView != null) {
					EnvSetup.getInstance().setSkinName(TestActivity.this, SKIN_TEST);
					EnvSetup.getInstance().setFontName(TestActivity.this, FONT_TEST);
					
					SparseArray<Parcelable> container = new SparseArray<Parcelable>();
					mContentView.saveHierarchyState(container);
					((ViewGroup) mContentView.getParent()).removeView(mContentView);
					
					mContentView = View.inflate(TestActivity.this, mLayoutResID, null);
					mContentView.restoreHierarchyState(container);
					setContentView(mContentView);
				}
				
//				// 插件式开发
//				File dexFile = new File(PathUtils.getDexAppDir(TestActivity.this), DEX_APP_TEST);
//				if (dexFile.exists()) {
//					try {
//						EnvDexContextWrapper context = new EnvDexContextWrapper(TestActivity.this);
//						context.setDexName(DEX_APP_TEST);
//						
//						ClassLoader parent = TestActivity.this.getClassLoader();// ClassLoader.getSystemClassLoader()
//						EnvDexClassLoader loader = EnvDexClassLoader.newInstance(TestActivity.this, dexFile.getAbsolutePath(), parent);
//						Class<?> clazz = loader.loadClass("com.v7lin.android.dex.style.StyleDexTest");
//						Object object = clazz.getConstructor(Context.class).newInstance(context);
//						if (object != null && object instanceof IDexTest) {
//							IDexTest test = (IDexTest) object;
//							LogCat.e("Get Text:" + test.getText());
//							LogCat.e("Get Dex Text:" + test.getDexText());
//						}
//					} catch (ClassNotFoundException e) {
//						e.printStackTrace();
//					} catch (InstantiationException e) {
//						e.printStackTrace();
//					} catch (IllegalAccessException e) {
//						e.printStackTrace();
//					} catch (IllegalArgumentException e) {
//						e.printStackTrace();
//					} catch (InvocationTargetException e) {
//						e.printStackTrace();
//					} catch (NoSuchMethodException e) {
//						e.printStackTrace();
//					}
//				}
			}
		});
		setContentView(mContentView);
	}
}
