package com.v7lin.android.content.dex;

/**
 * 
 * 
 * @author v7lin E-mail:v7lin@qq.com
 * @since 2014-11-7 23:26:40
 */
public abstract class EnvDexSetup {
	
	public static EnvDexSetup getInstance() {
		return EnvDexSetupHolder.INSTANCE;
	}
	
	private static class EnvDexSetupHolder {
		private static final EnvDexSetup INSTANCE = new EnvDexSetupImpl();
	}
}
