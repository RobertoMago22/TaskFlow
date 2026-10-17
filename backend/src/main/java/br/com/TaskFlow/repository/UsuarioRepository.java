package br.com.TaskFlow.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.TaskFlow.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	
	Usuario findByEmail(String email);

}
