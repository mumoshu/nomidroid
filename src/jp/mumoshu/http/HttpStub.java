package jp.mumoshu.http;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class HttpStub implements HttpClient {
	
	private InputStream in;
	private String name;

	public HttpStub(String stubName, InputStream in){
		this.name = stubName;
		this.in = in;
	}

	@Override
	public InputStream getInputStream(URL url, HttpMethod method)
			throws IOException {
		return in;
	}

}
