package com.haitian.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import android.util.Log;

/**
 * @author haitian
 * 
 */
public class LogInfo {
	public static boolean isDebug = true;

	public static void LogOut(String info) {

		if (isDebug && info != null) {
			Log.d("haitian", info);
		}
	}

	public static void logToFile(String info) {
		try {
			String aLogFile = Utils.getSdcardPath() + "/TL_LI.log";
			File iFile = new File(aLogFile);
			if (!iFile.exists()) {
				iFile.createNewFile();
			}
			FileWriter aFileStream = new FileWriter(iFile, true);
			BufferedWriter aWriter = new BufferedWriter(aFileStream);
			aWriter.write("[" + Utils.returnNowTime() + "]" + info);
			aWriter.write("\r\n");
			aWriter.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void LogOut(String string, String string2) {
		// TODO Auto-generated method stub
		if (isDebug && string2 != null) {
			Log.d(string, string2);
		}
	}

}
