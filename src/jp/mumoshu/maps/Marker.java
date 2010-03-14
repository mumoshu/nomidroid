package jp.mumoshu.maps;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.OverlayItem;

public abstract class Marker extends OverlayItem {

	public Marker(GeoPoint point, String title, String snippet) {
		super(point, title, snippet);
		// TODO Auto-generated constructor stub
	}
	abstract public void onTap();
}
