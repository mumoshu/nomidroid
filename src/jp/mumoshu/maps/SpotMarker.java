/**
 * 
 */
package jp.mumoshu.maps;

import java.util.ArrayList;

/**
 * @author mumoshu
 *
 */
public class SpotMarker<T extends Plottable> extends Marker {

	private ArrayList<TapListener<T>> tapListeners = new ArrayList<TapListener<T>>();
	private T spot;

	/**
	 * @param point
	 * @param title
	 * @param snippet
	 */
	public SpotMarker(T s) {
		super(s.getPoint(), s.getName(), s.getName());
		spot = s;
	}
	
	@Override
	public void onTap() {
		for(TapListener<T> listener : tapListeners) {
			listener.onTap(spot);
		}
	}

	public void addTapListener(TapListener<T> listener) {
		tapListeners.add(listener);
	}
}
