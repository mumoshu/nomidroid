package jp.mumoshu.json;

import org.json.JSONArray;
import org.json.JSONObject;

public class NullJSONArray extends JSONArray {
	private NullJSONArray(){
		;
	}
	@Override
	public String getString(int index){
		return null;
	}
	@Override
	public double getDouble(int index){
		return 0.;
	}
	@Override
	public JSONObject getJSONObject(int index){
		return null;
	}
	@Override
	public int length(){
		return 0;
	}
	private static final JSONArray instance = new NullJSONArray();
	public static JSONArray getInstance(){
		return instance;
	}
}
