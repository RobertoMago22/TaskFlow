package br.com.TaskFlow.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.TaskFlow.model.Tarefa;

public interface TarefaRepository extends JpaRepository<Tarefa, Long> {
	
	List<Tarefa> findByUsuarioEmail(String email);

}
