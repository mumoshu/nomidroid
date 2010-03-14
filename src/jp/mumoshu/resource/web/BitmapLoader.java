package jp.mumoshu.resource.web;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import jp.mumoshu.http.Http;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class BitmapLoader {
	public static Bitmap fromURL(URL url) throws IOException{
		Bitmap bitmap;
		InputStream inputStream;
		
		inputStream = Http.get(url);
		bitmap = BitmapFactory.decodeStream(inputStream);
		inputStream.close();
		return bitmap;
	}
}
