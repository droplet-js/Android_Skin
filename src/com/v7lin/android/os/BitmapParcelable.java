package com.v7lin.android.os;

import java.io.ByteArrayOutputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * 
 * 
 * @author v7lin E-mail:v7lin@qq.com
 * @since 2014-11-13 19:33:10
 */
public class BitmapParcelable implements Parcelable {

	private Bitmap bitmap;

	public BitmapParcelable(Bitmap bitmap) {
		super();
		this.bitmap = bitmap;
	}

	BitmapParcelable(Parcel source) {
		readFromParcel(source);
	}

	private void readFromParcel(Parcel source) {
		int length = source.readInt();
		byte[] byteArray = new byte[length];
		source.readByteArray(byteArray);
		bitmap = BitmapFactory.decodeByteArray(byteArray, 0, length);
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.PNG, 100, os);
		byte[] byteArray = os.toByteArray();
		int length = byteArray.length;
		dest.writeInt(length);
		dest.writeByteArray(byteArray);
	}

	public Bitmap getBitmap() {
		return bitmap;
	}

	public static final Parcelable.Creator<BitmapParcelable> CREATOR = new Parcelable.Creator<BitmapParcelable>() {

		@Override
		public BitmapParcelable createFromParcel(Parcel source) {
			return new BitmapParcelable(source);
		}

		@Override
		public BitmapParcelable[] newArray(int size) {
			return new BitmapParcelable[size];
		}
	};
}
