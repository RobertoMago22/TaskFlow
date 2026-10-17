package br.com.TaskFlow.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.TaskFlow.dto.LoginDTO;
import br.com.TaskFlow.model.Usuario;
import br.com.TaskFlow.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class UsuarioController {
	
	private UsuarioService usuarioService;

	
	public UsuarioController(UsuarioService usuarioService) {
		
		this.usuarioService = usuarioService;
	}
	
	
	@PostMapping("/login")
	public ResponseEntity<?> verificarUsuario(@Valid @RequestBody LoginDTO loginDTO, 
											  BindingResult bindingResult, HttpSession session){
		
		if(bindingResult.hasErrors()) {
			
			Map<String, List<String>> erros = new HashMap<>();
			
			for(FieldError e : bindingResult.getFieldErrors()) {
				
				erros.computeIfAbsent(e.getField(), chave -> new ArrayList<>()).add(e.getDefaultMessage());
			}
			
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("invalido", erros));
		}
		
		Usuario resultado = usuarioService.verificarUsuario(loginDTO);
		
		if(resultado != null) {
			
			session.setAttribute("nome", resultado.getNome());
			session.setAttribute("email", resultado.getEmail());
			
			
			return ResponseEntity.status(HttpStatus.OK).body(Map.of("mensagem", "Login realizado com sucesso!"));
			
		}
		
		
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("mensagem", "Email e/ou Senha inválidos!"));
	}
	
	
	@PostMapping("/cadastro")
	public ResponseEntity<?> cadastrarUsuario(@Valid @RequestBody Usuario usuario, 
											  BindingResult bindingResult, HttpSession session){
		
		if(bindingResult.hasErrors()) {
			
			Map<String, List<String>> erros = new HashMap<>();
			
			for(FieldError e : bindingResult.getFieldErrors()) {
				
				erros.computeIfAbsent(e.getField(), chave -> new ArrayList<>()).add(e.getDefaultMessage());
			}
			
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("invalido", erros));
		}
		
		boolean resultado = usuarioService.cadastrarUsuario(usuario);
		
		if(resultado) {
			
			return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("mensagem", "Cadastro realizado com sucesso!"));
		}
		
		return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("mensagem", "E-mail já cadastrado!"));
		
		
	}

}
