package br.com.isiflix.simplewebserver.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class WebLogger {
	

	public static final String GREEN  = "\u001B[32m";
	public static final String YELLOW = "\u001B[33m";
	public static final String WHITE  = "\u001B[37m";
	public static final String RED    = "\u001B[31m";
	public static final String RESET  = "\u001B[0m";
	
	public static DateTimeFormatter ISIDATE = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	
	public static void log(String message) {
		String date = LocalDateTime.now().format(ISIDATE);
		System.out.printf(GREEN+"%15s "+WHITE+"%s\n"+RESET, date, message);
	}
	
	public static void error(String message) {
		String date = LocalDateTime.now().format(ISIDATE);
		System.out.printf(GREEN+"%15s "+RED+"%s\n"+RESET, date, message);
	}

	public static void welcome() {
		System.out.println(YELLOW);
		System.out.println("    __  ___     _       __     __   _____                          ");
		System.out.println("   /  |/  /_  _| |     / /__  / /_ / ___/___  ______   _____  _____");
		System.out.println("  / /|_/ / / / / | /| / / _ \\/ __ \\\\__ \\/ _ \\/ ___/ | / / _ \\/ ___/");
		System.out.println(" / /  / / /_/ /| |/ |/ /  __/ /_/ /__/ /  __/ /   | |/ /  __/ /    ");
		System.out.println("/_/  /_/\\__, / |__/|__/\\___/_.___/____/\\___/_/    |___/\\___/_/     ");
		System.out.println("       /____/                                                      ");
		System.out.println(RESET);
	}
}
