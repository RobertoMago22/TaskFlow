package br.com.TaskFlow.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.TaskFlow.model.Status;
import br.com.TaskFlow.model.Tarefa;
import br.com.TaskFlow.model.Usuario;
import br.com.TaskFlow.repository.TarefaRepository;
import br.com.TaskFlow.repository.UsuarioRepository;

@Service
public class TarefaService {
	
	private final TarefaRepository tarefaRepository;
	
	private final UsuarioRepository usurioRepository;
	
	
	public TarefaService(TarefaRepository tarefaRepository, UsuarioRepository usurioRepository) {
		
		this.tarefaRepository = tarefaRepository;
		this.usurioRepository = usurioRepository;
	}

	public void cadastrarTarefa(Tarefa tarefa, String email) {
		
		Usuario usuarioEncontrado = usurioRepository.findByEmail(email);
		
		usuarioEncontrado.getTarefas().add(tarefa);
		
		tarefa.setUsuario(usuarioEncontrado);
		
		usurioRepository.save(usuarioEncontrado);
	}
	
	public List<Tarefa> buscarAllTarefas(String email){
		
		
		return tarefaRepository.findByUsuarioEmail(email);
	}
	
	public boolean alterarStatus(Status status, Long id) {
		
		Optional<Tarefa> tarefaEncontrada = tarefaRepository.findById(id);
		
		if(tarefaEncontrada.isPresent()) {
			
			Tarefa tarefaAlterada = tarefaEncontrada.get();
			
			tarefaAlterada.setStatus(status);
			
			tarefaRepository.save(tarefaAlterada);
			
			return true;
		}
		
		return false;
	}
	
	public boolean alterarTarefa(Tarefa tarefa, Long id) {
		
		Optional<Tarefa> tarefaEncontrada = tarefaRepository.findById(id);
		
		if(tarefaEncontrada.isPresent()) {
			
			Tarefa tarefaAtualizada = tarefaEncontrada.get();
			
			tarefaAtualizada.setDescricao(tarefa.getDescricao());
			tarefaAtualizada.setPrazo(tarefa.getPrazo());
			tarefaAtualizada.setPrioridade(tarefa.getPrioridade());
			tarefaAtualizada.setStatus(tarefa.getStatus());
			tarefaAtualizada.setTitulo(tarefa.getTitulo());
			
			tarefaRepository.save(tarefaAtualizada);
			
			return true;
		}
		
		return false;
	}
	
	public boolean deletarTarefa(Long id) {
		
		Optional<Tarefa> tarefaEncontrada = tarefaRepository.findById(id);
		
		if(tarefaEncontrada.isPresent()) {
			
			tarefaRepository.delete(tarefaEncontrada.get());
			
			return true;
		}
		
		return false;
	}

}
