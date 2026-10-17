package br.com.TaskFlow.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "tarefa")
public class Tarefa {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message = "O titulo é obrigatório!")
	@Column(nullable = false)
	private String titulo;
	
	private String descricao;
	
	//@NotNull(message = "A prioridade é obrigatória!")
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private Prioridade prioridade;
	
	//@NotNull(message = "O status é obrigatório!")
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private Status status;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate prazo;
	
	@ManyToOne
	@JoinColumn(name = "usuario_id")
	@JsonIgnore
	private Usuario usuario;
	
	
	public Tarefa() {
		
		
	}

	
	public Tarefa(@NotBlank(message = "O titulo é obrigatório!") 
				  String titulo, 
				  String descricao,
				  //@NotNull(message = "A prioridade é obrigatória!") 
				  Prioridade prioridade,
				  //@NotNull(message = "O status é obrigatório!") 
				  Status status, 
				  LocalDate prazo) {
		
		this.titulo = titulo;
		this.descricao = descricao;
		this.prioridade = prioridade;
		this.status = status;
		this.prazo = prazo;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getTitulo() {
		return titulo;
	}


	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}


	public String getDescricao() {
		return descricao;
	}


	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}


	public Prioridade getPrioridade() {
		return prioridade;
	}


	public void setPrioridade(Prioridade prioridade) {
		this.prioridade = prioridade;
	}


	public Status getStatus() {
		return status;
	}


	public void setStatus(Status status) {
		this.status = status;
	}


	public LocalDate getPrazo() {
		return prazo;
	}


	public void setPrazo(LocalDate prazo) {
		this.prazo = prazo;
	}


	public Usuario getUsuario() {
		return usuario;
	}


	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	
	

}
