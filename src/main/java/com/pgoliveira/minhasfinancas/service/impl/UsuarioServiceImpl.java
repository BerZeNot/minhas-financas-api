package com.pgoliveira.minhasfinancas.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pgoliveira.minhasfinancas.exception.ErroAutenticacao;
import com.pgoliveira.minhasfinancas.exception.RegraNegocioException;
import com.pgoliveira.minhasfinancas.model.entity.Usuario;
import com.pgoliveira.minhasfinancas.model.repository.UsuarioRepository;
import com.pgoliveira.minhasfinancas.service.UsuarioService;

@Service
public class UsuarioServiceImpl implements UsuarioService{
	
	private UsuarioRepository repository;
	private PasswordEncoder encoder;
	
	@Autowired
	public UsuarioServiceImpl(UsuarioRepository repository, PasswordEncoder encoder) {
		super();
		this.repository = repository;
		this.encoder = encoder;
	}

	
	@Override
	public Usuario autenticar(String email, String senha) {
		Optional<Usuario> usuario = repository.findByEmail(email);
		if(!usuario.isPresent()) {
			throw new ErroAutenticacao("Usuário não encontrado para o email informado.");
		}
		
		boolean senhaCorreta = encoder.matches(senha, usuario.get().getSenha());
		
		if(!senhaCorreta) {
			throw new ErroAutenticacao("Senha inválida.");
		}
		
		return usuario.get();
	}

	@Override
	@Transactional
	public Usuario salvarUsuario(Usuario usuario) {
		validarEmail(usuario.getEmail());
		criptografarSenha(usuario);
		return repository.save(usuario);
	}

	private void criptografarSenha(Usuario usuario) {
		usuario.setSenha(encoder.encode(usuario.getSenha()));		
	}


	@Override
	public void validarEmail(String email) {
		boolean existe = repository.existsByEmail(email);
		if(existe) {
			throw new RegraNegocioException("Já existe um usuário cadastrado com este email.");
		}
	}


	@Override
	public Optional<Usuario> obterPorId(Long id) {
		return repository.findById(id);
	}

}
