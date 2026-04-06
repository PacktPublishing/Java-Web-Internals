package br.com.isiflix.webtestapp.controller;

import io.isiflix.isispring.annotations.IsiController;
import io.isiflix.isispring.annotations.IsiGetMethod;

@IsiController
public class JController {
	
	@IsiGetMethod("/jchampionsconf")
	public String sayHelloToJChampions() {
		return "Hello World from JChampions Conference";
	}

}
