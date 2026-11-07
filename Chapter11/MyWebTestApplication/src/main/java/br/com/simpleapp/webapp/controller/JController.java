package br.com.simpleapp.webapp.controller;

import io.simpleweb.framework.annotations.SimpleController;
import io.simpleweb.framework.annotations.SimpleGetMethod;

@SimpleController
public class JController {
	
	@SimpleGetMethod("/jchampionsconf")
	public String sayHelloToJChampions() {
		return "Hello World from JChampions Conference";
	}

}
