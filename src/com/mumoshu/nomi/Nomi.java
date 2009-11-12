package com.mumoshu.nomi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.mumoshu.maps.Marker;
import com.mumoshu.maps.MarkerPane;
import com.mumoshu.maps.TapListener;

public class Nomi extends MapActivity implements LocationListener {
	static final int INITIAL_ZOOM_LEVEL = 16;
	static final int INITIAL_LATITUDE = 35455281;
	static final int INITIAL_LONGITUDE = 139629711;
	
	protected MapView mapView;
	protected MapController mapController;
	protected GeoPoint initialPoint;
	protected int initialZoom;
	protected MarkerPane markerPane;
	protected MarkerPane currentLocationMarkerPane;
	protected LocationManager locationManager;
	protected Geocoder geocoder;
	protected TextView statusText;
	
	protected long minLocationUpdateTime = 1;
	protected float minLocationUpdateDistance = 1.0f;


    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.main);
        
        this.initialPoint = new GeoPoint(Nomi.INITIAL_LATITUDE, Nomi.INITIAL_LONGITUDE);
        this.initialZoom = Nomi.INITIAL_ZOOM_LEVEL;
        
        this.mapView = (MapView)findViewById(R.id.mapview);
        this.mapController = this.mapView.getController();
        this.mapController.setCenter(this.initialPoint);
        this.mapController.setZoom(this.initialZoom);
        
        // TODO split markerPane for managing the center marker and the other markers independently.
        this.currentLocationMarkerPane = new MarkerPane(this.getResources().getDrawable(R.drawable.androidmarker));
        this.markerPane = new MarkerPane(this.getResources().getDrawable(R.drawable.bar));
        this.mapView.getOverlays().add(this.currentLocationMarkerPane);
        this.mapView.getOverlays().add(this.markerPane);
        //this.currentLocationMarkerPane.addOverlay(new OverlayItem(this.initialPoint, "title", "snippet"));
        
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
		
		// TODO LocationManager.NETWORK_PROVIDERÇ‡égÇ§
		
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
		Log.i(this.getClass().getName(), "onLocationChanged:start");
		
		/* animate map to the current location, and show address */
		GeoPoint point = new GeoPoint(
				(int)(location.getLatitude() * 1E6),
				(int)(location.getLongitude() * 1E6));
		this.mapController.animateTo(point);
		/* TODO should be executed in an async task? */
		/* this requires 100~200ms to complete as it communicates with google's reverse geocoding service. */
		this.showLocation(location);
		
		/* find and redraw nearby spots using the HotPepper API */
		String url = "http://webservice.recruit.co.jp/hotpepper/gourmet/v1/?key=4e134a6779786c91";
		url += "&lat=" + String.valueOf(location.getLatitude());
		url += "&lng=" + String.valueOf(location.getLongitude());
		url += "&range=3&order=41&count=30&format=json";
		//new HttpGetTask().execute(url);
		AsyncHTTPGetTask.string(url, new Callbackable<String>() {
			@Override
			public void callback(String data) {
				Log.i(this.getClass().getName(), "callback -> showSpots(jsonString)");
				showSpots(data);
			}
		});
		
		/* redraw the current location marker */
		this.currentLocationMarkerPane.removeOverlays();
		this.currentLocationMarkerPane.addOverlay(new Marker(point, "title", "snippet"));
		
		Log.i(this.getClass().getName(), "onLocationChanged:finish");
	}

	/**
	 * Determines the address for the given location, then displays it.
	 * @param location current location, usually passed from LocationManager
	 */
	private void showLocation(Location location) {
		Log.i(this.getClass().getName(), "showLocation:start");
		
		setProgressBarIndeterminateVisibility(true); 
		double lat = location.getLatitude();
		double lng = location.getLongitude();
		StringBuffer buf = new StringBuffer();
		buf.append("åªç›ín: ");
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
		
		Log.i(this.getClass().getName(), "showLocation:finish");
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
	
	public void showSpots(String jsonString) {
		Log.i(this.getClass().getName(), "showSpots:start");
		
		this.markerPane.removeOverlays();
		
		JSONObject json = null;
		JSONObject results = null;
		JSONArray shopsJSON;
		ArrayList<JSONObject> shops = new ArrayList<JSONObject>();
		try {
			json = new JSONObject(jsonString);
			results = json.getJSONObject("results");
			shopsJSON = results.getJSONArray("shop");
			Log.i("Shops",String.valueOf(shopsJSON.length()));
			for(int i=0; i< shopsJSON.length(); i++) {
				JSONObject shop = shopsJSON.getJSONObject(i);
				shops.add(shop);
				GeoPoint point = new GeoPoint((int)(shop.getDouble("lat") * 1E6), (int)(shop.getDouble("lng") * 1E6));
				final String shopName = shop.getString("name");
				Marker marker = new Marker(point, shop.getString("name"), shop.getString("catch"));
				Log.i(this.getClass().getName(), "shopName: " + shopName);
				marker.addTapListener(new TapListener() {
					@Override
					public void onTap() {
						Log.i(this.getClass().getName(), "onTap");
						Toast.makeText(mapView.getContext(), shopName, Toast.LENGTH_SHORT).show();
						Log.i("Toast.makeText",shopName);
					}
				});
				this.markerPane.addOverlay(marker);
				Log.i("shop point", point.toString());
			}
			
		} catch (Exception e) {
			Log.e(this.getClass().toString(), e.getMessage());
			e.printStackTrace();
			Log.e("results:", results.toString());
			return;
		}
		setProgressBarIndeterminateVisibility(false); 
		
		this.mapView.invalidate();
		
		Log.i(this.getClass().getName(), "showSpots:finish");
	}
}