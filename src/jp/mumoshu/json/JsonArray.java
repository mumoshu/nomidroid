package jp.mumoshu.json;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class JsonArray {
	JSONArray json;

	public JsonArray(JSONArray jsonArray) {
		json = jsonArray != null ? jsonArray : NullJSONArray.getInstance();
	}
	public int length(){
		return json.length();
	}
	public JSONObject getJSONObject(int index){
		JSONObject obj;
		try {
			obj = json.getJSONObject(index);
		} catch (JSONException e) {
			e.printStackTrace();
			logIndexOutOfRangeError(index);
			obj = null;
		}
		return obj;
	}
	public JsonObject getObject(int index){
		return new JsonObject(getJSONObject(index));
	}
	private void logIndexOutOfRangeError(int index){
		Log.e("jp.mumoshu.json.JsonArray", "index out of range: " + String.valueOf(index));
	}
}
