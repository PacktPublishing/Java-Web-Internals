package br.com.simpleapp.webapp.service;

import io.simpleweb.framework.annotations.SimpleService;

@SimpleService
public class IServiceImplementation implements IService{

	@Override
	public String sayCustomMessage(String message) {
		// TODO Auto-generated method stub
		return "ISI:"+message;
	}
	

}
