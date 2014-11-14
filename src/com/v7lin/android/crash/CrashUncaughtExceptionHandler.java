package com.v7lin.android.crash;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.Thread.UncaughtExceptionHandler;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.content.Context;
import android.text.TextUtils;

import com.v7lin.android.os.env.PathUtils;

/**
 * 崩溃异常捕获
 * 
 * @author v7lin E-mail:v7lin@qq.com
 * @since 2014-11-7 23:26:40
 */
public class CrashUncaughtExceptionHandler extends UncaughtExceptionHandlerWrapper {

	private Context mContext;

	public CrashUncaughtExceptionHandler(Context context, UncaughtExceptionHandler wrapped) {
		super(wrapped);
		this.mContext = context;
	}

	@Override
	public void uncaughtException(Thread thread, Throwable ex) {
		readAndWriteCrashReport(ex);
		super.uncaughtException(thread, ex);
	}

	private void readAndWriteCrashReport(Throwable ex) {
		String report = new CrashReport().getCrashReport(ex);
		if (!TextUtils.isEmpty(report)) {
			File crashDir = PathUtils.getCrashDir(mContext);

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd", Locale.getDefault());
			String crashFileName = sdf.format(new Date());

			FileOutputStream fos = null;
			try {
				File crashFile = new File(crashDir, crashFileName);
				if (!crashFile.exists() || !crashFile.isFile()) {
					crashFile.createNewFile();
				}

				fos = new FileOutputStream(crashFile, true);
				fos.write(report.getBytes());
				fos.flush();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if (fos != null) {
						fos.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
