package io.sas.simpleappserver.http;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;

public class HttpResponse {
	private OutputStream out;
	private String date;
	private static final String ENDL = "\r\n";
	private int statusCode;
	private byte[] content;
	private HashMap<String, String> headers;
	
	public static HashMap<Integer, String> textCodes=new HashMap<Integer,String>(){{
		put(200,"Ok");
		put(400,"Bad Request");
		put(404,"Not Found");
		put(403,"Forbidden");
		put(401,"Unauthorized");
		put(405,"Method not Allowed");
		put(500,"Internal Server Error");
	}};

	public HttpResponse(OutputStream out) {
		this.headers = new HashMap<String, String>();
		this.headers.put("Content-Type", "text/html");
		this.out = out;
	}

	public void write(String message)  {
		try {
			out.write((message).getBytes());
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
	public void write(byte[] message)  {
		try {
			out.write(message);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public void setHeader(String key, String value) {
		this.headers.put(key, value);
	}

	public void setContent(String message) {
		this.setContent(message.getBytes());
	}
	
	public void setContent(byte[] content) {
		this.write("HTTP/1.1 200 OK\r\n");
		for (String headerKey: headers.keySet()) {
			this.write(headerKey+":"+headers.get(headerKey)+ENDL);
		}
		this.content = content;
		this.write("Content-Length:" + content.length +ENDL);
		this.write(ENDL);
		this.write(content);
		this.write(ENDL+ENDL);
		this.close();
	}
	
	public void setError(byte[] content, Integer code) {
		this.write("HTTP/1.1 "+code+ " "+textCodes.get(code)+"\r\n");
		for (String headerKey: headers.keySet()) {
			this.write(headerKey+":"+headers.get(headerKey)+ENDL);
		}
		this.content = content;
		this.write("Content-Length:" + content.length +ENDL);
		this.write(ENDL);
		this.write(content);
		this.write(ENDL+ENDL);
		this.close();
	}
	
	
	public void close() {
		try
		{
			out.flush();
			out.close();
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
	}

}
