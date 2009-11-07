/**
 * 
 */
package com.mumoshu.maps;

import java.util.ArrayList;

import android.graphics.drawable.Drawable;

import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;

/**
 * @author mumoshu
 *
 */
public class MarkerPane extends ItemizedOverlay<OverlayItem> {
	private ArrayList<OverlayItem> overlays = new ArrayList<OverlayItem>();

	public MarkerPane(Drawable defaultMarker) {
		super(boundCenterBottom(defaultMarker));
		// TODO Auto-generated constructor stub
	}
	
	public void addOverlay(OverlayItem overlay) {
		overlays.add(overlay);
		populate();
	}

	@Override
	protected OverlayItem createItem(int i) {
		return overlays.get(i);
	}

	@Override
	public int size() {
		return overlays.size();
	}

}
