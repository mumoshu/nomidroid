package jp.mumoshu.webapi.hotpepper;

import jp.mumoshu.json.JsonObject;

import org.json.JSONObject;

public class GenreFactory {
	public static Genre fromJsonObject(JSONObject json){
		JsonObject obj;
		String code, description, name;

		obj = new JsonObject(json);
		code = obj.getString("name");
		description = obj.getString("catch");
		name = obj.getString("name");
		return new Genre(code, description, name);
	}
}
