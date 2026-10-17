package br.com.TaskFlow.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class LoginDTO {
	
	@NotBlank(message = "O email é obrigatorio!")
	@Email
	private String email;
	
	@NotBlank(message = "A senha é obrigatorio!")
	private String senha;
	

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
}
