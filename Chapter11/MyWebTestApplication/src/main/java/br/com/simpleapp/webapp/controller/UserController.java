package br.com.simpleapp.webapp.controller;

import br.com.simpleapp.webapp.model.User;
import io.simpleweb.framework.annotations.SimpleBody;
import io.simpleweb.framework.annotations.SimpleController;
import io.simpleweb.framework.annotations.SimpleGetMethod;
import io.simpleweb.framework.annotations.SimplePostMethod;

@SimpleController
public class UserController {
	
	@SimpleGetMethod("/user")
	public User showUser() {
		User u = new User();
		u.setUsername("admin");
		u.setPassword("1234");
		u.setLevel(0);
		return u;
	}

	
	@SimplePostMethod("/user")
	public User addNew(@SimpleBody User newUser) {
		System.out.println("New user added");
		System.out.println(newUser);
		return newUser;
	}
}
