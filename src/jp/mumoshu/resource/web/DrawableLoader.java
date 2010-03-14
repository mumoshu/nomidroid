package jp.mumoshu.resource.web;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import jp.mumoshu.http.Http;
import android.graphics.drawable.Drawable;

public class DrawableLoader {
	public static Drawable fromURL(String urlString) throws IOException{
		return fromURL(new URL(urlString));
	}
	public static Drawable fromURLOrNull(String urlString){
		try {
			return fromURL(urlString);
		} catch (IOException e) {
			return null;
		}
	}
    public static Drawable fromURL(URL url) throws IOException{
    	return fromInputStream(Http.get(url));
    }
	public static Drawable fromInputStream(InputStream in) {
		Drawable d;
		d = Drawable.createFromStream(in, "www");
    	d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicWidth());
    	return d;
	}
}
