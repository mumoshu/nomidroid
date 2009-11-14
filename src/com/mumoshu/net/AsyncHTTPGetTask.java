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

import android.os.AsyncTask;
import android.util.Log;

import com.mumoshu.patterns.Callbackable;
import com.mumoshu.patterns.Parser;

/**
 * @author mumoshu
 * AsyncTask's 2nd parameter:Integer is not used.
 */
public class AsyncHTTPGetTask<Result> extends
		AsyncTask<String, Integer, Result> {
	
	private Callbackable<Result> callbackable;
	private Parser<Result> parser;

	public AsyncHTTPGetTask(Parser<Result> parser, Callbackable<Result> callbackable_) {
		this.callbackable = callbackable_;
		this.parser = parser;
	}

	/* (non-Javadoc)
	 * @see android.os.AsyncTask#doInBackground(Params[])
	 */
	@Override
	protected Result doInBackground(String... urls) {
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
				Log.d("Parser", "start");
				Result result = parser.parse(strBuf.toString());
				Log.d("Parser", "finish");
				return result;
			}
		} catch (Exception e) {
			Log.e(this.getClass().toString(), e.getMessage());
			e.printStackTrace();
		}
		
		Log.i(this.getClass().getName(), "doInBackground:finish");
		
		return null;
	}
	
	@Override
	protected void onPostExecute(Result result) {
		this.callbackable.callback(result);
	}

}
