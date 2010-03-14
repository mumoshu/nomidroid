package jp.mumoshu.webapi.hotpepper;

import android.net.Uri.Builder;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;

public class Point {
	private Double lat;
	private Double lng;
	public Point(double lat, double lng){
		this.lat = lat;
		this.lng = lng;
	}
	public static Point fromGeoPoint(GeoPoint p){
		double lat = (double)p.getLatitudeE6() / 1E6;
		double lng = (double)p.getLongitudeE6() / 1E6;
		return new Point(lat, lng);
	}
	public static Point fromMapView(MapView m){
		return fromGeoPoint(m.getMapCenter());
	}
	public void appendQueryParametersTo(Builder b) {
		b.appendQueryParameter("lat", lat.toString());
		b.appendQueryParameter("lng", lng.toString());
	}
}
