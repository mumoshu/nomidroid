package jp.mumoshu.app.nomi;

import java.util.ArrayList;
import java.util.List;

import jp.mumoshu.maps.HasPlottables;
import jp.mumoshu.maps.SpotMarker;
import jp.mumoshu.resource.web.DrawableLoader;
import jp.mumoshu.webapi.hotpepper.HotpepperData;
import jp.mumoshu.webapi.hotpepper.Spot;
import android.util.Log;

public class SearchResult implements HasPlottables<NomiSpot> {

	private List<NomiSpot> spots;
	private List<SpotMarker<NomiSpot>> markers;

	public SearchResult(List<NomiSpot> nomiSpots, List<SpotMarker<NomiSpot>> nomiMarkers) {
		setSpots(nomiSpots);
		markers = nomiMarkers;
	}
	
	private static void logSpot(Spot spot){
		Log.i("SearchResult.logSpot", "shopName: " + spot.getName());
	}

	public static SearchResult fromHotpepperData(HotpepperData data) {
		List<Spot> spots = data.getSpots();
		ArrayList<NomiSpot> nomiSpots = new ArrayList<NomiSpot>();
		ArrayList<SpotMarker<NomiSpot>> markers = new ArrayList<SpotMarker<NomiSpot>>();
		for (Spot s : spots){
			NomiSpot n = new NomiSpot(s, DrawableLoader.fromURLOrNull(s.getImageURL()) );
			nomiSpots.add(n);
			markers.add(new SpotMarker<NomiSpot>(n));
			logSpot(s);
		}
		return new SearchResult(nomiSpots, markers);
	}

	@Override
	public List<? extends SpotMarker<NomiSpot>> getSpotMarkers() {
		return markers;
	}

	private void setSpots(List<NomiSpot> spots) {
		this.spots = spots;
	}

	public List<NomiSpot> getSpots() {
		return spots;
	}
}
