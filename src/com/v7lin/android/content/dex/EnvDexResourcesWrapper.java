package com.v7lin.android.content.dex;

import java.io.InputStream;
import java.util.concurrent.atomic.AtomicBoolean;

import com.v7lin.android.content.EnvRes;
import com.v7lin.android.content.EnvResourcesWrapper;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.TypedValue;

/**
 * 同名同类型资源
 * 优先级：应用自带资源 > 功能插件资源
 * 
 * 同一 resid，可能是主工程包和插件包都有，而对应资源却是不同的
 * 
 * 由于无法绕过 R 文件的编译，无法想 Android SDK 那样将资源文件按包名区分
 * 所以，使用同名资源替换发，有插件 Resource ID 解析得出资源的名称和类型
 * 然后到主工程中寻出同名资源
 * 主工程有同名资源，则以主工程为主
 * 主工程木有同名资源，则继续使用插件工程资源
 * 
 * Android SDK Package ID 以 0x01 开头
 * Android App Package ID 以 0x7f 开头
 * 
 * @author v7lin E-mail:v7lin@qq.com
 * @since 2014-11-9 22:55:29
 */
public class EnvDexResourcesWrapper extends EnvResourcesWrapper {

	private final Context mContext;
	private final AtomicBoolean mInitDexRes = new AtomicBoolean(false);
	private String mDexName;
	private String mDexPackageName;
	private Resources mDexRes;

	public EnvDexResourcesWrapper(Context context, Resources res) {
		super(context, res);
		this.mContext = context;
	}

	public void setDexName(String dexName) {
		this.mDexName = dexName;
	}

	private synchronized void ensureDexRes(Context context) {
		if (!TextUtils.isEmpty(mDexName) && mInitDexRes.compareAndSet(false, true)) {
			mDexRes = EnvDexResourcesManager.getInstance().getTopLevelResources(context, mDexName);
			mDexPackageName = EnvDexResourcesManager.getInstance().getDexPackageName(context, mDexName);
		}
	}
	
	/**
	 * 映射同名同类型资源
	 * 
	 * 皮肤插件包中并不包含所有资源，这导致 R 文件上的资源 id 无法一一对应。
	 * 所以这里需要做一次资源映射
	 */
	private EnvRes mappingEnvRes(int id) {
		EnvRes mapping = null;
		if (mDexRes != null) {
			String packageName = mDexRes.getResourcePackageName(id);
			if (TextUtils.equals(mDexPackageName, packageName)) {
				String typeName = mDexRes.getResourceTypeName(id);
				String entryName = mDexRes.getResourceEntryName(id);
				final int mappingid = super.getIdentifier(entryName, typeName, packageName);
				mapping = new EnvRes(mappingid);
			}
		}
		return mapping;
	}

	@Override
	public int getIdentifier(String name, String defType, String defPackage) {
		return TextUtils.equals(mDexPackageName, defPackage) ? mDexRes.getIdentifier(name, defType, defPackage) : super.getIdentifier(name, defType, defPackage);
	}

	@Override
	public String getResourceName(int resid) throws NotFoundException {
		ensureDexRes(mContext);
		EnvRes mapping = mappingEnvRes(resid);
		if (mapping != null && mapping.isValid()) {
			try {
				return super.getResourceName(mapping.getResid());
			} catch (NotFoundException e) {
			}
		}
		return mDexRes != null ? mDexRes.getResourceName(resid) : super.getResourceName(resid);
	}

	@Override
	public String getResourcePackageName(int resid) throws NotFoundException {
		ensureDexRes(mContext);
		EnvRes mapping = mappingEnvRes(resid);
		if (mapping != null && mapping.isValid()) {
			try {
				return super.getResourcePackageName(mapping.getResid());
			} catch (NotFoundException e) {
			}
		}
		return mDexRes != null ? mDexRes.getResourcePackageName(resid) : super.getResourcePackageName(resid);
	}

	@Override
	public String getResourceTypeName(int resid) throws NotFoundException {
		ensureDexRes(mContext);
		EnvRes mapping = mappingEnvRes(resid);
		if (mapping != null && mapping.isValid()) {
			try {
				return super.getResourceTypeName(mapping.getResid());
			} catch (NotFoundException e) {
			}
		}
		return mDexRes != null ? mDexRes.getResourceTypeName(resid) : super.getResourceTypeName(resid);
	}

	@Override
	public String getResourceEntryName(int resid) throws NotFoundException {
		ensureDexRes(mContext);
		EnvRes mapping = mappingEnvRes(resid);
		if (mapping != null && mapping.isValid()) {
			try {
				return super.getResourceEntryName(mapping.getResid());
			} catch (NotFoundException e) {
			}
		}
		return mDexRes != null ? mDexRes.getResourceEntryName(resid) : super.getResourceEntryName(resid);
	}

	@Override
	public Drawable getDrawable(int id) throws NotFoundException {
		ensureDexRes(mContext);
		EnvRes mapping = mappingEnvRes(id);
		if (mapping != null && mapping.isValid()) {
			try {
				return super.getDrawable(mapping.getResid());
			} catch (NotFoundException e) {
			}
		}
		return mDexRes != null ? mDexRes.getDrawable(id) : super.getDrawable(id);
	}

	@Override
	public int getColor(int id) throws NotFoundException {
		ensureDexRes(mContext);
		EnvRes mapping = mappingEnvRes(id);
		if (mapping != null && mapping.isValid()) {
			try {
				return super.getColor(mapping.getResid());
			} catch (NotFoundException e) {
			}
		}
		return mDexRes != null ? mDexRes.getColor(id) : super.getColor(id);
	}

	@Override
	public ColorStateList getColorStateList(int id) throws NotFoundException {
		ensureDexRes(mContext);
		EnvRes mapping = mappingEnvRes(id);
		if (mapping != null && mapping.isValid()) {
			try {
				return super.getColorStateList(mapping.getResid());
			} catch (NotFoundException e) {
			}
		}
		return mDexRes != null ? mDexRes.getColorStateList(id) : super.getColorStateList(id);
	}

	@Override
	public CharSequence getText(int id) throws NotFoundException {
		ensureDexRes(mContext);
		EnvRes mapping = mappingEnvRes(id);
		if (mapping != null && mapping.isValid()) {
			try {
				return super.getText(mapping.getResid());
			} catch (NotFoundException e) {
			}
		}
		return mDexRes != null ? mDexRes.getText(id) : super.getText(id);
	}

	@Override
	public CharSequence getQuantityText(int id, int quantity) throws NotFoundException {
		ensureDexRes(mContext);
		EnvRes mapping = mappingEnvRes(id);
		if (mapping != null && mapping.isValid()) {
			try {
				return super.getQuantityText(mapping.getResid(), quantity);
			} catch (NotFoundException e) {
			}
		}
		return mDexRes != null ? mDexRes.getQuantityText(id, quantity) : super.getQuantityText(id, quantity);
	}

	@Override
	public CharSequence getText(int id, CharSequence def) {
		ensureDexRes(mContext);
		EnvRes mapping = mappingEnvRes(id);
		if (mapping != null && mapping.isValid()) {
			try {
				return super.getText(mapping.getResid(), def);
			} catch (NotFoundException e) {
			}
		}
		return mDexRes != null ? mDexRes.getText(id, def) : super.getText(id, def);
	}

	@Override
	public CharSequence[] getTextArray(int id) throws NotFoundException {
		ensureDexRes(mContext);
		EnvRes mapping = mappingEnvRes(id);
		if (mapping != null && mapping.isValid()) {
			try {
				return super.getTextArray(mapping.getResid());
			} catch (NotFoundException e) {
			}
		}
		return mDexRes != null ? mDexRes.getTextArray(id) : super.getTextArray(id);
	}

	@Override
	public String[] getStringArray(int id) throws NotFoundException {
		ensureDexRes(mContext);
		EnvRes mapping = mappingEnvRes(id);
		if (mapping != null && mapping.isValid()) {
			try {
				return super.getStringArray(mapping.getResid());
			} catch (NotFoundException e) {
			}
		}
		return mDexRes != null ? mDexRes.getStringArray(id) : super.getStringArray(id);
	}

	@Override
	public int[] getIntArray(int id) throws NotFoundException {
		ensureDexRes(mContext);
		EnvRes mapping = mappingEnvRes(id);
		if (mapping != null && mapping.isValid()) {
			try {
				return super.getIntArray(mapping.getResid());
			} catch (NotFoundException e) {
			}
		}
		return mDexRes != null ? mDexRes.getIntArray(id) : super.getIntArray(id);
	}

	@Override
	public float getDimension(int id) throws NotFoundException {
		ensureDexRes(mContext);
		EnvRes mapping = mappingEnvRes(id);
		if (mapping != null && mapping.isValid()) {
			try {
				return super.getDimension(mapping.getResid());
			} catch (NotFoundException e) {
			}
		}
		return mDexRes != null ? mDexRes.getDimension(id) : super.getDimension(id);
	}

	@Override
	public int getDimensionPixelOffset(int id) throws NotFoundException {
		ensureDexRes(mContext);
		EnvRes mapping = mappingEnvRes(id);
		if (mapping != null && mapping.isValid()) {
			try {
				return super.getDimensionPixelOffset(mapping.getResid());
			} catch (NotFoundException e) {
			}
		}
		return mDexRes != null ? mDexRes.getDimensionPixelOffset(id) : super.getDimensionPixelOffset(id);
	}

	@Override
	public int getDimensionPixelSize(int id) throws NotFoundException {
		ensureDexRes(mContext);
		EnvRes mapping = mappingEnvRes(id);
		if (mapping != null && mapping.isValid()) {
			try {
				return super.getDimensionPixelSize(mapping.getResid());
			} catch (NotFoundException e) {
			}
		}
		return mDexRes != null ? mDexRes.getDimensionPixelSize(id) : super.getDimensionPixelSize(id);
	}

	@Override
	public float getFraction(int id, int base, int pbase) {
		ensureDexRes(mContext);
		EnvRes mapping = mappingEnvRes(id);
		if (mapping != null && mapping.isValid()) {
			try {
				return super.getFraction(mapping.getResid(), base, pbase);
			} catch (NotFoundException e) {
			}
		}
		return mDexRes != null ? mDexRes.getFraction(id, base, pbase) : super.getFraction(id, base, pbase);
	}

	@Override
	public boolean getBoolean(int id) throws NotFoundException {
		ensureDexRes(mContext);
		EnvRes mapping = mappingEnvRes(id);
		if (mapping != null && mapping.isValid()) {
			try {
				return super.getBoolean(mapping.getResid());
			} catch (NotFoundException e) {
			}
		}
		return mDexRes != null ? mDexRes.getBoolean(id) : super.getBoolean(id);
	}

	@Override
	public int getInteger(int id) throws NotFoundException {
		ensureDexRes(mContext);
		EnvRes mapping = mappingEnvRes(id);
		if (mapping != null && mapping.isValid()) {
			try {
				return super.getInteger(mapping.getResid());
			} catch (NotFoundException e) {
			}
		}
		return mDexRes != null ? mDexRes.getInteger(id) : super.getInteger(id);
	}

	@Override
	public XmlResourceParser getAnimation(int id) throws NotFoundException {
		ensureDexRes(mContext);
		EnvRes mapping = mappingEnvRes(id);
		if (mapping != null && mapping.isValid()) {
			try {
				return super.getAnimation(mapping.getResid());
			} catch (NotFoundException e) {
			}
		}
		return mDexRes != null ? mDexRes.getAnimation(id) : super.getAnimation(id);
	}

	@Override
	public XmlResourceParser getXml(int id) throws NotFoundException {
		ensureDexRes(mContext);
		EnvRes mapping = mappingEnvRes(id);
		if (mapping != null && mapping.isValid()) {
			try {
				return super.getXml(mapping.getResid());
			} catch (NotFoundException e) {
			}
		}
		return mDexRes != null ? mDexRes.getXml(id) : super.getXml(id);
	}

	@Override
	public TypedArray obtainTypedArray(int id) throws NotFoundException {
		ensureDexRes(mContext);
		EnvRes mapping = mappingEnvRes(id);
		if (mapping != null && mapping.isValid()) {
			try {
				return super.obtainTypedArray(mapping.getResid());
			} catch (NotFoundException e) {
			}
		}
		return mDexRes != null ? mDexRes.obtainTypedArray(id) : super.obtainTypedArray(id);
	}

	@Override
	public InputStream openRawResource(int id, TypedValue value) throws NotFoundException {
		ensureDexRes(mContext);
		EnvRes mapping = mappingEnvRes(id);
		if (mapping != null && mapping.isValid()) {
			try {
				return super.openRawResource(mapping.getResid(), value);
			} catch (NotFoundException e) {
			}
		}
		return mDexRes != null ? mDexRes.openRawResource(id, value) : super.openRawResource(id, value);
	}

	@Override
	public AssetFileDescriptor openRawResourceFd(int id) throws NotFoundException {
		ensureDexRes(mContext);
		EnvRes mapping = mappingEnvRes(id);
		if (mapping != null && mapping.isValid()) {
			try {
				return super.openRawResourceFd(mapping.getResid());
			} catch (NotFoundException e) {
			}
		}
		return mDexRes != null ? mDexRes.openRawResourceFd(id) : super.openRawResourceFd(id);
	}
}
