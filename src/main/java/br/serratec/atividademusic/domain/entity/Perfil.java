package br.serratec.atividademusic.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

@Entity
@Table(name = "perfil")
public class Perfil {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank(message = "O telefone é obrigatório")
	@Size(max = 20, message = "O telefone não pode exceder 20 caracteres")
	@Column(name = "telefone", length = 20, nullable = false)
	private String telefone;

	@NotNull(message = "A data de nascimento é obrigatória")
	@PastOrPresent(message = "A data de nascimento não pode ser futura")
	@Column(name = "data_nascimento", nullable = false)
	private LocalDate dataNascimento;

	@OneToOne(mappedBy = "perfil")
	private Usuario usuario;

	public Perfil() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}
