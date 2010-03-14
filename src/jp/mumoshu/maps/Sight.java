package jp.mumoshu.maps;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;

public class Sight {
	static final int SAMPLE_ZOOM = 16;
	static final int SAMPLE_LATITUDE  =  35455281;
	static final int SAMPLE_LONGITUDE = 139629711;
	private GeoPoint point;
	private int zoom;

	public Sight(GeoPoint point, int zoom){
		this.point = point;
		this.zoom = zoom;
	}
	public static Sight getSampleData(){
		return new Sight(new GeoPoint(SAMPLE_LATITUDE, SAMPLE_LONGITUDE), SAMPLE_ZOOM);
	}
	public void setToMapView(MapView m){
        setToMapController(m.getController());
	}
	public void setTo(BasicMap m){
		m.setCenter(point, zoom);
	}
	public void setToMapController(MapController c){
		c.setCenter(point);
        c.setZoom(zoom);
	}
}
