package jp.mumoshu.app.nomi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.util.Log;

public class AddressTextBuilder {
	Geocoder geocoder;
	public AddressTextBuilder(Geocoder geocoder){
		this.geocoder = geocoder;
	}
	public CharSequence fromLocation(Location location){
		StringBuffer buf = new StringBuffer();
		List<Address> list = getAddressesFromLocation(location);
		
		buf.append("åªç›ín: ");
		for (Address addr : list) {
			int last = addr.getMaxAddressLineIndex();
			logAddress(addr);
			for (int i=0; i<=last; i++) {
				String line = addr.getAddressLine(i);
				logAddressLine(i,line);
				buf.append(line);
				buf.append(" ");
			}
			buf.append("\n");
		}
		return buf;
	}
	private void logAddress(Address addr){
		final String tag = "Address";
		/* óXï÷î‘çÜ */
		String postal = addr.getPostalCode();
		/* ìsìπï{åß */
		String adminArea = addr.getAdminArea();
		/* ésãÊí¨ë∫ */
		String locality = addr.getLocality();
		/* í¨ñº */
		String premises = addr.getPremises();
		String subThoroughfare = addr.getSubThoroughfare();
		String subAdminArea = addr.getSubAdminArea();
		/* íö */
		String thoroughfare = addr.getThoroughfare();
		/* î‘ínÇªÇÃÇP */
		/* î‘ínÇªÇÃÇQ */
		String featureName = addr.getFeatureName();
		String subLocality = addr.getSubLocality();
		
		Log.d(tag, "Address");
		Log.d(tag, "PostalCode=" + postal);
		Log.d(tag, "AdminArea=" + adminArea);
		Log.d(tag, "Locality=" + locality);
		Log.d(tag, "Throughfare=" + thoroughfare);
		Log.d(tag, "FeatureName=" + featureName);
		Log.d(tag, "SubLocality=" + subLocality);
		Log.d(tag, "Premises=" + premises);
		Log.d(tag, "SubAdminArea=" + subAdminArea);
		Log.d(tag, "SubThoroughfare=" + subThoroughfare);
	}
	private String getTag(){
		return this.getClass().getName();
	}
	private void logAddressLine(int index, String line){
		Log.d(getTag(), "AddressLine[" + String.valueOf(index) + "]=" + line);
	}
	private List<Address> getAddressesFromLocation(Location location){
		double lat = location.getLatitude();
		double lng = location.getLongitude();
		List<Address> list;
		
		try {
			list = getAddressesFromLatAndLng(lat, lng);
		} catch (IOException e) {
			e.printStackTrace();
			logIOException(location);
			list = new ArrayList<Address>();
		}
		return list;
	}
	private List<Address> getAddressesFromLatAndLng(double lat, double lng) throws IOException{
		List<Address> list;
		
		list = this.geocoder.getFromLocation(lat, lng, 5);
		return list;
	}
	private void logIOException(Location location){
		Log.e(getTag(), "Could not get addresses for " + location.toString());
	}
}
