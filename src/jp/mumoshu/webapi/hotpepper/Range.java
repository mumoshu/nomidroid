package jp.mumoshu.webapi.hotpepper;

import android.net.Uri.Builder;

public class Range {
	private Integer value;
	private String description;
	private Range(int rangeValue, String rangeDescription){
		value = rangeValue;
		description = rangeDescription;
	}
	public static Range THREE_HUNDRED_METERS = new Range(1, "300m");
	public static Range FIVE_HUNDRED_METERS = new Range(2, "500m");
	public static Range ONE_KILOMETERS = new Range(3, "1000m (default)");
	public static Range TWO_KILOMETERS = new Range(4, "2000m");
	public static Range THREE_KILOMETERS = new Range(5, "3000m");
	public static Range DEFAULT = ONE_KILOMETERS;
	public void appendQueryParametersTo(Builder b) {
		b.appendQueryParameter("range", value.toString() + " - " + description);
	}
}
