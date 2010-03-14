package jp.mumoshu.json;

import java.net.URL;

import jp.mumoshu.http.URLParser;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class JsonObject {
	JSONObject json;

	public JsonObject(JSONObject source){
		json = source != null ? source : NullJSONObject.getInstance();
	}
	public JsonArray getArray(String key){
		try {
			return new JsonArray(json.getJSONArray(key));
		} catch (JSONException e) {
			onJSONExceptionWithKey(e, key);
			return null;
		}
	}
	public Double getDouble(String key){
		try {
			return new Double(json.getDouble(key));
		} catch (JSONException e) {
			onJSONExceptionWithKey(e, key);
			return null;
		}
	}
	public URL getURL(String key){
		return URLParser.parseOrNull(getString(key));
	}
	public String getString(String key){
		try {
			return json.getString(key);
		} catch (JSONException e) {
			onJSONExceptionWithKey(e, key);
			return null;
		}
	}
	public JSONObject getJSONObject(String key){
		try {
			return json.getJSONObject(key);
		} catch (JSONException e) {
			onJSONExceptionWithKey(e, key);
			return null;
		}
	}
	public JsonObject getObject(String key){
		return new JsonObject(getJSONObject(key));
	}
	private void onJSONExceptionWithKey(JSONException e, String key){
		e.printStackTrace();
		logKeyNotFoundError(key);
	}
	private void logKeyNotFoundError(String key){
		Log.e("jp.mumoshu.json.JsonObject", "key not found: " + (key == null ? "null" : key));
	}
}
