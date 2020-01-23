package com.rdc.ascratechassignment.model.commons;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayOutputStream;

public class Utility
{

	public static String BitMapToString(Bitmap bitmap)
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
		byte[] b = baos.toByteArray();
		String temp = Base64.encodeToString(b, Base64.DEFAULT);
		return temp;
	}

	public static Bitmap StringToBitMap(String encodedString)
	{
		try
		{
			byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
			Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
			return bitmap;
		}
		catch (Exception e)
		{
			e.getMessage();
			return null;
		}
	}

	public static byte[] imageinBytes(Bitmap imageBitmap)
	{
		byte imageInByte[] = null;
		try
		{
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
			imageInByte = stream.toByteArray();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			Log.e("Log--> ", "error :" + e);
		}
		return imageInByte;
	}
}
