package io.simpleweb.framework.web;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;

import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.startup.Tomcat;

import io.simpleweb.framework.annotations.SimpleGetMethod;
import io.simpleweb.framework.annotations.SimplePostMethod;
import io.simpleweb.framework.datastructures.ControllersMap;
import io.simpleweb.framework.datastructures.RequestControllerData;
import io.simpleweb.framework.datastructures.ServiceImplementationMap;
import io.simpleweb.framework.explorer.ClassExplorer;
import io.simpleweb.framework.util.SimpleLogger;

public class SimpleSpringWebApplication {
	public static void run(Class<?> sourceClass) {

		// zerar o logo do apache tomcat
		java.util.logging.Logger.getLogger("org.apache").setLevel(java.util.logging.Level.OFF);
		long ini, fim;
		SimpleLogger.showBanner();
		try {
			ini = System.currentTimeMillis();
			SimpleLogger.log("Embeded Web Container", "Starting " + sourceClass.getSimpleName());

			extractMetaData(sourceClass);

			Tomcat tomcat = new Tomcat();
			Connector connector = new Connector();
			connector.setPort(8080);
			SimpleLogger.log("Embeded Web Container", "Web Container started on port 8080");
			tomcat.setConnector(connector);

			Context ctx = tomcat.addContext("", new File(".").getAbsolutePath());
			Tomcat.addServlet(ctx, "SimpleDispatchServlet", new SimpleDispatchServlet());

			ctx.addServletMappingDecoded("/*", "SimpleDispatchServlet");

			tomcat.start();
			fim = System.currentTimeMillis();
			SimpleLogger.log("Embeded Web Container",
					sourceClass.getSimpleName() + " started in " + ((double) (fim - ini) / 1000) + " seconds");
			tomcat.getServer().await();

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private static void extractMetaData(Class<?> sourceClass) throws Exception {

		List<String> allClasses = ClassExplorer.retrieveAllClasses(sourceClass);
		for (String isiClass : allClasses) {
			// recupero as anotações da classe
			Annotation annotations[] = Class.forName(isiClass).getAnnotations();
			for (Annotation classAnnotation : annotations) {
				if (classAnnotation.annotationType().getName().equals("io.simpleweb.framework.annotations.SimpleController")) {
					SimpleLogger.log("Metadata Explorer", "Found a Controller " + isiClass);
					extractMethod(isiClass);
				}
				else if (classAnnotation.annotationType().getName().equals("io.simpleweb.framework.annotations.SimpleService")) {
					SimpleLogger.log("Metadata Explorer", "Found a Service Implmentation "+isiClass);					
					
					for (Class<?> interf: Class.forName(isiClass).getInterfaces()) {
						SimpleLogger.log("Metadata Explorer","    Class implements "+ interf.getName());
						ServiceImplementationMap.implementations.put(interf.getName(), isiClass);						
					}
				}
			}

		}
	}

	private static void extractMethod(String className) throws Exception {
		String httpMethod = "";
		String path = "";
		for (Method method : Class.forName(className).getDeclaredMethods()) {
			for (Annotation annotation : method.getAnnotations()) {
				if (annotation.annotationType().getName().equals("io.simpleweb.framework.annotations.SimpleGetMethod")) {
					path = ((SimpleGetMethod) annotation).value();
					httpMethod = "GET";

				} else if (annotation.annotationType().getName()
						.equals("io.simpleweb.framework.annotations.SimplePostMethod")) {
					path = ((SimplePostMethod) annotation).value();
					
					httpMethod = "POST";
				}
				RequestControllerData getData = new RequestControllerData(httpMethod, path, className,
						method.getName());
				ControllersMap.values.put(httpMethod + path, getData);
			}
		}
	}

}
