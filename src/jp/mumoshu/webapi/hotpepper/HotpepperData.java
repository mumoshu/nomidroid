package jp.mumoshu.webapi.hotpepper;

import java.util.ArrayList;
import java.util.List;

import jp.mumoshu.json.JsonArray;
import jp.mumoshu.json.JsonObject;
import android.util.Log;

public class HotpepperData {

	private ArrayList<Spot> spots;

	public HotpepperData(ArrayList<Spot> spots) {
		this.spots = spots;
	}

	public static HotpepperData fromJsonObject(JsonObject jsonObject) {
		JsonArray shops = jsonObject.getObject("results").getArray("shop");
		ArrayList<Spot> spots;
		
		spots = new ArrayList<Spot>(shops.length());
		logSpots(spots);
		for(int i=0; i<shops.length(); i++) {
			spots.add(SpotFactory.fromJsonObject(shops.getObject(i)));
		}
		return new HotpepperData(spots);
	}
	
	private static void logSpots(List<Spot> spots){
		Log.i("Number of spots", String.valueOf(spots.size()));
	}

	public ArrayList<Spot> getSpots() {
		return spots;
	}

}
