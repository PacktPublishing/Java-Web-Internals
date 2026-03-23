package io.sas.simpleappserver.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;

import io.ee.serweja.http.IsiHttpRequest;
import io.ee.serweja.http.IsiHttpResponse;
import io.ee.serweja.http.IsiHttpServlet;
import io.isiflix.serweja.util.WebConfig;
import io.isiflix.serweja.util.WebLogger;

public class WebServer {

	private ServerSocket serverSocket;
	private String httpMethod;
	private String resourcePath;

	public WebServer(int port) {
		try {
			WebLogger.welcome();
			this.serverSocket = new ServerSocket(port);
			WebLogger.log("SerWeJa Started on port " + port);
			WebLogger.log("Waiting for connections...");
		} catch (IOException ex) {
			WebLogger.log("Could not initialize SerWeJa on Port " + port);
			return;
		}

		while (true) {
			try {
				Socket socket = serverSocket.accept(); // aqui eu aceito trocar dados com o cliente
				handleRequest(socket);

			} catch (IOException ex) {
				WebLogger.log("Couldn't handle client request");
			}
		}
	}

	public WebServer() {
		this(80);
	}

	private void handleRequest(Socket socket) {
		try {
			IsiHttpRequest request = new IsiHttpRequest();
			InputStreamReader inReader = new InputStreamReader(socket.getInputStream());
			BufferedReader br = new BufferedReader(inReader);
			String line;

			do {
				line = br.readLine();
				if (line == null || line.isBlank()) {
					break;
				}
				if (line != null && (line.startsWith("GET") || line.startsWith("POST"))) {
					httpMethod = line.substring(0, line.indexOf(" "));
					resourcePath = line.substring(line.indexOf(" ") + 1, line.lastIndexOf(" "));

					request.setHttpMethod(httpMethod);
					int paramDelimiter = resourcePath.indexOf('?') == -1 ? resourcePath.length()
							: resourcePath.indexOf('?');
					String resourceFile = resourcePath.substring(0, paramDelimiter);
					request.setPath(resourceFile);
					if (resourcePath.indexOf('?') > 0) {
						String parameterListStr = resourcePath.substring(paramDelimiter + 1);
						WebLogger.log("Parameter List=" + parameterListStr);
						String paramList[] = parameterListStr.split("&");
						for (String paramTuple : paramList) {
							String keyValue[] = paramTuple.split("=");
							request.addParameter(keyValue[0], keyValue[1]);
						}
					}

				} else if (line != null && !line.isBlank()) {
					String headers[] = line.split(":");
					request.addHeader(headers[0], headers[1]);
				}
			} while (true);// && !line.isBlank());

			if (request.getHttpMethod() != null && request.getHttpMethod().equals("POST")) {

				WebLogger.log("Reading " + request.getHeader("Content-Length") + " bytes of "
						+ request.getHeader("Content-type"));

				int len = Integer.parseInt(request.getHeader("Content-length").trim());
				char buf[] = new char[len];
				br.read(buf);
				request.setBody(new String(buf));
			}
			WebLogger.log(request.toString());
			handleOutput(socket, request, new IsiHttpResponse(socket.getOutputStream()));
			socket.close();
		} catch (IOException ex) {
			WebLogger.log("Error on Handle request");
		}
	}

	private void handleOutput(Socket socket, IsiHttpRequest request, IsiHttpResponse response) {

		String completePath = WebConfig.DOCUMENT_FOLDER+WebConfig.DOC_ROOT + request.getPath();
		WebLogger.log(completePath);
		IsiHttpServlet servlet;

		try {

			if (Files.exists(Paths.get(completePath))) {
				byte[] content = Files.readAllBytes(Paths.get(completePath));

				String extension = completePath.substring(completePath.lastIndexOf(".") + 1);
				response.write("HTTP/1.1 200 " + WebConfig.textCodes.get(200) + "\r\n");
				response.setHeader("Content-Type", WebConfig.content.get(extension));
				response.write("Content-Type: " + WebConfig.content.get(extension) + "\r\n");

				// response.write("Date:" +LocalDate.now().toString()+"\r\n");
				response.setContent(content);
				response.close();
			
			}
			else if ( (servlet = ServletFinder.findServlet(request.getPath())) != null) {
				//IsiHttpServlet servlet = ServletFinder.findServlet(request.getPath());
				if (request.getHttpMethod().equals("GET"))
					servlet.doGet(request, response);
				else
					servlet.doPost(request, response);
			}
			else {
				WebLogger.log("404 - Not found");
			}
		} catch (IOException ex) {
			WebLogger.log(ex.getMessage());
		}
	}

}
