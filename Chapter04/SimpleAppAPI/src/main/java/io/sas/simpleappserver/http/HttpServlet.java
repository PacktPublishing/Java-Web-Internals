package io.sas.simpleappserver.http;

public class HttpServlet {

	public void doGet(HttpRequest request, HttpResponse response) {
		response.setHeader("Content-Type", "text/plain");
		String responseSTR = "GET: "+request.getPath();
		response.setContent(responseSTR.getBytes());
	}
	public void doPost(HttpRequest request, HttpResponse response) {
		response.setHeader("Content-Type", "text/plain");
		String responseSTR = "POST: "+request.getPath();
		response.setContent(responseSTR.getBytes());
	}
}
