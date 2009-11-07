package com.mumoshu.nomi;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;
import com.mumoshu.maps.MarkerPane;

public class Nomi extends MapActivity implements LocationListener {
	static final int INITIAL_ZOOM_LEVEL = 15;
	static final int INITIAL_LATITUDE = 35455281;
	static final int INITIAL_LONGITUDE = 139629711;
	
	protected MapView mapView;
	protected MapController mapController;
	protected GeoPoint initialPoint;
	protected int initialZoom;
	protected MarkerPane markerPane;
	protected LocationManager locationManager;
	protected Geocoder geocoder;
	protected TextView statusText;
	
	protected long minLocationUpdateTime = 1;
	protected float minLocationUpdateDistance = 1.0f;

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
        
        this.statusText = (TextView)findViewById(R.id.status_text);
        this.locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE); 
        this.geocoder = new Geocoder(getApplicationContext(), Locale.JAPAN);
    }

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void onPause() {
		super.onPause();
		this.locationManager.removeUpdates(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		
		// TODO LocationManager.NETWORK_PROVIDER‚àŽg‚¤
		
		Location loc = this.locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		if(loc != null) {
			this.onLocationChanged(loc);
		}
		
		this.locationManager.requestLocationUpdates(
				LocationManager.GPS_PROVIDER,
				this.minLocationUpdateTime,
				this.minLocationUpdateDistance,
				this);
	}

	@Override
	public void onLocationChanged(Location location) {
		GeoPoint point = new GeoPoint(
				(int)(location.getLatitude() * 1E6),
				(int)(location.getLongitude() * 1E6));
		this.mapController.animateTo(point);
		this.showLocation(location);
	}

	private void showLocation(Location location) {
		double lat = location.getLatitude();
		double lng = location.getLongitude();
		StringBuffer buf = new StringBuffer();
		try {
			List<Address> list = this.geocoder.getFromLocation(lat, lng, 1);
			for (Address addr : list) {
				int last = addr.getMaxAddressLineIndex();
				for (int i=0; i<=last; i++) {
					buf.append(addr.getAddressLine(i));
					buf.append(" ");
				}
				buf.append("\n");
			}
		} catch (IOException e) {
			Log.e(this.getClass().toString(), e.toString(), e);
		}
		this.statusText.setText(buf);
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}
}