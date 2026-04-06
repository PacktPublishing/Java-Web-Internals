package br.com.simpleapp.webappcontroller;

import br.com.simpleapp.webappmodel.Produto;
import br.com.simpleapp.webappservice.IService;
import io.simpleweb.frameworkannotations.SimpleBody;
import io.simpleweb.frameworkannotations.SimpleController;
import io.simpleweb.frameworkannotations.SimpleGetMethod;
import io.simpleweb.frameworkannotations.SimpleInjected;
import io.simpleweb.frameworkannotations.SimplePostMethod;

@SimpleController
public class HelloController {
	
	@SimpleInjected SimpleService service;
	
	@SimpleGetMethod("/hello")
	public String sayHelloWorld() {
		return "Hello World";
	}

	@SimpleGetMethod("/test")
	public String sayTeste() {
		return "it Works!";
	}
	
	@SimpleGetMethod("/produto")
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
