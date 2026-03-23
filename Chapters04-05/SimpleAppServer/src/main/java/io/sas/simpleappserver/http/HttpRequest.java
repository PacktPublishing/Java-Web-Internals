package io.sas.simpleappserver.http;

import java.util.HashMap;

public class HttpRequest {
	private String httpMethod;
	private String path;
	private HashMap<String, String> requestParameters;
	private HashMap<String, String> headers;
	private String body;
	
	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public HttpRequest() {
		super();
		this.requestParameters = new HashMap<String, String>();
		this.headers = new HashMap<String, String>();		
	}
 
	public String getHttpMethod() {
		return httpMethod;
	}

	public void setHttpMethod(String httpMethod) {
		this.httpMethod = httpMethod;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
	public void addParameter(String key, String value) {
		this.requestParameters.put(key.toLowerCase(), value);
	}
	public String getParameter(String key) {
		return this.requestParameters.get(key.toLowerCase());
	}
	
	public String getHeader(String key) {
		return this.headers.get(key.toLowerCase());
	}
	
	public void addHeader(String key, String value) {
		this.headers.put(key.toLowerCase(), value);
	}

	@Override
	public String toString() {
		return "Request [httpMethod=" + httpMethod + ", path=" + path +
	     " Body= "+body + "]";
	}
	

	
}
