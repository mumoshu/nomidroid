package jp.mumoshu.http;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.util.Log;

public class Http {
	public static InputStream get(URL url) throws IOException{
		return getInputStream(url, HttpMethod.GET);
	}
	
    private static InputStream getInputStream(URL url, HttpMethod method) throws IOException{
       	InputStream in;
    	HttpURLConnection http;

    	logAndThen(url, method);
    	http = (HttpURLConnection)url.openConnection();
		method.setTo(http);
		http.connect();
		in = http.getInputStream();
		return in;
    }
    
	public static InputStream get(String requestURL) throws MalformedURLException, IOException {
		return get(new URL(requestURL));
	}

	private static URL logAndThen(URL url, HttpMethod method){
		Log.i("jp.mumoshu.http.Http", method.toString() + ": " + url.toString());
		return url;
	}
}
