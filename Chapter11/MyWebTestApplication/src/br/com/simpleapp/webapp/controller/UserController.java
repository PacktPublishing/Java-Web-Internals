package br.com.isiflix.webtestapp.controller;

import br.com.isiflix.webtestapp.model.Usuario;
import io.isiflix.isispring.annotations.IsiBody;
import io.isiflix.isispring.annotations.IsiController;
import io.isiflix.isispring.annotations.IsiGetMethod;
import io.isiflix.isispring.annotations.IsiPostMethod;

@IsiController
public class UsuarioController {
	
	@IsiGetMethod("/usuario")
	public Usuario exibirUsuario() {
		Usuario u = new Usuario();
		u.setUsername("admin");
		u.setPassword("1234");
		u.setLevel(0);
		return u;
	}

	
	@IsiPostMethod("/usuario")
	public Usuario addNew(@IsiBody Usuario novo) {
		System.out.println("Novo usuario inserido");
		System.out.println(novo);
		return novo;
	}
}
