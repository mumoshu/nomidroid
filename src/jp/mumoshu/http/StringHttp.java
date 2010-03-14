package jp.mumoshu.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import android.util.Log;

public class StringHttp {
	public static String get(URL url) throws IOException{
		InputStream stream = Http.get(url);
		String str = getStringFrom(stream);
		return str;
	}
	
	private static String getStringFrom(InputStream inputStream){
		BufferedReader stream = new BufferedReader(new InputStreamReader(inputStream)); 
		StringBuffer strBuf = new StringBuffer();
		try {
			String line;
			while ((line = stream.readLine()) != null) {
				strBuf.append(line);
			}
		} catch (Exception e) {
			logError(e);
			return null;
		} finally {
			try {
				stream.close();
			} catch (Exception e) {
				logError(e);
			}
		}
		return strBuf.toString();
	}
	
	private static void logError(Exception e){
		Log.e("HttpString.getStringFrom", e.getMessage());
		e.printStackTrace();
	}
}
