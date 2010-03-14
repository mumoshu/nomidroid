package jp.mumoshu.app.nomi;

import jp.mumoshu.maps.Plottable;
import jp.mumoshu.webapi.hotpepper.Spot;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.google.android.maps.GeoPoint;


public class NomiSpot implements Plottable {
	private Drawable image;
	private Spot spot;

	public NomiSpot(Spot s, Drawable image) {
		this.spot = s;
		this.image = image;
	}
	
	public Drawable getImage() {
		return image;
	}

	public Spot getSpot() {
		return spot;
	}

	public void log() {
		Log.i("shop point", getSpot().getPoint().toString());
	}
	@Override
	public String getName() {
		return spot.getName();
	}
	@Override
	public GeoPoint getPoint() {
		return spot.getPoint();
	}
}
