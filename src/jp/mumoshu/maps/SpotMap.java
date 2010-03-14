package jp.mumoshu.maps;

import java.util.ArrayList;
import java.util.List;

import android.graphics.drawable.Drawable;

import com.google.android.maps.MapView;

public class SpotMap<T extends Plottable> extends BasicMap implements TapListener<T>{

	public SpotMap(MapView mapView) {
		super(mapView);
	}
	public SpotMap(MapView mapView, Drawable bar) {
		super(mapView, bar);
	}
	private ArrayList<TapListener<T>> tapListeners = null;

	public List<TapListener<T>> getTapListeners(){
		tapListeners = tapListeners == null ? new ArrayList<TapListener<T>>() : tapListeners;
		return tapListeners;
	}
	public void addTapListener(TapListener<T> l){
		getTapListeners().add(l);
	}
	public void plotMarkers(HasPlottables<T> result) {
		removeMarkers();
		
		for (SpotMarker<T> s : result.getSpotMarkers()) {
			s.addTapListener(this);
			addMarker(s);
		}
		invalidate();
	}
	@Override
	public void onTap(T source) {
		notifyTap(source);
	}
	private void notifyTap(T source) {
		for (TapListener<T> s : getTapListeners()){
			s.onTap(source);
		}
	}
}
