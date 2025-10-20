package br.serratec.atividademusic.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Set;

@Entity
@Table(name = "playlist")
public class Playlist {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank(message = "O nome da playlist é obrigatório")
	@Size(max = 100, message = "O nome não pode exceder 100 caracteres")
	@Column(name = "nome", length = 100, nullable = false)
	private String nome;

	@NotNull(message = "A playlist deve pertencer a um usuário")
	@ManyToOne
	@JoinColumn(name = "usuario_id", nullable = false)
	private Usuario usuario;

	@ManyToMany
	@JoinTable(name = "playlist_musica", joinColumns = @JoinColumn(name = "playlist_id"), inverseJoinColumns = @JoinColumn(name = "musica_id"))
	private Set<Musica> musicas;

	
	public Playlist() {
	}

	public Playlist(Long id, String nome, Usuario usuario, Set<Musica> musicas) {
		this.id = id;
		this.nome = nome;
		this.usuario = usuario;
		this.musicas = musicas;
	}

	// Getters e Setters
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

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Set<Musica> getMusicas() {
		return musicas;
	}

	public void setMusicas(Set<Musica> musicas) {
		this.musicas = musicas;
	}
}
