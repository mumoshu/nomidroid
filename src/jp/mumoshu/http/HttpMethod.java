package jp.mumoshu.http;

import java.net.HttpURLConnection;
import java.net.ProtocolException;

public class HttpMethod {
	String httpMethodName;
	private HttpMethod(String httpMethodName){
		this.httpMethodName = httpMethodName;
	}
	public void setTo(HttpURLConnection http){
		try {
			http.setRequestMethod(httpMethodName);
		} catch (ProtocolException e) {
			e.printStackTrace();
		}
	}
	@Override
	public String toString(){
		return "HttpMethod." + httpMethodName;
	}
	public static final HttpMethod GET = new HttpMethod("GET");
	public static final HttpMethod POST = new HttpMethod("POST");
	public static final HttpMethod PUT = new HttpMethod("PUT");
	public static final HttpMethod DELETE = new HttpMethod("DELETE");
}
