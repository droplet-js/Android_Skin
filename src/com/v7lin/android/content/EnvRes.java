package com.v7lin.android.content;

/**
 * 
 * 
 * @author v7lin E-mail:v7lin@qq.com
 * @since 2014-11-7 23:26:40
 */
public class EnvRes {
	
	private final int resid;
	
	public EnvRes(int resid) {
		super();
		this.resid = resid;
	}

	public int getResid() {
		return resid;
	}

	/**
	 * 判断资源映射结果是否有效
	 */
	public boolean isValid() {
		return resid > 0;
	}
}
