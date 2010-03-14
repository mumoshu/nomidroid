package jp.mumoshu.webapi.hotpepper;

import jp.mumoshu.maps.BasicMap;
import android.net.Uri;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;

public class HotpepperCondition {
	private static final int DEFAULT_PAGESIZE = 30;
	private Point point;
	private Range range = Range.DEFAULT;
	private Page page = Page.first(DEFAULT_PAGESIZE);	
	private Integer order = 41;
	private final static String format = "json";

	public HotpepperCondition(Point p){
		this.point = p;
	}
	
	public HotpepperCondition nextPage(){
		return this.page(page.next());
	}
	
	public HotpepperCondition page(Page p){
		HotpepperCondition c = new HotpepperCondition(point);
		c.page = p;
		return c;
	}
	
	public void appendQueryParametersTo(Uri.Builder b){
		this.point.appendQueryParametersTo(b);
		this.range.appendQueryParametersTo(b);
		this.page.appendQueryParametersTo(b);
		b.appendQueryParameter("order", order.toString());
		b.appendQueryParameter("format", format);
	}
	
	public static HotpepperCondition fromGeoPoint(GeoPoint p){
		return new HotpepperCondition(Point.fromGeoPoint(p));
	}

	public static HotpepperCondition fromMapView(MapView m){
		return fromGeoPoint(m.getMapCenter());
	}
	
	public static HotpepperCondition fromMap(BasicMap m){
		return fromGeoPoint(m.getCenter());
	}
}
