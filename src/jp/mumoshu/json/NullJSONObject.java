package jp.mumoshu.json;

import org.json.JSONObject;

public class NullJSONObject extends JSONObject {
	private NullJSONObject(){
		;
	}
	public String getString(String key){
		return null;
	}
	public double getDouble(String key){
		return 0.;
	}
	public JSONObject getJSONObject(String key){
		return null;
	}
	private static final JSONObject instance = new NullJSONObject();
	public static JSONObject getInstance(){
		return instance;
	}
}
