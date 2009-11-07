package com.mumoshu.nomi;

import android.os.Bundle;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;
import com.mumoshu.maps.MarkerPane;

public class Nomi extends MapActivity {
	static final int INITIAL_ZOOM_LEVEL = 15;
	static final int INITIAL_LATITUDE = 35455281;
	static final int INITIAL_LONGITUDE = 139629711;
	
	protected MapView mapView;
	protected MapController mapController;
	protected GeoPoint initialPoint;
	protected int initialZoom;
	protected MarkerPane markerPane;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        this.initialPoint = new GeoPoint(Nomi.INITIAL_LATITUDE, Nomi.INITIAL_LONGITUDE);
        this.initialZoom = Nomi.INITIAL_ZOOM_LEVEL;
        
        this.mapView = (MapView)findViewById(R.id.mapview);
        this.mapController = this.mapView.getController();
        this.mapController.setCenter(this.initialPoint);
        this.mapController.setZoom(this.initialZoom);
        
        this.markerPane = new MarkerPane(this.getResources().getDrawable(R.drawable.androidmarker));
        this.mapView.getOverlays().add(this.markerPane);
        this.markerPane.addOverlay(new OverlayItem(this.initialPoint, "title", "snippet"));
    }

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
}