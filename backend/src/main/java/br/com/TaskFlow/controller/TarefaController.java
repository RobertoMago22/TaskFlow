package br.com.TaskFlow.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.TaskFlow.model.Status;
import br.com.TaskFlow.model.Tarefa;
import br.com.TaskFlow.service.TarefaService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/tarefas")
public class TarefaController {
	
	private TarefaService tarefaService;
	
	
	public TarefaController(TarefaService tarefaService) {
		this.tarefaService = tarefaService;
	}
	

	@PostMapping
	public ResponseEntity<?> cadastrarTarefa(@Valid @RequestBody Tarefa tarefa, 
											 BindingResult bindingResult, HttpSession session){
		
		if(bindingResult.hasErrors()) {
			
			Map<String, List<String>> erros = new HashMap<>();
			
			for(FieldError e : bindingResult.getFieldErrors()) {
				
				erros.computeIfAbsent(e.getField(), chave -> new ArrayList<>()).add(e.getDefaultMessage());
			}
			
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("invalido", erros));
		}
		
		String email = (String) session.getAttribute("email");
		
		
		if(email != null) {
			
			tarefaService.cadastrarTarefa(tarefa, email);
			
			return ResponseEntity.status(HttpStatus.CREATED).body(tarefaService.buscarAllTarefas(email));
		}
		
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("mensagem", "Usuário não autorizado "
																		   + "ou não logado!" ));
	}
	
	@GetMapping
	public ResponseEntity<?> listaTarefas(HttpSession session){
		
		String email = (String) session.getAttribute("email");
		
		return ResponseEntity.status(HttpStatus.OK).body(Map.of("tarefas", 
																tarefaService.buscarAllTarefas(email)));
	}
	
	@PatchMapping("{id}/status")
	public ResponseEntity<?> alterarStatus(@PathVariable Long id, @RequestBody Status status, 
										   BindingResult bindingResult, HttpSession session){
		
		if(bindingResult.hasErrors()) {
			
			Map<String, List<String>> erros = new HashMap<>();
			
			for(FieldError e : bindingResult.getFieldErrors()) {
				
				erros.computeIfAbsent(e.getField(), chave -> new ArrayList<>()).add(e.getDefaultMessage());
			}
			
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("invalido", erros));
		}
		
		String email = (String) session.getAttribute("email");
		
		if(email != null) {
			
			boolean resultado = tarefaService.alterarStatus(status, id);
			
			if(resultado) {
				
				return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
			}
			
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("mensagem", "Usuário não autorizado "
				   + "ou não logado!" ));
		
	}
	
	@PutMapping("{id}")
	public ResponseEntity<?> atualizarTarefa(@PathVariable Long id, @RequestBody Tarefa tarefa, 
											 BindingResult bindingResult, HttpSession session){
		
		if(bindingResult.hasErrors()) {
			
			Map<String, List<String>> erros = new HashMap<>();
			
			for(FieldError e : bindingResult.getFieldErrors()) {
				
				erros.computeIfAbsent(e.getField(), chave -> new ArrayList<>()).add(e.getDefaultMessage());
			}
			
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("invalido", erros));
		}
		
		String email = (String) session.getAttribute("email");
		
		if(email != null) {
			
			boolean resultado = tarefaService.alterarTarefa(tarefa, id);
			
			if(resultado) {
				
				return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
			}
			
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("mensagem", "Usuário não autorizado "
				   + "ou não logado!"));
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<?> deletarTarefa(@PathVariable Long id, HttpSession session) {
		
		String email = (String) session.getAttribute("email");
		
		if(email != null) {
			
			boolean resultado = tarefaService.deletarTarefa(id);
			
			if(resultado) {
				
				return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
			}
			
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("mensagem", "Usuário não autorizado "
				   + "ou não logado!"));
		
	}
	
	
	@GetMapping("/usuario")
	public ResponseEntity<?> nomeUsuario(HttpSession session){
		
		String nome = (String) session.getAttribute("nome");
		
		if(nome != null) {
			
			return ResponseEntity.status(HttpStatus.OK).body(Map.of("nome", nome));
		}
		
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	
	}
	
	@PostMapping("/logout")
	public ResponseEntity<?> logout(HttpSession session){
		
		session.invalidate();
		
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
	

}
