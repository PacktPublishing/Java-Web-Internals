package io.sas.simpleappserver.util;

import java.nio.file.Path;
import java.util.HashMap;

/**
 * The `WebConfig` class holds configuration constants and mappings for the web application.
 */
public class WebConfig {
    public static final Path ROOT = Path.of(System.getProperty("user.dir"));
    public static final String DOCUMENT_FOLDER = ROOT + "/webapps";
    public static final String DOC_ROOT = "/SMP-FLD";
    public static final String APP_ROOT = "/WEB-FLD";

    public static final HashMap<String, String> content = new HashMap<>() {{
        put("html", "text/html");
        put("htm", "text/html");
        put("jpg", "image/jpg");
        put("png", "image/png");
        put("jpeg", "image/jpeg");
        put("txt", "text/plain");
        put("ico", "image/icon");
        put("js", "text/javascript");
        put("css", "text/css");
    }};

    public static final HashMap<Integer, String> textCodes = new HashMap<>() {{
        put(200, "Ok");
        put(400, "Bad Request");
        put(404, "Not Found");
        put(403, "Forbidden");
        put(401, "Unauthorized");
        put(405, "Method Not Allowed");
        put(500, "Internal Server Error");
    }};
}