package br.serratec.atividademusic.domain.entity;

import br.serratec.atividademusic.domain.enums.GeneroMusical;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Set;

@Entity
@Table(name = "musica")
public class Musica {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank(message = "O título da música é obrigatório")
	@Size(max = 100, message = "O título não pode exceder 100 caracteres")
	@Column(name = "titulo", length = 100, nullable = false)
	private String titulo;

	@Schema(description = "Duração da música em minutos (máximo 10 minutos).")
	@NotNull(message = "A duração em minutos é obrigatória")
	@Min(value = 1, message = "A música deve ter no mínimo 1 minuto")
	@Max(value = 10, message = "A música não pode ter mais de 10 minutos")
	@Column(name = "minutos", nullable = false)
	private Integer minutos;

	@Schema(description = "Gênero musical (Enum).")
	@NotNull(message = "O gênero musical é obrigatório")
	@Enumerated(EnumType.STRING)
	@Column(name = "genero", nullable = false)
	private GeneroMusical genero;

	@ManyToMany
	@JoinTable(name = "musica_artista", joinColumns = @JoinColumn(name = "musica_id"), inverseJoinColumns = @JoinColumn(name = "artista_id"))
	private Set<Artista> artistas;

	@ManyToMany(mappedBy = "musicas")
	private Set<Playlist> playlists;

	public Musica() {
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

	public Integer getMinutos() {
		return minutos;
	}

	public void setMinutos(Integer minutos) {
		this.minutos = minutos;
	}

	public GeneroMusical getGenero() {
		return genero;
	}

	public void setGenero(GeneroMusical genero) {
		this.genero = genero;
	}

	public Set<Artista> getArtistas() {
		return artistas;
	}

	public void setArtistas(Set<Artista> artistas) {
		this.artistas = artistas;
	}

	public Set<Playlist> getPlaylists() {
		return playlists;
	}

	public void setPlaylists(Set<Playlist> playlists) {
		this.playlists = playlists;
	}
}