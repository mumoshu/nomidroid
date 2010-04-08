package jp.mumoshu.http;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.util.Log;

public class Http implements HttpClient {
	public static InputStream get(URL url) throws IOException{
		return instance.getInputStream(url, HttpMethod.GET);
	}
    
	public static InputStream get(String requestURL) throws MalformedURLException, IOException {
		return get(new URL(requestURL));
	}

	private static URL logAndThen(URL url, HttpMethod method){
		Log.i("jp.mumoshu.http.Http", method.toString() + ": " + url.toString());
		return url;
	}

	private static HttpClient instance = new Http();

	public static void setHttpClient(HttpClient client){
		instance = client;
	}

	public InputStream getInputStream(URL url, HttpMethod method) throws IOException{
       	InputStream in;
    	HttpURLConnection http;

    	logAndThen(url, method);
    	http = (HttpURLConnection)url.openConnection();
		method.setTo(http);
		http.connect();
		in = http.getInputStream();
		return in;
	}
}
