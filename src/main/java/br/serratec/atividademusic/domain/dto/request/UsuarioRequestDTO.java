package br.serratec.atividademusic.domain.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Schema(description = "DTO para criação de Usuário (inclui Perfil aninhado).")
public class UsuarioRequestDTO {
	@NotBlank(message = "O nome é obrigatório")
	@Size(max = 100, message = "O nome não pode exceder 100 caracteres")
	private String nome;

	@NotBlank(message = "O email é obrigatório")
	@Email(message = "O email deve ser válido")
	@Size(max = 100, message = "O email não pode exceder 100 caracteres")
	private String email;

	@NotNull(message = "O perfil (data de nascimento e telefone) é obrigatório")
	@Valid
	private PerfilRequestDTO perfil;


	public UsuarioRequestDTO() {
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public PerfilRequestDTO getPerfil() {
		return perfil;
	}

	public void setPerfil(PerfilRequestDTO perfil) {
		this.perfil = perfil;
	}
}
