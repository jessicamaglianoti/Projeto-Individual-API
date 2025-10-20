package br.serratec.atividademusic.domain.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

@Schema(description = "DTO para Perfil (usado em requisições aninhadas).")
public class PerfilRequestDTO {
	@NotBlank(message = "O telefone é obrigatório")
	@Size(max = 20, message = "O telefone não pode exceder 20 caracteres")
	private String telefone;

	@NotNull(message = "A data de nascimento é obrigatória")
	@PastOrPresent(message = "A data de nascimento não pode ser futura")
	private LocalDate dataNascimento;


	public PerfilRequestDTO() {
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public LocalDate getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

}
