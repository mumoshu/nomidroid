package jp.mumoshu.http;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public interface HttpStrategy {
	InputStream getInputStream(URL url, HttpMethod method) throws IOException;
}
