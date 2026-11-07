package io.simpleweb.framework.explorer;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ClassExplorer {

	public static List<String> retrieveAllClasses(Class<?> sourceClass){
		return packageExplorer(sourceClass.getPackageName());
	}
	
	public static List<String> packageExplorer(String packageName){
		ArrayList<String> classNames = new ArrayList<String>();
		try {
			InputStream stream = ClassLoader.getSystemClassLoader().getResourceAsStream(packageName.replaceAll("\\.", "/"));
			BufferedReader br = new BufferedReader(new InputStreamReader(stream));
			String linha;
			while ((linha = br.readLine())!= null) {
				if (linha.endsWith(".class")) {
					classNames.add(packageName+ "." +linha.substring(0, linha.indexOf(".class")));	
				}
				else {
					classNames.addAll(packageExplorer(packageName+"."+linha));
				}
			}
			return classNames;
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}		
		return null;
	}
}
