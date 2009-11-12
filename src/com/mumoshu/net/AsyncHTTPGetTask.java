/**
 * 
 */
package com.mumoshu.net;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.mumoshu.patterns.Callbackable;

import android.os.AsyncTask;
import android.util.Log;

/**
 * @author mumoshu
 * AsyncTask's 2nd parameter:Integer is not used.
 */
public class AsyncHTTPGetTask extends
		AsyncTask<String, Integer, String> {
	
	private Callbackable<String> callbackable;
	
	public static void string(String url, Callbackable<String> callbackable_) {
		new AsyncHTTPGetTask(callbackable_).execute(url);
	}
	
	public AsyncHTTPGetTask(Callbackable<String> callbackable_) {
		this.callbackable = callbackable_;
	}

	/* (non-Javadoc)
	 * @see android.os.AsyncTask#doInBackground(Params[])
	 */
	@Override
	protected String doInBackground(String... urls) {
		Log.i(this.getClass().getName(), "doInBackground:start");
		Log.i(this.getClass().getName(), "doInBackground:get " + urls[0]);
		
		DefaultHttpClient http = new DefaultHttpClient(); 
		HttpGet get = new HttpGet();
		HttpResponse res;
		URI uri;
		
		try {
		 uri = new URI(urls[0]);
		} catch( URISyntaxException e) {
			Log.e(this.getClass().toString(), e.getMessage());
			e.printStackTrace();
			return null;
		}
		
		try {
			get.setURI(uri);
			res = http.execute(get);
			int status = res.getStatusLine().getStatusCode();
			if (200 <= status && status <= 304) {
				BufferedReader stream = new BufferedReader(new InputStreamReader(res.getEntity().getContent())); 
				StringBuffer strBuf = new StringBuffer();
				try {
					String line;
					while ((line = stream.readLine()) != null) {
						strBuf.append(line);
					}
				} catch (Exception e) {
					Log.e(this.getClass().toString(), e.getMessage());
					e.printStackTrace();
					return null;
				} finally {
					try {
						stream.close();
					} catch (Exception e) {
						Log.e(this.getClass().toString(), e.getMessage());
						e.printStackTrace();
					}
				}
				return strBuf.toString(); 
			}
		} catch (Exception e) {
			Log.e(this.getClass().toString(), e.getMessage());
			e.printStackTrace();
		}
		
		Log.i(this.getClass().getName(), "doInBackground:finish");
		
		return null;
	}
	
	@Override
	protected void onPostExecute(String result) {
		this.callbackable.callback(result);
	}

}
