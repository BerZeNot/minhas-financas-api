package com.pgoliveira.minhasfinancas.api.resource;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pgoliveira.minhasfinancas.api.dto.UsuarioDTO;
import com.pgoliveira.minhasfinancas.exception.ErroAutenticacao;
import com.pgoliveira.minhasfinancas.exception.RegraNegocioException;
import com.pgoliveira.minhasfinancas.model.entity.Usuario;
import com.pgoliveira.minhasfinancas.service.UsuarioService;

@RestController
@RequestMapping("/api/usuarios")
@SuppressWarnings("rawtypes")
public class UsuarioResource {
	
	private UsuarioService service;
	
	public UsuarioResource(UsuarioService service) {
		this.service = service;
	}
	
	@PostMapping("/autenticar")
	public ResponseEntity autenticar( @RequestBody UsuarioDTO dto ){
		try {
			Usuario usuarioAutenticado =  service.autenticar(dto.getEmail(), dto.getSenha());
			return ResponseEntity.ok(usuarioAutenticado);
		} catch (ErroAutenticacao e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@PostMapping
	@SuppressWarnings("unchecked")
	public ResponseEntity salvar( @RequestBody UsuarioDTO dto ){
		
		Usuario usuario = Usuario.builder()
				.nome(dto.getNome())
				.email(dto.getEmail())
				.senha(dto.getSenha()).build();
		
		try {
			Usuario usuarioSalvo = service.salvarUsuario(usuario);
			return new ResponseEntity(usuarioSalvo, HttpStatus.CREATED);
		} catch (RegraNegocioException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
}
