/**
 * 
 */
package com.mumoshu.maps;

import java.util.ArrayList;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.OverlayItem;

/**
 * @author mumoshu
 *
 */
public class Marker extends OverlayItem implements TapObservable {
	private ArrayList<TapListener> tapListeners = new ArrayList<TapListener>();

	/**
	 * @param point
	 * @param title
	 * @param snippet
	 */
	public Marker(GeoPoint point, String title, String snippet) {
		super(point, title, snippet);
		// TODO Auto-generated constructor stub
	}
	
	public void onTap() {
		for(TapListener listener : tapListeners) {
			listener.onTap();
		}
	}

	@Override
	public void addTapListener(TapListener listener) {
		tapListeners.add(listener);
	}

}
