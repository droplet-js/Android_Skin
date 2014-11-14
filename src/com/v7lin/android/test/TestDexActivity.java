package com.v7lin.android.test;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.v7lin.android.R;
import com.v7lin.android.content.dex.EnvDexActivity;

/**
 * 
 * 
 * @author v7lin E-mail:v7lin@qq.com
 * @since 2014-11-7 23:26:40
 */
public class TestDexActivity extends EnvDexActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		View contentView = View.inflate(this, R.layout.layout_dex, null);
		contentView.setBackgroundColor(Color.BLUE);
		setContentView(contentView);
	}
}
