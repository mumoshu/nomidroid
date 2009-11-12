/**
 * 
 */
package com.mumoshu.net;

import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;

import com.mumoshu.patterns.Callbackable;

/**
 * @author mumoshu
 *
 */
public class GET {
	public static void json(String url, Callbackable<JSONObject> observer_) {
		final Callbackable<JSONObject> observer = observer_;
		AsyncHTTPGetTask.string(url, new Callbackable<String>() {
			@Override
			public void callback(String data) {
				new AsyncTask<String, Integer, JSONObject>() {
					@Override
					protected JSONObject doInBackground(String... params) {
						JSONObject json = null;
						String data = params[0];
						try {
							json = new JSONObject(data);
						} catch (Exception e) {
							Log.e(this.getClass().toString(), e.getMessage());
							e.printStackTrace();
							Log.e(this.getClass().getName(), "json: " + data);
							return null;
						}
						return json;
					}
					protected void onPostExecute(JSONObject json) {
						observer.callback(json);						
					}
				}.execute(data);
			}
		});
	}

}
