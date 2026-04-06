package io.simpleweb.framework.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SimpleLogger {

	public static final String GREEN  = "\u001B[32m";
	public static final String YELLOW = "\u001B[35m";
	public static final String WHITE  = "\u001B[30m";
	//public static final String WHITE  = "\u001B[37m";
	public static final String RESET  = "\u001B[0m";
	
	public static DateTimeFormatter ISIDATE = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	
	
	public static void showBanner() {
		System.out.println(YELLOW);
		System.out.println("____       ____     _ _____            _                ____  ");  
		System.out.println("\\ \\ \\     /  _/____(_) ___/____  _____(_)___  ____ _    \\ \\ \\ ");
		System.out.println(" \\ \\ \\    / // ___/ /\\__ \\/ __ \\/ ___/ / __ \\/ __ `/     \\ \\ \\  IsiSpring Web Framework");
		System.out.println(" / / /  _/ /(__  ) /___/ / /_/ / /  / / / / / /_/ /      / / /  For Educational Purposes");
		System.out.println("/_/_/  /___/____/_//____/ .___/_/  /_/_/ /_/\\__, /      /_/_/   By Professor Isidro and IsiFLIX");
		System.out.println("                       /_/                 /____/             ");
		System.out.println(RESET);
	}
	public static void log(String modulo, String mensagem) {
		String date = LocalDateTime.now().format(ISIDATE);
		System.out.printf(GREEN+"%15s "+YELLOW+"%-30s:"+WHITE+"%s\n"+RESET, date, modulo, mensagem);
	}
}
