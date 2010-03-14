package jp.mumoshu.http;

import java.net.MalformedURLException;
import java.net.URL;

import android.util.Log;

public class URLParser {
	public static URL parseOrNull(String urlString) {
		try {
			return parse(urlString);
		} catch (MalformedURLException e) {
			e.printStackTrace();
			logMalformedURLException(urlString);
			return null;
		}
	}
	public static URL parse(String urlString) throws MalformedURLException {
		return new URL(urlString);
	}
	private static void logMalformedURLException(String urlString){
		Log.e("jp.mumoshu.http.URLParser", "MalformedURLException: urlString=" + dumpString(urlString) );
	}
	private static String dumpString(String str){
		return (str == null ? "null" : '"' + str + '"');
	}
}
