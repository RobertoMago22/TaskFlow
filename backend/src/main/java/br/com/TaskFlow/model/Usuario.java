package br.com.TaskFlow.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "usuario")
public class Usuario {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message = "O email é obrigatorio!")
	@Column(unique = true, nullable = false)
	@Email
	private String email;
	
	@NotBlank(message = "A senha é obrigatorio!")
	@Column(nullable = false)
	private String senha;
	
	@NotBlank(message = "O nome é obrigatorio!")
	@Column(nullable = false)
	private String nome;
	
	@OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
	private List<Tarefa> tarefas = new ArrayList<>();

	
	public Usuario() {
		
	}
	
	
	public Usuario(@NotBlank(message = "O nome é obrigatorio!") 
				   String nome,
				   @NotBlank(message = "O email é obrigatorio!") 
	   			   @Email 
	   			   String email) {
		
		this.nome = nome;
		this.email = email;
	}

	
	/*public Usuario(@NotBlank(message = "O email é obrigatorio!") 
				   @Email 
				   String email,
				   @NotBlank(message = "A senha é obrigatorio!") 
				   String senha) {
		
		this.email = email;
		this.senha = senha;
	}*/
	
	public Usuario(@NotBlank(message = "O email é obrigatorio!") 
				   @Email 
				   String email) {
		
		this.email = email;
	}
	

	public Usuario(@NotBlank(message = "O email é obrigatorio!") 
				   @Email 
				   String email,
				   @NotBlank(message = "A senha é obrigatorio!") 
				   String senha, 
				   @NotBlank(message = "O nome é obrigatorio!") 
				   String nome) {
		
		this.email = email;
		this.senha = senha;
		this.nome = nome;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getSenha() {
		return senha;
	}


	public void setSenha(String senha) {
		this.senha = senha;
	}


	public String getNome() {
		return nome;
	}


	public void setNome(String nome) {
		this.nome = nome;
	}


	public List<Tarefa> getTarefas() {
		return tarefas;
	}


	public void setTarefas(List<Tarefa> tarefas) {
		this.tarefas = tarefas;
	}
	
	
	

}
