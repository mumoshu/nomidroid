package jp.mumoshu.webapi.hotpepper;

import android.net.Uri.Builder;

public class Page {
	private Integer pageSize;
	private Integer pageNumber;
	public Page(int pageNumber, int pageSize) {
		this.pageNumber = pageNumber;
		this.pageSize = pageSize;
	}
	public static Page first(int pageSize){
		return new Page(1, pageSize);
	}
	public void appendQueryParametersTo(Builder b) {
		b.appendQueryParameter("count", pageSize.toString());
		b.appendQueryParameter("start", getStart().toString());
	}
	private Integer getStart(){
		return (pageNumber - 1) * pageSize + 1;
	}
	public Page next() {
		return new Page(pageNumber + 1, pageSize);
	}
}
