package io.sas.simpleappserver.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class WebLogger {
	/*
	 * _____ _ __ __ / ___/___ ____| | / /__ / /___ _ \__ \/ _ \/ ___/ | /| / / _
	 * \__ / / __ `/ ___/ / __/ / | |/ |/ / __/ /_/ / /_/ / /____/\___/_/
	 * |__/|__/\___/\____/\__,_/
	 * 
	 */

	public static final String GREEN = "\u001B[32m";
	public static final String YELLOW = "\u001B[33m";
	public static final String WHITE = "\u001B[37m";
	public static final String RESET = "\u001B[0m";

	public static DateTimeFormatter ISIDATE = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	public static void welcome() {
		System.out.println(GREEN);
		System.out.println("   _____          _       __         __");
		System.out.println("  / ___/___  ____| |     / /__      / /___ _       By IsiFLIX");
		System.out.println("  \\__ \\/ _ \\/ ___/ | /| / / _ \\__  / / __ `/   For Educational Purposes");
		System.out.println(" ___/ /  __/ /   | |/ |/ /  __/ /_/ / /_/ /       WebServer 100% Java");
		System.out.println("/____/\\___/_/    |__/|__/\\___/\\____/\\__,_/");
	}

	public static void log(String message) {
		String date = LocalDateTime.now().format(ISIDATE);
		System.out.printf(YELLOW+"%15s :"+WHITE+"%s\n"+RESET, date, message);
	}

}
