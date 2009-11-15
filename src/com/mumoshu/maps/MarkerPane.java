/**
 * 
 */
package com.mumoshu.maps;

import java.util.ArrayList;

import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.drawable.Drawable;

import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapView;

/**
 * @author mumoshu
 *
 */
public class MarkerPane extends ItemizedOverlay<Marker> {
	private ArrayList<Marker> overlays = new ArrayList<Marker>();
	Drawable defaultMarker;
	boolean transparentFocused;

	public MarkerPane(Drawable defaultMarker, boolean transparentFocused) {
		super(boundCenterBottom(defaultMarker));
		this.defaultMarker = defaultMarker;
		/* draw focused item myself */
		this.transparentFocused = transparentFocused;
	}
	
	/* (non-Javadoc)
	 * @see com.google.android.maps.ItemizedOverlay#draw(android.graphics.Canvas, com.google.android.maps.MapView, boolean)
	 */
	@Override
	public void draw(Canvas canvas, MapView mapView, boolean shadow) {
		if(!transparentFocused) {
			super.draw(canvas, mapView, shadow);
			return;
		}
		int lastFocused = getLastFocusedIndex();
		Point p = new Point();
		Marker m;
		Drawable d;
		for(int i=0; i<size(); i++) {
			if(i == lastFocused) {
				continue;
			}
			m = createItem(i);
			mapView.getProjection().toPixels(m.getPoint(), p);
			d = m.getMarker(0);
			if(d == null) {
				d = defaultMarker;
			}
			d.setAlpha(128);
			if(!shadow) {
				drawAt(canvas, d, p.x, p.y, false);
			} else {
				drawAt(canvas, d, p.x, p.y, true);
			}
		}
		if(lastFocused > -1) {
			m = createItem(lastFocused);
			mapView.getProjection().toPixels(m.getPoint(), p);
			d = m.getMarker(0);
			if(d == null) {
				d = defaultMarker;
			}
			d.setAlpha(255);
			if(!shadow) {
				drawAt(canvas, d, p.x, p.y, false);
			} else {
				drawAt(canvas, d, p.x, p.y, true);
			}
		}
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
