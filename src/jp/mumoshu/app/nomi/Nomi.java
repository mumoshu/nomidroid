package jp.mumoshu.app.nomi;

import java.util.Locale;

import jp.mumoshu.maps.Sight;
import jp.mumoshu.maps.SpotMap;
import jp.mumoshu.maps.TapListener;
import jp.mumoshu.webapi.hotpepper.HotpepperSearch;
import jp.mumoshu.webapi.hotpepper.Spot;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.MenuItem.OnMenuItemClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;

public class Nomi extends MapActivity implements LocationListener, OnMenuItemClickListener, SearchResultHandler, TapListener<NomiSpot> {
	protected LocationManager locationManager;
	protected Geocoder geocoder;
	protected TextView statusText;
	
	protected long minLocationUpdateTime = 1;
	protected float minLocationUpdateDistance = 1.0f;
	
	private static final int SHOP_DETAILS_DIALOG = 0;
	
	private SpotDialog shopDialog;
	private SpotMap<NomiSpot> map;


    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.main);

        Drawable bar = this.getResources().getDrawable(R.drawable.bar);
        map = new SpotMap<NomiSpot>( (MapView)findViewById(R.id.mapview), bar );
        map.addTapListener(this);
        Sight.getSampleData().setTo(map);
        
        statusText = (TextView)findViewById(R.id.status_text);
        locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE); 
        geocoder = new Geocoder(getApplicationContext(), Locale.JAPAN);
        shopDialog = new SpotDialog(this);
        progress = new Progress(this);
    }

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * called when another activity comes in front of the activity
	 * the activity could be killed or stopped(onStop() will be called) afterwards
	 */
	@Override
	protected void onPause() {
		super.onPause();
		this.locationManager.removeUpdates(this);
		
		map.disableMyLocation();
	}

	/**
	 * called when the activity comes to the foreground
	 */
	@Override
	protected void onResume() {
		super.onResume();
		
		// TODO LocationManager.NETWORK_PROVIDERも使う
		
		Location loc = this.locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		if(loc != null) {
			this.onLocationChanged(loc);
		}
		
		this.locationManager.requestLocationUpdates(
				LocationManager.GPS_PROVIDER,
				this.minLocationUpdateTime,
				this.minLocationUpdateDistance,
				this);
		
		
		if(!map.enableMyLocation()) {
			Toast.makeText(getBaseContext(), "コンパスが使えないようです", Toast.LENGTH_SHORT);
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		locationManager.removeUpdates(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuItem item = menu.add("検索");
		item.setIcon(R.drawable.androidmarker);
		item.setOnMenuItemClickListener(this);
		return super.onCreateOptionsMenu(menu);
	}
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreateDialog(int)
	 */
	@Override
	protected Dialog onCreateDialog(int id) {
		return shopDialog;
	}
	
	Progress progress;

	public void searchSpots() {
		/* find and redraw nearby spots using the HotPepper API */
		HotpepperSearch search = HotpepperSearch.fromMap(map,"4e134a6779786c91");
		SearchTask task = new SearchTask(this);
		task.execute(search);
		progress.show();
	}
	
	@Override
	public void onLocationChanged(Location location) {
		Log.i(this.getClass().getName(), "onLocationChanged:start");
		
		/* animate map to the current location, and show address */
		GeoPoint point = new GeoPoint(
				(int)(location.getLatitude() * 1E6),
				(int)(location.getLongitude() * 1E6));
		map.animateTo(point);
		/* TODO should be executed in an asynchronous task? */
		/* this requires 100~200ms to complete as it communicates with google's reverse geocoding service. */
		this.showLocation(location);
		
		//searchSpots(location.getLatitude(), location.getLongitude());
		
		Log.i(this.getClass().getName(), "onLocationChanged:finish");
	}

	/**
	 * Determines the address for the given location, then displays it.
	 * @param location current location, usually passed from LocationManager
	 */
	private void showLocation(Location location) {
		Log.i(this.getClass().getName(), "showLocation:start");
		
		setProgressBarIndeterminateVisibility(true); 
		
		AddressTextBuilder builder = new AddressTextBuilder(this.geocoder);
		this.statusText.setText(builder.fromLocation(location));
		
		setProgressBarIndeterminateVisibility(false); 
		
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
	
	public void showSpots(jp.mumoshu.app.nomi.SearchResult result) {
		Log.i(this.getClass().getName(), "showSpots:start");
		
		map.plotMarkers(result);
		Log.i(this.getClass().getName(), "showSpots:finish");
	}
	
	@Override
	public boolean onMenuItemClick(MenuItem item) {
		searchSpots();
		return false;
	}

	@Override
	public void process(jp.mumoshu.app.nomi.SearchResult result) {
		if(progress.isNotCanceled()) {
			showSpots(result);
		} else {
			Log.d("callback","json loaded, but the operation had been canceled.");
		}
		progress.dismiss();
	}

	@Override
	public void onTap(NomiSpot s) {
		Drawable image = s.getImage();
		Spot spot = s.getSpot();
		Log.i(this.getClass().getName(), "onTap");
		Toast.makeText(getBaseContext(), spot.getName(), Toast.LENGTH_SHORT).show();
		shopDialog.setSpot(spot, image);
		showDialog(SHOP_DETAILS_DIALOG);
		Log.i("Toast.makeText",spot.getName());
	}
}