package io.simpleweb.framework.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

import com.google.gson.Gson;

import io.simpleweb.framework.datastructures.ControllersInstances;
import io.simpleweb.framework.datastructures.ControllersMap;
import io.simpleweb.framework.datastructures.DependencyInjectionMap;
import io.simpleweb.framework.datastructures.RequestControllerData;
import io.simpleweb.framework.datastructures.ServiceImplementationMap;
import io.simpleweb.framework.util.SimpleLogger;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class SimpleDispatchServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		if (request.getRequestURL().toString().endsWith("/favicon.ico")) {
			return;
		}
		PrintWriter out = new PrintWriter(response.getWriter());
		Gson gson = new Gson();

		String url = request.getRequestURI();
		String httpMethod = request.getMethod().toUpperCase();
		String key = httpMethod + url;
		RequestControllerData data = ControllersMap.values.get(key);
		SimpleLogger.log("SimpleDispatcherServlet", "URL:" + url + "(" + httpMethod + ") - Handler "
				+ data.getControllerClass() + "." + data.getControllerMethod());

		Object controller;
		SimpleLogger.log("DispatcherServlet", "Searching for controller Instance");
		try {
			controller = ControllersInstances.instances.get(data.controllerClass);
			if (controller == null) {
				SimpleLogger.log("DispatcherServlet", "Creating new Controller Instance");
				controller = Class.forName(data.controllerClass).getDeclaredConstructor().newInstance();
				ControllersInstances.instances.put(data.controllerClass, controller);
				
				injectDependencies(controller);
			}

			
			Method controllerMethod = null;
			for (Method method : controller.getClass().getMethods()) {
				if (method.getName().equals(data.controllerMethod)) {
					controllerMethod = method;
					break;
				}
			}
			
			SimpleLogger.log("DispatcherServlet", "Invoking method " + controllerMethod.getName() + " to handle request");

			
			if (controllerMethod.getParameterCount() > 0) {
				SimpleLogger.log("SimpleDispatchServlet", "Method "+controllerMethod.getName()+ " has parameters");
				Object arg;
				Parameter parameter = controllerMethod.getParameters()[0];
				if (parameter.getAnnotations()[0].annotationType().getName().equals("io.simpleweb.framework.annotations.SimpleBody")) {
					String body = readBytesFromRequest(request);
					SimpleLogger.log("","    Found Parameter from request of type "+parameter.getType().getName());
					SimpleLogger.log("","    Parameter content: "+body);
					arg = gson.fromJson(body, parameter.getType());
					
					out.println(gson.toJson(controllerMethod.invoke(controller, arg)));
				}
			}
			else {
				out.write(gson.toJson(controllerMethod.invoke(controller)));
			}
			out.close();

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	
	private String readBytesFromRequest(HttpServletRequest request) throws Exception {
		StringBuilder str = new StringBuilder();
		String line;
		BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
		while ((line = br.readLine()) != null) {
			str.append(line);
			
		}
		return str.toString();
	}
	
	private void injectDependencies(Object client) throws Exception{
		for (Field attr: client.getClass().getDeclaredFields()) {
			String attrType = attr.getType().getName();
			SimpleLogger.log("SimpleDispatcherServlet",  "Injected "+attr.getName() + " Field has type "+attrType);			
			Object serviceImpl;
			if (DependencyInjectionMap.objects.get(attrType) == null) {
				SimpleLogger.log("DependencyInjection", "Couldn't find Instance for "+attrType);
				String implType = ServiceImplementationMap.implementations.get(attrType);
				if (implType != null) {
					SimpleLogger.log("DependencyInjection", "Found Instance for "+implType);
					
					serviceImpl = DependencyInjectionMap.objects.get(implType);
					if (serviceImpl == null) {
						SimpleLogger.log("DependencyInjection", "Injecting new object");
						serviceImpl = Class.forName(implType).getDeclaredConstructor().newInstance();
						DependencyInjectionMap.objects.put(implType, serviceImpl);
					}
					
					attr.setAccessible(true);
					attr.set(client, serviceImpl);
					SimpleLogger.log("DependencyInjection", "Injected Object successfully");
				}
			}
		}
	}
}
