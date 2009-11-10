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

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;
import com.mumoshu.maps.MarkerPane;

public class Nomi extends MapActivity implements LocationListener {
	static final int INITIAL_ZOOM_LEVEL = 11;
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
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.main);
        
        this.initialPoint = new GeoPoint(Nomi.INITIAL_LATITUDE, Nomi.INITIAL_LONGITUDE);
        this.initialZoom = Nomi.INITIAL_ZOOM_LEVEL;
        
        this.mapView = (MapView)findViewById(R.id.mapview);
        this.mapController = this.mapView.getController();
        this.mapController.setCenter(this.initialPoint);
        this.mapController.setZoom(this.initialZoom);
        
        // TODO split markerPane for managing the center marker and the other markers independently.
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
		String url = "http://webservice.recruit.co.jp/hotpepper/gourmet/v1/?key=4e134a6779786c91";
		url += "&lat=" + String.valueOf(location.getLatitude());
		url += "&lng=" + String.valueOf(location.getLongitude());
		url += "&range=5&order=41&format=json";
		new HttpGetTask().execute(url);
		this.markerPane.addOverlay(new OverlayItem(point, "title", "snippet"));
	}

	private void showLocation(Location location) {
		setProgressBarIndeterminateVisibility(true); 
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
	
	public void showSpots(String jsonString) {
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
				this.markerPane.addOverlay(new OverlayItem(point, shop.getString("name"), shop.getString("catch")));
				Log.i("shop point", point.toString());
			}
			
		} catch (Exception e) {
			Log.e(this.getClass().toString(), e.getMessage());
			e.printStackTrace();
			Log.e("results:", results.toString());
			return;
		}
		setProgressBarIndeterminateVisibility(false); 
	}
			
	public class HttpGetTask extends AsyncTask<String, Integer, String> {

		@Override
		protected String doInBackground(String... urls) {
			
			DefaultHttpClient http = new DefaultHttpClient(); 
			HttpGet get = new HttpGet();
			HttpResponse res;
			URI uri;
			
			try {
			 uri = new URI(urls[0]);
			} catch( URISyntaxException e) {
				Log.e(this.getClass().toString(), e.getMessage());
				e.printStackTrace();
				return null;
			}
			
			try {
				get.setURI(uri);
				res = http.execute(get);
				int status = res.getStatusLine().getStatusCode();
				if (200 <= status && status <= 304) {
					BufferedReader stream = new BufferedReader(new InputStreamReader(res.getEntity().getContent())); 
					StringBuffer strBuf = new StringBuffer();
					try {
						String line;
						while ((line = stream.readLine()) != null) {
							strBuf.append(line);
						}
					} catch (Exception e) {
						Log.e(this.getClass().toString(), e.getMessage());
						e.printStackTrace();
						return null;
					} finally {
						try {
							stream.close();
						} catch (Exception e) {
							Log.e(this.getClass().toString(), e.getMessage());
							e.printStackTrace();
						}
					}
					return strBuf.toString(); 
				}
			} catch (Exception e) {
				Log.e(this.getClass().toString(), e.getMessage());
				e.printStackTrace();
			}
			
			return null;
		}
		
		@Override
		protected void onPostExecute(String result) {
			showSpots(result);
			
		}

	}
}