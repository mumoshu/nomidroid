package jp.mumoshu.json;

import java.io.IOException;
import java.net.URL;

import jp.mumoshu.http.StringHttp;

import org.json.JSONException;
import org.json.JSONObject;

public class JsonHttp {
	public static JsonObject get(URL url) throws IOException, JSONException{
		return new JsonObject(getJSONObject(url));
	}
	public static JSONObject getJSONObject(URL url) throws JSONException, IOException{
		return new JSONObject(StringHttp.get(url));
	}
}
