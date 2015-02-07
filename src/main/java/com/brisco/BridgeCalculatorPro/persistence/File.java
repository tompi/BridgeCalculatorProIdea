package com.brisco.BridgeCalculatorPro.persistence;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.net.Uri;
import android.os.Environment;

public class File {
	public static String Encoding = "utf-8";

	public static Uri SaveStringBuilderToFile(StringBuilder content,
			String fileName) {
		java.io.File sdDir = Environment.getExternalStorageDirectory();
		java.io.File file = new java.io.File(sdDir, fileName);
		// File file = get
		FileOutputStream out;
		try {
			out = new FileOutputStream(file);
			try {
				out.write(content.toString().getBytes(Encoding));
				out.flush();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		return Uri.fromFile(file);
	}

}
