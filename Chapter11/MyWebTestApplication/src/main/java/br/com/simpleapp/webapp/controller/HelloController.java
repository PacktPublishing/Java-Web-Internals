package br.com.simpleapp.webapp.controller;

import br.com.simpleapp.webapp.model.Product;
import br.com.simpleapp.webapp.service.IService;
import io.simpleweb.framework.annotations.SimpleBody;
import io.simpleweb.framework.annotations.SimpleController;
import io.simpleweb.framework.annotations.SimpleGetMethod;
import io.simpleweb.framework.annotations.SimpleInjected;
import io.simpleweb.framework.annotations.SimplePostMethod;

@SimpleController
public class HelloController {
	
	@SimpleInjected IService service;
	
	@SimpleGetMethod("/hello")
	public String sayHelloWorld() {
		return "Hello World";
	}

	@SimpleGetMethod("/test")
	public String sayTeste() {
		return "it Works!";
	}
	
	@SimpleGetMethod("/product")
	public Product getProduct() {
		Product p = new Product();
		p.setId(123456);
		p.setName("Computer");
		p.setPrice(2500.00);
		p.setLinkPhoto("computer.jpg");
		return p;
	}
	
	@SimplePostMethod("/product")
	public Product addNewProduct(@SimpleBody Product newProduct) {
		System.out.println(newProduct);
		return newProduct;
		
	}
	
	@SimpleGetMethod("/injected")
	public String sayCustomMessage() {
		return service.sayCustomMessage("Hello World");
	}
	
}
