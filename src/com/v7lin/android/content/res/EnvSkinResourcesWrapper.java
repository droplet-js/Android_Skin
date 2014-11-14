package com.v7lin.android.content.res;

import java.io.InputStream;
import java.util.concurrent.atomic.AtomicBoolean;

import com.v7lin.android.content.EnvResourcesWrapper;
import com.v7lin.android.content.EnvRes;

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
 * 优先级：皮肤插件资源 > 应用自带资源
 * 
 * 皮肤插件，不建议换布局，所以这里不重写 {@link Resources#getLayout(int)} 方法
 * 
 * @author v7lin E-mail:v7lin@qq.com
 * @since 2014-11-7 23:26:40
 */
public class EnvSkinResourcesWrapper extends EnvResourcesWrapper {

	private final Context mContext;
	private final String mPackageName;
	private final AtomicBoolean mInitSkinRes = new AtomicBoolean(false);
	private String mSkinName;
	private Resources mSkinRes;

	public EnvSkinResourcesWrapper(Context context, Resources res) {
		super(context, res);
		this.mContext = context;
		this.mPackageName = context.getPackageName();
	}

	private synchronized void ensureSkinRes(Context context) {
		if (mInitSkinRes.compareAndSet(false, true) || EnvSkinResourcesManager.getInstance().isSkinChanged(context, mSkinName)) {
			mSkinName = EnvSkinResourcesManager.getInstance().getCurSkinName(context);
			mSkinRes = EnvSkinResourcesManager.getInstance().getTopLevelResources(context);
			// 这里需要清理一下 Resources 中的缓存，避免影响 Drawable.ConstantState 和 ColorStateList
			EnvSkinResClearCompat.clear(this);
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
		if (mSkinRes != null) {
			String packageName = getResourcePackageName(id);
			if (TextUtils.equals(mPackageName, packageName)) {
				String typeName = getResourceTypeName(id);
				String entryName = getResourceEntryName(id);
				final int mappingid = mSkinRes.getIdentifier(entryName, typeName, packageName);
				mapping = new EnvRes(mappingid);
			}
		}
		return mapping;
	}

	@Override
	public Drawable getDrawable(int id) throws NotFoundException {
		ensureSkinRes(mContext);
		EnvRes mapping = mappingEnvRes(id);
		if (mapping != null && mapping.isValid() && mSkinRes != null) {
			try {
				return mSkinRes.getDrawable(mapping.getResid());
			} catch (NotFoundException e) {
			}
		}
		return super.getDrawable(id);
	}

	@Override
	public int getColor(int id) throws NotFoundException {
		ensureSkinRes(mContext);
		EnvRes mapping = mappingEnvRes(id);
		if (mapping != null && mapping.isValid() && mSkinRes != null) {
			try {
				return mSkinRes.getColor(mapping.getResid());
			} catch (NotFoundException e) {
			}
		}
		return super.getColor(id);
	}

	@Override
	public ColorStateList getColorStateList(int id) throws NotFoundException {
		ensureSkinRes(mContext);
		EnvRes mapping = mappingEnvRes(id);
		if (mapping != null && mapping.isValid() && mSkinRes != null) {
			try {
				return mSkinRes.getColorStateList(mapping.getResid());
			} catch (NotFoundException e) {
			}
		}
		return super.getColorStateList(id);
	}

	@Override
	public CharSequence getText(int id) throws NotFoundException {
		ensureSkinRes(mContext);
		EnvRes mapping = mappingEnvRes(id);
		if (mapping != null && mapping.isValid() && mSkinRes != null) {
			try {
				return mSkinRes.getText(mapping.getResid());
			} catch (NotFoundException e) {
			}
		}
		return super.getText(id);
	}

	@Override
	public CharSequence getQuantityText(int id, int quantity) throws NotFoundException {
		ensureSkinRes(mContext);
		EnvRes mapping = mappingEnvRes(id);
		if (mapping != null && mapping.isValid() && mSkinRes != null) {
			try {
				return mSkinRes.getQuantityText(mapping.getResid(), quantity);
			} catch (NotFoundException e) {
			}
		}
		return super.getQuantityText(id, quantity);
	}

	@Override
	public CharSequence getText(int id, CharSequence def) {
		ensureSkinRes(mContext);
		EnvRes mapping = mappingEnvRes(id);
		if (mapping != null && mapping.isValid() && mSkinRes != null) {
			try {
				return mSkinRes.getText(mapping.getResid());
			} catch (NotFoundException e) {
			}
		}
		return super.getText(id, def);
	}

	@Override
	public CharSequence[] getTextArray(int id) throws NotFoundException {
		ensureSkinRes(mContext);
		EnvRes mapping = mappingEnvRes(id);
		if (mapping != null && mapping.isValid() && mSkinRes != null) {
			try {
				return mSkinRes.getTextArray(mapping.getResid());
			} catch (NotFoundException e) {
			}
		}
		return super.getTextArray(id);
	}

	@Override
	public String[] getStringArray(int id) throws NotFoundException {
		ensureSkinRes(mContext);
		EnvRes mapping = mappingEnvRes(id);
		if (mapping != null && mapping.isValid() && mSkinRes != null) {
			try {
				return mSkinRes.getStringArray(mapping.getResid());
			} catch (NotFoundException e) {
			}
		}
		return super.getStringArray(id);
	}

	@Override
	public int[] getIntArray(int id) throws NotFoundException {
		ensureSkinRes(mContext);
		EnvRes mapping = mappingEnvRes(id);
		if (mapping != null && mapping.isValid() && mSkinRes != null) {
			try {
				return mSkinRes.getIntArray(mapping.getResid());
			} catch (NotFoundException e) {
			}
		}
		return super.getIntArray(id);
	}

	@Override
	public float getDimension(int id) throws NotFoundException {
		ensureSkinRes(mContext);
		EnvRes mapping = mappingEnvRes(id);
		if (mapping != null && mapping.isValid() && mSkinRes != null) {
			try {
				return mSkinRes.getDimension(mapping.getResid());
			} catch (NotFoundException e) {
			}
		}
		return super.getDimension(id);
	}

	@Override
	public int getDimensionPixelOffset(int id) throws NotFoundException {
		ensureSkinRes(mContext);
		EnvRes mapping = mappingEnvRes(id);
		if (mapping != null && mapping.isValid() && mSkinRes != null) {
			try {
				return mSkinRes.getDimensionPixelOffset(mapping.getResid());
			} catch (NotFoundException e) {
			}
		}
		return super.getDimensionPixelOffset(id);
	}

	@Override
	public int getDimensionPixelSize(int id) throws NotFoundException {
		ensureSkinRes(mContext);
		EnvRes mapping = mappingEnvRes(id);
		if (mapping != null && mapping.isValid() && mSkinRes != null) {
			try {
				return mSkinRes.getDimensionPixelSize(mapping.getResid());
			} catch (NotFoundException e) {
			}
		}
		return super.getDimensionPixelSize(id);
	}

	@Override
	public float getFraction(int id, int base, int pbase) {
		ensureSkinRes(mContext);
		EnvRes mapping = mappingEnvRes(id);
		if (mapping != null && mapping.isValid() && mSkinRes != null) {
			try {
				return mSkinRes.getFraction(mapping.getResid(), base, pbase);
			} catch (NotFoundException e) {
			}
		}
		return super.getFraction(id, base, pbase);
	}

	@Override
	public boolean getBoolean(int id) throws NotFoundException {
		ensureSkinRes(mContext);
		EnvRes mapping = mappingEnvRes(id);
		if (mapping != null && mapping.isValid() && mSkinRes != null) {
			try {
				return mSkinRes.getBoolean(mapping.getResid());
			} catch (NotFoundException e) {
			}
		}
		return super.getBoolean(id);
	}

	@Override
	public int getInteger(int id) throws NotFoundException {
		ensureSkinRes(mContext);
		EnvRes mapping = mappingEnvRes(id);
		if (mapping != null && mapping.isValid() && mSkinRes != null) {
			try {
				return mSkinRes.getInteger(mapping.getResid());
			} catch (NotFoundException e) {
			}
		}
		return super.getInteger(id);
	}

	@Override
	public XmlResourceParser getAnimation(int id) throws NotFoundException {
		ensureSkinRes(mContext);
		EnvRes mapping = mappingEnvRes(id);
		if (mapping != null && mapping.isValid() && mSkinRes != null) {
			try {
				return mSkinRes.getAnimation(mapping.getResid());
			} catch (NotFoundException e) {
			}
		}
		return super.getAnimation(id);
	}

	@Override
	public XmlResourceParser getXml(int id) throws NotFoundException {
		ensureSkinRes(mContext);
		EnvRes mapping = mappingEnvRes(id);
		if (mapping != null && mapping.isValid() && mSkinRes != null) {
			try {
				return mSkinRes.getXml(mapping.getResid());
			} catch (NotFoundException e) {
			}
		}
		return super.getXml(id);
	}

	@Override
	public TypedArray obtainTypedArray(int id) throws NotFoundException {
		ensureSkinRes(mContext);
		EnvRes mapping = mappingEnvRes(id);
		if (mapping != null && mapping.isValid() && mSkinRes != null) {
			try {
				return mSkinRes.obtainTypedArray(mapping.getResid());
			} catch (NotFoundException e) {
			}
		}
		return super.obtainTypedArray(id);
	}

	@Override
	public InputStream openRawResource(int id, TypedValue value) throws NotFoundException {
		ensureSkinRes(mContext);
		EnvRes mapping = mappingEnvRes(id);
		if (mapping != null && mapping.isValid() && mSkinRes != null) {
			try {
				return mSkinRes.openRawResource(mapping.getResid(), value);
			} catch (NotFoundException e) {
			}
		}
		return super.openRawResource(id, value);
	}

	@Override
	public AssetFileDescriptor openRawResourceFd(int id) throws NotFoundException {
		ensureSkinRes(mContext);
		EnvRes mapping = mappingEnvRes(id);
		if (mapping != null && mapping.isValid() && mSkinRes != null) {
			try {
				return mSkinRes.openRawResourceFd(mapping.getResid());
			} catch (NotFoundException e) {
			}
		}
		return super.openRawResourceFd(id);
	}
}
