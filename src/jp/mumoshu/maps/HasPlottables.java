package jp.mumoshu.maps;

import java.util.List;

public interface HasPlottables<T extends Plottable> {
	public List<? extends SpotMarker<T>> getSpotMarkers();
}
