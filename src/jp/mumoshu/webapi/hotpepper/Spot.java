package jp.mumoshu.webapi.hotpepper;

import java.net.URL;

import jp.mumoshu.maps.Plottable;

import com.google.android.maps.GeoPoint;

public class Spot implements Plottable {
	Genre genre;
	String imageURL, keitaiCoupon, name, open;
	GeoPoint point;
	URL url;

	public Spot(String name, String open, Genre genre, String imageURL, String coupon, GeoPoint p, URL url){
		this.name = name;
		this.open = open;
		this.genre = genre;
		this.imageURL = imageURL;
		this.keitaiCoupon = coupon;
		this.point = p;
		this.url = url;
	}
	@Override
	public String getName() {
		return name;
	}
	@Override
	public GeoPoint getPoint() {
		return point;
	}
	public Genre getGenre() {
		return genre;
	}
	public String getImageURL() {
		return imageURL;
	}
	public String getKeitaiCoupon() {
		return keitaiCoupon;
	}

	public String getOpen() {
		return open;
	}
	public URL getURL() {
		return url;
	}
}
