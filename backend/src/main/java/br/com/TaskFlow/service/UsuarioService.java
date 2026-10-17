package br.com.TaskFlow.service;

import org.springframework.stereotype.Service;

import br.com.TaskFlow.dto.LoginDTO;
import br.com.TaskFlow.model.Usuario;
import br.com.TaskFlow.repository.UsuarioRepository;

@Service
public class UsuarioService {
	
	private final UsuarioRepository usuarioRepository;

	
	public UsuarioService(UsuarioRepository usuarioRepository) {
		this.usuarioRepository = usuarioRepository;
	}
	
	
	public Usuario verificarUsuario(LoginDTO loginDTO) {
		
		Usuario usuarioEncontrado = usuarioRepository.findByEmail(loginDTO.getEmail());
		
		if(usuarioEncontrado != null && usuarioEncontrado.getSenha().equals(loginDTO.getSenha())) {
			
			return new Usuario(usuarioEncontrado.getNome(), usuarioEncontrado.getEmail());
		}
		
		return null;
		
	}
	
	
	public boolean cadastrarUsuario(Usuario usuario) {
		
		boolean emailExistente = verificarEmail(usuario.getEmail());
		
		if(!emailExistente) {
			
			usuarioRepository.save(usuario);
			
			return true;
		}
		
		return false;
		
	}
	
	
	public boolean verificarEmail(String email) {
		
		Usuario usuarioExistente = usuarioRepository.findByEmail(email);
		
		if(usuarioExistente == null) {
			
			return false;
		}
		
		return true;
		
	}
	
	
	

}
