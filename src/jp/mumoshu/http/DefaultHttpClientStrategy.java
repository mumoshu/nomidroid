package jp.mumoshu.http;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.util.Log;

public class DefaultHttpClientStrategy implements HttpStrategy {
	private URI urlToURI(URL url){
		URI uri;
		
		try {
		 uri = new URI(url.toString());
		} catch( URISyntaxException e) {
			Log.e(this.getClass().toString(), e.getMessage());
			e.printStackTrace();
			return null;
		}
		return uri;
	}
	@Override
	public InputStream getInputStream(URL url, HttpMethod method) throws IOException {
		DefaultHttpClient http = new DefaultHttpClient(); 
		HttpGet get = new HttpGet();
		HttpResponse res;
		URI uri;
		
		uri = urlToURI(url);
		get.setURI(uri);
		res = http.execute(get);
		return res.getEntity().getContent(); 
	}
}
