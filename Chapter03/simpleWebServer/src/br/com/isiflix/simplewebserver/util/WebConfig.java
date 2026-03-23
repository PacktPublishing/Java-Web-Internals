package br.com.isiflix.simplewebserver.util;

import java.util.HashMap;
import java.util.Map;

public class WebConfig {
	public static final String DOCUMENT_ROOT="/Users/isidro/Documents/mywebserver";
	//public static final String DOCUMENT_ROOT="C:\\Users\\isidro\\Documents\\mywebserver";
	
	
	public static Map<String, String> contents;
	
	public static void setup() {
		contents = new HashMap<String, String>();
		contents.put("html","text/html");
		contents.put("htm","text/html");
		contents.put("jpg","image/jpg");
		contents.put("png","image/png");
		contents.put("jpeg","image/jpeg");
	}
}
