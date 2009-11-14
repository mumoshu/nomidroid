/**
 * 
 */
package com.mumoshu.net;

import org.json.JSONObject;

import com.mumoshu.parsers.JSONParser;
import com.mumoshu.patterns.Callbackable;

/**
 * @author mumoshu
 *
 */
public class GET {
	private static JSONParser jsonParser = new JSONParser();
	
	public static void json(String url, Callbackable<JSONObject> observer_) {
		final Callbackable<JSONObject> observer = observer_;
		/* TODO catch exceptions in the json parser here */
		new AsyncHTTPGetTask<JSONObject>(jsonParser, new Callbackable<JSONObject>() {
			@Override
			public void callback(JSONObject data) {
				observer.callback(data);
			}
		}).execute(url);
	}

}
