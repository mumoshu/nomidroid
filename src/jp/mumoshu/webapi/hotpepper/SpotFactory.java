package jp.mumoshu.webapi.hotpepper;

import jp.mumoshu.json.JsonObject;

import com.google.android.maps.GeoPoint;

public class SpotFactory {
	public static Spot fromJsonObject(JsonObject obj){
		return new Spot(
					obj.getString("name"),
					obj.getString("open"),
					GenreFactory.fromJsonObject(obj.getJSONObject("genre")),
					obj.getObject("photo").getObject("mobile").getString("s"),
					obj.getString("ktai_coupon"),
					getGeoPoint(obj),
					obj.getObject("urls").getURL("pc")
				);
	}
	private static GeoPoint getGeoPoint(JsonObject extractor){
		int lat = (int)(extractor.getDouble("lat") * 1E6);
		int lng = (int)(extractor.getDouble("lng") * 1E6);
		return new GeoPoint(lat, lng);
	}
}
