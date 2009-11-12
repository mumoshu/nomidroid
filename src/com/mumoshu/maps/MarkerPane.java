/**
 * 
 */
package com.mumoshu.maps;

import java.util.ArrayList;

import android.graphics.drawable.Drawable;

import com.google.android.maps.ItemizedOverlay;

/**
 * @author mumoshu
 *
 */
public class MarkerPane extends ItemizedOverlay<Marker> {
	private ArrayList<Marker> overlays = new ArrayList<Marker>();

	public MarkerPane(Drawable defaultMarker) {
		super(boundCenterBottom(defaultMarker));
		// TODO Auto-generated constructor stub
	}
	
	public void addOverlay(Marker overlay) {
		overlays.add(overlay);
		populate();
	}
	
	public void removeOverlays() {
		overlays.clear();
		populate();
	}

	@Override
	protected Marker createItem(int i) {
		return overlays.get(i);
	}

	@Override
	public int size() {
		return overlays.size();
	}
	
	@Override
	public boolean onTap(int index) {
		Marker marker = createItem(index);
		setFocus(marker);
		marker.onTap();
		return false;
	}
}
