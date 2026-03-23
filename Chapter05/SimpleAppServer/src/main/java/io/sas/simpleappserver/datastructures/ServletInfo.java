package io.sas.simpleappserver.datastructures;

public class ServletInfo {
	private String path;
	private String method;
	private Class<?>  classInfo;
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public Class<?> getClassInfo() {
		return classInfo;
	}
	public void setClassInfo(Class<?> classInfo) {
		this.classInfo = classInfo;
	}
}
