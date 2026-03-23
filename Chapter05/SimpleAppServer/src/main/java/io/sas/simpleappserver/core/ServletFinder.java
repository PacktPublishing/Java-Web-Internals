package io.sas.simpleappserver.core;


import java.io.File;
import java.io.FileInputStream;
import java.lang.annotation.Annotation;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

import io.ee.serweja.annotations.IsiServlet;
import io.ee.serweja.http.IsiHttpServlet;
import io.isiflix.serweja.util.WebConfig;
import io.isiflix.serweja.util.WebLogger;


/**
 * The `ServletFinder` class is responsible for finding and loading servlets
 * annotated with `@IsiServlet` from the specified class folder and JAR files.
 */
public class ServletFinder {

    private static URLClassLoader loader;

    static {
        try {
            String classFolder = WebConfig.DOCUMENT_FOLDER + WebConfig.APP_ROOT;
            File folderFile = new File(classFolder);
            List<URL> urls = new ArrayList<>();
            urls.add(folderFile.toURI().toURL());
            File[] files = folderFile.listFiles((dir, name) -> name.endsWith(".jar"));

            if (files != null) {
                for (File file : files) {
                    urls.add(file.toURI().toURL());
                }
            }
            loader = new URLClassLoader(urls.toArray(new URL[0]), ServletFinder.class.getClassLoader());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Finds and returns an instance of `IsiHttpServlet` that matches the given path.
     *
     * @param path The request path to match against the `@IsiServlet` annotation.
     * @return An instance of `IsiHttpServlet` if a matching servlet is found, otherwise null.
     */
    public static IsiHttpServlet findServlet(String path) {
        try {
            String classFolder = WebConfig.DOCUMENT_FOLDER + WebConfig.APP_ROOT;
            File[] files = new File(classFolder).listFiles();

            for (File file : files) {

                if (file.getName().endsWith(".jar")) {
                    try (JarInputStream jarStream = new JarInputStream(new FileInputStream(file))) {
                        JarEntry entry;

                        while ((entry = jarStream.getNextJarEntry()) != null) {
                            if (entry.getName().endsWith(".class")) {
                                String className = entry.getName()
                                        .replace("/", ".")
                                        .replace(".class", "");
                                Class<?> classRef = loader.loadClass(className);

                                for (Annotation an : classRef.getDeclaredAnnotations()) {
                                    WebLogger.log("     + New Annotation: " + an.annotationType().getName());
                                    if (an.annotationType().getName().equals("io.ee.serweja.annotations.IsiServlet")) {
                                        IsiServlet srvAn = (IsiServlet) an;
                                        WebLogger.log("     + Request Path: " + srvAn.path());
                                        if (srvAn.path().equals(path)) {
                                            return (IsiHttpServlet) classRef.getDeclaredConstructor().newInstance();
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                if (file.getName().endsWith(".class")) {
                    WebLogger.log("Found Class - " + file.getName());
                    String className = file.getName().substring(0, file.getName().indexOf(".class"));
                    Class<?> classRef = loader.loadClass(className);

                    for (Annotation an : classRef.getDeclaredAnnotations()) {
                        WebLogger.log("     + New Annotation: " + an.annotationType().getName());
                        if (an.annotationType().getName().equals("io.ee.serweja.annotations.IsiServlet")) {
                            IsiServlet srvAn = (IsiServlet) an;
                            WebLogger.log("     + Request Path: " + srvAn.path());
                            if (srvAn.path().equals(path)) {
                                return (IsiHttpServlet) classRef.getDeclaredConstructor().newInstance();
                            }
                        }
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}