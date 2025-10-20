package br.serratec.atividademusic.domain.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity
@Table(name = "usuario")
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank(message = "O nome é obrigatório")
	@Size(max = 100, message = "O nome não pode exceder 100 caracteres")
	@Column(name = "nome", length = 100, nullable = false)
	private String nome;

	@NotBlank(message = "O email é obrigatório")
	@Email(message = "O email deve ser válido")
	@Size(max = 100, message = "O email não pode exceder 100 caracteres")
	@Column(name = "email", length = 100, nullable = false, unique = true)
	private String email;

	@Schema(description = "Detalhes do perfil (data de nascimento e telefone).")
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "perfil_id", referencedColumnName = "id")
	private Perfil perfil;

	@OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Playlist> playlists;

	public Usuario() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Perfil getPerfil() {
		return perfil;
	}

	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}

	public List<Playlist> getPlaylists() {
		return playlists;
	}

	public void setPlaylists(List<Playlist> playlists) {
		this.playlists = playlists;
	}
}