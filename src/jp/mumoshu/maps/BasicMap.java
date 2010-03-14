package jp.mumoshu.maps;

import android.graphics.drawable.Drawable;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;
import com.google.android.maps.Overlay;
import com.google.android.maps.MapView.ReticleDrawMode;

public class BasicMap {
	private MapView view;
	private MapController controller;
	private MarkerPane markerPane;
	private MyLocationOverlay myLocationOverlay;
	private Drawable icon;


	public BasicMap(MapView mapView){
		init(mapView, null);
	}
	public BasicMap(MapView mapView, Drawable icon){
		init(mapView, icon);
	}
	public void init(MapView mapView, Drawable icon){
		view = mapView;
		controller = mapView.getController();
		setupMapView();
		setupPanes(icon);
	}
	public void setDefaulrMarkerIcon(Drawable icon){
		this.icon = icon;
	}
	public void addOverlay(Overlay o){
		view.getOverlays().add(o);
	}
	public void addMarker(Marker marker){
		markerPane.addOverlay(marker);
	}
	public void removeMarkers(){
		markerPane.removeOverlays();
	}
	public void invalidate(){
		view.invalidate();
	}
	private void setupPanes(Drawable icon) {
        markerPane = new MarkerPane(icon,true);
        addOverlay(markerPane);
        
        myLocationOverlay = new MyLocationOverlay(view.getContext(), view);
        addOverlay(myLocationOverlay);
	}
	private void setupMapView(){
        view.setBuiltInZoomControls(true);
        view.setReticleDrawMode(ReticleDrawMode.DRAW_RETICLE_OVER);
	}
	public void setCenter(GeoPoint point){
		controller.setCenter(point);
	}
	public void setCenter(GeoPoint point, int zoomLevel){
		setCenter(point);
		controller.setZoom(zoomLevel);
	}
	public GeoPoint getCenter(){
		return view.getMapCenter();
	}
	public void animateTo(GeoPoint point) {
		controller.animateTo(point);
	}
	public void disableMyLocation() {
		myLocationOverlay.disableCompass();
		myLocationOverlay.disableMyLocation();
	}
	public boolean enableMyLocation() {
		myLocationOverlay.enableMyLocation();
		return myLocationOverlay.enableCompass();
	}


}
